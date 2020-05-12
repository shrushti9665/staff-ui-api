package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.staffuiapi.service.dto.ProfileResponse;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;

import java.util.List;

public interface UmsService {
    PageableDto<UserDto> getAllUsers(Integer page, Integer size);

    void registerUser(UserDto userDto);

    List<UserDto> searchUsersByFirstNameAndORLastName(String term);

    UserDto getUser(Long userId);

    void updateUser(Long userId, UserDto userDto);

    Object initiateUserActivation(Long userId, String xForwardedProto, String xForwardedHost, String xForwardedPort);

    Object getCurrentUserCreationInfo(Long userId);

    void disableUser(Long userId);

    void enableUser(Long userId);

    ProfileResponse getProviderProfile();
}