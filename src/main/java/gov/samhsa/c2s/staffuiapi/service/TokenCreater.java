package gov.samhsa.c2s.staffuiapi.service;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.samhsa.c2s.staffuiapi.config.StaffUiApiProperties;
import gov.samhsa.c2s.staffuiapi.service.dto.LoginUser;


@Component
public class TokenCreater {
	 @Autowired
	  	private final StaffUiApiProperties staffUiApiProperties;
	 	private static final String OAUTH2_GRAND_TYPE = "password";
	 	private static final String OAUTH2_RESPONSE_TYPE = "token";
	    private static final String OAUTH2_TOKEN_FORMAT = "opaque";
	    
	    @Autowired
	    public TokenCreater(StaffUiApiProperties staffUiApiProperties) {
	        this.staffUiApiProperties = staffUiApiProperties;
	     //   this.loginRequestDto = loginRequestDto;
	    }
	    @Autowired
		private  LoginUser loginRequestDto;
	    public String tokenGenrator() {

		String username=loginRequestDto.getUsername();
		String password=loginRequestDto.getPassword();
	 //   String opt=loginRequestDto.getOtp();
		HttpHeaders headers = new HttpHeaders();
		 String url=staffUiApiProperties.getOauth2().getUrl();
		 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);		 
		 MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		 map.add("client_id", staffUiApiProperties.getOauth2().getClient().getClientId());
		 map.add("client_secret", staffUiApiProperties.getOauth2().getClient().getClientSecret());
		 map.add("grant_type", OAUTH2_GRAND_TYPE);
		 map.add("token_format", OAUTH2_TOKEN_FORMAT);
		 map.add("response_type", OAUTH2_RESPONSE_TYPE);
		 map.add("username", username);
		 map.add("password", password);
	//	 map.add("mfaCode",otp);
		 HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		 
		 RestTemplate restTemplate = new RestTemplate();
		 MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		 converter.setObjectMapper(new ObjectMapper());
		 restTemplate.getMessageConverters().add(converter);
		 ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
		 
		 String str =response.toString();
		 String str1=str.replace("<200 OK,"," ");
		 String str2=str1.replace(">"," ");
		 JSONObject jsonObject;
		 String token = null;
		try {
			jsonObject = new JSONObject(str2);
			token =(String) jsonObject.get("access_token");
			
		} catch (JSONException e) {	 e.printStackTrace(); }
	
		return token;
}
  }