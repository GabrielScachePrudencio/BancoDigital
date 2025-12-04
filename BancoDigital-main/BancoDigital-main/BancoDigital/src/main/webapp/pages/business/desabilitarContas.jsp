<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Contas - Desabilitar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
    <style>
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
            <i class="bi bi-person-slash me-2 text-danger"></i>
            Desabilitar Contas Ativas
        </h2>

        <%-- Tratamento da Mensagem de Sucesso/Erro --%>
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

        <div class="row">
            <div class="col-md-6">
                <%-- IMPORTANTE: Action deve apontar para o DesabilitarContasCommand --%>
                <form action="${pageContext.request.contextPath}/pessoa/desabilitarContas" method="post	" class="d-flex">
                    <input type="text" name="termoBusca" class="form-control filter-input me-2" 
                           placeholder="Pesquisar por Nome..." 
                           >
                    
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-search"></i> Pesquisar
                    </button>
                </form>
            </div>
        </div>

        <%-- Recupera a lista de pessoas HABILITADAS do Request --%>
        <%
        ArrayList<Pessoa> pessoasHabilitadas = (ArrayList<Pessoa>) request.getAttribute("pessoasHabilitadas");

        if (pessoasHabilitadas != null && !pessoasHabilitadas.isEmpty()) {
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
                        <% for (Pessoa pessoa : pessoasHabilitadas) { %>
                            <tr>
                                <td><%= pessoa.getId() %></td>
                                <td><%= pessoa.getCpf() %></td>
                                <td><%= pessoa.getNome() %></td>
                                <td><%= pessoa.getEmail() %></td>
                                <td>
                                    <%-- Formulário para Desabilitar a Conta --%>
                                    <%-- IMPORTANTE: Action deve apontar para o DesabilitarContasCommand --%>
                                    <form action="${pageContext.request.contextPath}/pessoa/desabilitarContas" method="post" style="display:inline;">
                                        <input type="hidden" name="idContaParaSerDesabilitada" value="<%= pessoa.getId() %>">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Tem certeza que deseja desabilitar a conta de <%= pessoa.getNome() %>?');">
                                            <i class="bi bi-slash-circle me-1"></i>
                                            Desabilitar
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
                Não há contas de clientes atualmente ativas.
            </div>
        <%
        }
        %>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>