// ===== CHART =====
const chartCanvas = document.getElementById('loanChart');

if (chartCanvas && parcelas && parcelas.length > 0) {
	const ctx = chartCanvas.getContext('2d');
	
	// Calculate totals
	let totalAmortizacao = 0;
	let totalJuros = 0;
	
	parcelas.forEach(p => {
		totalAmortizacao += p.amortizacao;
		totalJuros += p.juros;
	});
	
	// Create gradients
	const gradient1 = ctx.createLinearGradient(0, 0, 0, 400);
	gradient1.addColorStop(0, 'rgba(138, 5, 190, 0.8)');
	gradient1.addColorStop(1, 'rgba(98, 0, 234, 0.8)');
	
	const gradient2 = ctx.createLinearGradient(0, 0, 0, 400);
	gradient2.addColorStop(0, 'rgba(234, 29, 44, 0.8)');
	gradient2.addColorStop(1, 'rgba(198, 40, 40, 0.8)');
	
	new Chart(ctx, {
		type: 'doughnut',
		data: {
			labels: ['Valor Principal', 'Juros'],
			datasets: [{
				data: [totalAmortizacao, totalJuros],
				backgroundColor: [gradient1, gradient2],
				borderWidth: 0,
				borderRadius: 8,
				spacing: 4
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			cutout: '70%',
			plugins: {
				legend: {
					display: true,
					position: 'bottom',
					labels: {
						font: {
							size: 13,
							family: 'Inter',
							weight: '600'
						},
						padding: 20,
						usePointStyle: true,
						pointStyle: 'circle'
					}
				},
				tooltip: {
					backgroundColor: 'rgba(30, 33, 57, 0.95)',
					padding: 16,
					titleFont: {
						size: 14,
						weight: '600'
					},
					bodyFont: {
						size: 13
					},
					borderColor: 'rgba(232, 236, 244, 0.3)',
					borderWidth: 1,
					displayColors: true,
					boxWidth: 12,
					boxHeight: 12,
					boxPadding: 6,
					usePointStyle: true,
					callbacks: {
						label: function(context) {
							const value = context.parsed;
							const total = context.dataset.data.reduce((a, b) => a + b, 0);
							const percentage = ((value / total) * 100).toFixed(1);
							return context.label + ': R$ ' + value.toFixed(2) + ' (' + percentage + '%)';
						}
					}
				}
			}
		}
	});
}

// ===== FORM HANDLING =====
const loanForm = document.getElementById('loanForm');

if (loanForm) {
	loanForm.addEventListener('submit', function(e) {
		const submitBtn = this.querySelector('button[type="submit"]');
		submitBtn.disabled = true;
		submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Simulando...';
	});
}

// ===== INPUT FORMATTING =====
const valorInput = document.getElementById('valor');
const taxaInput = document.getElementById('taxa');

[valorInput, taxaInput].forEach(input => {
	if (input) {
		input.addEventListener('input', function(e) {
			let value = e.target.value.replace(/[^\d.]/g, '');
			const parts = value.split('.');
			if (parts.length > 2) {
				value = parts[0] + '.' + parts.slice(1).join('');
			}
			if (parts[1] && parts[1].length > 2) {
				value = parts[0] + '.' + parts[1].substring(0, 2);
			}
			e.target.value = value;
		});
	}
});

// ===== ANIMATE ON LOAD =====
document.addEventListener('DOMContentLoaded', function() {
	// Animate header
	const header = document.querySelector('.emprestimo-header');
	if (header) {
		header.style.opacity = '0';
		header.style.transform = 'translateY(20px)';
		setTimeout(() => {
			header.style.transition = 'all 0.6s ease';
			header.style.opacity = '1';
			header.style.transform = 'translateY(0)';
		}, 100);
	}
	
	// Animate KPIs
	const kpis = document.querySelectorAll('.kpi-card');
	kpis.forEach((kpi, index) => {
		kpi.style.opacity = '0';
		kpi.style.transform = 'scale(0.9)';
		setTimeout(() => {
			kpi.style.transition = 'all 0.5s ease';
			kpi.style.opacity = '1';
			kpi.style.transform = 'scale(1)';
		}, 200 + (index * 100));
	});
	
	// Animate cards
	const cards = document.querySelectorAll('.card');
	cards.forEach((card, index) => {
		card.style.opacity = '0';
		card.style.transform = 'translateY(20px)';
		setTimeout(() => {
			card.style.transition = 'all 0.6s ease';
			card.style.opacity = '1';
			card.style.transform = 'translateY(0)';
		}, 300 + (index * 100));
	});
	
	// Animate table rows
	const tableRows = document.querySelectorAll('.loan-table tbody tr');
	tableRows.forEach((row, index) => {
		row.style.opacity = '0';
		row.style.transform = 'translateX(-20px)';
		setTimeout(() => {
			row.style.transition = 'all 0.4s ease';
			row.style.opacity = '1';
			row.style.transform = 'translateX(0)';
		}, 600 + (index * 50));
	});
	
	// Auto-dismiss alerts
	const alerts = document.querySelectorAll('.alert');
	alerts.forEach(alert => {
		setTimeout(() => {
			const bsAlert = new bootstrap.Alert(alert);
			bsAlert.close();
		}, 5000);
	});
	
	// Focus first input
	if (valorInput) {
		setTimeout(() => {
			valorInput.focus();
		}, 500);
	}
});