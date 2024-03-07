package com.pmli.lifeasia.service;

import com.pmli.lifeasia.connector.MQAgentConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AgentServiceImpl implements AgentService {

    private Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    MQAgentConnector mqAgentConnector;

    @Override
    public String createAgent(String agentRequest) {
        logger.info("In Service Impl Create Agent Start::LocalDateTime::{}", LocalDateTime.now());
        logger.info("createAgent::Request::{}",agentRequest);
        String response = mqAgentConnector.sendMessage(agentRequest);
        logger.info("In Service Impl Create Agent End::LocalDateTime::{}", LocalDateTime.now());
        return parseResponse(response);
    }

    @Override
    public String modifyAgent(String agentRequest) {
        logger.info("In Service Impl Modify Agent Start::LocalDateTime::{}", LocalDateTime.now());
        logger.info("modifyAgent::Request::{}",agentRequest);
        String response = mqAgentConnector.sendMessage(agentRequest);
        logger.info("In Service Impl Modify Agent End::LocalDateTime::{}", LocalDateTime.now());
        return parseResponse(response);
    }

    @Override
    public String terminateAgent(String agentRequest) {
        logger.info("In Service Impl Terminate Agent Start::LocalDateTime::{}", LocalDateTime.now());
        logger.info("terminateAgent::Request::{}",agentRequest);
        String response = mqAgentConnector.sendMessage(agentRequest);
        logger.info("In Service Impl Terminate Agent End::LocalDateTime::{}", LocalDateTime.now());
        return parseResponse(response);
    }

    @Override
    public String reinstateAgent(String agentRequest) {
        logger.info("In Service Impl Reinstante Agent Start::LocalDateTime::{}", LocalDateTime.now());
        logger.info("reinstateAgent::Request::{}",agentRequest);
        String response = mqAgentConnector.sendMessage(agentRequest);
        logger.info("In Service Impl Reinstante Agent End::LocalDateTime::{}", LocalDateTime.now());
        return parseResponse(response);
    }

    private String parseResponse(String response) {
        logger.info("LifeAsia Connector::Response::{}",response);
        return response;
    }
}
