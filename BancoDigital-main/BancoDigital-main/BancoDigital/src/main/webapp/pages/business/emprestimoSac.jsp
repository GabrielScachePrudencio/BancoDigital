<%@page import="java.util.List"%>
<%@page import="edu.br.ifsp.bank.modelo.ParcelaSac"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    List<ParcelaSac> parcelas = (List<ParcelaSac>) request.getAttribute("parcelas");
    Double totalPago = (Double) request.getAttribute("totalPago");
    Double totalJuros = (Double) request.getAttribute("totalJuros");
    String erro = (String) request.getAttribute("erro");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Empréstimo SAC</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/emprestimo.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
</head>
<body>
	<div class="emprestimo-wrapper">
		<div class="emprestimo-container">
			<!-- Header -->
			<div class="emprestimo-header">
				<a href="<%=request.getContextPath()%>/home" class="back-link">
					<i class="bi bi-arrow-left"></i>
					Voltar
				</a>
				<div class="header-content">
					<div class="header-icon">
						<i class="bi bi-cash-stack"></i>
					</div>
					<div>
						<h1 class="emprestimo-title">Simular Empréstimo</h1>
						<p class="emprestimo-subtitle">Sistema SAC - Amortização Constante</p>
					</div>
				</div>
			</div>

			<!-- Error Message -->
			<% if (erro != null) { %>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<i class="bi bi-exclamation-triangle-fill me-2"></i>
				<strong>Erro!</strong> <%=erro%>
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
			<% } %>

			<div class="row g-4">
				<!-- Left Column - Form -->
				<div class="col-lg-4">
					<div class="card simulator-card">
						<div class="card-body">
							<h5 class="card-title mb-4">
								<i class="bi bi-calculator me-2"></i>
								Simulador
							</h5>
							<form action="<%=request.getContextPath()%>/pessoa/emprestimoSac" method="post" id="loanForm">
								<div class="form-group mb-3">
									<label for="valor" class="form-label">Valor do empréstimo (R$)</label>
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
									<label for="prazo" class="form-label">Prazo (meses)</label>
									<input type="number" class="form-control" id="prazo" name="prazo" placeholder="12" required>
								</div>

								<button type="submit" class="btn btn-primary w-100">
									<i class="bi bi-graph-up me-2"></i>Simular
								</button>
							</form>

							<!-- Info Box -->
							<div class="info-box mt-4">
								<div class="info-header">
									<i class="bi bi-info-circle"></i>
									<strong>Sistema SAC</strong>
								</div>
								<p class="info-text">
									No sistema SAC, a amortização é constante e os juros diminuem a cada parcela, 
									resultando em prestações decrescentes.
								</p>
							</div>
						</div>
					</div>
				</div>

				<!-- Right Column - Results -->
				<div class="col-lg-8">
					<% if (parcelas != null && !parcelas.isEmpty()) { %>
					
					<!-- KPIs -->
					<div class="row g-3 mb-4">
						<div class="col-md-4">
							<div class="kpi-card kpi-primary">
								<div class="kpi-icon">
									<i class="bi bi-cash"></i>
								</div>
								<div class="kpi-content">
									<div class="kpi-label">Total a Pagar</div>
									<div class="kpi-value">R$ <%=String.format("%.2f", totalPago)%></div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="kpi-card kpi-danger">
								<div class="kpi-icon">
									<i class="bi bi-percent"></i>
								</div>
								<div class="kpi-content">
									<div class="kpi-label">Total em Juros</div>
									<div class="kpi-value">R$ <%=String.format("%.2f", totalJuros)%></div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="kpi-card kpi-info">
								<div class="kpi-icon">
									<i class="bi bi-calendar-range"></i>
								</div>
								<div class="kpi-content">
									<div class="kpi-label">Parcelas</div>
									<div class="kpi-value"><%=parcelas.size()%>x</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Chart -->
					<div class="card chart-card mb-4">
						<div class="card-body">
							<h5 class="card-title mb-3">
								<i class="bi bi-pie-chart me-2"></i>
								Composição do Empréstimo
							</h5>
							<div style="height: 300px; display: flex; align-items: center; justify-content: center;">
								<canvas id="loanChart"></canvas>
							</div>
						</div>
					</div>

					<!-- Table -->
					<div class="card table-card">
						<div class="card-body">
							<h5 class="card-title mb-4">
								<i class="bi bi-table me-2"></i>
								Tabela de Amortização
							</h5>
							<div class="table-responsive">
								<table class="table loan-table">
									<thead>
										<tr>
											<th>Parcela</th>
											<th>Amortização</th>
											<th>Juros</th>
											<th>Valor da Parcela</th>
											<th>Saldo Devedor</th>
										</tr>
									</thead>
									<tbody>
									<% for (ParcelaSac p : parcelas) { %>
										<tr>
											<td>
												<span class="badge bg-primary">
													#<%=p.getNumero()%>
												</span>
											</td>
											<td><strong>R$ <%=String.format("%.2f", p.getAmortizacao())%></strong></td>
											<td class="text-danger">R$ <%=String.format("%.2f", p.getJuros())%></td>
											<td><strong class="text-primary">R$ <%=String.format("%.2f", p.getValorParcela())%></strong></td>
											<td class="text-muted">R$ <%=String.format("%.2f", p.getSaldoDevedor())%></td>
										</tr>
									<% } %>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<% } else { %>
					<!-- Empty State -->
					<div class="card empty-card">
						<div class="card-body">
							<div class="empty-state">
								<i class="bi bi-calculator"></i>
								<h4>Simule seu Empréstimo</h4>
								<p>Preencha os campos ao lado para ver a simulação completa com gráficos e tabelas.</p>
							</div>
						</div>
					</div>
					<% } %>
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

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script>
		// Chart Data
		const parcelas = [
			<% if (parcelas != null && !parcelas.isEmpty()) {
				for (int i = 0; i < parcelas.size(); i++) {
					ParcelaSac p = parcelas.get(i);
			%>
				{
					numero: <%=p.getNumero()%>,
					amortizacao: <%=p.getAmortizacao()%>,
					juros: <%=p.getJuros()%>,
					valorParcela: <%=p.getValorParcela()%>,
					saldoDevedor: <%=p.getSaldoDevedor()%>
				}<%= i < parcelas.size() - 1 ? "," : "" %>
			<% 	}
			} %>
		];
	</script>
	<script src="<%=request.getContextPath()%>/assets/js/emprestimo.js"></script>
</body>
</html>