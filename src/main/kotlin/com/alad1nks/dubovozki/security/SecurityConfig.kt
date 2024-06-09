package com.alad1nks.dubovozki.security

import com.alad1nks.dubovozki.model.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(
                        "/router/bus-schedule/list",
                        "/router/bus-schedule/list-pair",
                        "/router/bus-schedule/revision",
                        "/router/castellan",
                        "/router/gym",
                        "/router/registration/email/validate",
                        "/router/registration/email/verify"
                    ).permitAll()
                    .requestMatchers(
                        "/router/random-coffee/join",
                        "/router/random-coffee/my-match"
                    ).authenticated()
                    .requestMatchers(
                        "/router/bus-schedule/update",
                        "/router/castellan/update",
                        "/router/gym/update",
                        "/router/random-coffee/add-words",
                        "/router/random-coffee/generate-pairs",
                        "/router/random-coffee/status",
                        "/router/registration/status",
                        "/router/user/create",
                        "/router/user/list"
                    ).hasAnyAuthority(UserRole.ADMIN.name, UserRole.OWNER.name)
                    .anyRequest().hasAuthority(UserRole.OWNER.name)
            }
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}
