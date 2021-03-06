package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

//	@Autowired
//	private MyCustomUserDetailsService userDetailsService;

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}

//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(encoder());
//		return authProvider;
//	}
//
//	@Bean
//	public PasswordEncoder encoder() {
//		return new BCryptPasswordEncoder(11);  
//	}
	
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/static/login.html").setViewName("login.html");
//	    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//	}
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
		.antMatchers("/api/**").hasAnyRole("sadmin","admin")
		.antMatchers("/h2-console").hasAnyRole("sadmin","admin")
		.antMatchers("/game/").hasAnyRole("sadmin","admin")
		.antMatchers("/topic/").hasAnyRole("sadmin","admin")
		.antMatchers("/topic/add**").hasRole("sadmin")
		.antMatchers("/topic/edit/**").hasRole("sadmin")
		.antMatchers("/topic/del/**").hasRole("sadmin")
		.antMatchers("/game/topic/**").hasRole("sadmin")
		.antMatchers("/api/**").permitAll()
		.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
		.logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll().and()
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

		
		
	}
}