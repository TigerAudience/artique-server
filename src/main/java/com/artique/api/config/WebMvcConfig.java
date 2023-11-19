package com.artique.api.config;

import com.artique.api.converter.MusicalReviewOrderByConverter;
import com.artique.api.converter.ReportTypeConverter;
import com.artique.api.converter.SearchMusicalOrderByConverter;
import com.artique.api.converter.UserReviewOrderByConverter;
import com.artique.api.intertceptor.AuthorizationHeaderInterceptor;
import com.artique.api.intertceptor.CookieAuthorizationInterceptor;
import com.artique.api.intertceptor.HttpRequestInfoInterceptor;
import com.artique.api.intertceptor.InvalidUrlInterceptor;
import com.artique.api.resolver.SessionUserResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
  private final SessionUserResolver sessionUserResolver;
  private final MusicalReviewOrderByConverter musicalReviewOrderByConverter;
  private final SearchMusicalOrderByConverter searchMusicalOrderByConverter;
  private final UserReviewOrderByConverter userReviewOrderByConverter;
  private final HttpRequestInfoInterceptor httpRequestInfoInterceptor;
  private final AuthorizationHeaderInterceptor authorizationHeaderInterceptor;
  private final ReportTypeConverter reportTypeConverter;
  private final InvalidUrlInterceptor invalidUrlInterceptor;
  private  final String[] excludePatternsForStaticResource = {"/css/**", "/images/**", "/js/**","/favicon.ico",
          "/webjars/**","/error/**", "/oauth-redirect/**", "/swagger-ui/**",
          "/swagger-resources/**","/v3/api-docs/**","META-INF/**"};

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(musicalReviewOrderByConverter);
    registry.addConverter(searchMusicalOrderByConverter);
    registry.addConverter(userReviewOrderByConverter);
    registry.addConverter(reportTypeConverter);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    String[] excludePatterns = buildExcludePattern();
    registry.addInterceptor(httpRequestInfoInterceptor).order(1).excludePathPatterns();
    registry.addInterceptor(invalidUrlInterceptor).order(2).excludePathPatterns(excludePatternsForStaticResource);
    registry.addInterceptor(authorizationHeaderInterceptor).order(3).excludePathPatterns(excludePatterns);
  }
  public String[] buildExcludePattern(){
    String[] excludePatternsForUri = {"/member/**","/feed/**","/review/**","/musical/**","/search/**",
            "/config/**","/session/**"};
    List<String> patterns = new ArrayList<>();
    patterns.addAll(Arrays.asList(excludePatternsForStaticResource));
    patterns.addAll(Arrays.asList(excludePatternsForUri));
    return patterns.toArray(String[]::new);
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
