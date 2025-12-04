// Funções para abrir e fechar modais
function modalEditar() {
	const modal = document.getElementById('modalEditar');
	modal.style.display = 'block';
	document.body.style.overflow = 'hidden';
}

function fecharModalEditar() {
	const modal = document.getElementById('modalEditar');
	modal.style.display = 'none';
	document.body.style.overflow = 'auto';
}


function modalAlterarSenha() {
	const modal = document.getElementById('modalAlterarSenha');
	modal.style.display = 'block';
	document.body.style.overflow = 'hidden';
}

function fecharModalAlterarSenha() {
	const modal = document.getElementById('modalAlterarSenha');
	modal.style.display = 'none';
	document.body.style.overflow = 'auto';
}

function modalDeletar() {
	const modal = document.getElementById('modalDeletar');
	modal.style.display = 'block';
	document.body.style.overflow = 'hidden';
}

function fecharModalDeletar() {
	const modal = document.getElementById('modalDeletar');
	modal.style.display = 'none';
	document.body.style.overflow = 'auto';
}

// Fechar modal ao clicar fora
window.onclick = function(event) {
	const modalEditar = document.getElementById('modalEditar');
	const modalDeletar = document.getElementById('modalDeletar');

	if (event.target === modalEditar) {
		fecharModalEditar();
	}
	if (event.target === modalDeletar) {
		fecharModalDeletar();
	}
}

// Fechar modal com tecla ESC
document.addEventListener('keydown', function(event) {
	if (event.key === 'Escape') {
		fecharModalEditar();
		fecharModalDeletar();
	}
});

// Animação dos cards ao carregar
document.addEventListener('DOMContentLoaded', function() {
	const configCards = document.querySelectorAll('.config-card');

	configCards.forEach((card, index) => {
		card.style.opacity = '0';
		card.style.transform = 'translateY(20px)';

		setTimeout(() => {
			card.style.transition = 'all 0.5s ease';
			card.style.opacity = '1';
			card.style.transform = 'translateY(0)';
		}, index * 100);
	});
});

// Máscara para CPF
const cpfInput = document.getElementById('cpf');
if (cpfInput) {
	cpfInput.addEventListener('input', function(e) {
		let value = e.target.value.replace(/\D/g, '');
		if (value.length <= 11) {
			value = value.replace(/(\d{3})(\d)/, '$1.$2');
			value = value.replace(/(\d{3})(\d)/, '$1.$2');
			value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
			e.target.value = value;
		}
	});
}

// Máscara para Telefone
const telefoneInput = document.getElementById('telefone');
if (telefoneInput) {
	telefoneInput.addEventListener('input', function(e) {
		let value = e.target.value.replace(/\D/g, '');
		if (value.length <= 11) {
			if (value.length <= 10) {
				value = value.replace(/(\d{2})(\d)/, '$1 $2');
				value = value.replace(/(\d{4})(\d)/, '$1-$2');
			} else {
				value = value.replace(/(\d{2})(\d)/, '$1 $2');
				value = value.replace(/(\d{5})(\d)/, '$1-$2');
			}
			e.target.value = value;
		}
	});
}

// Animação suave do saldo
const balanceElement = document.querySelector('.balance-amount');
if (balanceElement) {
	const finalValue = parseFloat(balanceElement.textContent.replace('R$', '').replace(',', '.').trim());
	let currentValue = 0;
	const duration = 1500;
	const steps = 60;
	const increment = finalValue / steps;
	const stepDuration = duration / steps;

	let step = 0;
	const timer = setInterval(() => {
		currentValue += increment;
		step++;

		if (step >= steps) {
			currentValue = finalValue;
			clearInterval(timer);
		}

		balanceElement.textContent = 'R$ ' + currentValue.toFixed(2).replace('.', ',');
	}, stepDuration);
}

// Confirmação adicional para deletar conta
const formDeletar = document.querySelector('#modalDeletar form');
if (formDeletar) {
	formDeletar.addEventListener('submit', function(e) {
		const confirmed = confirm('ATENÇÃO: Esta ação é IRREVERSÍVEL!\n\nTodos os seus dados serão permanentemente apagados.\n\nDeseja realmente continuar?');
		if (!confirmed) {
			e.preventDefault();
		}
	});
}