package edu.kh.community.member.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import edu.kh.community.model.service.MemberService;

@WebServlet("/member/sendCheckNumber")
public class CheckCNumberServlet extends HttpServlet{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		// 디비 확인용 이메일 가져오기
		String memberEmail = req.getParameter("memberEamil");
		
		try {
			MemberService service = new MemberService();
			
			String result = service.CheckCNumberServlet(memberEmail);
			
			resp.getWriter().print(result);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	

}
