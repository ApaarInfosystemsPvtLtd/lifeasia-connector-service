package com.pmli.lifeasia.connector;

import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.pmli.lifeasia.initialize.MasterStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MQAgentConnector {

    public static final String EXCEPTION_IN_MQAGENT_CONNECTOR = "Exception in MQAgentConnector1::{}";

    @Autowired
    MasterStore masterStore;

    MQQueueManager mqQueueManager;
    private Logger logger = LoggerFactory.getLogger(MQAgentConnector.class);
    MQAgentConnector mqAgentConnector = null;

    public MQAgentConnector() {
        try {
            MQEnvironment.properties.put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT);
        } catch (Exception e) {
            logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
        }
    }

    public MQAgentConnector(String host, int port, String user, String password, String channelName) {
        MQEnvironment.properties.put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT);
        MQEnvironment.hostname = host.trim();
        MQEnvironment.port = port;
        MQEnvironment.channel = channelName.trim();
        MQEnvironment.userID = user.trim();
        MQEnvironment.password = password.trim();
    }

    public void connectToQM(String QmanagerName) {
        try {
            this.mqQueueManager = new MQQueueManager(QmanagerName.trim());
        } catch (Exception e) {
            logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
        }
    }

    public boolean isMqConnected() {
        if (this.mqQueueManager == null)
            return false;
        boolean status = this.mqQueueManager.isConnected();
        return status;
    }

    public String getQueueName() throws MQException {
        return this.mqQueueManager.getName();
    }

    public void disconnectFromMQ() {
        if (this.mqQueueManager != null && this.mqQueueManager.isConnected()) {
            try {
                this.mqQueueManager.disconnect();
            } catch (Exception e) {
                logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
            }
        }
    }

    public MQAgentConnector getInputMQ() {
        logger.info("getInputMQ::propertyReader::{}",masterStore.toJson());
        try {
            mqAgentConnector = new MQAgentConnector(masterStore.getConnectionMap().get("host"),
                    Integer.parseInt(masterStore.getConnectionMap().get("port")),
                    masterStore.getConnectionMap().get("user"),
                    masterStore.getConnectionMap().get("password"),
                    masterStore.getConnectionMap().get("channel"));

        } catch (Exception e) {
            logger.error("GetInputQueue::{}", Arrays.asList(e.getStackTrace()));
        }
        return mqAgentConnector;
    }

    public MQAgentConnector getOutputMQ() {
        logger.info("getOutputMQ::propertyReader::{}",masterStore.toJson());
        try {
            mqAgentConnector = new MQAgentConnector(masterStore.getConnectionMap().get("host"),
                    Integer.parseInt(masterStore.getConnectionMap().get("port")),
                    masterStore.getConnectionMap().get("user"),
                    masterStore.getConnectionMap().get("password"),
                    masterStore.getConnectionMap().get("channel"));

        } catch (Exception e) {
            logger.error("GetOutputQueue::{}", Arrays.asList(e.getStackTrace()));
        }
        return mqAgentConnector;
    }

    public byte[] putMessage(String toQueueName, String msgData, String replayToQueueName, String replayToMQ) {
        MQMessage mqMessage = new MQMessage();
        try {
            mqMessage.messageType = CMQC.MQMT_DATAGRAM;
            mqMessage.format = CMQC.MQFMT_STRING;
            mqMessage.persistence = 1;
            mqMessage.replyToQueueManagerName = replayToQueueName;
            mqMessage.replyToQueueName = replayToMQ;
            //mqMessage.replyToQueueManagerName =replayToMQ ;
            //mqMessage.replyToQueueName = replayToQueueName;
            mqMessage.putApplicationName = "";
            mqMessage.writeString(msgData);
            MQPutMessageOptions mqPutMessageoption = new MQPutMessageOptions();
            mqPutMessageoption.options = CMQC.MQPMO_NEW_MSG_ID | CMQC.MQPMO_NEW_CORREL_ID;
            this.mqQueueManager.put(toQueueName, this.getQueueName(), mqMessage, mqPutMessageoption);
        } catch (Exception e) {
            logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
        }
        return mqMessage.messageId;
    }

    public String sendMessage(String message) {
        byte[] output = null;
        try {
            MQAgentConnector manager = getInputMQ();
            manager.connectToQM(masterStore.getConnectionMap().get("queuemanager"));
            output = manager.putMessage(masterStore.getConnectionMap().get("inputqueuename"),
                    message,
                    masterStore.getConnectionMap().get("outputqueuename"),
                    masterStore.getConnectionMap().get("queuemanager")
            );
            try {
                manager.mqQueueManager.commit();
            } catch (MQException e) {
                logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
            }
        } catch (Exception e) {
            logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
        }
        String corelationID = new String(output);
        String responseMessage = receiveMessage(output);
        return responseMessage;
    }

    public String getMessage(String fromQueueName, byte[] messageId, int waitInterval) {
        byte[] outPutMessage = null;
        String returnMsg = null;
        MQMessage msg = new MQMessage();
        MQGetMessageOptions getMessageOptions = new MQGetMessageOptions();
        getMessageOptions.options = CMQC.MQGMO_COMPLETE_MSG | CMQC.MQGMO_WAIT | CMQC.MQGMO_CONVERT;
        if (waitInterval == 0)
            getMessageOptions.waitInterval = CMQC.MQWI_UNLIMITED;
        else
            getMessageOptions.waitInterval = waitInterval;

        getMessageOptions.matchOptions = CMQC.MQMO_MATCH_MSG_ID;
        msg.messageId = messageId;
        msg.characterSet = CMQC.MQCCSI_Q_MGR;
        try {
            MQQueue mqQueue = new MQQueue(this.mqQueueManager
                    , fromQueueName
                    , 1
                    , null
                    , null
                    , null);
            mqQueue.get(msg, getMessageOptions);
            outPutMessage = new byte[msg.getTotalMessageLength()];
            msg.readFully(outPutMessage);
            returnMsg = new String(outPutMessage, "UTF8").toString();
            mqQueue.close();
        } catch (Exception e) {
            logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
        }
        return returnMsg;
    }

    public String receiveMessage(byte[] bts) {
        String msg = "";
        MQAgentConnector mqClient = getOutputMQ();
        try {
            // byte[] bts = corelationId.getBytes();
            mqClient.connectToQM(masterStore.getConnectionMap().get("queuemanager"));
            msg = mqClient.getMessage(masterStore.getConnectionMap().get("outputqueuename"), bts, 6000);
            return msg;
        } catch (Exception e) {
            logger.error(Marker.ANY_MARKER, EXCEPTION_IN_MQAGENT_CONNECTOR, e);
        } finally {
            mqClient.disconnectFromMQ();
        }
        return null;
    }
}
