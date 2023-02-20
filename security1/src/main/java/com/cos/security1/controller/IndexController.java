package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@GetMapping("/test/login")
	public @ResponseBody String loginTest(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) {	//의존성 주입(DI)
		System.out.println("============================1");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println(principalDetails.getUser());
		System.out.println(userDetails.getUser());
		return "세션 정보 확인";
	}
	@GetMapping("/test/oauth/login")
	public @ResponseBody String oauthloginTest(Authentication authentication
			,@AuthenticationPrincipal OAuth2User oauth) {	//의존성 주입(DI)
		System.out.println("============================2");
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println(oauth2User.getAttributes());
		System.out.println(oauth.getAttributes());
		return "세션 정보 확인";
	}
	
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		// view resolver 설정: templates (prefix), .mustache(suffix)
		return "index";
	}
	
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		return "user";
	}
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	// 스프링 시큐리티가 낚아챔. -> security config 파일 생성 후 작동 안함
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	@GetMapping("/joinForm")
	public String lojoinFormgin() {
		return "joinForm";
	}
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassWord = user.getPassword();
		String encPassWord = bcryptPasswordEncoder.encode(rawPassWord);
		user.setPassword(encPassWord);
		userRepo.save(user); // -> 회원가입 잘 되지만 비밀번호: 1234 => 시큐리티로 로그인 불가 / 패스워드 암호화가 안되어 있음.
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
	
}
