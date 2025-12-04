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
	ArrayList<Pessoa> lista = (ArrayList<Pessoa>) request.getAttribute("todosUsuarios");
%>

<% if (usuarioLogado != null || role == TipoUsuario.ADMIN) { %>
	<h2>Lista de Usuários</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Endereço</th>
            <th>Role</th>
            <th>Saldo</th>
        </tr>
    </thead>

    <tbody>
    <% 
        if (lista != null && !lista.isEmpty()) {
            for (Pessoa p : lista) { 
    %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNome() %></td>
            <td><%= p.getCpf() %></td>
            <td><%= p.getEmail() %></td>
            <td><%= p.getTelefone() %></td>
            <td><%= p.getEndereco() %></td>
            <td><%= p.getRole() %></td>
            <td>R$ <%= String.format("%.2f", p.getSaldo()) %></td>
        </tr>
    <% 
            }
        } else {
    %>
        <tr>
            <td colspan="8">Nenhum usuário encontrado.</td>
        </tr>
    <% 
        }
    %>
    </tbody>
</table>
	

<%} %>
</body>
</html>