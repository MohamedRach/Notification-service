package org.com.NotificationService.controller;


import org.com.NotificationService.DTO.EmailNotificationDTO;
import org.com.NotificationService.DTO.EmailScheduleDTO;
import org.com.NotificationService.DTO.SmsScheduleDTO;
import org.com.NotificationService.DTO.SmsnDTO;
import org.com.NotificationService.service.EmailService;
import org.com.NotificationService.service.SchedulerService;
import org.com.NotificationService.service.SmsService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/notification")
@RestController
public class NotificationController {

    private final EmailService emailService;
    private final SmsService smsService;
    private final SchedulerService schedulerService;


    public NotificationController(EmailService emailService, SmsService smsService, SchedulerService schedulerService) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.schedulerService = schedulerService;

    }







    @PostMapping("/email")
    public String PublishEmailNotification(@RequestBody() EmailNotificationDTO emailDTO) {
        return emailService.PublishToEmailQueue(emailDTO);
    }

    @PostMapping("/sms")
    public String PublishSmsNotification(@RequestBody() SmsnDTO smsNotificationDTO) {
        return smsService.publishToSmsQueue(smsNotificationDTO);
    }

    @PostMapping("/schedule/email")
    public String ScheduleEmailNotification(@RequestBody() EmailScheduleDTO  emailScheduleDTO) {
        EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
        emailNotificationDTO.setDestination(emailScheduleDTO.getDestination());
        emailNotificationDTO.setSubject(emailScheduleDTO.getSubject());
        emailNotificationDTO.setEmailMessage(emailScheduleDTO.getEmailMessage());
        return schedulerService.scheduleEmailNotification("emailRule", emailScheduleDTO.getCronExpression(), emailNotificationDTO);
    }

    @PostMapping("/schedule/sms")
    public String ScheduleSmsNotification(@RequestBody() SmsScheduleDTO smsScheduleDTO) {
        SmsnDTO smsnDTO = new SmsnDTO();
        smsnDTO.setDestination(smsScheduleDTO.getDestination());
        smsnDTO.setSmsMessage(smsScheduleDTO.getSmsMessage());
        return schedulerService.scheduleSmsNotification("smsRule", smsScheduleDTO.getCronExpression(), smsnDTO);
    }








}
