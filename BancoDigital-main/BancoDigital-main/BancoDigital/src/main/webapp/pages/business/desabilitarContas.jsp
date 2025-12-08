<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Desabilitar Contas</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/desabilitar.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<div class="desabilitar-wrapper">
		<div class="desabilitar-container">
			<!-- Header -->
			<div class="desabilitar-header">
				<a href="<%=request.getContextPath()%>/home" class="back-link">
					<i class="bi bi-arrow-left"></i>
					Voltar
				</a>
				<div class="header-content">
					<div class="header-icon">
						<i class="bi bi-person-slash"></i>
					</div>
					<div>
						<h1 class="desabilitar-title">Desabilitar Contas</h1>
						<p class="desabilitar-subtitle">Gerencie contas ativas de clientes</p>
					</div>
				</div>
			</div>

			<!-- Success Message -->
			<% 
			String sucesso = (String) request.getAttribute("sucesss");
			if (sucesso != null && !sucesso.isEmpty()) {
			%>
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				<i class="bi bi-check-circle-fill me-2"></i>
				<strong>Sucesso!</strong> <%=sucesso%>
				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
			</div>
			<%
			}
			%>

			<!-- Search Card -->
			<div class="card search-card">
				<div class="card-body">
					<form action="<%=request.getContextPath()%>/pessoa/desabilitarContas" method="post">
						<div class="row g-3 align-items-end">
							<div class="col-md-9">
								<label for="termoBusca" class="form-label">
									<i class="bi bi-search me-2"></i>
									Pesquisar conta
								</label>
								<input 
									type="text" 
									name="termoBusca" 
									id="termoBusca"
									class="form-control form-control-lg" 
									placeholder="Digite o nome do cliente...">
							</div>
							<div class="col-md-3">
								<button type="submit" class="btn btn-primary btn-lg w-100">
									<i class="bi bi-search me-2"></i>Pesquisar
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>

			<!-- Accounts Table -->
			<%
			ArrayList<Pessoa> pessoasHabilitadas = (ArrayList<Pessoa>) request.getAttribute("pessoasHabilitadas");

			if (pessoasHabilitadas != null && !pessoasHabilitadas.isEmpty()) {
			%>
			<!-- Stats Card -->
			<div class="stats-card">
				<div class="stat-item">
					<i class="bi bi-people-fill"></i>
					<div>
						<div class="stat-value"><%=pessoasHabilitadas.size()%></div>
						<div class="stat-label">Contas ativas</div>
					</div>
				</div>
			</div>

			<!-- Table Card -->
			<div class="card table-card">
				<div class="card-body">
					<h5 class="card-title mb-4">
						<i class="bi bi-table me-2"></i>
						Contas Ativas
					</h5>
					<div class="table-responsive">
						<table class="table accounts-table" id="pessoasTable">
							<thead>
								<tr>
									<th>ID</th>
									<th>CPF</th>
									<th>Nome</th>
									<th>Email</th>
									<th class="text-end">Ação</th>
								</tr>
							</thead>
							<tbody>
							    <% 
							        for (Pessoa pessoa : pessoasHabilitadas) { 
							            // Pular ADMIN e GERENTE
							            if (pessoa.getRole().equals(TipoUsuario.ADMIN) || pessoa.getRole().equals(TipoUsuario.GERENTE)) continue;
							    %>
							
							    <tr>
							        <td>
							            <span class="badge bg-primary">#<%=pessoa.getId()%></span>
							        </td>
							        <td><strong><%=pessoa.getCpf()%></strong></td>
							
							        <td>
							            <div class="user-info">
							                <div class="user-avatar">
							                    <%= pessoa.getNome().substring(0, 1).toUpperCase() %>
							                </div>
							                <span><%= pessoa.getNome() %></span>
							            </div>
							        </td>
							
							        <td><%= pessoa.getEmail() %></td>
							
							        <td class="text-end">
							            <form action="<%=request.getContextPath()%>/pessoa/desabilitarContas" method="post" style="display:inline;">
							                <input type="hidden" name="idContaParaSerDesabilitada" value="<%= pessoa.getId() %>">
							
							                <button 
							                    type="submit" 
							                    class="btn btn-danger btn-sm" 
							                    onclick="return confirm('Tem certeza que deseja desabilitar a conta de <%=pessoa.getNome()%>?');">
							                    <i class="bi bi-slash-circle me-1"></i>
							                    Desabilitar
							                </button>
							            </form>
							        </td>
							    </tr>
							
							    <% } %>
							</tbody>

						</table>
					</div>
				</div>
			</div>
			<%
			} else {
			%>
			<!-- Empty State -->
			<div class="card empty-card">
				<div class="card-body">
					<div class="empty-state">
						<i class="bi bi-inbox"></i>
						<h4>Nenhuma conta ativa encontrada</h4>
						<p>Não há contas de clientes atualmente ativas ou nenhuma conta corresponde à sua pesquisa.</p>
					</div>
				</div>
			</div>
			<%
			}
			%>

			<!-- Back Link -->
			<div class="back-section">
				<a href="<%=request.getContextPath()%>/home" class="back-main-link">
					<i class="bi bi-house-door me-2"></i>
					Voltar para home
				</a>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/desabilitar.js"></script>
</body>
</html>