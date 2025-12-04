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
			<h1>Digite o codido Do Email</h1>
		</div>

		<div class="form-container">
			<%
			if (request.getAttribute("error") != null) {
			%>
			<div class="error-message">
				codigo errado
			</div>
			<%
			}
			%>

			<form action="<%=request.getContextPath()%>/verificacaoCodigo" method="post">
				<div class="form-group">
					<label for="user"><span class="icon">👤</span>Codigo:</label>
					 <input
						type="text" id="codigo" name="codigo" required
						placeholder="Digite o codigo">
				</div>

				<%
				if (request.getParameter("next") != null) {
				%>
				<input type="hidden" name="next"
					value="<%=request.getParameter("next")%>">
				<%
				}
				%>

				<button type="submit">Validar</button>
			</form>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/assets/js/login.js"></script>
</body>
</html>