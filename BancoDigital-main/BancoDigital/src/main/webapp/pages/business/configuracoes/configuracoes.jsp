<%@page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Configurações - Banco Digital</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/images/iconsite.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/configuracoes.css">
</head>
<body>

	<%
	Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
	TipoUsuario role = (TipoUsuario) session.getAttribute("role");
	%>

	<%
	if (usuarioLogado != null) {
	%>

	<div class="container">
		<header class="header">
			<div class="header-content">
				<div class="user-info">
					<div class="avatar">
						<%=usuarioLogado.getNome().substring(0, 1).toUpperCase()%>
					</div>
					<div class="user-details">
						<h1>
							Bem-vindo,
							<%=usuarioLogado.getNome()%>!
						</h1>
						<span class="user-role">Configurações da Conta</span>
					</div>
				</div>
				<div class="balance-card">
					<span class="balance-label">Saldo disponível</span>
					<h2 class="balance-amount">
						R$
						<%=String.format("%.2f", usuarioLogado.getSaldo())%></h2>
				</div>
			</div>
		</header>

		<main class="main-content">
			<h3 class="section-title">⚙️ Gerenciar Conta</h3>

			<div class="config-grid">
				<button class="config-card" onclick="modalEditar()">
					<div class="config-icon">✏️</div>
					<h4>Editar Dados</h4>
					<p>Atualize suas informações pessoais</p>
				</button>
				<button class="config-card" onclick="modalAlterarSenha()">
					<div class="config-icon">✏️</div>
					<h4>Trocar sua senha por email</h4>
					<p>Atualize suas informações pessoais</p>
				</button>
				

				<button class="config-card delete-card" onclick="modalDeletar()">
					<div class="config-icon">🗑️</div>
					<h4>Deletar Conta</h4>
					<p>Remover permanentemente sua conta</p>
				</button>

				<%
				if (role == TipoUsuario.ADMIN) {
				%>
				<a href="pessoa/todosUsuarios" class="config-card admin-card">
					<div class="config-icon">👥</div>
					<h4>Todos os Usuários</h4>
					<p>Gerenciar usuários do sistema</p>
				</a> <a href="pessoa/todosTransferencias" class="config-card admin-card">
					<div class="config-icon">💸</div>
					<h4>Todas as Transferências</h4>
					<p>Visualizar histórico completo</p>
				</a>
				<%
				}
				%>
			</div>

			<div class="action-buttons">
				<a href="home" class="btn-home">🏠 Voltar para Home</a> <a
					href="pages/login/login.jsp" class="btn-logout">🚪 Sair</a>
			</div>
		</main>

		<footer class="footer">
			<p>© 2025 Banco Digital - IFSP</p>
		</footer>
	</div>

	<!-- Modal Deletar -->
	<div id="modalDeletar" class="modal">
		<div class="modal-overlay" onclick="fecharModalDeletar()"></div>
		<div class="modal-content">
			<span class="fechar" onclick="fecharModalDeletar()">✕</span>
			<div class="modal-icon delete-icon">⚠️</div>
			<h2>Deletar Conta</h2>
			<p class="modal-warning">Esta ação é irreversível. Todos os seus
				dados serão permanentemente removidos.</p>
			<form action="${pageContext.request.contextPath}/pessoa/deletar"
				method="post">
				<input type="hidden" name="id" value="<%=usuarioLogado.getId()%>">
				<button type="submit" class="btn-confirm-delete">Sim,
					deletar minha conta</button>
				<button type="button" class="btn-cancel"
					onclick="fecharModalDeletar()">Cancelar</button>
			</form>
		</div>
	</div>
	
	<!-- Modal Alterar Senha -->
	<div id="modalAlterarSenha" class="modal">
	    <div class="modal-overlay" onclick="fecharModalAlterarSenha()"></div>
	
	    <div class="modal-content modal-confirm">
	        <span class="fechar" onclick="fecharModalAlterarSenha()">✕</span>
	
	        <h2>Alterar Senha</h2>
	
	        <p>Tem certeza que deseja alterar sua senha?</p>
	
	        <div class="modal-buttons">
	            <button class="btn-cancelar" onclick="fecharModalAlterarSenha()">Cancelar</button>
	
	            <form action="${pageContext.request.contextPath}/pessoa/enviarEmail" method="post">
	                <!-- caso precise usar id/codigo, coloca aqui como hidden -->
	                <input type="hidden" name="id" value="${usuarioLogado.id}">
	
	
	                <button type="submit" class="btn-confirmar">
	                    Confirmar
	                </button>
	            </form>
	        </div>
	    </div>
	</div>
	

	
	<!-- Modal Editar -->
	<div id="modalEditar" class="modal">
		<div class="modal-overlay" onclick="fecharModalEditar()"></div>
		<div class="modal-content modal-large">
			<span class="fechar" onclick="fecharModalEditar()">✕</span>
			<div class="modal-icon edit-icon">✏️</div>
			<h2>Editar Seus Dados</h2>
			<form action="${pageContext.request.contextPath}/pessoa/editar"
				method="post">
				<input type="hidden" name="id" value="<%=usuarioLogado.getId()%>">

				<div class="form-row">
					<div class="form-group">
						<label for="cpf">📋 CPF:</label> <input type="text" id="cpf"
							name="cpf" pattern="\d{3}\.?\d{3}\.?\d{3}-?\d{2}"
							placeholder="000.000.000-00"
							value="<%=usuarioLogado.getCpf()%>" required>
					</div>

					<div class="form-group">
						<label for="nome">👤 Nome Completo:</label> <input type="text"
							id="nome" name="nome" value="<%=usuarioLogado.getNome()%>"
							required>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="email">📧 E-mail:</label> <input type="email"
							id="email" name="email" value="<%=usuarioLogado.getEmail()%>"
							required>
					</div>

					<div class="form-group">
						<label for="telefone">📱 Telefone:</label> <input type="tel"
							id="telefone" name="telefone"
							pattern="[0-9]{2} [0-9]{4,5}-[0-9]{4}"
							placeholder="00 00000-0000"
							value="<%=usuarioLogado.getTelefone()%>" required>
					</div>
				</div>

				<div class="form-group">
					<label for="endereco">🏠 Endereço:</label> <input type="text"
						id="endereco" name="endereco"
						value="<%=usuarioLogado.getEndereco()%>" required>
				</div>

				<div class="form-group">
					<label for="senha">🔒 Senha:</label> <input type="password"
						id="senha" name="senha" value="<%=usuarioLogado.getSenha()%>"
						readonly>
				</div>

				<input type="hidden" id="saldo" name="saldo"
					value="<%=usuarioLogado.getSaldo()%>"> <input
					type="hidden" id="role" name="role"
					value="<%=usuarioLogado.getRole()%>">

				<div class="form-actions">
					<button type="submit" class="btn-save">💾 Salvar
						Alterações</button>
					<button type="button" class="btn-cancel"
						onclick="fecharModalEditar()">Cancelar</button>
				</div>
			</form>
		</div>
	</div>

	<%
	} else {
	%>

	<div class="access-denied">
		<div class="denied-card">
			<div class="denied-icon">🔒</div>
			<h1>Acesso Negado</h1>
			<p>Você precisa fazer login para acessar sua conta.</p>
			<a href="login" class="login-button">Ir para o Login</a>
		</div>
	</div>

	<footer class="footer">
		<p>© 2025 Banco Digital - IFSP</p>
	</footer>

	<%
	}
	%>

	<script
		src="${pageContext.request.contextPath}/assets/js/configuracoes.js"></script>

</body>
</html>