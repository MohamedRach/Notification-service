package org.com.NotificationService.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.NotificationService.DTO.SmsnDTO;
import org.springframework.stereotype.Service;

@Service
public class SmsService {


    private final String SNS_TOPIC = "arn:aws:sns:us-east-1:211125423661:smsNotification";
    private AmazonSNSClient amazonSNSClient;

    public SmsService(AmazonSNSClient amazonSNSClient) {

        this.amazonSNSClient = amazonSNSClient;
    }





    public String publishToSmsQueue(SmsnDTO smsNotification) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(smsNotification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn(SNS_TOPIC)
                .withMessage(jsonString)
                .withSubject("sms notification published");
        amazonSNSClient.publish(publishRequest);
        return "Sent to " + smsNotification.getDestination() + " successfully";
    }

}
