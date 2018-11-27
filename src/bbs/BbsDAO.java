package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BbsDAO {

	private Connection conn;
	
	private ResultSet rs;

	public BbsDAO() {
		try {									 
			String dbURL = "jdbc:mysql://localhost:3306/BBS?verifyServerCertificate=false&useSSL=true";//dhddlrkwhr?verifyServerCertificate=false&useSSL=true"
			String dbID = "root";
			String dbPassword = "root1234";
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이버 연결성공");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			System.out.println("게시판 데이터베이스 연결성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		String SQL="SELECT NOW();";
		try {
			 PreparedStatement pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);		//sql화면 참고	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  					//데이터베이스 오류
	}
	
	
	public	int getNext() {
		String SQL="SELECT bbsID FROM bbs ORDER BY bbsID DESC";
		try {
			 PreparedStatement pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;			
			}return 1; 							//첫번쨰 게시글인경우 1반환
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("게시글번호 불러오기");
		return -1 ;  					//데이터베이스 오류
	}
	public int	write(String bbsTitle,String userID,String bbsContent) {
		String SQL="INSERT INTO bbs VALUES(?,?,?,?,?,?)";
		try {
			 PreparedStatement pstmt=conn.prepareStatement(SQL);
			 pstmt.setInt(1, getNext());
			 pstmt.setString(2, bbsTitle);
			 pstmt.setString(3, userID);
			 pstmt.setString(4, getDate());
			 pstmt.setString(5, bbsContent);
			 pstmt.setInt(6, 1);
			
			return pstmt.executeUpdate();			
									
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;  					//데이터베이스 오류
	}
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL="SELECT * FROM BBS WHERE bbsID <? AND bbsAvailable = 1 ORDER BY bbsID desc LIMIT 10";
		ArrayList<Bbs> list =new ArrayList<Bbs>();
		try {
			 PreparedStatement pstmt=conn.prepareStatement(SQL);
			 pstmt.setInt(1, getNext()-(pageNumber-1)* 10);
			 rs=pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;  	
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL="SELECT * FROM BBS WHERE bbsID <? AND bbsAvailable = 1 ";
		try {
			 PreparedStatement pstmt=conn.prepareStatement(SQL);
			 pstmt.setInt(1, getNext()-(pageNumber-1)* 10);
			 rs=pstmt.executeQuery();
			if(rs.next()) {
				return true;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;  	
	}
	public Bbs getBbs(int bbsID) {
		String SQL="SELECT * FROM BBS WHERE bbsID =? ";
	
		try {
			 PreparedStatement pstmt=conn.prepareStatement(SQL);
			 pstmt.setInt(1, bbsID);
			 rs=pstmt.executeQuery();
			if(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}return null;
	}
	public int update(int bbsId,String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle = ?,bbsContent = ? WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsId);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
		
	}
	public int delete(int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
}