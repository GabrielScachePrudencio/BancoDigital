<%@page import="java.util.List"%>
<%@page import="edu.br.ifsp.bank.modelo.Investimento"%>
<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
    List<Investimento> investimentos =
        (List<Investimento>) request.getAttribute("investimentos");
%>

<h1>Aplicar em Investimentos</h1>

<p>Saldo atual: R$ <%= String.format("%.2f", usuarioLogado.getSaldo()) %></p>

<% if (request.getAttribute("erro") != null) { %>
    <p style="color:red;"><%= request.getAttribute("erro") %></p>
<% } %>
<% if (request.getAttribute("msg") != null) { %>
    <p style="color:green;"><%= request.getAttribute("msg") %></p>
<% } %>

<form action="<%=request.getContextPath()%>/pessoa/investir" method="post">
    Valor a aplicar:<br>
    <input type="number" name="valor" step="0.01" required><br><br>

    Taxa de juros mensal (%):<br>
    <input type="number" name="taxa" step="0.01" required><br><br>

    Prazo (meses):<br>
    <input type="number" name="prazo" required><br><br>

    <button type="submit">Aplicar</button>
</form>

<hr>

<% if (investimentos != null && !investimentos.isEmpty()) { %>
    <h3>Seus investimentos</h3>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>Nome</th>
            <th>Valor</th>
            <th>Taxa mensal</th>
            <th>Prazo (meses)</th>
            <th>Data aplicação</th>
            <th>Lucro (R$)</th>
        </tr>
        <% for (Investimento inv : investimentos) { %>
            <tr>
                <td><%= usuarioLogado.getNome() %></td>
                <td>R$ <%= String.format("%.2f", inv.getValor()) %></td>
                <td><%= String.format("%.2f", inv.getTaxaMensal() * 100) %> %</td>
                <td><%= inv.getPrazoMeses() %></td>
                <td><%= inv.getDataAplicacao().toString().replace("T", " ") %></td>
                <td>R$ <%= String.format("%.2f", inv.getLucro()) %></td>
            </tr>
        <% } %>
    </table>
<% } else { %>
    <p>Você ainda não possui investimentos registrados.</p>
<% } %>

<br>
<a href="<%=request.getContextPath()%>/home">Voltar para home</a>
