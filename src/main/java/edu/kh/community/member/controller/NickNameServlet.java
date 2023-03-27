package edu.kh.community.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.kh.community.model.service.MemberService;

@WebServlet("/member/nickName")
public class NickNameServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		// 파라미터로 닉네임 인풋 밸류 받아오기
		
		String nickName = req.getParameter("nickName");
		
		// 닉네임이 있으면 1 없으면 0으로 받아올거임
		int result = 0;
		
		try {
			MemberService service = new MemberService();
			
			result = service.SelectNickName(nickName);
			
			resp.getWriter().print(result);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
