<%@page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Internet Banking - Home</title>
<base href="<%= request.getContextPath() %>/">


</head>
<body>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
	TipoUsuario role = (TipoUsuario) session.getAttribute("role");
%>

<% if (usuarioLogado != null) { %>

  <header>
    <h1>Bem-vindo, <%= usuarioLogado.getNome() %>!</h1>
    <h3>Saldo atual: R$ <%= String.format("%.2f", usuarioLogado.getSaldo()) %></h3>
  </header>

  <nav>
    <ul>
      <li><a href="pessoa/configuracoes">Configurações</a></li>
      <li><a href="pessoa/transferir">Transferência</a></li>
      <li><a href="pessoa/depositar">Depositar</a></li>
      <li><a href="pessoa/retirar">Retirar</a></li>
      <li><a href="pessoa/investir">Investir</a></li>
      <li><a href="pessoa/historico">Histórico</a></li>
    
    </ul>
  </nav>

  <main>
    <p>Escolha uma das opções acima para continuar.</p>
  </main>

<% } else { %>

  <header>
    <h1>Acesso negado</h1>
  </header>

  <main>
    <p>Você precisa fazer login para acessar sua conta.</p>
    <a href="login">Ir para o Login</a>
  </main>

<% } %>

<footer>
  <p>© 2025 Banco Digital - IFSP</p>
</footer>

</body>
</html>
