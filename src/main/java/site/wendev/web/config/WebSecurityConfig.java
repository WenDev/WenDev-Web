package site.wendev.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import site.wendev.web.service.impl.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService userDetailService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .mvcMatchers("/css/**", "/js/**")
                .mvcMatchers("/register", "/register?error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth/login")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/login", "/index", "/", "/register").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers("/login?error", "/register?error").permitAll()
                .antMatchers("/superAdmin/**").hasRole("superAdmin")
                .anyRequest()
                .authenticated()
                .and()
                .logout()
//                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService()).passwordEncoder(passwordEncoder());
    }
}
