package edu.kh.community.model.service;

import java.sql.Connection;
import java.util.List;

import static edu.kh.community.common.JDBCTemplate.*;
import edu.kh.community.model.dao.MemberDAO;
import edu.kh.community.model.vo.Member;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();
	
	
	public Member selectOne(String memberEmail) throws Exception{
		
		// 커넥션 연결하기
		
		Connection conn = getConnection();
		
		Member member = dao.selectOne(conn, memberEmail);
		
		return member;
	}


	public int insertCertification(String inputEmail, String cNumber) throws Exception {
		
		int result = 0;
		
		Connection conn = getConnection();
		
		result = dao.updateCertification(conn, inputEmail, cNumber);
		
		if(result == 0) {
			result = dao.insertCertification(conn, inputEmail, cNumber);
		}
		
		if(result == 1)	commit(conn);
		else 			rollback(conn);
		
		close(conn);
		
		
		return result;
	}


	public String CheckCNumberServlet(String memberEmail) throws Exception {
		
		String result = null;
		
		Connection conn = getConnection();
		
		result = dao.CheckCNumberServlet(conn, memberEmail);
		
		close(conn);
		
		return result;
		
		
	}


	public int SelectNickName(String nickName) throws Exception{
		
		int result = 0;
		Connection conn = getConnection();
		result = dao.SelectNickName(conn, nickName);
		close(conn);
		return result;
	}


	public List<Member> selectAll() throws Exception {
		
		
		
		Connection conn = getConnection();
		
		List<Member> memberList = dao.selectAll(conn);
		
		close(conn);
		
		
		return memberList;
	}
	
	
	

}
