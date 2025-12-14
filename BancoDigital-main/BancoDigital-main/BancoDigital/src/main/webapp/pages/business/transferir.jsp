<%@page import="edu.br.ifsp.bank.modelo.Transferencia"%>
<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
    Transferencia ultima = (Transferencia) session.getAttribute("ultimaTransferencia");
    String tipoPdf = (String) session.getAttribute("tipoPdf");
    String erro = (String) request.getAttribute("erro");
    Pessoa destino = (Pessoa) request.getAttribute("destino");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Transferir</title>

<base href="<%=request.getContextPath()%>/">

<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/transferir.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
</head>

<body>
<div class="transferir-wrapper">
    <div class="transferir-container">

        <!-- HEADER -->
        <div class="transferir-header">
            <a href="<%=request.getContextPath()%>/home" class="back-link">
                <i class="bi bi-arrow-left"></i> Voltar
            </a>
            <div class="header-content">
                <div class="header-icon">
                    <i class="bi bi-arrow-left-right"></i>
                </div>
                <div>
                    <h1 class="transferir-title">Transferência</h1>
                    <p class="transferir-subtitle">Envie dinheiro de forma rápida e segura</p>
                </div>
            </div>
        </div>

        <!-- ERRO -->
        <% if (erro != null && ultima == null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
            <strong>Erro!</strong> <%= erro %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <% if (ultima == null) { %>

        <!-- BUSCAR DESTINO -->
        <% if (destino == null) { %>
        <div class="card search-card">
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/pessoa/transferir" method="post">
                    <div class="form-group mb-4">
                        <label class="form-label">
                            <i class="bi bi-search me-2"></i>
                            Pesquisar conta destino
                        </label>
                        <input
                            type="text"
                            class="form-control form-control-lg"
                            name="texto"
                            placeholder="Digite nome ou CPF"
                            required>
                    </div>

                    <button type="submit" class="btn btn-primary btn-lg w-100">
                        <i class="bi bi-search me-2"></i>
                        Buscar
                    </button>
                </form>
            </div>
        </div>
        <% } %>

        <!-- DESTINO + TRANSFERÊNCIA -->
        <% if (destino != null) { %>
        <div class="card destino-card mb-4">
            <div class="card-body">
                <div class="destino-header">
                    <div class="destino-avatar">
                        <%= destino.getNome().substring(0,1).toUpperCase() %>
                    </div>
                    <div>
                        <h3><%= destino.getNome() %></h3>
                        <p>CPF: <%= destino.getCpf() %></p>
                    </div>
                </div>

                <p class="mt-3">
                    <i class="bi bi-wallet2"></i>
                    Seu saldo atual:
                    <strong>
                        R$ <%= String.format("%.2f", usuarioLogado.getSaldo()) %>
                    </strong>
                </p>
            </div>
        </div>

        <div class="card transfer-card">
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/pessoa/transferir" method="post">
                    <div class="form-group mb-4">
                        <label class="form-label">
                            <i class="bi bi-cash-coin me-2"></i>
                            Valor da transferência
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

                    <input type="hidden" name="destinoCpf" value="<%= destino.getCpf() %>">

                    <button type="submit" class="btn btn-primary btn-lg w-100">
                        <i class="bi bi-send-fill me-2"></i>
                        Transferir
                    </button>
                </form>
            </div>
        </div>
        <% } %>

        <% } else if ("transferencia".equals(tipoPdf)) { %>

        <!-- TELA FINAL (IGUAL AO DEPOSITAR) -->
        <div class="card transfer-card text-center">
            <div class="card-body">

                <h4 class="text-success mb-4">
                    <i class="bi bi-check-circle-fill"></i>
                    Transferência realizada com sucesso!
                </h4>

				<form action="<%=request.getContextPath()%>/pessoa/pdf"
				      method="post"
				      class="mb-3">
                    <input type="hidden" name="tipo" value="transferencia">
                    <button type="submit" class="btn btn-danger btn-lg w-100">
                        <i class="bi bi-file-earmark-pdf"></i>
                        Gerar comprovante em PDF
                    </button>
                </form>

                <a href="<%=request.getContextPath()%>/home"
                   class="btn btn-outline-secondary btn-lg w-100">
                    <i class="bi bi-arrow-left"></i>
                    Voltar
                </a>

            </div>
        </div>

        <% } %>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/transferir.js"></script>
</body>
</html>
