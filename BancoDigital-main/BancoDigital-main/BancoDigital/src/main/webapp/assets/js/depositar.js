// ===== FORM HANDLING =====
const depositForm = document.getElementById('depositForm');
const valorInput = document.getElementById('valor');

if (depositForm) {
	depositForm.addEventListener('submit', function(e) {
		const valor = valorInput.value;
		
		if (!valor || parseFloat(valor) <= 0) {
			e.preventDefault();
			alert('Por favor, digite um valor válido para depositar');
			return;
		}
		
		// Add loading state
		const submitBtn = this.querySelector('button[type="submit"]');
		submitBtn.disabled = true;
		submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Processando...';
	});
}

// ===== MONEY INPUT FORMATTING =====
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
	
	// Add animation on focus
	valorInput.addEventListener('focus', function() {
		const inputGroup = this.closest('.input-group');
		if (inputGroup) {
			inputGroup.style.transform = 'scale(1.02)';
		}
	});
	
	valorInput.addEventListener('blur', function() {
		const inputGroup = this.closest('.input-group');
		if (inputGroup) {
			inputGroup.style.transform = 'scale(1)';
		}
	});
}

// ===== ANIMATE ELEMENTS ON LOAD =====
document.addEventListener('DOMContentLoaded', function() {
	// Animate header
	const header = document.querySelector('.depositar-header');
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
	
	// Animate info items
	const infoItems = document.querySelectorAll('.info-item');
	infoItems.forEach((item, index) => {
		item.style.opacity = '0';
		item.style.transform = 'translateX(-20px)';
		
		setTimeout(() => {
			item.style.transition = 'all 0.5s cubic-bezier(0.4, 0, 0.2, 1)';
			item.style.opacity = '1';
			item.style.transform = 'translateX(0)';
		}, 600 + (index * 100));
	});
	
	// Focus valor input
	if (valorInput) {
		setTimeout(() => {
			valorInput.focus();
		}, 800);
	}
	
	// Add input group transition
	const inputGroups = document.querySelectorAll('.input-group');
	inputGroups.forEach(group => {
		group.style.transition = 'transform 0.2s ease';
	});
});