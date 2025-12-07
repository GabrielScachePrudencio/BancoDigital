<%@ page import="java.util.List" %>
<%@ page import="edu.br.ifsp.bank.modelo.HistoricoItem" %>
<%@ page import="edu.br.ifsp.bank.modelo.Transferencia" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    List<HistoricoItem> itens = (List<HistoricoItem>) request.getAttribute("itensHistorico");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Histórico</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/historico.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
</head>
<body>
	<div class="historico-wrapper">
		<div class="historico-container">
			<!-- Header -->
			<div class="historico-header">
				<a href="<%=request.getContextPath()%>/home" class="back-link">
					<i class="bi bi-arrow-left"></i>
					Voltar
				</a>
				<div class="header-content">
					<div class="header-icon">
						<i class="bi bi-clock-history"></i>
					</div>
					<div>
						<h1 class="historico-title">Histórico de Transações</h1>
						<p class="historico-subtitle">Acompanhe todas as suas movimentações</p>
					</div>
				</div>
			</div>

			<!-- Filters -->
			<div class="filters-section">
				<div class="row g-3">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-text">
								<i class="bi bi-search"></i>
							</span>
							<input type="text" class="form-control" id="searchInput" placeholder="Buscar transações...">
						</div>
					</div>
					<div class="col-md-3">
						<select class="form-select" id="filterType">
							<option value="">Todos os tipos</option>
							<option value="transferencia">Transferências</option>
							<option value="deposito">Depósitos/Retiradas</option>
						</select>
					</div>
					<div class="col-md-3">
						<select class="form-select" id="filterPeriod">
							<option value="">Todo período</option>
							<option value="hoje">Hoje</option>
							<option value="semana">Última semana</option>
							<option value="mes">Último mês</option>
						</select>
					</div>
					<div class="col-md-2">
						<button class="btn btn-outline-primary w-100" id="clearFilters">
							<i class="bi bi-x-circle me-2"></i>Limpar
						</button>
					</div>
				</div>
			</div>

			<!-- Transactions Table -->
			<div class="table-section">
				<%
				if (itens == null || itens.isEmpty()) {
				%>
				<div class="empty-state">
					<div class="empty-icon">
						<i class="bi bi-inbox"></i>
					</div>
					<h3>Nenhuma transação encontrada</h3>
					<p>Você ainda não possui transações no seu histórico.</p>
					<a href="<%=request.getContextPath()%>/pessoa/transferir" class="btn btn-primary mt-3">
						<i class="bi bi-plus-circle me-2"></i>Nova transação
					</a>
				</div>
				<%
				} else {
				%>
				<div class="table-responsive">
					<table class="table table-hover historico-table">
						<thead>
							<tr>
								<th>Data / Hora</th>
								<th>Tipo</th>
								<th>Valor</th>
								<th>Remetente</th>
								<th>Destinatário</th>
								<th class="text-end">Ações</th>
							</tr>
						</thead>
						<tbody id="transactionsBody">
						<%
							for (HistoricoItem item : itens) {
								Transferencia t = item.getTransferencia();
								boolean isDepositoRetirada = t.getId_usuarioQueTransferiu() == t.getId_usuarioQueRecebeu();
								String tipo = isDepositoRetirada ? "Depósito/Retirada" : "Transferência";
								String tipoClass = isDepositoRetirada ? "deposito" : "transferencia";
								String icon = isDepositoRetirada ? "bi-arrow-down-up" : "bi-arrow-right-circle";
						%>
							<tr class="transaction-row" data-tipo="<%=tipoClass%>">
								<td>
									<div class="transaction-date">
										<i class="bi bi-calendar3 me-2"></i>
										<%=t.getHorario()%>
									</div>
								</td>
								<td>
									<span class="badge badge-<%=tipoClass%>">
										<i class="bi <%=icon%> me-1"></i>
										<%=tipo%>
									</span>
								</td>
								<td>
									<div class="transaction-value">
										R$ <%=String.format("%.2f", t.getValor())%>
									</div>
								</td>
								<td>
									<div class="user-info">
										<div class="user-avatar">
											<%=item.getNomeRemetente().substring(0, 1).toUpperCase()%>
										</div>
										<div class="user-details">
											<div class="user-name"><%=item.getNomeRemetente()%></div>
											<div class="user-cpf"><%=item.getCpfRemetente()%></div>
										</div>
									</div>
								</td>
								<td>
									<div class="user-info">
										<div class="user-avatar">
											<%=item.getNomeDestinatario().substring(0, 1).toUpperCase()%>
										</div>
										<div class="user-details">
											<div class="user-name"><%=item.getNomeDestinatario()%></div>
											<div class="user-cpf"><%=item.getCpfDestinatario()%></div>
										</div>
									</div>
								</td>
								<td class="text-end">
									<button class="btn btn-sm btn-outline-primary" 
										onclick="showDetails('<%=t.getHorario()%>', '<%=tipo%>', '<%=String.format("%.2f", t.getValor())%>', '<%=item.getNomeRemetente()%>', '<%=item.getNomeDestinatario()%>')">
										<i class="bi bi-eye"></i>
									</button>
								</td>
							</tr>
						<%
							}
						%>
						</tbody>
					</table>
				</div>

				<!-- Summary -->
				<div class="summary-section">
					<div class="row g-3">
						<div class="col-md-4">
							<div class="summary-card">
								<div class="summary-icon total">
									<i class="bi bi-list-ul"></i>
								</div>
								<div class="summary-content">
									<div class="summary-label">Total de transações</div>
									<div class="summary-value" id="totalTransactions"><%=itens.size()%></div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="summary-card">
								<div class="summary-icon income">
									<i class="bi bi-arrow-down-circle"></i>
								</div>
								<div class="summary-content">
									<div class="summary-label">Total recebido</div>
									<div class="summary-value income" id="totalIncome">
										<%
											double totalRecebido = 0;
											for (HistoricoItem item : itens) {
												// Aqui você pode adicionar lógica para calcular recebimentos
											}
										%>
										R$ <%=String.format("%.2f", totalRecebido)%>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="summary-card">
								<div class="summary-icon expense">
									<i class="bi bi-arrow-up-circle"></i>
								</div>
								<div class="summary-content">
									<div class="summary-label">Total enviado</div>
									<div class="summary-value expense" id="totalExpense">
										<%
											double totalEnviado = 0;
											for (HistoricoItem item : itens) {
												// Aqui você pode adicionar lógica para calcular envios
											}
										%>
										R$ <%=String.format("%.2f", totalEnviado)%>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<!-- Modal de Detalhes -->
	<div class="modal fade" id="detailsModal" tabindex="-1">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="bi bi-info-circle me-2"></i>Detalhes da Transação
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body" id="modalBody">
					<!-- Conteúdo será inserido via JavaScript -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/historico.js"></script>
</body>
</html>