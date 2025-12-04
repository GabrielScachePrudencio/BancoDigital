<%@page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Dashboard</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/home.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
</head>
<body>
	
	<%
	Pessoa usuarioLogado = (Pessoa) session	.getAttribute("usuarioLogado");
	TipoUsuario role = (TipoUsuario) session.getAttribute("role");
	%>

	<%
	if (usuarioLogado != null) {
	%>

	<aside class="sidebar">
		<div class="sidebar-header">
			<div class="logo">
				<div class="logo-icon">
					<i class="bi bi-bank2"></i>
				</div>
				<span class="logo-text">IFSP Bank</span>
			</div>
		</div>
		
		<nav class="sidebar-nav">
			<a href="#dashboard" class="nav-item active" data-section="dashboard">
				<i class="bi bi-speedometer2 nav-icon"></i>
				<span class="nav-text">Dashboard</span>
			</a>
			<a href="pessoa/historico" class="nav-item">
				<i class="bi bi-clock-history nav-icon"></i>
				<span class="nav-text">Histórico</span>
			</a>
			<a href="pessoa/configuracoes" class="nav-item">
				<i class="bi bi-gear nav-icon"></i>
				<span class="nav-text">Configurações</span>
			</a>
		</nav>

		<div class="sidebar-footer">
			<a href="pages/login/login.jsp" class="logout-btn">
				<i class="bi bi-box-arrow-left nav-icon"></i>
				<span class="nav-text">Sair</span>
			</a>
		</div>
	</aside>

	<main class="main-container">
		<header class="topbar">
			<div class="topbar-left">
				<button class="menu-toggle" id="menuToggle">
					<span></span>
					<span></span>
					<span></span>
				</button>
			</div>
			<div class="topbar-right">
				<button class="notification-btn">
					<i class="bi bi-bell-fill"></i>
					<span class="badge bg-danger">3</span>
				</button>
				<div class="user-menu">
					<div class="user-avatar">
						<%=usuarioLogado.getNome().substring(0, 1).toUpperCase()%>
					</div>
					<div class="user-info">
						<span class="user-name"><%=usuarioLogado.getNome()%></span>
						<span class="user-status">Online</span>
					</div>
				</div>
			</div>
		</header>

		<div class="content-section active" id="dashboard">
			<div class="container-fluid px-4">
				<div class="row mb-4">
					<div class="col-12">
						<div class="balance-card-main">
							<div class="balance-header">
								<span class="balance-label">Saldo Disponível</span>
								<button class="btn-icon">
									<i class="bi bi-three-dots-vertical"></i>
								</button>
							</div>
							<h2 class="balance-value" id="mainBalance">
								R$ <%=String.format("%.2f", usuarioLogado.getSaldo())%>
							</h2>
							<div class="balance-footer">
								<div class="card-chip"></div>
								<span class="card-number">•••• •••• •••• 4829</span>
							</div>
						</div>
					</div>
				</div>

				<section class="mb-5">
					<h3 class="section-title mb-4">Ações Rápidas</h3>
					<div class="row g-3">
						
						<% 
							// Verifica se o usuário está habilitado para as ações principais
							if (usuarioLogado.isHabilitadoPeloCliente()) { 
						%>
						
						<div class="col-6 col-md-4 col-lg-2">
							<a href="pessoa/transferir" class="action-card">
								<div class="action-icon transfer">
									<i class="bi bi-arrow-left-right"></i>
								</div>
								<span class="action-label">Transferir</span>
							</a>
						</div>
						
						<div class="col-6 col-md-4 col-lg-2">
							<a href="pessoa/depositar" class="action-card">
								<div class="action-icon deposit">
									<i class="bi bi-cash-coin"></i>
								</div>
								<span class="action-label">Depositar</span>
							</a>
						</div>
						
						<div class="col-6 col-md-4 col-lg-2">
							<a href="pessoa/retirar" class="action-card">
								<div class="action-icon withdraw">
									<i class="bi bi-wallet2"></i>
								</div>
								<span class="action-label">Sacar</span>
							</a>
						</div>
						
						<div class="col-6 col-md-4 col-lg-2">
							<a href="pessoa/emprestimoSac" class="action-card">
								<div class="action-icon loan">
									<i class="bi bi-graph-up-arrow"></i>
								</div>
								<span class="action-label">Empréstimo</span>
							</a>
						</div>
						
						<div class="col-6 col-md-4 col-lg-2">
							<a href="pessoa/investir" class="action-card">
								<div class="action-icon invest">
									<i class="bi bi-piggy-bank"></i>
								</div>
								<span class="action-label">Investir</span>
							</a>
						</div>
						
						<div class="col-6 col-md-4 col-lg-2">
							<a href="pessoa/historico" class="action-card">
								<div class="action-icon history">
									<i class="bi bi-clock-history"></i>
								</div>
								<span class="action-label">Histórico</span>
							</a>
						</div>
						
						<% 
							} else { // Se não estiver habilitado, mostra a mensagem de aviso
						%>
						
						<div class="col-12">
							<div class="alert alert-warning" role="alert">
								<i class="bi bi-exclamation-triangle-fill me-2"></i>
								Sua conta ainda não está habilitada para transações. Por favor, aguarde a aprovação.
							</div>
						</div>
						
						<% 
							} 
						%>

						<% if(role != null && role.equals(TipoUsuario.GERENTE)){ %>
							<div class="col-6 col-md-4 col-lg-2">
								<a href="pessoa/habilitarContas" class="action-card">
									<div class="action-icon settings">
										<i class="bi bi-person-check"></i>
									</div>
									<span class="action-label">HABILITAR CONTAS</span>
								</a>
								
								
							</div>
							
							<div class="col-6 col-md-4 col-lg-2">
								<a href="pessoa/desabilitarContas" class="action-card">
									<div class="action-icon settings">
										<i class="bi bi-person-check"></i>
									</div>
									<span class="action-label">DESABILITAR CONTAS</span>
								</a>
						<% } %>

					</div>
				</section>


				<div class="row g-4 mb-5">
					<div class="col-md-6">
						<div class="info-card">
							<div class="info-header">
								<h4 class="info-title">
									<i class="bi bi-shield-check text-success me-2"></i>
									Segurança em Primeiro Lugar
								</h4>
							</div>
							<p class="info-text">
								Suas transações são protegidas com criptografia de ponta e autenticação em múltiplos fatores.
							</p>
						</div>
					</div>
					<div class="col-md-6">
						<div class="info-card">
							<div class="info-header">
								<h4 class="info-title">
									<i class="bi bi-headset text-primary me-2"></i>
									Suporte 24/7
								</h4>
							</div>
							<p class="info-text">
								Nossa equipe está sempre disponível para ajudar você com qualquer dúvida ou necessidade.
							</p>
						</div>
					</div>
				</div>

				<section class="news-section">
					<h3 class="section-title mb-4">
						<i class="bi bi-newspaper me-2"></i>
						Notícias do Mercado Financeiro
					</h3>
					<div class="row g-4" id="newsContainer">
						<div class="col-12 text-center py-5">
							<div class="spinner-border text-primary" role="status">
								<span class="visually-hidden">Carregando...</span>
							</div>
							<p class="text-muted mt-3">Carregando notícias...</p>
						</div>
					</div>
				</section>
			</div>
		</div>
	</main>

	<%
	} else {
	%>

	<div class="access-denied">
		<div class="denied-card">
			<div class="denied-icon">
				<i class="bi bi-lock-fill"></i>
			</div>
			<h1>Acesso Negado</h1>
			<p>Você precisa fazer login para acessar sua conta.</p>
			<a href="login" class="btn btn-primary btn-lg">
				<i class="bi bi-box-arrow-in-right me-2"></i>
				Ir para o Login
			</a>
		</div>
	</div>

	<%
	}
	%>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/home.js"></script>
</body>
</html>	