package com.artique.api.config;

import com.artique.api.converter.MusicalReviewOrderByConverter;
import com.artique.api.converter.SearchMusicalOrderByConverter;
import com.artique.api.converter.UserReviewOrderByConverter;
import com.artique.api.intertceptor.CookieAuthorizationInterceptor;
import com.artique.api.resolver.SessionUserResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
  private final CookieAuthorizationInterceptor cookieAuthorizationInterceptor;
  private final SessionUserResolver sessionUserResolver;
  private final MusicalReviewOrderByConverter musicalReviewOrderByConverter;
  private final SearchMusicalOrderByConverter searchMusicalOrderByConverter;
  private final UserReviewOrderByConverter userReviewOrderByConverter;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(musicalReviewOrderByConverter);
    registry.addConverter(searchMusicalOrderByConverter);
    registry.addConverter(userReviewOrderByConverter);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(cookieAuthorizationInterceptor)
            .excludePathPatterns("/css/**", "/images/**", "/js/**","/favicon.ico","/webjars/**","/error/**",
                    "/oauth-redirect/**", "/swagger-ui/**","/swagger-resources/**","/v3/api-docs/**",
                    "/member/**","/feed/**","/review/**","/musical/**","/search/**","META-INF/**");
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
