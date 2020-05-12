package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.config.StaffUiApiProperties;
import gov.samhsa.c2s.staffuiapi.config.UmsDbID;
import gov.samhsa.c2s.staffuiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.staffuiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.RequiredIdentifierSystem.Algorithm;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.staffuiapi.service.dto.JwtTokenKey;
import gov.samhsa.c2s.staffuiapi.service.dto.ProfileResponse;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UmsServiceImpl implements UmsService {

	@Autowired
	private final JwtTokenExtractor jwtTokenExtractor;
	@Autowired
	private UmsClient umsClient;
	@Autowired
	private UmsLookupClient umsLookupClient;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private TokenCreater accessTokenCreater;
	@Autowired
	private final StaffUiApiProperties staffUiApiProperties;
	@Autowired
	private TokenHolder tokenHolder;

	public UmsServiceImpl(JwtTokenExtractor jwtTokenExtractor, TokenCreater accessTokenCreater,
			StaffUiApiProperties staffUiApiProperties) {
		this.jwtTokenExtractor = jwtTokenExtractor;
		this.accessTokenCreater = accessTokenCreater;
		this.staffUiApiProperties = staffUiApiProperties;
	}

	@Override
	public PageableDto<UserDto> getAllUsers(Integer page, Integer size) {
		// Mapping of generic parameterized types
		Type pageableUserDtoType = new TypeToken<PageableDto<UserDto>>() {
		}.getType();

		PageableDto<UmsUserDto> pageableUmsUserDto = umsClient.getAllUsers(page, size);
		List<UserDto> userDtos = pageableUmsUserDto.getContent().stream()
				.map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class)).collect(Collectors.toList());

		PageableDto<UserDto> pageableUserDto = modelMapper.map(pageableUmsUserDto, pageableUserDtoType);
		pageableUserDto.setContent(userDtos);

		return pageableUserDto;
	}

	@Override
	public void registerUser(UserDto userDto) {
		DecodedJWT jwt = null;
		String Stoken = null;
		HashMap claimsMap = null;
		String authToken = UmsDbID.getCurrentToken();
		try {
			if (authToken.startsWith("Bearer ")) {
				Stoken = authToken.substring(7, authToken.length());
			} else {
			}
			Jwt jwtToken = JwtHelper.decode(Stoken);
			String claims = jwtToken.getClaims();			
			try {
				claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JWTDecodeException exception) {
		}
		Object lastUpdatedBy=claimsMap.values().toArray()[0];	
		userDto.setCreatedBy((String) lastUpdatedBy);
		userDto.setLastUpdatedBy((String) lastUpdatedBy);
		// userDto.setCreatedBy(getLastUpddatedBy());
		// userDto.setLastUpdatedBy(getLastUpddatedBy());
		userDto.setOrganizationId(staffUiApiProperties.getOrganization().getId());
		String token = null;
		try {
			token = accessTokenCreater.tokenGenrator();
		} catch (Exception ex) {
		}
		umsClient.registerUser(token, modelMapper.map(userDto, UmsUserDto.class));
	}

	@Override
	public List<UserDto> searchUsersByFirstNameAndORLastName(String term) {
		return umsClient.searchUsersByFirstNameAndORLastName(term).stream()
				.map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class)).collect(Collectors.toList());
	}

	@Override
	public UserDto getUser(Long userId) {
		return modelMapper.map(umsClient.getUser(userId), UserDto.class);
	}

	@Override
	public void updateUser(Long userId, UserDto userDto) {
		DecodedJWT jwt = null;
		String Stoken = null;
		HashMap claimsMap = null;
		String authToken = UmsDbID.getCurrentToken();
		try {
			if (authToken.startsWith("Bearer ")) {
				Stoken = authToken.substring(7, authToken.length());
			} else {
			}
			Jwt jwtToken = JwtHelper.decode(Stoken);
			String claims = jwtToken.getClaims();			
			try {
				claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JWTDecodeException exception) {
		}
		Object lastUpdatedBy=claimsMap.values().toArray()[0];	
		
		userDto.setLastUpdatedBy((String) lastUpdatedBy);
		//userDto.setLastUpdatedBy(getLastUpddatedBy());
		userDto.setOrganizationId(staffUiApiProperties.getOrganization().getId());

		/*String token = null;
		try {
			token = accessTokenCreater.tokenGenrator();
		} catch (Exception ex) {
		}*/
		umsClient.updateUser(/*token,*/ userId, modelMapper.map(userDto, UmsUserDto.class));
	}

	@Override
	public Object initiateUserActivation(Long userId, String xForwardedProto, String xForwardedHost,
			String xForwardedPort) {
		DecodedJWT jwt = null;
		String Stoken = null;
		HashMap claimsMap = null;
		String authToken = UmsDbID.getCurrentToken();
		try {
			if (authToken.startsWith("Bearer ")) {
				Stoken = authToken.substring(7, authToken.length());
			} else {
			}
			Jwt jwtToken = JwtHelper.decode(Stoken);
			String claims = jwtToken.getClaims();			
			try {
				claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JWTDecodeException exception) {
		}
		Object lastUpdatedBy=claimsMap.values().toArray()[0];	
		
		return umsClient.initiateUserActivation(userId, (String) lastUpdatedBy, xForwardedProto, xForwardedHost,
				xForwardedPort);
	}

	@Override
	public Object getCurrentUserCreationInfo(Long userId) {
		return umsClient.getCurrentUserCreationInfo(userId);
	}

	@Override
	public void disableUser(Long userId) {
		DecodedJWT jwt = null;
		String Stoken = null;
		HashMap claimsMap = null;
		String authToken = UmsDbID.getCurrentToken();
		try {
			if (authToken.startsWith("Bearer ")) {
				Stoken = authToken.substring(7, authToken.length());
			} else {
			}
			Jwt jwtToken = JwtHelper.decode(Stoken);
			String claims = jwtToken.getClaims();			
			try {
				claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JWTDecodeException exception) {
		}
		Object lastUpdatedBy=claimsMap.values().toArray()[0];	
		umsClient.disableUser(userId, (String) lastUpdatedBy);
	}

	@Override
	public void enableUser(Long userId) {
		DecodedJWT jwt = null;
		String Stoken = null;
		HashMap claimsMap = null;
		String authToken = UmsDbID.getCurrentToken();
		try {
			if (authToken.startsWith("Bearer ")) {
				Stoken = authToken.substring(7, authToken.length());
			} else {
			}
			Jwt jwtToken = JwtHelper.decode(Stoken);
			String claims = jwtToken.getClaims();			
			try {
				claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JWTDecodeException exception) {
		}
		Object lastUpdatedBy=claimsMap.values().toArray()[0];	
		umsClient.enableUser(userId, (String) lastUpdatedBy);
	}

	@Override
	public ProfileResponse getProviderProfile() {
		// Get system supported Locales
		List<BaseUmsLookupDto> supportedLocales = umsLookupClient.getLocales();
		// TODO Implement get staff profile from DB
		return ProfileResponse.builder().userLocale("en").supportedLocales(supportedLocales).username("")
				.firstName("Admin").lastName("Staff").build();
	}

	private String getLastUpddatedBy() {
  	  DecodedJWT jwt = null;
		String Stoken = null;
		HashMap claimsMap = null;
		String authToken = UmsDbID.getCurrentToken();
		try {
			if (authToken.startsWith("Bearer ")) {
				Stoken = authToken.substring(7, authToken.length());
			} else {
			}
			Jwt jwtToken = JwtHelper.decode(Stoken);
			String claims = jwtToken.getClaims();			
			try {
				claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JWTDecodeException exception) {
		}
		Object Username=claimsMap.values().toArray()[1];	
		Object AuthId=claimsMap.values().toArray()[0];	
     
    String userAuthId=(String) AuthId;   
    String currentUsername=(String) Username; 
		return userAuthId;
		//return jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
	}

	private String getName() {
	  	  DecodedJWT jwt = null;
			String Stoken = null;
			HashMap claimsMap = null;
			String authToken = UmsDbID.getCurrentToken();
			try {
				if (authToken.startsWith("Bearer ")) {
					Stoken = authToken.substring(7, authToken.length());
				} else {
				}
				Jwt jwtToken = JwtHelper.decode(Stoken);
				String claims = jwtToken.getClaims();			
				try {
					claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (JWTDecodeException exception) {
			}
			Object Username=claimsMap.values().toArray()[1];	
			Object AuthId=claimsMap.values().toArray()[0];	
	     
	    String userAuthId=(String) AuthId;   
	    String currentUsername=(String) Username; 
		return currentUsername;
		//return jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_NAME);
	}

	private String getEmail() {
	  	  DecodedJWT jwt = null;
			String Stoken = null;
			HashMap claimsMap = null;
			String authToken = UmsDbID.getCurrentToken();
			try {
				if (authToken.startsWith("Bearer ")) {
					Stoken = authToken.substring(7, authToken.length());
				} else {
				}
				Jwt jwtToken = JwtHelper.decode(Stoken);
				String claims = jwtToken.getClaims();			
				try {
					claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (JWTDecodeException exception) {
			}
			
			Object email=claimsMap.values().toArray()[15];	
	     
	    String useremail=(String) email;   
	
		return useremail;
		//return jwtTokenExtractor.getValueByKey(JwtTokenKey.EMAIL);
	}
}
