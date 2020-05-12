package gov.samhsa.c2s.staffuiapi.service;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class TokenHolder {
//@NotBlank	
private String token;
}
