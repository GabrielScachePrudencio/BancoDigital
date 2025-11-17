<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Cadastrar Nova Pessoa</title>
	<base href="<%= request.getContextPath() %>/">
 
</head>
<body>
	<h1>Loja Patterns - Cadastro</h1>
	
    <%

	if (request.getAttribute("error") != null) {
	%>
	<p style="color: red;">Erro ao tentar cadastrar: <%= request.getAttribute("error") %></p>
	<%
	}
	%>
    
    <%
	if (request.getAttribute("success") != null) {
	%>
	<p style="color: green;">Sucesso: <%= request.getAttribute("success") %></p>
	<%
	}
	%>

	<h2>Crie Sua Conta</h2>
	
	<form action="pessoa/cadastrar" method="post">
		
		<label for="nome">Nome Completo:</label>
		<input type="text" id="nome" name="nome" required>
		<br>
        
		<label for="cpf">CPF:</label>
		<input type="text" id="cpf" name="cpf" pattern="\d{3}\.?\d{3}\.?\d{3}-?\d{2}" placeholder="000.000.000-00" required>
		<br>
		
		<label for="email">E-mail:</label>
		<input type="email" id="email" name="email" required>
		<br>
		
		<label for="telefone">Telefone:</label>
		<input type="tel" id="telefone" name="telefone" pattern="[0-9]{2} [0-9]{4,5}-[0-9]{4}" placeholder="00 00000-0000" required>
		<br>
		
		<label for="senha">Senha:</label>
		<input type="password" id="senha" name="senha" required>
		<br>

        <label for="endereco">Endereço:</label>
		<input type="text" id="endereco" name="endereco" required>
		<br>
		
		<button type="submit">Cadastrar</button>

	</form>
	
</body>
</html>