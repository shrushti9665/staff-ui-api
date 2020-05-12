package gov.samhsa.c2s.staffuiapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.List;
import java.util.Objects;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(List<PropertyMap> propertyMaps) {
        final ModelMapper modelMapper = new ModelMapper();
        propertyMaps.stream().filter(Objects::nonNull).forEach(modelMapper::addMappings);
        return modelMapper;
    }
    

    @Bean 
    public RequestInterceptor interceptor1() { 
     return new RequestInterceptor() {

		@Override
		public void apply(RequestTemplate template) {
			String tenantid=UmsDbID.getCurrentTenant();
			String version=UmsDbID.getCurrentVersion();
			String token =UmsDbID.getCurrentToken();
			template.header("tenantId", tenantid);
			template.header("version", version);
			template.header("Authorization", token);
			// TODO Auto-generated method stub 
			
		} 
      
	
     }; 
    }
}