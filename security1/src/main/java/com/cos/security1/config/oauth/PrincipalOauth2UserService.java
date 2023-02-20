package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	// 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		System.out.println(userRequest);
		System.out.println(userRequest.getAccessToken().getTokenValue());
		System.out.println(userRequest.getClientRegistration());	// registrationId 어떤 Oauth로 로그인 했는지 알아보기
		// 구글 로그인 버트 -> 구글 로그인 창 -> 로그인 완료 -> code 리턴(Oauth-Client 라이브러리) -> access token 요청
		//user request 정보 -> loaduser 함수호출 -> 구글로부터 회원 프로필 받음.
		System.out.println(super.loadUser(userRequest).getAttributes());
		
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		// 회원가입 강제로 진행.
		return super.loadUser(userRequest);
		
	}

}
