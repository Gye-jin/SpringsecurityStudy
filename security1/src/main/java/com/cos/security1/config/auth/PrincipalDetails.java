package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.model.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줌,(security contextholder)
// 오브젝트 => authentication 타입 객체
// authentication 안에 user정보가 있어야 함.
// user오브젝트의 타입 => userDetails 타입 객체

// security session => authentication => userDetails타입

// 일반 로그인 userDetails
// 구글 로그인 Oauth2User
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user=user;
	}
	
	// 해당 유저의 권한을 리턴하는 곳!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(() -> {
			return user.getRole();
		});
		return collet;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트 1년동안 회원이 로그인을 안할 경우
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
