package com.javaenthu.blog.app.blogapplication.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.javaenthu.blog.app.blogapplication.secuirty.CustomUserSecurity;
import com.javaenthu.blog.app.blogapplication.secuirty.JwtAuthenticationEntryPoint;
import com.javaenthu.blog.app.blogapplication.secuirty.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private CustomUserSecurity customUserSecurity;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private AuthenticationManagerBuilder authentictionManagerBean;
	
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	        .cors()
	        .and()
	        .csrf()
	        .disable()
	        .authorizeHttpRequests()
	        .antMatchers("/api/v1/auth/**").permitAll()
            .antMatchers(HttpMethod.GET).permitAll()
//            .antMatchers(HttpMethod.POST, "/").permitAll()
//            .antMatchers(HttpMethod.PUT, "/").permitAll()
//            .antMatchers(HttpMethod.DELETE, "/**").permitAll()
//            .antMatchers(HttpMethod.OPTIONS, "*").permitAll()
	        .anyRequest()
	        .authenticated()
	        .and()
	        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	        .and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        
	        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserSecurity).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
//	@Bean
//	public FilterRegistrationBean coresFilter() {
//		UrlBasedCorsConfigurationSource  urlBasedConfigurationSource=  new UrlBasedCorsConfigurationSource();
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.addAllowedOrigin("*");
//		corsConfiguration.addAllowedHeader("Authorization");
//		corsConfiguration.addAllowedHeader("Content-Type");
//		corsConfiguration.addAllowedHeader("Accept");
//		corsConfiguration.addAllowedMethod("POST");
//		corsConfiguration.addAllowedMethod("GET");
//		corsConfiguration.addAllowedMethod("DELETE");
//		corsConfiguration.addAllowedMethod("PUT");
//		corsConfiguration.addAllowedMethod("OPTIONS");
//		corsConfiguration.setMaxAge(3600L);
//		urlBasedConfigurationSource.registerCorsConfiguration("/**",corsConfiguration );
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(urlBasedConfigurationSource));
//		return bean;
//	}
	
	

}
