package edu.kh.community.common.wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncryptWrapper extends HttpServletRequestWrapper{

	public EncryptWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	public String getParameter(String name) {
		// 매개변수 name은 input 그의 name 속성
		// super.getParameter(name) : 기존 getParameter() 메서드
		
		String value = null;
		// 값을 담을 변수 선언
		
		switch(name) { // 케이스 구문으로 여러가지 종류 비밀번호 선택
		case "inputPw" :
		case "memberPw" :
		case "currentPw" :
		case "newPw" :
			
			value = getSha512(super.getParameter(name));
			break;
		
		default : super.getParameter(name); // 기존 비밀번호
		}
		return value;
	}

	private String getSha512(String pw) {
		
		// 매개변수 pw : 암호화 되기 전 비밀번호
		
		String encryptPw = null; // 암호화된 비밀번호 저장변수
		
		// 1. 해시함수 수행할 객체를 참조할 변수 선언
		
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-512");
			// SHA-512 방식의 해시 함수를 수행할 수 있는 객체 얻어오기
			
			// md를 
			byte[] bytes = pw.getBytes(Charset.forName("UTF-8"));
			md.update(bytes);
			
			
			// 5. 암호화된 비밀번호를 enctyptPw에 대입
			// -> byte[]을 String으로 변환할 필요가 있음
			//  Base64: byte코드를 문자열로 변환하는 객체
			encryptPw = Base64.getEncoder().encodeToString(md.digest());
			
			System.out.println("암호화 전  : " + pw);
			System.out.println("암호화 후 : " + encryptPw);
			
			
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return encryptPw;
	}
	
	
	
	

}
