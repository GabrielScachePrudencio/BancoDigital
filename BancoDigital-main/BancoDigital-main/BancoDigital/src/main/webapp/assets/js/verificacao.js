//
// Arquivo: verificacao.js
// Funcionalidades para a página de Verificação de Código.
//

document.addEventListener('DOMContentLoaded', function() {

	// ===== 1. ANIMAÇÃO SUAVE NO CARREGAMENTO =====
	const container = document.querySelector('.verificacao-container');
	if (container) {
		container.style.opacity = '0';
		container.style.transform = 'translateY(20px)';
		setTimeout(() => {
			container.style.transition = 'all 0.8s cubic-bezier(0.25, 0.8, 0.25, 1)'; /* Efeito Material Design */
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
			// Verifica se o Bootstrap está carregado antes de usar new bootstrap.Alert
			if (typeof bootstrap !== 'undefined' && bootstrap.Alert) {
				const bsAlert = new bootstrap.Alert(alert);
				bsAlert.close();
			}
		}, 5000); // Fecha o alerta após 5 segundos
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