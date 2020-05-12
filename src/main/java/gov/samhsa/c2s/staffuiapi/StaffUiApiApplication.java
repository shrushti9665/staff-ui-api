package gov.samhsa.c2s.staffuiapi;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import gov.samhsa.c2s.staffuiapi.service.TokenHolder;
import gov.samhsa.c2s.staffuiapi.service.dto.LoginUser;
import io.jsonwebtoken.io.IOException;
@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
//@EnableResourceServer
public class StaffUiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffUiApiApplication.class, args);
    }
    @Bean
    public LoginUser getUserDetails() {
    	return new LoginUser();
    }
    @Bean
    public TokenHolder getToken() {
    	return new TokenHolder();
    }
    
    
    @Component
       public class CorsFilter extends OncePerRequestFilter {

           @Override
           protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                           final FilterChain filterChain) throws ServletException, IOException {
           	
           	
           	response.addHeader("Content-Type", "*");
           	response.addHeader("Access-Control-Allow-Credentials", "*");
               response.addHeader("Access-Control-Allow-Origin", "*");          
               response.addHeader("Access-Control-Allow-Methods","*");
               response.setHeader("Access-Control-Allow-Headers", "*");
               response.addIntHeader("Access-Control-Max-Age", 3600);
            // response.addHeader("Access-Control-Allow-Methods","HEAD, GET, PUT, POST, DELETE, PATCH,UPDATE,OPTIONS");
            //  response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Content-Type,X-Forwarded-Proto,X-Forwarded-Host,X-Forwarded-Port");
             

               if ("OPTIONS".equalsIgnoreCase(request.getMethod())) 
               {
                   response.setStatus(HttpServletResponse.SC_OK);
               } 
               else 
               {
                   try {
   					filterChain.doFilter(request, response);
   				} catch (java.io.IOException e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				}
               }
           }

   		protected void doFilterInternal1(HttpServletRequest request, HttpServletResponse response,
   				FilterChain filterChain) throws ServletException, java.io.IOException {
   			// TODO Auto-generated method stub
   			
   		}
       }
    
    @EnableWebMvc
    public class CorsConfiguration implements WebMvcConfigurer
    {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("*").allowedHeaders("*");
        }

		@Override
		public void configurePathMatch(PathMatchConfigurer configurer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addFormatters(FormatterRegistry registry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configureViewResolvers(ViewResolverRegistry registry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Validator getValidator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MessageCodesResolver getMessageCodesResolver() {
			// TODO Auto-generated method stub
			return null;
		}
    }
    
}
