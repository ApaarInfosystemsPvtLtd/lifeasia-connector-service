package com.pmli.lifeasia.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class LifeAsiaConnectionConfig {
    @Value("${lifeasia.agent.host}")
    private String host;
    @Value("${lifeasia.agent.port}")
    private int port;
    @Value("${lifeasia.agent.user}")
    private String user;
    @Value("${lifeasia.agent.password}")
    private String password;
    @Value("${lifeasia.agent.channel}")
    private String channel;
    @Value("${lifeasia.agent.queuemanager}")
    private String queuemanager;
    @Value("${lifeasia.agent.inputqueuename}")
    private String inputqueuename;
    @Value("${lifeasia.agent.outputqueuename}")
    private String outputqueuename;
    @Value("${lifeasia.connector.wait}")
    private int wait;

    public String getHost() {
        return host;
    }

    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }

    public void setPort(int port) { this.port = port; }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getQueuemanager() { return queuemanager; }

    public void setQueuemanager(String queuemanager) {
        this.queuemanager = queuemanager;
    }

    public String getInputqueuename() {
        return inputqueuename;
    }

    public void setInputqueuename(String inputqueuename) {
        this.inputqueuename = inputqueuename;
    }

    public String getOutputqueuename() { return outputqueuename; }

    public void setOutputqueuename(String outputqueuename) {
        this.outputqueuename = outputqueuename;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }
}
