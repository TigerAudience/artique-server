package com.artique.api.config;

import com.artique.api.intertceptor.CookieAuthorizationInterceptor;
import com.artique.api.session.CustomSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
  private final CookieAuthorizationInterceptor cookieAuthorizationInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(cookieAuthorizationInterceptor)
            .excludePathPatterns("/css/**", "/images/**", "/js/**","/oauth-redirect/**",
                    "/swagger-ui/**","/swagger-resources/**","/v3/api-docs/**",
                    "/member/*");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedOrigins("*")
            .exposedHeaders("authorization");
  }
}
