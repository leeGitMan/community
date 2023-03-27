package edu.kh.community.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.kh.community.model.service.MemberService;
import edu.kh.community.model.vo.Member;

@WebServlet("/member/index")
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// POST 방식 요청 시 문자 인코딩이 서버 기본값으로 지정
		// -> 한글 깨짐 -> 문자 인코딩 변경 필요
		//req.setCharacterEncoding("UTF-8");
		
		// * 모든 doPost() 메서드에 인코딩 변경 코드를 작성해야함....(귀찮...)
		// ->  모든 요청(전달 방식 가리지 않음) 시 req, resp의 문자 인코딩을 UTF-8 변경
		//	 -> 필터(Filter)
		
		String inputEmail = req.getParameter("inputEmail");
		String inputPw = req.getParameter("inputPw");
		
		
		
		Member mem = new Member();
		mem.setMemberEmail(inputEmail);
		mem.setMemberPw(inputPw);
		
		try {
			MemberService service = new MemberService();
			
			Member loginMember = service.login(mem);
			
			HttpSession session = req.getSession();
			
			if(loginMember != null) { // 성공
				
				session.setAttribute("loginMember", loginMember);
				
				session.setMaxInactiveInterval(3600);
				// 초 단위로 작성
				// 얼마나 세션에 로그인 정보가 남아있을지 정하는 코드
				
				// =================================================================
				// 아이디 저장(Cookie)
				
				/* Cookie : 클라이언트(브라우저)에서 관리하는 파일
				 * 
				 * - 특정 주소 요청 시 마다
				 *   해당 주소와 연관된 쿠키 파일을 브라우저가 알아서 읽어옴
				 *   -> 읽어온 쿠키 파일 내용을 서버에 같이 전달
				 * 
				 * 생성 및 사용 방법
				 * 
				 * 1) 서버가 요청에 대한 응답을 할 때
				 *    쿠기를 생성한 후
				 *    응답에 쿠키를 담아서 클라이언트에게 전달
				 *    
				 * 2) 응답에 담긴 쿠키가 클라이언트에 파일형태로 저장
				 * 
				 * 3) 이후 특정 주소 요청 시
				 *    쿠키 파일을 브라우저가 찾아 자동으로 요청에 실어서 보냄.
				 *    
				 * 4) 서버는 요청에 실려온 쿠키 파일을 사용함.
				 * */
				
				// 쿠키 객체 생성
				//Cookie c = new Cookie("클라이언트쪽에 저장될 쿠키 이름", "쿠키 내용");
				Cookie c = new Cookie("saveId", inputEmail);
				
				if(req.getParameter("saveId") != null) {
					
					c.setMaxAge(60*60*24*30);
				}else {
					c.setMaxAge(0);
				}
				
				// 해당 쿠키 파일이 적용될 주소를 지정
				c.setPath( req.getContextPath()  );
				// req.getContextPath() : 최상위 주소(/community)
				// -> /community 로 시작하는 주소에서만 쿠키 적용
				
				
				// 응답 객체를 이용해서 클라이언트로 전달
				resp.addCookie(c); // 코드가 해석되는 순간 바로 전달
				
			}else {
				session.setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			}
			
			
			// 클라이언트  요청 -> 서버 요청 처리(Servlet) -> 응답 화면 만들어줘(JSP 위임)
			
			// 1. forward(요청 위임)
			// - Servlet으로 응답 화면 만들기가 불편하기 때문에
			//   JSP로 req, resp 객체를 위임하여
			//   요청에 대한 응답 화면을 대신 만듦.
			
			// *** 화면이 변경되도 요청 주소가 유지된다 ***
			
			// ex) 아.아 주세요  ->   주문 받음	  ->   바리스타가 만든 커피
			//		 클				  캐셔(Servlet)       응답 결과(JSP)
			
			
			
			// 2. Redirect(재요청)
			// - 현재 Servlet에서 응답 페이지를 만들지 않고
			//   응답 페이지를 만들 수 있는 
			//   다른 요청의 주소로 클라이언트를 이동 시킴(재요청)
			
			
			// - 클라이언트 재요청 
			//  -> 기존 HttpServletRequest/Response 제거
			//  -> 새로운 HttpServletRequest/Response 생성
			
			//  ---> 리다이렉트 시 request 객체가 유지되지 않기 때문에
			//       특정 데이터를 전달하거나 유지하고 싶으면
			//       session 또는 application 범위에 세팅해야 한다!
			
			//dispatcher.forward(req, resp);
			
			
			
			// CGV 카페
			// ex) 팝콘 주세요  ->   팝콘 파는 위치를 알려줌 ->  (클) 팝콘 파는 곳으로 이동
			//		    클		          캐셔(Servlet)           클라이언트의 다른 주소 재요청
				
			resp.sendRedirect( req.getContextPath() );
			// req.getContextPath() : 최상위 주소 (/community)
			
			// forward
			//req.getRequestDispatcher("../index.jsp").forward(req, resp);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
