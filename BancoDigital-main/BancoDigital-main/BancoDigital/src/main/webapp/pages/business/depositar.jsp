<%@page import="edu.br.ifsp.bank.modelo.Transferencia"%>
<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Depositar</title>

<base href="<%=request.getContextPath()%>/">

<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/depositar.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
</head>

<%
Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
Transferencia ultima = (Transferencia) session.getAttribute("ultimaTransferencia");
String tipoPdf = (String) session.getAttribute("tipoPdf");
%>

<body>
<div class="depositar-wrapper">
    <div class="depositar-container">

        <!-- HEADER -->
        <div class="depositar-header">
            <a href="<%=request.getContextPath()%>/home" class="back-link">
                <i class="bi bi-arrow-left"></i> Voltar
            </a>
            <div class="header-content">
                <div class="header-icon">
                    <i class="bi bi-cash-coin"></i>
                </div>
                <div>
                    <h1 class="depositar-title">Depositar</h1>
                    <p class="depositar-subtitle">Adicione dinheiro à sua conta</p>
                </div>
            </div>
        </div>

        <!-- SALDO -->
        <div class="card saldo-card">
            <div class="card-body">
                <div class="saldo-info">
                    <div class="saldo-label">
                        <i class="bi bi-wallet2 me-2"></i> Saldo atual
                    </div>
                    <div class="saldo-value">
                        R$ <%=String.format("%.2f", usuarioLogado.getSaldo())%>
                    </div>
                </div>
            </div>
        </div>

 
        <% if (ultima == null) { %>

        <div class="card depositar-card">
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/pessoa/depositar" method="post">

                    <div class="form-group mb-4">
                        <label class="form-label">
                            <i class="bi bi-cash-stack me-2"></i>
                            Digite o valor que vai ser depositado
                        </label>
                        <div class="input-group input-group-lg">
                            <span class="input-group-text">R$</span>
                            <input
                                type="number"
                                class="form-control"
                                name="valor"
                                step="0.01"
                                min="0.01"
                                required>
                        </div>
                    </div>

                    <input type="hidden" name="cpf" value="<%=usuarioLogado.getCpf()%>">

                    <button type="submit" class="btn btn-primary btn-lg w-100">
                        <i class="bi bi-check-circle me-2"></i>
                        Depositar
                    </button>
                </form>
            </div>
        </div>

        <% } else if ("deposito".equals(tipoPdf)) { %>

        <div class="card depositar-card text-center">
            <div class="card-body">

                <h4 class="text-success mb-4">
                    <i class="bi bi-check-circle-fill"></i>
                    Depósito realizado com sucesso!
                </h4>

                <form action="<%=request.getContextPath()%>/pessoa/pdf" method="get" class="mb-3">
                    <input type="hidden" name="tipo" value="deposito">
                    <button type="submit" class="btn btn-danger btn-lg w-100">
                        <i class="bi bi-file-earmark-pdf"></i>
                        Gerar comprovante em PDF
                    </button>
                </form>

                <a href="<%=request.getContextPath()%>/home" class="btn btn-outline-secondary btn-lg w-100">
                    <i class="bi bi-arrow-left"></i>
                    Voltar
                </a>

            </div>
        </div>

        <% } %>

        <!-- INFO -->
        <div class="card info-card">
            <div class="card-body">
                <div class="info-item">
                    <i class="bi bi-lightning-charge-fill"></i>
                    <div>
                        <strong>Depósito instantâneo</strong>
                        <p>O valor cai na hora em sua conta</p>
                    </div>
                </div>
                <div class="info-item">
                    <i class="bi bi-shield-check"></i>
                    <div>
                        <strong>100% seguro</strong>
                        <p>Transação protegida e criptografada</p>
                    </div>
                </div>
                <div class="info-item">
                    <i class="bi bi-clock-history"></i>
                    <div>
                        <strong>Disponível 24/7</strong>
                        <p>Faça depósitos a qualquer momento</p>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/depositar.js"></script>
</body>
</html>
