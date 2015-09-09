package com.mojun.reminder.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;

@Configuration
@EnableWebSecurity
@ComponentScan({"com.mojun.reminder.springsecurity.config", "com.mojun.reminder.reminderresource"})
@EnableGlobalMethodSecurity(prePostEnabled = true, mode = AdviceMode.ASPECTJ, proxyTargetClass = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
    /*
    @Autowired
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    } */
	
	//private MongoDBauthProdiver mongoBDauthProvider = new MongoDBauthProdiver();
	
	@Autowired
	private MongoDBauthProdiver mongoBDauthProvider;
	
	@Autowired
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(mongoBDauthProvider);
	}
	
	
	@Override 
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webapi/freemodel/**");
	}
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.csrf().disable()
    		.authorizeRequests()
    			//.antMatchers("/webapi/reminder/user/{\\d+}/**").access("hasAnyAuthority('ROLE_TOKENSAVED')")
    			//.antMatchers("/webapi/reminder/event/{\\d+}/**").access("hasAnyAuthority('ROLE_TOKENSAVED')")
    			.antMatchers("/webapi/reminder/**").hasRole("CUSTOMER")
    			//.antMatchers("/webapi/login/**").permitAll()
    			.anyRequest().authenticated()
    			.and()
    		.requiresChannel()
    			.anyRequest().requiresInsecure() //currently dont require HTTPS
    			.and()
    		.formLogin()
    			.loginProcessingUrl("/webapi/login") //default is /login with post
    			.failureUrl("/webapi/loginfail")   
    			.usernameParameter("username")  //default is username
    			.passwordParameter("password")  //default is password
    			.and() 
    		.logout()
    			//.logoutUrl("/webapi/logout")
    			.logoutRequestMatcher(new AntPathRequestMatcher("/webapi/logout", "GET" ))
    			.deleteCookies("JSESSIONID")
    			.invalidateHttpSession(true);
    	
    	http
    		.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
    }
}
