<%@page import="java.util.List"%>
<%@page import="edu.br.ifsp.bank.modelo.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
    List<Pessoa> resultados = (List<Pessoa>) request.getAttribute("resultados");
    Pessoa destino = (Pessoa) request.getAttribute("destino");

    String erro = (String) request.getAttribute("erro");
    String sucesso = (String) request.getAttribute("sucesso");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transferir</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/transferir.css">
    <script defer src="${pageContext.request.contextPath}/assets/js/transferir.js"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/images/iconsite.png">
</head>

<body>

<div class="container">

    <h1>Transferência</h1>

    <% if (erro != null) { %>
        <p class="msg-erro"><%= erro %></p>
    <% } %>

    <% if (sucesso != null) { %>
        <p class="msg-sucesso"><%= sucesso %></p>
    <% } %>

    <% if (destino == null) { %>

        <form class="card" action="${pageContext.request.contextPath}/pessoa/transferir" method="post">
            <label>Pesquisar conta destino</label>
            <input id="texto" type="text" name="texto" placeholder="Digite nome ou CPF" required>

            <button type="submit" class="btn" onclick="return validadorBusca()">Buscar</button>
        </form>

    <% } %>

    <% if (destino != null) { %>

        <div class="card destino-card">
            <h3>Transferência para: <%= destino.getNome() %></h3>
            <p>CPF: <%= destino.getCpf() %></p>
            <p>Seu saldo atual: R$ <%= String.format("%.2f", usuarioLogado.getSaldo()) %></p>
        </div>

        <form class="card" action="${pageContext.request.contextPath}/pessoa/transferir" method="post">
            <label>Valor da transferencia</label>
            <input type="number" name="valor" step="0.01" min="0.01" required>

            <input type="hidden" name="destinoCpf" value="<%= destino.getCpf() %>">

            <button type="submit" class="btn btn-confirmar">Continuar</button>
        </form>

    <% } %>

    <a class="voltar" href="<%= request.getContextPath() + "/home" %>">Voltar para a página principal</a>

</div>

</body>
</html>
