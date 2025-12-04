<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
    Boolean podeIrSenhaNova = (Boolean) request.getAttribute("podeIrSenhaNova");
    if (podeIrSenhaNova == null) podeIrSenhaNova = false;

    String erro = (String) request.getAttribute("erro");
    String sucesso = (String) request.getAttribute("sucesso");
%>

    
  
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trocar Senha</title>
<link rel="icon" type="image/png"
      href="<%=request.getContextPath()%>/images/iconsite.png">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/login.css">
</head>
<body>

    <div class="container">
        <div class="header">
            <h1>🏦 Banco Digital IFSP</h1>
            <h2>Trocar Senha</h2>
        </div>
		<% if (erro != null) { %>
	        <p class="msg-erro"><%= erro %></p>
	    <% } %>
	
	    <% if (sucesso != null) { %>
	        <p class="msg-sucesso"><%= sucesso %></p>
	    <% } %>



        <div class="form-container">
			<% if (podeIrSenhaNova == true) { %>
			
	            <form action="<%=request.getContextPath()%>/pessoa/trocarsenha" method="post">
	
	                <!-- ID e código vindos do link -->
	                <input type="hidden" name="id" value="<%=request.getAttribute("id")%>">
	                <input type="hidden" name="codigo" value="<%=request.getAttribute("codigo")%>">
	
	                <div class="form-group">
	                    <label for="senhaNova">🔒 Nova Senha:</label>
	                    <input type="password" id="senhaNova" name="senhaNova" required placeholder="Digite a nova senha">
	                </div>
	
	                <button type="submit">Confirmar troca</button>
	
	            </form>
            <% } else { %>
            	<form action="<%=request.getContextPath()%>/pessoa/trocarsenha" method="post">
	
	                <!-- ID e código vindos do link -->
	                <input type="hidden" name="id" value="<%=request.getAttribute("id")%>">
	                <input type="hidden" name="codigo" value="<%=request.getAttribute("codigo")%>">
	
	                <div class="form-group">
	                    <label for="senhaAntiga">🔒 Antiga Senha:</label>
	                    <input type="password" id="senhaAntiga" name="senhaAntiga" required placeholder="Digite a antiga senha">
	                </div>
	
	                <button type="submit">Confirmar troca</button>
	
	            </form>
            <% } %>
            
        </div>
    </div>

</body>
</html>
