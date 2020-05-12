package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.service.UaaService;
import gov.samhsa.c2s.staffuiapi.service.dto.LoginRequestDto;
import gov.samhsa.c2s.staffuiapi.service.dto.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/uaa")
@CrossOrigin(origins = "*" , allowedHeaders="*")
public class UaaRestController {

    @Autowired
    private UaaService uaaService;
    @Autowired
    LoginUser loginuser;
    @PostMapping("/login")
    Object login(@Valid @RequestBody LoginRequestDto requestDto) {
    	loginuser.setUsername(requestDto.getUsername());
    	loginuser.setPassword(requestDto.getPassword());
     	loginuser.setOtp(requestDto.getOtp());
        return uaaService.login(requestDto);
    } 
    
}