# Notification Service API

The **Notification Service API** is a scalable notification system that allows users to send scheduled and instant notifications via SMS and email. It’s built using **Spring Boot** and leverages AWS services for message queuing, scheduling, and processing.

## Architecture

The architecture leverages several AWS services to handle different aspects of the notification workflow.

- **Notification API**: Built with Spring Boot, acts as the main entry point for notification requests.
- **Amazon EventBridge**: Manages scheduled notifications, sending them to the appropriate queue at the designated times.
- **Amazon SNS**: Handles instant notifications by forwarding them to the appropriate messaging queue.
- **Amazon SQS**: Stores notifications temporarily in **Email Queue** and **SMS Queue**.
- **AWS Lambda**: Processes queued messages and sends them via email or SMS.
  - **Email Processor**: Uses Amazon SES to send emails.
  - **SMS Processor**: Uses AWS SNS for SMS delivery.

## Architecture Diagram

![Architecture Diagram](images/architecture.png)

The above diagram illustrates the flow of notifications through the system, from the API to the end-user delivery.

## Features

- **Scheduled Notifications**: Set up notifications to be sent at specific times.
- **Instant Notifications**: Send notifications immediately to selected users.
- **Multiple Channels**: Supports SMS and email notifications.
- **AWS-Managed Infrastructure**: Utilizes AWS services for scalable and reliable message processing.

## Technologies

- **Backend**: Spring Boot, AWS SDK
- **AWS Services**: SNS, SQS, EventBridge, Lambda, SES

## Setup and Installation

### Prerequisites

- **Java 11+**
- **AWS Account** with permissions for SNS, SQS, SES, EventBridge, and Lambda
- **Spring Boot** installed locally#### Steps

##### Clone the repository:

```bash
git clone https://github.com/yourusername/notification-service-api.git
cd notification-service-api
```

Configure AWS Credentials: Set up your AWS credentials locally. This can be done by configuring the AWS CLI or setting environment variables for AWS access keys.

##### Build the Project:

```bash
./mvnw clean install
```

##### Run the Application:

```bash
    ./mvnw spring-boot:Run
```

Configure AWS Resources: Ensure SNS, SQS queues, and Lambda functions are configured according to the architecture. You may use an Infrastructure-as-Code tool like Terraform or AWS CloudFormation to automate this setup.

Usage

    - Instant Notification: Create a notification with "type": "instant", which triggers an SNS message immediately.
    - Scheduled Notification: Set "type": "scheduled" with a scheduleTime to queue the notification for future processing via EventBridge.
