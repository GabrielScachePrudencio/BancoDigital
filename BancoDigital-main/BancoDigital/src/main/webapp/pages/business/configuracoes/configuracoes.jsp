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
%>

<% if (usuarioLogado != null) { %>

  <header>
    <h1>Bem-vindo, <%= usuarioLogado.getNome() %>!</h1>
    <h3>Saldo atual: R$ <%= String.format("%.2f", usuarioLogado.getSaldo()) %></h3>
  </header>

<div id="modalDeletar" class="modal">
	<div class="modal-content">
		<span class="fechar" onclick="fecharModalDeletar()">fechar</span>
		<h2>Deletar conta</h2>
        <form action="${pageContext.request.contextPath}/pessoa/deletar" method="post">
        	<input type="hidden" name="id" value="<%= usuarioLogado.getId() %>"> 
        	<button type="submit">Tem certeza?</button>
        </form>
	</div>
	
</div>


<div id="modalEditar" class="modal">
	<div class="modal-content">
		<span class="fechar" onclick="fecharModalEditar()">fechar</span>
		<h2>Editar seus dados </h2>
        <form action="${pageContext.request.contextPath}/pessoa/editar" method="post">
        	<input type="hidden" name="id" value="<%= usuarioLogado.getId() %>"> 
			<br>
			<label for="cpf">CPF:</label>
			<input type="text" id="cpf" name="cpf" pattern="\d{3}\.?\d{3}\.?\d{3}-?\d{2}" placeholder="000.000.000-00" value="<%= usuarioLogado.getCpf() %>" required>
			<br>
        	<label for="nome">Nome Completo:</label>
			<input type="text" id="nome" name="nome" value="<%= usuarioLogado.getNome() %>" required>
			
			<br>
			<label for="senha">Senha:</label>
			<input type="password" id="senha" name="senha" value="<%= usuarioLogado.getSenha() %>" required>
	        
			<br>
			
			<label for="email">E-mail:</label>
			<input type="email" id="email" name="email"  value="<%= usuarioLogado.getEmail() %>" required>
			<br>
			
			<label for="telefone">Telefone:</label>
			<input type="tel" id="telefone" name="telefone" pattern="[0-9]{2} [0-9]{4,5}-[0-9]{4}" placeholder="00 00000-0000" value="<%= usuarioLogado.getTelefone() %>" required>			<br>
			
	        <label for="endereco">Endereço:</label>
			<input type="text" id="endereco" name="endereco" value="<%= usuarioLogado.getEndereco() %>" required>
			
			<input type="hidden" id="saldo" name="saldo" value="<%= usuarioLogado.getSaldo() %>" required>
			
			<input type="hidden" id="role" name="role" value="<%= usuarioLogado.getRole() %>" required>
			<br>
			
			<button type="submit">Salvar</button>
        </form>
	</div>
	
</div>

  <nav>
    <ul>
      <button onclick="modalEditar()"> Editar dados </button>
      <button onclick="modalDeletar()"> Deletar conta </button>
      <%
      	if(role == TipoUsuario.ADMIN){
      		%>
   				<li><a href="pessoa/todosUsuarios">todos os usuarios do sistemas</a></li>
      			<li><a href="pessoa/todosTransferencias">todos as trasnferencias do sistemas</a></li>
      		<%
      	}
      %>
      
      
      <li><a href="home">home</a></li>
   	<li><a href="logout">Sair</a></li>
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
	
<script src="${pageContext.request.contextPath}/assets/js/configuracoes.js"></script>
	
</body>
</html>