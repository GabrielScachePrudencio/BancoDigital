// ===== ANIMATE ON LOAD =====
document.addEventListener('DOMContentLoaded', function() {
	// Animate header
	const header = document.querySelector('.desabilitar-header');
	if (header) {
		header.style.opacity = '0';
		header.style.transform = 'translateY(20px)';
		setTimeout(() => {
			header.style.transition = 'all 0.6s ease';
			header.style.opacity = '1';
			header.style.transform = 'translateY(0)';
		}, 100);
	}
	
	// Animate cards
	const cards = document.querySelectorAll('.card, .stats-card');
	cards.forEach((card, index) => {
		card.style.opacity = '0';
		card.style.transform = 'translateY(20px)';
		setTimeout(() => {
			card.style.transition = 'all 0.6s ease';
			card.style.opacity = '1';
			card.style.transform = 'translateY(0)';
		}, 200 + (index * 100));
	});
	
	// Animate table rows
	const tableRows = document.querySelectorAll('.accounts-table tbody tr');
	tableRows.forEach((row, index) => {
		row.style.opacity = '0';
		row.style.transform = 'translateX(-20px)';
		setTimeout(() => {
			row.style.transition = 'all 0.4s ease';
			row.style.opacity = '1';
			row.style.transform = 'translateX(0)';
		}, 400 + (index * 50));
	});
	
	// Auto-dismiss alerts
	const alerts = document.querySelectorAll('.alert');
	alerts.forEach(alert => {
		setTimeout(() => {
			// Verifica se o Bootstrap está carregado antes de usar new bootstrap.Alert
			if (typeof bootstrap !== 'undefined' && bootstrap.Alert) {
				const bsAlert = new bootstrap.Alert(alert);
				bsAlert.close();
			}
		}, 5000);
	});
	
	// Focus search input
	const searchInput = document.getElementById('termoBusca');
	if (searchInput) {
		setTimeout(() => {
			searchInput.focus();
		}, 500);
	}
});


// ===== SEARCH FILTER (CLIENT-SIDE) - CORREÇÃO PARA AMBAS AS PÁGINAS =====
const searchInput = document.getElementById('termoBusca');

// Procura a tabela de desabilitar ('pessoasTable') OU a tabela de habilitar ('tabelaHabilitar')
const table = document.getElementById('pessoasTable') || document.getElementById('tabelaHabilitar'); 

if (searchInput && table) {
	searchInput.addEventListener('input', function() {
		const searchTerm = this.value.toLowerCase();
		const rows = table.querySelectorAll('tbody tr');
		
		let visibleCount = 0;
		
		rows.forEach(row => {
			const text = row.textContent.toLowerCase();
			if (text.includes(searchTerm)) {
				row.style.display = '';
				visibleCount++;
			} else {
				row.style.display = 'none';
			}
		});
		
		// Update count if exists (funciona para o card de estatísticas em ambas as páginas)
		const statValue = document.querySelector('.stat-value');
		if (statValue) {
			statValue.textContent = visibleCount;
		}
	});
}

// ===== FORM SUBMIT WITH LOADING =====
const forms = document.querySelectorAll('form');
forms.forEach(form => {
	form.addEventListener('submit', function(e) {
		const submitBtn = this.querySelector('button[type="submit"]');
		// Só aplica o loading se não for um formulário de desabilitar/habilitar com confirmação (onclick)
		if (submitBtn && !submitBtn.getAttribute('onclick')) {
			submitBtn.disabled = true;
			submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Processando...';
		}
	});
});