package org.com.NotificationService.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailNotificationDTO {
    private List<String> destination;
    private String subject;
    private String emailMessage;
}
