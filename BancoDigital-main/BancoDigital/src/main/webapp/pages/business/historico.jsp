<%@ page import="java.util.List" %>
<%@ page import="edu.br.ifsp.bank.modelo.HistoricoItem" %>
<%@ page import="edu.br.ifsp.bank.modelo.Transferencia" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    List<HistoricoItem> itens = (List<HistoricoItem>) request.getAttribute("itensHistorico");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Histórico de Transações</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/transferir.css">
    <style>
        .table-historico { width:100%; border-collapse:collapse; }
        .table-historico th, .table-historico td { border-bottom:1px solid #ddd; padding:8px; text-align:left; }
        .table-historico thead th { background:#f5f5f5; }
    </style>
</head>
<body>
<div class="container">
    <h1>Histórico de Transações</h1>

    <table class="table-historico">
        <thead>
            <tr>
                <th>Data / Hora</th>
                <th>Tipo</th>
                <th>Valor (R$)</th>
                <th>Remetente</th>
                <th>Destinatário</th>
            </tr>
        </thead>
        <tbody>
        <%
            if (itens == null || itens.isEmpty()) {
        %>
            <tr><td colspan="5">Nenhuma transação encontrada.</td></tr>
        <%
            } else {
                for (HistoricoItem item : itens) {
                    Transferencia t = item.getTransferencia();
                    String tipo = (t.getId_usuarioQueTransferiu() == t.getId_usuarioQueRecebeu()) ? "Depósito/Retirada" : "Transferência";
        %>
            <tr>
                <td><%= t.getHorario() %></td>
                <td><%= tipo %></td>
                <td><%= String.format("%.2f", t.getValor()) %></td>
                <td><%= item.getNomeRemetente() %> <small>(<%= item.getCpfRemetente() %>)</small></td>
                <td><%= item.getNomeDestinatario() %> <small>(<%= item.getCpfDestinatario() %>)</small></td>
            </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

    <br/>
    <a href="<%= request.getContextPath() + "/home" %>">Voltar</a>
</div>
</body>
</html>
