package com.example.shopping.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true) // preAuthorize 어노테이션 추가하기 위해
public class SecurityConfig{

   private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrfConfig) -> csrfConfig.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler))

                .authorizeHttpRequests((authorize) -> authorize
                       // .anyRequest("/").authenticated()

                        .requestMatchers("/", "/**").permitAll().anyRequest().authenticated())
                .formLogin(f->f.disable()) //ok
                .httpBasic(h -> h.disable()); //ok
            //    .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
               // .logout((logout) -> logout
                 //       .logoutSuccessUrl("/login")
                   //     .invalidateHttpSession(true))


                //.antMatchers("/mysql/**").permitAll() // "/h2-console/**"
                // 권한이나 인증이 필요한 곳에서 불리는 검증 필터
              // .apply(new JwtSecurityConfig(tokenProvider));


        http.with(new JwtSecurityConfig(tokenProvider), customizer -> {} );


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    //비밀번호 암호화

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
}