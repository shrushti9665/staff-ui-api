package gov.samhsa.c2s.staffuiapi.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "cn.staff-ui-api")
@Data
public class StaffUiApiProperties {

    @NotNull
    @Valid
    private Oauth2 oauth2;
    private Organization organization;
    
    @Data
    public static class Organization {
    	@NotBlank
        private String id;
    
    }

    @Data
    public static class Oauth2 {
        @NotBlank
        private String baseUrl;
        
        @NotBlank
        private String url;

        @Valid
        private Client client;

        @Data
        public static class Client {
            @NotBlank
            private String clientId;
            @NotBlank
            private String clientSecret;
        }
    }
}