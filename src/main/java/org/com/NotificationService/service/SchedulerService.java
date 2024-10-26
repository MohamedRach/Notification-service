package org.com.NotificationService.service;

import com.amazonaws.services.eventbridge.AmazonEventBridgeClient;
import com.amazonaws.services.eventbridge.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.NotificationService.DTO.EmailNotificationDTO;
import org.com.NotificationService.DTO.SmsnDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class SchedulerService {
    private final String EMAIL_SNS_TOPIC = "arn:aws:sns:us-east-1:211125423661:emailNotification";
    private final String SMS_SNS_TOPIC = "arn:aws:sns:us-east-1:211125423661:smsNotification";
    private final AmazonEventBridgeClient amazonEventBridgeClient;

    public SchedulerService(AmazonEventBridgeClient amazonEventBridgeClient) {
        this.amazonEventBridgeClient = amazonEventBridgeClient;
    }

    public String scheduleEmailNotification(String ruleName, String cronExpression, EmailNotificationDTO emailNotificationDTO) {
        // Create or update the rule
        PutRuleRequest ruleRequest = new PutRuleRequest();
        ruleRequest.setName(ruleName);
        ruleRequest.setScheduleExpression("cron(" + cronExpression + ")");
        ruleRequest.setState("ENABLED");

        amazonEventBridgeClient.putRule(ruleRequest);

        // Create the custom input JSON for SNS message
        Map<String, String> inputPathsMap = new HashMap<>();
        inputPathsMap.put("detail-type", "Scheduled Event");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(emailNotificationDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // Use Input Transformer to transform the custom data into the message sent to SNS
        InputTransformer inputTransformer = new InputTransformer();
        inputTransformer.setInputPathsMap(inputPathsMap);
        inputTransformer.setInputTemplate("{ \"message\": \"" +jsonString + "\", \"eventTime\": <time> }");

        // Define the SNS target with transformed input
        Target target = new Target();
        target.setArn(EMAIL_SNS_TOPIC);
        target.setId("sns-target");
        target.setInputTransformer(inputTransformer);

        Collection<Target> targets = new ArrayList<>();
        targets.add(target);

        PutTargetsRequest targetsRequest = new PutTargetsRequest();
        targetsRequest.setRule(ruleName);
        targetsRequest.setTargets(targets);

        amazonEventBridgeClient.putTargets(targetsRequest);

       return "EventBridge Rule Created with custom data sent to SNS: " + ruleName;
    }

    public String scheduleSmsNotification(String ruleName, String cronExpression, SmsnDTO smsNotification) {
        // Create or update the rule
        PutRuleRequest ruleRequest = new PutRuleRequest();
        ruleRequest.setName(ruleName);
        ruleRequest.setScheduleExpression("cron(" + cronExpression + ")");
        ruleRequest.setState("ENABLED");

        amazonEventBridgeClient.putRule(ruleRequest);

        // Create the custom input JSON for SNS message
        Map<String, String> inputPathsMap = new HashMap<>();
        inputPathsMap.put("detail-type", "Scheduled Event");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(smsNotification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // Use Input Transformer to transform the custom data into the message sent to SNS
        InputTransformer inputTransformer = new InputTransformer();
        inputTransformer.setInputPathsMap(inputPathsMap);
        inputTransformer.setInputTemplate("{ \"message\": " + jsonString.replace("\"", "\\\"") + ", \"eventTime\": <time> }");

        // Define the SNS target with transformed input
        Target target = new Target();
        target.setArn(SMS_SNS_TOPIC);
        target.setInputTransformer(inputTransformer);

        Collection<Target> targets = new ArrayList<>();
        targets.add(target);

        PutTargetsRequest targetsRequest = new PutTargetsRequest();
        targetsRequest.setRule(ruleName);
        targetsRequest.setTargets(targets);

        amazonEventBridgeClient.putTargets(targetsRequest);

        return "EventBridge Rule Created with custom data sent to SNS: " + ruleName;
    }

}
