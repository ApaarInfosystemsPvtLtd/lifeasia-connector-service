package com.pmli.lifeasia.service;

public interface AgentService {

    String createAgent(String agentRequest);
    String modifyAgent(String agentRequest);
    String terminateAgent(String agentRequest);
    String reinstateAgent(String agentRequest);
}
