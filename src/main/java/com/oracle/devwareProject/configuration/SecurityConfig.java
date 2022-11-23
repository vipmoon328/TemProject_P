package com.oracle.devwareProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//IoC 빈(bean)을 등록
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
//filter chain을 관리를 시작하는 annotation이다
@EnableWebSecurity
public class SecurityConfig {
	
	//인스턴스 형성 @Autowired 로 사용
	//spring security에서 제공하는 암호화 기법
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// csrf는 해킹기법
		http.csrf().disable();
		//인증할 시 어떤것이든 허용해주겠다
		//Authentication 인증 (내가 사용자인가 아닌가)
		//authorization 인가 (인증은 받았지만 권한을 확인)
		http.authorizeRequests().anyRequest().permitAll();
		
		/*
		 * //로그인 설정 http. formLogin() .loginPage("/loginForm")
		 * .loginProcessingUrl("/login") .defaultSuccessUrl("/");
		 * 
		 * //로그아웃 설정 http. logout() .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logOut")) .logoutSuccessUrl("/loginForm")
		 * .invalidateHttpSession(true);
		 */
		
		return http.build();
		

	}
	
}
