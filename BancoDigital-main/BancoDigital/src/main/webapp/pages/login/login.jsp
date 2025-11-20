<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Banco Digital</title>
</head>
<body>
	<h1>Banco Digital IFSP</h1>
	
	<h2>Login</h2>
	<%
	if (request.getAttribute("error") != null) {
	%>
	<p>Nome de usuário e senha inválidos.</p>
	<%
	}
	%>
	
	<form action="<%=request.getContextPath() %>/login" method="post">
		<label>Usuário: </label>
		<input type="text" name="user" required>
		<br>
		
		<label>Senha: </label>
		<input type="password" name="password" required>
		<br>
		
		<%
		if (request.getParameter("next") != null) {
		%>
		<input type="hidden" name="next" value="<%= request.getParameter("next") %>">
		<%
		}
		%>
		
		<button type="submit">Enviar</button>
		
		<br>
		<a href="<%=request.getContextPath() %>/pessoa/cadastrar">Crie uma conta</a>
	</form>
	
	
	
</body>
</html>