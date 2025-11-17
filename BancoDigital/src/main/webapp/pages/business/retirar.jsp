<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Retirar</title>
</head>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
%>

<body>
	<h1> Retirar </h1>
	
	<form action="${pageContext.request.contextPath}/pessoa/retirar" method="post">
    <label>Digite o valor que vai ser retirado</label><br>

    <label>Saldo atual: <%= usuarioLogado.getSaldo() %></label><br><br>

    <input type="number" name="valor" required><br><br>

    <input type="number" value="<%= usuarioLogado.getCpf() %>" disabled>

    <input type="hidden" name="cpf" value="<%= usuarioLogado.getCpf() %>">

    <br><br>

    <button type="submit">Retirar</button>
</form>

</body>
</html>