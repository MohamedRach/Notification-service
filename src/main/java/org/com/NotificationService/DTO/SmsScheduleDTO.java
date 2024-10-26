package org.com.NotificationService.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsScheduleDTO {
    private String cronExpression;
    private String destination;
    private String smsMessage;
}
