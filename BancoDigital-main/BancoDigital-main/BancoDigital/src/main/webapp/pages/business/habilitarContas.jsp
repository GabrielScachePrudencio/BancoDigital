<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.br.ifsp.bank.modelo.Pessoa"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="edu.br.ifsp.bank.modelo.TipoUsuario"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Habilitar Contas</title>
<base href="<%=request.getContextPath()%>/">
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/desabilitar.css"> 
<!-- Usa o MESMO CSS da página de desabilitar, mantendo identidade visual -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>
<div class="desabilitar-wrapper">
    <div class="desabilitar-container">

        <!-- HEADER -->
        <div class="desabilitar-header">
            <a href="<%=request.getContextPath()%>/home" class="back-link">
                <i class="bi bi-arrow-left"></i>
                Voltar
            </a>

            <div class="header-content">
                <div class="header-icon" style="background: linear-gradient(135deg, var(--success) 0%, #009624 100%);">
                    <i class="bi bi-person-check"></i>
                </div>

                <div>
                    <h1 class="desabilitar-title">Habilitar Contas</h1>
                    <p class="desabilitar-subtitle">Gerencie contas inativas e reative clientes</p>
                </div>
            </div>
        </div>

        <!-- ALERTA DE SUCESSO -->
        <% 
        String sucessoHab = (String) request.getAttribute("sucesss");
        if (sucessoHab != null && !sucessoHab.isEmpty()) {
        %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>
            <strong>Sucesso!</strong> <%=sucessoHab%>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% } %>

        <!-- CARD DE BUSCA -->
       <div class="card search-card">
            <div class="card-body">
                <% 
                // 1. RECUPERA O TERMO DE BUSCA DO SERVLET
                String termoBuscaHab = (String) request.getAttribute("termoBusca");
                if (termoBuscaHab == null) {
                    termoBuscaHab = "";
                }
                %>
                <form action="<%=request.getContextPath()%>/pessoa/habilitarContas" method="post">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-9">
                            <label for="termoBusca" class="form-label">
                                <i class="bi bi-search me-2"></i>
                                Pesquisar conta inativa
                            </label>
                            <input 
                                type="text" 
                                name="termoBusca" 
                                id="termoBusca"
                                class="form-control form-control-lg" 
                                placeholder="Digite o nome do cliente..."
                                value="<%= termoBuscaHab %>"> </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-primary btn-lg w-100">
                                <i class="bi bi-search me-2"></i>Pesquisar
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- LISTA DE CONTAS INATIVAS -->
        <%
        ArrayList<Pessoa> pessoasInativas = (ArrayList<Pessoa>) request.getAttribute("pessoasNaoHabilitadas");

        if (pessoasInativas != null && !pessoasInativas.isEmpty()) {
        %>

        <!-- CARD DE ESTATÍSTICAS -->
        <div class="stats-card" style="background: linear-gradient(135deg, var(--success) 0%, #009624 100%);">
            <div class="stat-item">
                <i class="bi bi-person-x-fill"></i>
                <div>
                    <div class="stat-value"><%=pessoasInativas.size()%></div>
                    <div class="stat-label">Contas inativas</div>
                </div>
            </div>
        </div>

        <!-- TABELA -->
        <div class="card table-card">
            <div class="card-body">
                <h5 class="card-title mb-4">
                    <i class="bi bi-table me-2"></i>
                    Contas Inativas
                </h5>

                <div class="table-responsive">
                    <table class="table accounts-table" id="tabelaHabilitar">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>CPF</th>
                                <th>Nome</th>
                                <th>Email</th>
                                <th class="text-end">Ação</th>
                            </tr>
                        </thead>
                        <tbody>

                        <% 
						for (Pessoa pessoa : pessoasInativas) { 
						
						    // Se não tem ROLE, pula
						    if (pessoa.getRole() == null) continue;
						
						    // Se for ADMIN ou GERENTE, pula
						    if (pessoa.getRole().equals(TipoUsuario.ADMIN) ||
						        pessoa.getRole().equals(TipoUsuario.GERENTE)) continue;
						%>
						
						<tr>
						    <td><span class="badge bg-primary">#<%=pessoa.getId()%></span></td>
						    <td><strong><%=pessoa.getCpf()%></strong></td>
						
						    <td>
						        <div class="user-info">
						            <div class="user-avatar">
						                <%=pessoa.getNome().substring(0, 1).toUpperCase()%>
						            </div>
						            <span><%=pessoa.getNome()%></span>
						        </div>
						    </td>
						
						    <td><%=pessoa.getEmail()%></td>
						
						    <td class="text-end">
						        <form action="<%=request.getContextPath()%>/pessoa/habilitarContas"
						              method="post" style="display:inline;">
						
						            <input type="hidden" name="idContaParaSerHabilitada"
						                   value="<%=pessoa.getId()%>">
						
						            <button 
						                type="submit" 
						                class="btn btn-success btn-sm"
						                style="background: linear-gradient(135deg, var(--success) 0%, #009624 100%); border:none;"
						                onclick="return confirm('Habilitar conta de <%=pessoa.getNome()%>?');">
						                
						                <i class="bi bi-check-circle me-1"></i> Habilitar
						            </button>
						        </form>
						    </td>
						</tr>
						
						<% } %>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <% } else { %>

        <!-- ESTADO VAZIO -->
        <div class="card empty-card">
            <div class="card-body">
                <div class="empty-state">
                    <i class="bi bi-inbox"></i>
                    <h4>Nenhuma conta inativa encontrada</h4>
                    <p>Não há contas aguardando habilitação ou nenhuma corresponde à sua busca.</p>
                </div>
            </div>
        </div>

        <% } %>

        <!-- VOLTAR -->
        <div class="back-section">
            <a href="<%=request.getContextPath()%>/home" class="back-main-link">
                <i class="bi bi-house-door me-2"></i>
                Voltar para home
            </a>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Usa o MESMO JS da página de desabilitar -->
<script src="<%=request.getContextPath()%>/assets/js/habilitar.js"></script>

</body>
</html>
