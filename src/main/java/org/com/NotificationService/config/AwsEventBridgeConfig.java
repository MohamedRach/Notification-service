package org.com.NotificationService.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClient;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsEventBridgeConfig {
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Bean
    public AmazonEventBridgeClient eventBridgeClient() {
        // Create AWS credentials using BasicAWSCredentials
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        // Build the EventBridge client
        return (AmazonEventBridgeClient) AmazonEventBridgeClientBuilder.standard()
                .withRegion("us-east-1")  // Specify the region
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))  // Set the credentials
                .build();
    }
}
