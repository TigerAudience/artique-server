package com.artique.api.config;

import com.artique.api.intertceptor.CookieAuthorizationInterceptor;
import com.artique.api.resolver.SessionUserResolver;
import com.artique.api.session.CustomSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
  private final CookieAuthorizationInterceptor cookieAuthorizationInterceptor;
  private final SessionUserResolver sessionUserResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(cookieAuthorizationInterceptor)
            .excludePathPatterns("/css/**", "/images/**", "/js/**","/favicon.ico","/webjars/**","/error/**",
                    "/oauth-redirect/**", "/swagger-ui/**","/swagger-resources/**","/v3/api-docs/**",
                    "/member/**","/feed/**","/musical/**","META-INF/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedOrigins("*")
            .exposedHeaders("authorization");
  }
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(sessionUserResolver);
  }
}
