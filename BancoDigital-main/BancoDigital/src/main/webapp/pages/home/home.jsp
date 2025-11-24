<%@page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Internet Banking - Home</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/images/iconsite.png">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/home.css">
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
						<span class="user-role">Cliente</span>
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
			<h3 class="section-title">O que você deseja fazer?</h3>

			<nav class="menu-grid">
				<a href="pessoa/transferir" class="menu-card">
					<div class="menu-icon">💸</div>
					<h4>Transferência</h4>
					<p>Envie dinheiro para outras contas</p>
				</a> <a href="pessoa/depositar" class="menu-card">
					<div class="menu-icon">💰</div>
					<h4>Depositar</h4>
					<p>Adicione fundos à sua conta</p>
				</a> <a href="pessoa/retirar" class="menu-card">
					<div class="menu-icon">🏧</div>
					<h4>Retirar</h4>
					<p>Saque dinheiro da sua conta</p>
				</a> <a href="pessoa/emprestimoSac" class="menu-card">
					<div class="menu-icon">📊</div>
					<h4>Empréstimo SAC</h4>
					<p>Solicite um empréstimo</p>
				</a> <a href="pessoa/investir" class="menu-card">
					<div class="menu-icon">📈</div>
					<h4>Investir</h4>
					<p>Faça seu dinheiro crescer</p>
				</a> <a href="pessoa/historico" class="menu-card">
					<div class="menu-icon">📋</div>
					<h4>Histórico</h4>
					<p>Veja suas transações</p>
				</a> <a href="pessoa/configuracoes" class="menu-card">
					<div class="menu-icon">⚙️</div>
					<h4>Configurações</h4>
					<p>Gerencie sua conta</p>
				</a> <a href="pages/login/login.jsp" class="menu-card logout-card">
					<div class="menu-icon">🚪</div>
					<h4>Sair</h4>
					<p>Encerrar sessão</p>
				</a>
			</nav>

			<section class="news-section">
				<h3 class="section-title">📰 Últimas Notícias do Setor Bancário</h3>
				<div class="news-grid" id="newsContainer">
					<div class="loading-news">
						<div class="spinner"></div>
						<p>Carregando notícias...</p>
					</div>
				</div>
			</section>
		</main>

		<footer class="footer">
			<p>© 2025 Banco Digital - IFSP</p>
		</footer>
	</div>

	<%
	} else {
	%>

	<div class="access-denied">
		<div class="denied-card">
			<div class="denied-icon">🔒</div>
			<h1>Acesso negado</h1>
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

	<script src="<%=request.getContextPath()%>/assets/js/home.js"></script>
</body>
</html>