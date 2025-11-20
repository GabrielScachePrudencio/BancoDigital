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
</head>
<body>

<h1>Transferência</h1>

<% if (erro != null) { %>
    <p style="color:red;"><%= erro %></p>
<% } %>

<% if (sucesso != null) { %>
    <p style="color:green;"><%= sucesso %></p>
<% } %>

<!-- FORM DE BUSCA -->
<% if (destino == null) {%>
	<form action="${pageContext.request.contextPath}/pessoa/transferir" method="post">
	    <label>Pesquisar conta destino:</label><br>
	    <input id="texto" type="text" name="texto" required>
	    <button type="submit" onclick="return validadorBusca()" >Buscar</button>
	</form>

<% } %>
<br><br>


<!-- SE JÁ TEM DESTINO ESCOLHIDO -->
<% if (destino != null) { %>

    <h3>Transferência para: <%= destino.getNome() %></h3>
    <p>CPF: <%= destino.getCpf() %></p>
	<p>Saldo Atual: <%= usuarioLogado.getSaldo() %></p>
	
    <form action="${pageContext.request.contextPath}/pessoa/transferir" method="post">
        <label>Valor:</label>
        <input type="number" name="valor" required>
			
        <input type="hidden" name="destinoCpf" value="<%= destino.getCpf() %>">

        <button type="submit">continuar</button>
    </form>

<% } %>

	<p>voltar <a href="<%= request.getContextPath() + "/home" %>">pagina principal</a>...</p>

<script src="${pageContext.request.contextPath}/assets/js/transferir.js"></script>

</body>
</html>
