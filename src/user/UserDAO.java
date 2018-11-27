package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?verifyServerCertificate=false&useSSL=true";//dhddlrkwhr?verifyServerCertificate=false&useSSL=true"
			String dbID = "root";
			String dbPassword = "root1234";
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이버 연결성공");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			System.out.println("데이터베이스 연결성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);// 위의 SQL 의 1번쨰 ? 에 매개변수로드렁온userID를 넣어줌
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(userPassword))
					return 1; // 로그인 성공
				else
					return 0; // 비밀번호 불일치
			}
			return -1; // 아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}

	public int join(User user) {

		String SQL = "INSERT INTO USER VALUES (?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID()); // 위의 SQL 의1번쨰 ? 에 매개변수로 들어온 user 의 안에 필드로있는 user.getuserID 로 꺼내와서 넣어줌
			pstmt.setString(2, user.getUserPassword()); // 위의 SQL 의2번쨰 ? 에
			pstmt.setString(3, user.getUserName()); // 위의 SQL 의3번쨰 ? 에
			pstmt.setString(4, user.getUserGender()); // 위의 SQL 의4번쨰 ? 에
			pstmt.setString(5, user.getUserEmail()); // 위의 SQL 의5번쨰 ? 에
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 리턴타입이 int insert 문장이 정상적으로 실행된다면 0이상의 값이 리턴된단다 데이터베이스 오류

	}
}
