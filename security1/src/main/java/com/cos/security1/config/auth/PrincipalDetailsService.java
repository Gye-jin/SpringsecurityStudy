package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
// 시큐리티 설정에서 loginprocessingurl("/login");
// login요청이 오면 자동으로 userDetailsService 타입으로 Ioc되어있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	// 시큐리티 session(내부 authentication(내부userDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		User userEntity = userRepo.findByUsername(username); //jpa name함수 참고
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}

}
