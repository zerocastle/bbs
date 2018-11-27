<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean  class="bbs.Bbs" id="bbs" scope="page"/>
<jsp:setProperty property="bbsTitle" name="bbs"/>
<jsp:setProperty property="bbsContent" name="bbs"/>

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
		if	(userID==null){
			PrintWriter script =response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요')");
			script.println("location.href='login.jsp'");
			script.println("</script>");
		}else{
			if(bbs.getBbsTitle()==null||bbs.getBbsContent()==null){
				PrintWriter script =response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안된사항이 있습니다')");
				script.println("<history.back()");
				script.println("</script>");
			}else{
				BbsDAO bbsDAO = new BbsDAO();
				int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());
				if(result == -1){
					PrintWriter script =response.getWriter();
					script.println("<script>");
					script.println("alert('글쓰기에 실패 했습니다')");
					script.println("<history.back()");
					script.println("</script>");
				}else {
					
					PrintWriter script =response.getWriter();
					script.println("<script>");
					script.println("location.href='bbs.jsp'");
					script.println("</script>");
				}
		}
	}
	%>
</body>
</html>