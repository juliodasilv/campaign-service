package br.com.avaliacao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/admin/**", "/h2/**").hasRole("ADMIN")
				.antMatchers("/v1/**").authenticated()
			.and()
				.httpBasic()
			.and()
				.csrf()
				.disable();
		http.headers().frameOptions().disable();
	}

	/**
	 * Criação de acessos em memória para acesso de usuario interno 
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
				.withUser("user").password("user").roles("USER")
				.and()
				.withUser("admin").password("admin").roles("USER", "ADMIN");
	}
	
	public static void main(String[] args) {
		criptografa();
	}

	private static void criptografa() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("admin"));
	}
}