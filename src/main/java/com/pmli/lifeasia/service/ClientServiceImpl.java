package com.pmli.lifeasia.service;

import com.pmli.lifeasia.connector.MQClientConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientServiceImpl implements ClientService {

    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    MQClientConnector mqClientConnector;

    @Override
    public String createClient(String clientRequest) {
        logger.info("In Service Impl Create Client Start::LocalDateTime::{}", LocalDateTime.now());
        logger.info("createClient::Request::{}",clientRequest);
        String response = mqClientConnector.sendMessage(clientRequest);
        logger.info("In Service Impl Create Client End::LocalDateTime::{}", LocalDateTime.now());
        return parseResponse(response);
    }

    @Override
    public String modifyClient(String clientRequest) {
        logger.info("In Service Impl Modify Client Start::LocalDateTime::{}", LocalDateTime.now());
        logger.info("modifyClient::Request::{}",clientRequest);
        String response = mqClientConnector.sendMessage(clientRequest);
        logger.info("In Service Impl Modify Client End::LocalDateTime::{}", LocalDateTime.now());
        return parseResponse(response);
    }

    private String parseResponse(String response) {
        logger.info("LifeAsia Connector::Response::{}",response);
        return response;
    }

}
