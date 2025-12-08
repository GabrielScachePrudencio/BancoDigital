<%@page import="java.util.List"%>
<%@page import="edu.br.ifsp.bank.modelo.Investimento"%>
<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>

<%
Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
List<Investimento> investimentos = (List<Investimento>) request.getAttribute("investimentos");

// Calcular totais
double totalInvestido = 0;
double totalLucro = 0;
if (investimentos != null && !investimentos.isEmpty()) {
    for (Investimento inv : investimentos) {
        totalInvestido += inv.getValor();
        totalLucro += inv.getLucroAteAgora();
    }
}
double totalGeral = totalInvestido + totalLucro;
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Investir</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/investir.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
</head>
<body>
	<div class="investir-wrapper">
		<div class="investir-container">
			<!-- Header -->
			<div class="investir-header">
				<a href="<%=request.getContextPath()%>/home" class="back-link">
					<i class="bi bi-arrow-left"></i>
					Voltar
				</a>
				<div class="header-content">
					<div class="header-icon">
						<i class="bi bi-graph-up-arrow"></i>
					</div>
					<div>
						<h1 class="investir-title">Aplicar em Investimentos</h1>
						<p class="investir-subtitle">Faça seu dinheiro crescer com segurança</p>
					</div>
				</div>
			</div>

			<!-- Messages -->
			<% if (request.getAttribute("erro") != null) { %>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<i class="bi bi-exclamation-triangle-fill me-2"></i>
				<strong>Erro!</strong> <%=request.getAttribute("erro")%>
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
			<% } %>

			<% if (request.getAttribute("msg") != null) { %>
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				<i class="bi bi-check-circle-fill me-2"></i>
				<strong>Sucesso!</strong> <%=request.getAttribute("msg")%>
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
			<% } %>

			<div class="row g-4">
				<!-- Left Column -->
				<div class="col-lg-5">
					<!-- Saldo Card -->
					<div class="card saldo-card">
						<div class="card-body">
							<div class="saldo-label">
								<i class="bi bi-wallet2 me-2"></i>
								Saldo disponível
							</div>
							<div class="saldo-value">
								R$ <%=String.format("%.2f", usuarioLogado.getSaldo())%>
							</div>
						</div>
					</div>

					<!-- Investment Form -->
					<div class="card form-card">
						<div class="card-body">
							<h5 class="card-title mb-4">
								<i class="bi bi-currency-dollar me-2"></i>
								Nova Aplicação
							</h5>
							<form action="<%=request.getContextPath()%>/pessoa/investir" method="post" id="investForm">
								<div class="form-group mb-3">
									<label for="valor" class="form-label">Valor a aplicar</label>
									<div class="input-group">
										<span class="input-group-text">R$</span>
										<input type="number" class="form-control" id="valor" name="valor" step="0.01" placeholder="0,00" required>
									</div>
								</div>

								<div class="form-group mb-3">
									<label for="taxa" class="form-label">Taxa de juros mensal (%)</label>
									<div class="input-group">
										<input type="number" class="form-control" id="taxa" name="taxa" step="0.01" placeholder="0,00" required>
										<span class="input-group-text">%</span>
									</div>
								</div>

								<div class="form-group mb-4">
									<label for="prazoDias" class="form-label">Prazo (dias)</label>
									<input type="number" class="form-control" id="prazoDias" name="prazoDias" placeholder="30" required>
								</div>

								<button type="submit" class="btn btn-primary w-100">
									<i class="bi bi-check-circle me-2"></i>Aplicar
								</button>
							</form>
						</div>
					</div>
				</div>

				<!-- Right Column -->
				<div class="col-lg-7">
					<!-- KPIs -->
					<% if (investimentos != null && !investimentos.isEmpty()) { %>
					<div class="row g-3 mb-4">
						<div class="col-md-4">
							<div class="kpi-card kpi-primary">
								<div class="kpi-icon">
									<i class="bi bi-cash-stack"></i>
								</div>
								<div class="kpi-content">
									<div class="kpi-label">Total Investido</div>
									<div class="kpi-value">R$ <%=String.format("%.2f", totalInvestido)%></div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="kpi-card kpi-success">
								<div class="kpi-icon">
									<i class="bi bi-graph-up"></i>
								</div>
								<div class="kpi-content">
									<div class="kpi-label">Lucro Acumulado</div>
									<div class="kpi-value">R$ <%=String.format("%.2f", totalLucro)%></div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="kpi-card kpi-info">
								<div class="kpi-icon">
									<i class="bi bi-pie-chart"></i>
								</div>
								<div class="kpi-content">
									<div class="kpi-label">Total Geral</div>
									<div class="kpi-value">R$ <%=String.format("%.2f", totalGeral)%></div>
								</div>
							</div>
						</div>
					</div>

					<!-- Chart Card -->
					<div class="card chart-card mb-4">
						<div class="card-body">
							<h5 class="card-title mb-3">
								<i class="bi bi-bar-chart-line me-2"></i>
								Visão Geral
							</h5>
							<canvas id="investmentChart" height="200"></canvas>
						</div>
					</div>
					<% } %>

					<!-- Investments Table -->
					<div class="card investments-card">
						<div class="card-body">
							<h5 class="card-title mb-4">
								<i class="bi bi-list-ul me-2"></i>
								Seus Investimentos
							</h5>

							<% if (investimentos != null && !investimentos.isEmpty()) { %>
							<div class="table-responsive">
								<table class="table investment-table">
									<thead>
										<tr>
											<th>Nome</th>
											<th>Valor</th>
											<th>Taxa</th>
											<th>Prazo</th>
											<th>Data</th>
											<th>Lucro</th>
											<th class="text-end">Ações</th>
										</tr>
									</thead>
									<tbody>
									<% for (Investimento inv : investimentos) { %>
										<tr>
											<td>
												<div class="investor-info">
													<div class="investor-avatar">
														<%=usuarioLogado.getNome().substring(0, 1).toUpperCase()%>
													</div>
													<span><%=usuarioLogado.getNome()%></span>
												</div>
											</td>
											<td><strong>R$ <%=String.format("%.2f", inv.getValor())%></strong></td>
											<td><span class="badge bg-primary"><%=String.format("%.2f", inv.getTaxaMensal() * 100)%>%</span></td>
											<td><%=inv.getPrazoDias()%> dias</td>
											<td><small><%=inv.getDataAplicacao().toString().replace("T", " ")%></small></td>
											<td><strong class="text-success">R$ <%=String.format("%.2f", inv.getLucroAteAgora())%></strong></td>
											<td class="text-end">
												<button type="button" class="btn btn-sm btn-outline-info me-1" onclick="showDetails(<%=inv.getId()%>, '<%=String.format("%.2f", inv.getValor())%>', '<%=String.format("%.2f", inv.getTaxaMensal() * 100)%>', '<%=inv.getPrazoDias()%>', '<%=String.format("%.2f", inv.getLucroAteAgora())%>')">
													<i class="bi bi-eye"></i>
												</button>
												<form action="<%=request.getContextPath()%>/pessoa/resgatar" method="post" style="display: inline;">
													<input type="hidden" name="idInvestimento" value="<%=inv.getId()%>">
													<button type="submit" class="btn btn-sm btn-success" onclick="return confirm('Deseja realmente resgatar este investimento?')">
														<i class="bi bi-cash-coin"></i>
													</button>
												</form>
											</td>
										</tr>
									<% } %>
									</tbody>
								</table>
							</div>
							<% } else { %>
							<div class="empty-state">
								<i class="bi bi-inbox"></i>
								<p>Você ainda não possui investimentos registrados.</p>
							</div>
							<% } %>
						</div>
					</div>
				</div>
			</div>

			<!-- Back Link -->
			<div class="back-section">
				<a href="<%=request.getContextPath()%>/home" class="back-main-link">
					<i class="bi bi-house-door me-2"></i>
					Voltar para home
				</a>
			</div>
		</div>
	</div>

	<!-- Modal Details -->
	<div class="modal fade" id="detailsModal" tabindex="-1">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="bi bi-info-circle me-2"></i>Detalhes do Investimento
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body" id="modalBody"></div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script>
		// Chart Data
		const investimentos = [
			<% if (investimentos != null && !investimentos.isEmpty()) {
				for (int i = 0; i < investimentos.size(); i++) {
					Investimento inv = investimentos.get(i);
			%>
				{
					valor: <%=inv.getValor()%>,
					lucro: <%=inv.getLucroAteAgora()%>,
					taxa: <%=inv.getTaxaMensal() * 100%>
				}<%= i < investimentos.size() - 1 ? "," : "" %>
			<% 	}
			} %>
		];
	</script>
	<script src="<%=request.getContextPath()%>/assets/js/investir.js"></script>
</body>
</html>