// ===== VALIDADOR BUSCA (MANTÉM A FUNÇÃO ORIGINAL) =====
function validadorBusca() {
	const texto = document.getElementById('texto');
	
	if (!texto || !texto.value.trim()) {
		alert('Por favor, digite um nome ou CPF para buscar');
		return false;
	}
	
	return true;
}

// ===== FORM HANDLING =====
const forms = document.querySelectorAll('form');


// ===== MONEY INPUT FORMATTING =====
const valorInput = document.getElementById('valor');

if (valorInput) {
	valorInput.addEventListener('input', function(e) {
		// Remove non-numeric characters except decimal point
		let value = e.target.value.replace(/[^\d.]/g, '');
		
		// Ensure only one decimal point
		const parts = value.split('.');
		if (parts.length > 2) {
			value = parts[0] + '.' + parts.slice(1).join('');
		}
		
		// Limit to 2 decimal places
		if (parts[1] && parts[1].length > 2) {
			value = parts[0] + '.' + parts[1].substring(0, 2);
		}
		
		e.target.value = value;
	});
	
	// Focus and select on click
	valorInput.addEventListener('focus', function() {
		this.select();
	});
}

// ===== ANIMATE ELEMENTS ON LOAD =====
document.addEventListener('DOMContentLoaded', function() {
	// Animate header
	const header = document.querySelector('.transferir-header');
	if (header) {
		header.style.opacity = '0';
		header.style.transform = 'translateY(20px)';
		
		setTimeout(() => {
			header.style.transition = 'all 0.6s cubic-bezier(0.4, 0, 0.2, 1)';
			header.style.opacity = '1';
			header.style.transform = 'translateY(0)';
		}, 100);
	}
	
	// Animate cards
	const cards = document.querySelectorAll('.card');
	cards.forEach((card, index) => {
		card.style.opacity = '0';
		card.style.transform = 'translateY(20px)';
		
		setTimeout(() => {
			card.style.transition = 'all 0.6s cubic-bezier(0.4, 0, 0.2, 1)';
			card.style.opacity = '1';
			card.style.transform = 'translateY(0)';
		}, 200 + (index * 100));
	});
	
	// Auto-dismiss alerts after 5 seconds
	const alerts = document.querySelectorAll('.alert');
	alerts.forEach(alert => {
		setTimeout(() => {
			const bsAlert = new bootstrap.Alert(alert);
			bsAlert.close();
		}, 5000);
	});
	
	// Focus first input
	const firstInput = document.getElementById('texto') || document.getElementById('valor');
	if (firstInput) {
		setTimeout(() => {
			firstInput.focus();
		}, 500);
	}
});