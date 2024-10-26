package org.com.NotificationService.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.NotificationService.DTO.EmailNotificationDTO;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String SNS_TOPIC = "arn:aws:sns:us-east-1:211125423661:emailNotification";

    private final AmazonSNSClient amazonSNSClient;

    public EmailService(AmazonSNSClient amazonSNSClient) {
        this.amazonSNSClient = amazonSNSClient;
    }



    public String PublishToEmailQueue(EmailNotificationDTO email) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn(SNS_TOPIC)
                .withMessage(jsonString)
                .withSubject("email notification");
        amazonSNSClient.publish(publishRequest);
        return "Sent email successfully";
    }
}
