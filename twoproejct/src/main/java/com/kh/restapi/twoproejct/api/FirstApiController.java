package com.kh.restapi.twoproejct.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j // 롬복이 지원하는 로그레벨
public class FirstApiController {

	//url요청이 들어오면 매핑
	
	@GetMapping("/api/hello") // 웹에 들어갈 도메인
	public String hello() {
		
		log.info("FirstApiController의 hello 메서드 실행");
		
		return "hello world"; // 웹화면에 들어갈 데이터
		
	}
	
}
