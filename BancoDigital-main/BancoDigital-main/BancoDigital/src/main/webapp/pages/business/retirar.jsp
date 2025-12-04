<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Retirar</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/transferir.css">
<link rel="icon" href="${pageContext.request.contextPath}/images/iconsite.png">
</head>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
    String erro = (String) request.getAttribute("erro");
    String sucesso = (String) request.getAttribute("sucesso");
%>

<body>

<div class="container">

    <h1>Retirada</h1>

    <% if (erro != null) { %>
        <p class="msg-erro"><%= erro %></p>
    <% } %>

    <% if (sucesso != null) { %>
        <p class="msg-sucesso"><%= sucesso %></p>
    <% } %>

    <form class="card" action="${pageContext.request.contextPath}/pessoa/retirar" method="post">
        <label>Digite o valor que deseja retirar</label>
        <input type="number" name="valor" step="0.01" min="0.01" required>

        <p>Saldo atual: R$ <%= String.format("%.2f", usuarioLogado.getSaldo()) %></p>

        <input type="hidden" name="cpf" value="<%= usuarioLogado.getCpf() %>">

        <button type="submit" class="btn btn-confirmar">Confirmar</button>
    </form>

    <a class="voltar" href="<%= request.getContextPath() + "/home" %>">Voltar</a>

</div>

</body>
</html>
