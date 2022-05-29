package com.example.security.config;


import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/auth/**").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .loginProcessingUrl("/login-user")
                .and()
                .logout().logoutSuccessUrl("/");
    }

    //in memory users

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user = User.builder()
//                .username("Alex")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("ADMIN")
//                .password(passwordEncoder().encode("123"))
//                .roles("ADMIN","USER")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }



    // in database

//    @Bean
//    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource){
//        UserDetails user = User.builder()
//                .username("Alex")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("ADMIN")
//                .password(passwordEncoder().encode("123"))
//                .roles("ADMIN","USER")
//                .build();
//
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//
//        if(users.userExists(user.getUsername())){
//            users.deleteUser(user.getUsername());
//        }
//
//        if(users.userExists(admin.getUsername())){
//            users.deleteUser(admin.getUsername());
//        }
//
//        users.createUser(user);
//        users.createUser(admin);
//
//        return users;
//    }


    @Bean
    public DaoAuthenticationProvider provider(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
