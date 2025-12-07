// ===== CHART =====
const chartCanvas = document.getElementById('investmentChart');

if (chartCanvas && investimentos && investimentos.length > 0) {
	const ctx = chartCanvas.getContext('2d');
	
	// Prepare data
	const labels = investimentos.map((_, i) => `Investimento ${i + 1}`);
	const valores = investimentos.map(inv => inv.valor);
	const lucros = investimentos.map(inv => inv.lucro);
	
	new Chart(ctx, {
		type: 'bar',
		data: {
			labels: labels,
			datasets: [
				{
					label: 'Valor Investido (R$)',
					data: valores,
					backgroundColor: 'rgba(255, 165, 0, 0.7)',
					borderColor: 'rgba(255, 165, 0, 1)',
					borderWidth: 2,
					borderRadius: 8
				},
				{
					label: 'Lucro (R$)',
					data: lucros,
					backgroundColor: 'rgba(0, 200, 83, 0.7)',
					borderColor: 'rgba(0, 200, 83, 1)',
					borderWidth: 2,
					borderRadius: 8
				}
			]
		},
		options: {
			responsive: true,
			maintainAspectRatio: true,
			plugins: {
				legend: {
					display: true,
					position: 'top',
					labels: {
						font: {
							size: 12,
							family: 'Inter'
						},
						padding: 15,
						usePointStyle: true
					}
				},
				tooltip: {
					backgroundColor: 'rgba(30, 33, 57, 0.95)',
					padding: 12,
					titleFont: {
						size: 13,
						weight: '600'
					},
					bodyFont: {
						size: 12
					},
					borderColor: 'rgba(232, 236, 244, 0.3)',
					borderWidth: 1,
					displayColors: true,
					callbacks: {
						label: function(context) {
							return context.dataset.label + ': R$ ' + context.parsed.y.toFixed(2);
						}
					}
				}
			},
			scales: {
				y: {
					beginAtZero: true,
					ticks: {
						callback: function(value) {
							return 'R$ ' + value.toFixed(0);
						},
						font: {
							size: 11
						}
					},
					grid: {
						color: 'rgba(0, 0, 0, 0.05)'
					}
				},
				x: {
					ticks: {
						font: {
							size: 11
						}
					},
					grid: {
						display: false
					}
				}
			}
		}
	});
}

// ===== SHOW DETAILS MODAL =====
function showDetails(id, valor, taxa, prazo, lucro) {
	const modalBody = document.getElementById('modalBody');
	
	const total = (parseFloat(valor) + parseFloat(lucro)).toFixed(2);
	const rentabilidade = ((parseFloat(lucro) / parseFloat(valor)) * 100).toFixed(2);
	
	modalBody.innerHTML = `
		<div class="row g-3">
			<div class="col-12">
				<div class="detail-item">
					<div class="detail-label">
						<i class="bi bi-cash me-2"></i>Valor Investido
					</div>
					<div class="detail-value">R$ ${valor}</div>
				</div>
			</div>
			<div class="col-12">
				<div class="detail-item">
					<div class="detail-label">
						<i class="bi bi-percent me-2"></i>Taxa Mensal
					</div>
					<div class="detail-value">${taxa}%</div>
				</div>
			</div>
			<div class="col-12">
				<div class="detail-item">
					<div class="detail-label">
						<i class="bi bi-calendar3 me-2"></i>Prazo
					</div>
					<div class="detail-value">${prazo} dias</div>
				</div>
			</div>
			<div class="col-12">
				<div class="detail-item success">
					<div class="detail-label">
						<i class="bi bi-graph-up-arrow me-2"></i>Lucro Atual
					</div>
					<div class="detail-value">R$ ${lucro}</div>
				</div>
			</div>
			<div class="col-12">
				<div class="detail-item info">
					<div class="detail-label">
						<i class="bi bi-calculator me-2"></i>Total a Receber
					</div>
					<div class="detail-value">R$ ${total}</div>
				</div>
			</div>
			<div class="col-12">
				<div class="detail-item warning">
					<div class="detail-label">
						<i class="bi bi-trophy me-2"></i>Rentabilidade
					</div>
					<div class="detail-value">${rentabilidade}%</div>
				</div>
			</div>
		</div>
	`;
	
	// Add styles
	const style = document.createElement('style');
	style.textContent = `
		.detail-item {
			background: var(--bg);
			padding: 16px;
			border-radius: 12px;
			border-left: 4px solid var(--primary);
		}
		.detail-item.success {
			border-left-color: var(--success);
		}
		.detail-item.info {
			border-left-color: var(--info);
		}
		.detail-item.warning {
			border-left-color: var(--warning);
		}
		.detail-label {
			font-size: 12px;
			color: var(--text-light);
			font-weight: 600;
			text-transform: uppercase;
			letter-spacing: 0.5px;
			margin-bottom: 8px;
			display: flex;
			align-items: center;
		}
		.detail-value {
			font-size: 20px;
			color: var(--text);
			font-weight: 700;
		}
	`;
	
	if (!document.getElementById('modal-styles')) {
		style.id = 'modal-styles';
		document.head.appendChild(style);
	}
	
	const modal = new bootstrap.Modal(document.getElementById('detailsModal'));
	modal.show();
}

// ===== FORM HANDLING =====
const investForm = document.getElementById('investForm');

if (investForm) {
	investForm.addEventListener('submit', function(e) {
		const submitBtn = this.querySelector('button[type="submit"]');
		submitBtn.disabled = true;
		submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Processando...';
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
	const header = document.querySelector('.investir-header');
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
	
	// Auto-dismiss alerts
	const alerts = document.querySelectorAll('.alert');
	alerts.forEach(alert => {
		setTimeout(() => {
			const bsAlert = new bootstrap.Alert(alert);
			bsAlert.close();
		}, 5000);
	});
});