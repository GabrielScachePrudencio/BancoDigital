<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Contas - Habilitar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
    <style>
        /* Estilos adicionais para melhor visualização */
        .page-header {
            margin-bottom: 30px;
        }
        .filter-input {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

    <div class="container mt-5">
        <h2 class="page-header">
            <i class="bi bi-person-check me-2"></i>
            Habilitar Contas Pendentes
        </h2>

        <%-- Tratamento da Mensagem de Sucesso (após habilitação) --%>
        <% 
        String sucesso = (String) request.getAttribute("sucesss");
        if (sucesso != null && !sucesso.isEmpty()) {
        %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>
                <%= sucesso %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <%
        }
        %>

        <%-- Campo de Pesquisa (Filtro via Command) --%>
		<div class="row">
		    <div class="col-md-6">
		        <form action="${pageContext.request.contextPath}/pessoa/habilitarContas" method="get" class="d-flex">
		            <%-- Novo campo para o termo de pesquisa (o nome é 'termoBusca') --%>
		            <input type="text" name="termoBusca" class="form-control filter-input me-2" 
		                   placeholder="Pesquisar por Nome..." 
		                   value="<%= request.getParameter("termoBusca") != null ? request.getParameter("termoBusca") : "" %>">
		            
		            <button type="submit" class="btn btn-primary">
		                <i class="bi bi-search"></i> Pesquisar
		            </button>
		        </form>
		    </div>
		</div>

        <%-- Recupera a lista de pessoas não habilitadas do Request --%>
        <%
        ArrayList<Pessoa> pessoasNaoHabilitadas = (ArrayList<Pessoa>) request.getAttribute("pessoasNaoHabilitadas");

        if (pessoasNaoHabilitadas != null && !pessoasNaoHabilitadas.isEmpty()) {
        %>
            <div class="table-responsive">
                <table class="table table-striped table-hover" id="pessoasTable">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>CPF</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Ação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Pessoa pessoa : pessoasNaoHabilitadas) { %>
                            <tr>
                                <td><%= pessoa.getId() %></td>
                                <td><%= pessoa.getCpf() %></td>
                                <td><%= pessoa.getNome() %></td>
                                <td><%= pessoa.getEmail() %></td>
                                <td>
                                    <%-- Formulário para enviar o ID para o Command --%>
                                    <form action="${pageContext.request.contextPath}/pessoa/habilitarContas" method="post" style="display:inline;">
                                        <input type="hidden" name="idContaParaSerHabilitada" value="<%= pessoa.getId() %>">
                                        <button type="submit" class="btn btn-success btn-sm">
                                            <i class="bi bi-check-lg me-1"></i>
                                            Habilitar
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <%
        } else {
        %>
            <div class="alert alert-info" role="alert">
                <i class="bi bi-info-circle-fill me-2"></i>
                Não há contas de clientes pendentes para habilitação.
            </div>
        <%
        }
        %>
    </div>

    <%-- JavaScript para o Filtro de Pesquisa --%>
    <script>
        $(document).ready(function(){
            $("#searchInput").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $("#pessoasTable tbody tr").filter(function() {
                    // Filtra o Nome (índice 2) e o CPF (índice 1)
                    var nome = $(this).find("td:eq(2)").text().toLowerCase();
                    var cpf = $(this).find("td:eq(1)").text().toLowerCase();
                    
                    // Exibe a linha se o texto pesquisado estiver contido no Nome OU no CPF
                    $(this).toggle(nome.indexOf(value) > -1 || cpf.indexOf(value) > -1)
                });
            });
        });
    </script>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>