<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Depositar</title>
</head>

<%
Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
%>

<body>
	<h1>Depositar</h1>

	<form action="${pageContext.request.contextPath}/pessoa/depositar"
		method="post">
		<label>Digite o valor que vai ser depositado</label><br> <label>Saldo
			atual: <%=usuarioLogado.getSaldo()%></label><br> <br> <input
			type="number" name="valor" required><br> <br> <input
			type="number" value="<%=usuarioLogado.getCpf()%>" disabled> <input
			type="hidden" name="cpf" value="<%=usuarioLogado.getCpf()%>">
		<br> <br>

		<button type="submit">Depositar</button>
	</form>

	<br>
	<br>

</body>
</html>