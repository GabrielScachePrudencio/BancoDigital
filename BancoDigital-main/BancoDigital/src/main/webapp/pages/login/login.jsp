<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Banco Digital</title>
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/images/iconsite.png">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/login.css">
</head>
<body>
	<div class="container">
		<div class="header">
			<h1>🏦 Banco Digital IFSP</h1>
			<h2>Login</h2>
		</div>

		<div class="form-container">
			<%
			if (request.getAttribute("error") != null) {
			%>
			<div class="error-message">⚠️ Nome de usuário e senha
				inválidos.</div>
			<%
			}
			%>

			<form action="<%=request.getContextPath()%>/login" method="post">
				<div class="form-group">
					<label for="user"><span class="icon">👤</span>Usuário:</label> <input
						type="text" id="user" name="user" required
						placeholder="Digite seu usuário">
				</div>

				<div class="form-group">
					<label for="password"><span class="icon">🔒</span>Senha:</label> <input
						type="password" id="password" name="password" required
						placeholder="Digite sua senha">
				</div>

				<%
				if (request.getParameter("next") != null) {
				%>
				<input type="hidden" name="next"
					value="<%=request.getParameter("next")%>">
				<%
				}
				%>

				<button type="submit">Entrar</button>

				
			</form>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/assets/js/login.js"></script>
</body>
</html>