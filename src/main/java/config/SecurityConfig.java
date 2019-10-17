package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@PropertySource("classpath:app.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername("${db.username}");
        basicDataSource.setUrl("jdbc:mysql://localhost/simple");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setPassword("");
        return basicDataSource;
    }

    @Bean
    public PropertyPlaceholderConfigurer placeholderConfigurer(){
        return new PropertyPlaceholderConfigurer();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .and()
                .withUser("sina").password(passwordEncoder().encode("sina")).roles("USER");
//        auth.jdbcAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .dataSource(dataSource())
//                .usersByUsernameQuery("select username, password, true from myusers where username=?")
//                .authoritiesByUsernameQuery("select username,authority from authorities where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .formLogin()
//                .and()
//                .logout().logoutUrl("/logout");
                .and().csrf().disable();
    }

}
