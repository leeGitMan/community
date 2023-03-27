package edu.kh.community.model.dao;

import static edu.kh.community.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.community.model.vo.Member;

public class MemberDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	private Properties prop;
	
	
	public MemberDAO() {
		
		try {
			prop = new Properties();
			String filePath = MemberDAO.class.getResource("/edu/kh/community/sql/member-sql.xml").getPath();
			
			prop.loadFromXML(new FileInputStream(filePath));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	public Member selectOne(Connection conn, String memberEmail) throws Exception{
		
		Member member = new Member();
		
		
		
		try {
			
			
			String sql = prop.getProperty("selectOne");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberEmail);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				member = new Member();
				
				member.setMemberEmail(memberEmail);
				member.setMemberNickname(rs.getString(2));
				member.setMemberTel(rs.getString(3));
				member.setMemberAddress(rs.getString(4));
				member.setEnrollDate(rs.getString(5));
				
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return member;
	}



	// 인증번호 생성 DAO
	public int insertCertification(Connection conn, String inputEmail, String cNumber) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertCertification");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputEmail);
			pstmt.setString(2, cNumber);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			
			close(pstmt);
		}
		return result;
	}



	// 인증번호 업데이트 DAO
	public int updateCertification(Connection conn, String inputEmail, String cNumber) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateCertification");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cNumber);
			pstmt.setString(2, inputEmail);
			
			result = pstmt.executeUpdate();
		}finally {
			
			close(pstmt);
		}
		
		return result;
	}




	public String CheckCNumberServlet(Connection conn, String memberEmail) throws Exception{
		
		String result = null;
		
		try {
			
			String sql = prop.getProperty("CheckCNumberServlet");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberEmail);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("cNumber");
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}




	public int SelectNickName(Connection conn, String nickName) throws Exception{

		int result = 0;
		
		try {
			String sql = prop.getProperty("SelectNickName");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			rs = pstmt.executeQuery();
			
			if(rs.next())	result = 1;
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return result;
	}




	public List<Member> selectAll(Connection conn) throws Exception{
		
		List<Member> memberList = new ArrayList<>();
		
		
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int memberNo = rs.getInt(1);
				String memberEmail = rs.getString(2);
				String memberNickName = rs.getString(3);
				
				memberList.add(new Member(memberNo, memberEmail, memberNickName));
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		
		return memberList;
	}




	public Member login(Connection conn, Member mem) throws Exception{
		
		Member loginMember = null;
		
		try {
			
			String sql = prop.getProperty("loginMember");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem.getMemberEmail());
			pstmt.setString(2, mem.getMemberPw());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				loginMember = new Member();
				
				
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberEmail(rs.getString("MEMBER_EMAIL"));
				loginMember.setMemberNickname(rs.getString("MEMBER_NICK"));
				loginMember.setMemberTel(rs.getString("MEMBER_TEL"));
				loginMember.setMemberAddress(rs.getString("MEMBER_ADDR"));
				loginMember.setProfileImage(rs.getString("PROFILE_IMG"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DT"));
				
				
			}
			
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		
		
		return loginMember;
	}

}
