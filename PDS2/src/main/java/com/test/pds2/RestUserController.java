package com.test.pds2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* 이것의 리턴값은 페이지가 아니다. 객체로 리턴된다.
 * 스프링에서 비동기 요청에 특화된 컨트롤러 
 * 이것이 리턴할 수 있는 뷰를 만들어 주어야 한다.
 * 
 * xml, json 라이브러리를 추가하면 리턴이 각각 xml, json형태로 리턴된다.
 *  */
@RestController
public class RestUserController {
	@RequestMapping(value="/idCheck", method=RequestMethod.POST)
	public String idCheck(@RequestParam(value="id") String id) {
		System.out.println("idCheck : " + id);
		
		return "T"; // 사용가능하면 T, 불가능하면 F
	}
}
