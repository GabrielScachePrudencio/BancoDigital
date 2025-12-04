<%@page import="edu.br.ifsp.bank.modelo.Transferencia"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Internet Banking - Home</title>
<base href="<%= request.getContextPath() %>/">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/configuracoes.css">


</head>
<body>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
	TipoUsuario role = (TipoUsuario) session.getAttribute("role");
	ArrayList<Transferencia> lista = (ArrayList<Transferencia>) request.getAttribute("todosTransferencias");
%>

<% if (usuarioLogado != null || role == TipoUsuario.ADMIN) { %>
	<h2>Lista de Transferências</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <tr>
            <th>ID</th>
            <th>Quem Transferiu (ID)</th>
            <th>Quem Recebeu (ID)</th>
            <th>Horário</th>
            <th>Valor</th>
        </tr>
    </thead>

    <tbody>
    <% 
        if (lista != null && !lista.isEmpty()) {
            for (Transferencia t : lista) { 
    %>
        <tr>
            <td><%= t.getId() %></td>
            <td><%= t.getId_usuarioQueTransferiu() %></td>
            <td><%= t.getId_usuarioQueRecebeu() %></td>
            <td><%= t.getHorario().toString().replace("T", " ") %></td>
            <td>R$ <%= String.format("%.2f", t.getValor()) %></td>
        </tr>
    <% 
            }
        } else {
    %>
        <tr>
            <td colspan="5">Nenhuma transferência encontrada.</td>
        </tr>
    <% 
        }
    %>
    </tbody>
</table>
	

<%} %>
</body>
</html>