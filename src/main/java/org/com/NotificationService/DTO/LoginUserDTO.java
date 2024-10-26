package org.com.NotificationService.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginUserDTO {

    private String username;
    private String password;

}
