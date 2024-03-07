package com.pmli.lifeasia.initialize;

import com.pmli.lifeasia.model.LifeAsiaConnectionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value="singleton")
public class MasterStore {

    private Logger logger = LoggerFactory.getLogger(MasterStore.class);

    @Autowired
    LifeAsiaConnectionConfig lifeAsiaConnectionConfig;

    private Map<String,String> connectionMap = new HashMap<>();

    public Map<String, String> getConnectionMap() {
        return connectionMap;
    }

    public void setConnectionMap(Map<String, String> connectionMap) {
        this.connectionMap = connectionMap;
    }

    public void initialize(){
        initializeConnectionMap();
    }

    public void clear() {
        getConnectionMap().clear();
    }

    public void initializeConnectionMap(){

        connectionMap.put("host",lifeAsiaConnectionConfig.getHost());
        connectionMap.put("port",String.valueOf(lifeAsiaConnectionConfig.getPort()));
        connectionMap.put("user",lifeAsiaConnectionConfig.getUser());
        connectionMap.put("password",lifeAsiaConnectionConfig.getPassword());
        connectionMap.put("channel",lifeAsiaConnectionConfig.getChannel());
        connectionMap.put("queuemanager",lifeAsiaConnectionConfig.getQueuemanager());
        connectionMap.put("inputqueuename",lifeAsiaConnectionConfig.getInputqueuename());
        connectionMap.put("outputqueuename",lifeAsiaConnectionConfig.getOutputqueuename());
        connectionMap.put("wait",String.valueOf(lifeAsiaConnectionConfig.getWait()));

        logger.info("checkTypeMap::{}",getConnectionMap());
    }

    public String toJson(){
        String host = nullPointerCheck(getConnectionMap().get("host"));
        String port =nullPointerCheck( getConnectionMap().get("port"));
        String user = nullPointerCheck(getConnectionMap().get("user"));

        String password = getConnectionMap().get("host");
        if(password != null && !"".equalsIgnoreCase(password)){
            password = "********";
        }

        String channel = nullPointerCheck(getConnectionMap().get("channel"));
        String queuemanager = nullPointerCheck(getConnectionMap().get("queuemanager"));
        String inputqueuename = nullPointerCheck(getConnectionMap().get("inputqueuename"));
        String outputqueuename = nullPointerCheck(getConnectionMap().get("outputqueuename"));
        String wait = nullPointerCheck(getConnectionMap().get("wait"));

        return "MasterStore{" +
                "Host='" + host + '\'' +
                ", Port='" + port + '\'' +
                ", User='" + user + '\'' +
                ", Channel='" + channel + '\'' +
                ", Queuemanager='" + queuemanager + '\'' +
                ", Inputqueuename='" + inputqueuename + '\'' +
                ", Outputqueuename='" + outputqueuename + '\'' +
                '}';

    }

    public String nullPointerCheck(String message){
        return (message!=null) ? message : "";
    }

}
