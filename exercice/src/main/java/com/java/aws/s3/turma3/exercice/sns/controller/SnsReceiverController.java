package com.java.aws.s3.turma3.exercice.sns.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.aws.s3.turma3.exercice.sns.service.SnsReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sns/receiver")
public class SnsReceiverController {

    private static final Logger log = LoggerFactory.getLogger(SnsReceiverController.class);

    private final SnsReceiverService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SnsReceiverController(SnsReceiverService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> receive(@RequestBody String body) throws Exception {

        log.info("Requisição recebida do SNS. Type: {}", body);

        JsonNode payload = objectMapper.readTree(body);
        String type = payload.get("Type").asText();

        log.info("Requisição recebida do SNS. Type: {}", type);

        return switch (type) {
            case "SubscriptionConfirmation" -> ResponseEntity.ok(service.confirmSubscription(payload));
            case "Notification" -> ResponseEntity.ok(service.processNotification(payload));
            default -> {
                log.warn("Tipo de mensagem SNS desconhecido: {}", type);
                yield ResponseEntity.ok("Unknown type: " + type);
            }
        };
    }
}