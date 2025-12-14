<%@page import="edu.br.ifsp.bank.modelo.Transferencia"%>
<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Retirar</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/retirar.css">
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
</head>

<%
Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
Transferencia ultima = (Transferencia) session.getAttribute("ultimaTransferencia");
String tipoPdf = (String) session.getAttribute("tipoPdf");
String erro = (String) request.getAttribute("erro");
%>

<body>
<div class="retirar-wrapper">
<div class="retirar-container">

<!-- HEADER -->
<div class="retirar-header">
    <a href="<%=request.getContextPath()%>/home" class="back-link">
        <i class="bi bi-arrow-left"></i> Voltar
    </a>
    <div class="header-content">
        <div class="header-icon">
            <i class="bi bi-wallet2"></i>
        </div>
        <div>
            <h1 class="retirar-title">Retirada</h1>
            <p class="retirar-subtitle">Saque dinheiro da sua conta</p>
        </div>
    </div>
</div>

<!-- ====== SE JÁ SACOU → MOSTRA PDF ====== -->
<% if (ultima != null && "saque".equals(tipoPdf)) { %>

    <div class="card retirar-card text-center">
        <div class="card-body">
            <h4 class="mb-3 text-success">
                <i class="bi bi-check-circle-fill"></i>
                Saque realizado com sucesso!
            </h4>

            <form action="<%=request.getContextPath()%>/pessoa/pdf" method="get" class="mb-3">
                <input type="hidden" name="tipo" value="saque">
                <button type="submit" class="btn btn-danger btn-lg w-100">
                    <i class="bi bi-file-earmark-pdf"></i>
                    Gerar comprovante em PDF
                </button>
            </form>

            <a href="<%=request.getContextPath()%>/home" class="btn btn-outline-primary btn-lg w-100">
                <i class="bi bi-house-door"></i>
                Voltar para a Home
            </a>
        </div>
    </div>

<!-- ====== SENÃO → FORMULÁRIO NORMAL ====== -->
<% } else { %>

    <% if (erro != null) { %>
    <div class="alert alert-danger">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <%=erro%>
    </div>
    <% } %>

    <!-- SALDO -->
    <div class="card saldo-card">
        <div class="card-body">
            <div class="saldo-info">
                <div class="saldo-label">
                    <i class="bi bi-wallet2 me-2"></i>
                    Saldo disponível
                </div>
                <div class="saldo-value">
                    R$ <%=String.format("%.2f", usuarioLogado.getSaldo())%>
                </div>
            </div>
        </div>
    </div>

    <!-- FORM -->
    <div class="card retirar-card">
        <div class="card-body">
            <form action="<%=request.getContextPath()%>/pessoa/retirar" method="post">
                <div class="form-group mb-4">
                    <label class="form-label">
                        Valor para saque
                    </label>
                    <div class="input-group input-group-lg">
                        <span class="input-group-text">R$</span>
                        <input type="number" name="valor" step="0.01" min="0.01"
                               class="form-control" required>
                    </div>
                </div>

                <input type="hidden" name="cpf" value="<%=usuarioLogado.getCpf()%>">

                <button type="submit" class="btn btn-primary btn-lg w-100">
                    <i class="bi bi-check-circle"></i>
                    Confirmar saque
                </button>
            </form>
        </div>
    </div>

<% } %>

</div>
</div>
</body>
</html>
