<%@page import="java.util.List"%>
<%@page import="edu.br.ifsp.bank.modelo.ParcelaSac"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Simular Empréstimo - SAC</title>
</head>
<body>

<h1>Simular Empréstimo (Sistema SAC)</h1>

<%
    String erro = (String) request.getAttribute("erro");
    if (erro != null) {
%>
    <p style="color:red;"><%= erro %></p>
<%
    }
%>

<form action="<%= request.getContextPath() %>/pessoa/emprestimoSac" method="post">
    <label>Valor do empréstimo (R$):</label><br>
    <input type="number" step="0.01" name="valor" required><br><br>

    <label>Taxa de juros mensal (%):</label><br>
    <input type="number" step="0.01" name="taxa" required><br><br>

    <label>Prazo (meses):</label><br>
    <input type="number" name="prazo" required><br><br>

    <button type="submit">Simular</button>
</form>

<%
    List<ParcelaSac> parcelas = (List<ParcelaSac>) request.getAttribute("parcelas");
    if (parcelas != null && !parcelas.isEmpty()) {
        Double totalPago = (Double) request.getAttribute("totalPago");
        Double totalJuros = (Double) request.getAttribute("totalJuros");
%>

    <h2>Resultado da simulação</h2>
    <p>Valor total pago: R$ <%= String.format("%.2f", totalPago) %></p>
    <p>Total em juros: R$ <%= String.format("%.2f", totalJuros) %></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>Parcela</th>
                <th>Amortização (R$)</th>
                <th>Juros (R$)</th>
                <th>Valor da parcela (R$)</th>
                <th>Saldo devedor (R$)</th>
            </tr>
        </thead>
        <tbody>
        <%
            for (ParcelaSac p : parcelas) {
        %>
            <tr>
                <td><%= p.getNumero() %></td>
                <td><%= String.format("%.2f", p.getAmortizacao()) %></td>
                <td><%= String.format("%.2f", p.getJuros()) %></td>
                <td><%= String.format("%.2f", p.getValorParcela()) %></td>
                <td><%= String.format("%.2f", p.getSaldoDevedor()) %></td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
<%
    }
%>

<p><a href="<%= request.getContextPath() %>/home">Voltar para home</a></p>

</body>
</html>
