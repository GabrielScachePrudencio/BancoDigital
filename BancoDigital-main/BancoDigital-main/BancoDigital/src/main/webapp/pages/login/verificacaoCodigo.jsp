<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IFSP Bank - Verificação de Código</title>
<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/iconsite.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

<style>
/* * CSS - Verificação de Código
 * Baseado nas variáveis do seu tema (roxo/azul e moderno)
 */

/* ===== RESET & VARIABLES ===== */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

:root {
	--primary: #8A05BE; /* Roxo principal */
	--primary-dark: #6200EA; /* Roxo escuro */
	--danger: #EA1D2C; /* Vermelho de erro */
	--text: #1A1A1D;
	--text-light: #6E6E73;
	--bg: #F5F5F5;
	--white: #FFFFFF;
	--shadow-md: 0 4px 16px rgba(0, 0, 0, 0.08);
	--transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

body {
    font-family: 'Inter', sans-serif;
    background: var(--bg);
    color: var(--text);
    line-height: 1.6;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    margin: 0;
    padding: 20px;
}

/* ----------------------
 * CONTAINER PRINCIPAL
 * ---------------------- */
.verificacao-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
}

.verificacao-container {
    background-color: var(--white);
    padding: 40px;
    border-radius: 20px; /* Borda mais suave */
    box-shadow: var(--shadow-md);
    max-width: 420px;
    width: 100%;
    text-align: center;
    border-top: 5px solid var(--primary); /* Detalhe da cor primária */
}

/* ----------------------
 * HEADER E TÍTULOS
 * ---------------------- */
.verificacao-header {
    margin-bottom: 30px;
}

.verification-icon {
    width: 65px;
    height: 65px;
    margin: 0 auto 20px;
    background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: var(--white);
    font-size: 30px;
    box-shadow: 0 4px 12px rgba(138, 5, 190, 0.3);
}

.verificacao-title {
    font-size: 1.8rem;
    font-weight: 700;
    color: var(--text);
    margin-bottom: 5px;
}

.verificacao-subtitle {
    font-size: 1rem;
    font-weight: 500;
    color: var(--text-light);
    margin-bottom: 0;
}

/* ----------------------
 * FORMULÁRIO E INPUTS
 * ---------------------- */
.verificacao-form {
    text-align: left;
}

.form-label {
    font-weight: 600;
    color: var(--text);
    display: block;
    margin-bottom: 8px;
    font-size: 0.9rem;
}

.form-control-lg {
    border-radius: 10px;
    padding: 12px 15px;
    font-size: 1.25rem;
    border: 1px solid #ced4da;
    transition: var(--transition);
    text-align: center; /* Centraliza o código */
    letter-spacing: 2px;
}

.form-control-lg:focus {
    border-color: var(--primary);
    box-shadow: 0 0 0 0.25rem rgba(138, 5, 190, 0.25);
}

.btn-primary {
    background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
    border: none;
    font-weight: 600;
    padding: 12px;
    border-radius: 10px;
    transition: var(--transition);
    box-shadow: 0 4px 12px rgba(138, 5, 190, 0.2);
}

.btn-primary:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(138, 5, 190, 0.3);
    background: var(--primary-dark);
}

.btn-primary:active {
    transform: translateY(0);
}

/* ----------------------
 * ALERTA DE ERRO
 * ---------------------- */
.alert {
    border-radius: 10px;
    text-align: left;
    margin-bottom: 25px;
    animation: fadeIn 0.5s ease-out;
}

.alert-danger {
    background-color: rgba(234, 29, 44, 0.1);
    color: var(--danger);
    border-color: rgba(234, 29, 44, 0.2);
    font-weight: 500;
}

/* ===== MEDIA QUERIES ===== */
@media (max-width: 500px) {
    .verificacao-container {
        padding: 30px 20px;
    }

    .verificacao-title {
        font-size: 1.6rem;
    }
}
</style>

</head>
<body>
	<div class="verificacao-wrapper">
		<div class="verificacao-container">
			<div class="verificacao-header">
				<div class="verification-icon">
					<i class="bi bi-shield-check"></i>
				</div>
				<h1 class="verificacao-title">Banco Digital IFSP</h1>
				<h2 class="verificacao-subtitle">Digite o código enviado para seu Email</h2>
			</div>

			<%
			String errorMsg = (String) request.getAttribute("error");
			if (errorMsg != null) {
			%>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<i class="bi bi-exclamation-triangle-fill me-2"></i>
				<strong>Erro:</strong> <%=errorMsg.equals("invalid") ? "Código inválido. Verifique seu email e tente novamente." : errorMsg %>
				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
			</div>
			<%
			}
			%>

			<form action="<%=request.getContextPath()%>/verificacaoCodigo" method="post" class="verificacao-form">
				<div class="form-group mb-4">
					<label for="codigo" class="form-label">
						<i class="bi bi-key-fill me-2"></i>Código de Verificação:
					</label>
					<input 
						type="text" 
						class="form-control form-control-lg" 
						id="codigo" 
						name="codigo" 
						required
						maxlength="6"
						placeholder="000000"
						inputmode="numeric"
						pattern="\d*">
				</div>

				<%
				String nextParam = request.getParameter("next");
				if (nextParam != null) {
				%>
				<input type="hidden" name="next" value="<%=nextParam%>">
				<%
				}
				%>

				<button type="submit" class="btn btn-primary btn-lg w-100">
					<i class="bi bi-check-circle me-2"></i>Validar Código
				</button>
			</form>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	
	<script>
	// 
	// JavaScript - Verificação de Código
	// Adiciona animação, foco e estado de loading
	//
	
	document.addEventListener('DOMContentLoaded', function() {
	
		// ===== 1. ANIMAÇÃO SUAVE NO CARREGAMENTO =====
		const container = document.querySelector('.verificacao-container');
		if (container) {
			container.style.opacity = '0';
			container.style.transform = 'translateY(20px)';
			setTimeout(() => {
				container.style.transition = 'all 0.8s cubic-bezier(0.25, 0.8, 0.25, 1)'; 
				container.style.opacity = '1';
				container.style.transform = 'translateY(0)';
			}, 100);
		}
	
		// ===== 2. FOCO AUTOMÁTICO NO INPUT DO CÓDIGO =====
		const codigoInput = document.getElementById('codigo');
		if (codigoInput) {
			// Pequeno delay para garantir que a animação CSS termine
			setTimeout(() => {
				codigoInput.focus();
			}, 600);
		}
	
		// ===== 3. AUTO-DISMISS ALERTS (Para mensagens de erro) =====
		const alerts = document.querySelectorAll('.alert');
		alerts.forEach(alert => {
			setTimeout(() => {
				if (typeof bootstrap !== 'undefined' && bootstrap.Alert) {
					const bsAlert = new bootstrap.Alert(alert);
					bsAlert.close();
				}
			}, 7000); // Fecha o alerta após 7 segundos
		});
	
		// ===== 4. ESTADO DE CARREGAMENTO NO BOTÃO (LOADING) =====
		const form = document.querySelector('.verificacao-form');
		if (form) {
			form.addEventListener('submit', function(e) {
				const submitBtn = this.querySelector('button[type="submit"]');
				if (submitBtn) {
					submitBtn.disabled = true;
					submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Validando...';
				}
			});
		}
	});
	</script>
</body>
</html>