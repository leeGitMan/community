package edu.kh.community.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.kh.community.model.service.MemberService;
import edu.kh.community.model.vo.Member;

@WebServlet("/member/selectAll")
public class MemberSelectServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberService service = new MemberService();
		
		try {
			
			List<Member> memberList = new ArrayList<>();
			
			memberList = service.selectAll();
			
			new Gson().toJson(memberList, resp.getWriter());
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
