<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean  class="user.User" id="user" scope="page"/>
<jsp:setProperty property="userID" name="user"/>
<jsp:setProperty property="userPassword" name="user"/>
<jsp:setProperty property="userName" name="user"/>
<jsp:setProperty property="userGender" name="user"/>
<jsp:setProperty property="userEmail" name="user"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>JSP 게시판</title>
</head>
<body>
	<%
		String userID=null;
		if	(session.getAttribute("userID") !=null){
		userID=(String)session.getAttribute("userID");
		}
		if	(userID!=null){
			PrintWriter script =response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다')");
			script.println("location.href='main.jsp'");
			script.println("</script>");
		}
		
		if(user.getUserID()==null||user.getUserPassword()==null||user.getUserName()==null||user.getUserGender()==null||user.getUserEmail()==null){
			PrintWriter script =response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된사항이 있습니다')");
			script.println("<history.back()");
			script.println("</script>");
		}else{
			UserDAO userDAO = new UserDAO();
			int result = userDAO.join(user);
			if(result == -1){
				PrintWriter script =response.getWriter();
				script.println("<script>");
				script.println("alert('존재하는 아이디입니다')");
				script.println("<history.back()");
				script.println("</script>");
			}else {
				session.setAttribute("userID", user.getUserID());			//회원가입에 성공하였을때  세션에 사용자의 id값으로  userID란 세션을 부여한다
				PrintWriter script =response.getWriter();
				script.println("<script>");
				script.println("location.href='main.jsp'");
				script.println("</script>");
			}
			
		}
	%>
</body>
</html>