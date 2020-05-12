package gov.samhsa.c2s.staffuiapi.service.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
@Data
public class LoginUser {
	@NotBlank
    private String username;
    @NotBlank
    private String password;
    
    private String otp;
}
