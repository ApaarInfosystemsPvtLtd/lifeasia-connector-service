package com.pmli.lifeasia.controller;

import com.pmli.lifeasia.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/pnbmetlife/v1/lifeasia")
public class LAConnectorControllerV1 {

    private final static Logger log = LogManager.getLogger();

    @Autowired
    AgentService agentService;

    @Autowired
    ClientService clientService;

    @PutMapping("/agent")
    public ResponseEntity<String> addAgent(@RequestBody String agentRequest) {
        log.info("Execution Start::LocalDateTime::{}", LocalDateTime.now());
        String status = agentService.createAgent(agentRequest);
        log.info("Execution End::LocalDateTime::{}", LocalDateTime.now());
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/agent")
    public ResponseEntity<String> updateAgent(@RequestBody String agentRequest) {
        log.info("Execution Start::LocalDateTime::{}", LocalDateTime.now());
        String status = agentService.modifyAgent(agentRequest);
        log.info("Execution End::LocalDateTime::{}", LocalDateTime.now());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/agent/terminate")
    public ResponseEntity<String> terminateAgent(@RequestBody String agentRequest) {
        log.info("Execution Start::LocalDateTime::{}", LocalDateTime.now());
        String status = agentService.terminateAgent(agentRequest);
        log.info("Execution End::LocalDateTime::{}", LocalDateTime.now());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/agent/reinstate")
    public ResponseEntity<String> reinstateAgent(@RequestBody String agentRequest) {
        log.info("Execution Start::LocalDateTime::{}", LocalDateTime.now());
        String status = agentService.reinstateAgent(agentRequest);
        log.info("Execution End::LocalDateTime::{}", LocalDateTime.now());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/client")
    public ResponseEntity<String> createClient(@RequestBody String clientRequest) {
        log.info("Execution Start::LocalDateTime::{}", LocalDateTime.now());
        String status = clientService.createClient(clientRequest);
        log.info("Execution End::LocalDateTime::{}", LocalDateTime.now());
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/client")
    public ResponseEntity<String> modifyClient(@RequestBody String clientRequest) {
        log.info("Execution Start::LocalDateTime::{}", LocalDateTime.now());
        String status = clientService.modifyClient(clientRequest);
        log.info("Execution End::LocalDateTime::{}", LocalDateTime.now());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
