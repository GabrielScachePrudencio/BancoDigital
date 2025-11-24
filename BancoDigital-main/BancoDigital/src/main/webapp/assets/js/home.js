// Animação dos cards ao carregar a página
document.addEventListener('DOMContentLoaded', function() {
	const menuCards = document.querySelectorAll('.menu-card');

	menuCards.forEach((card, index) => {
		card.style.opacity = '0';
		card.style.transform = 'translateY(20px)';

		setTimeout(() => {
			card.style.transition = 'all 0.5s ease';
			card.style.opacity = '1';
			card.style.transform = 'translateY(0)';
		}, index * 100);
	});
});

// Adiciona efeito de ripple nos cards ao clicar
const menuCards = document.querySelectorAll('.menu-card');

menuCards.forEach(card => {
	card.addEventListener('click', function(e) {
		const ripple = document.createElement('span');
		const rect = this.getBoundingClientRect();
		const size = Math.max(rect.width, rect.height);
		const x = e.clientX - rect.left - size / 2;
		const y = e.clientY - rect.top - size / 2;

		ripple.style.width = ripple.style.height = size + 'px';
		ripple.style.left = x + 'px';
		ripple.style.top = y + 'px';
		ripple.classList.add('ripple');

		this.appendChild(ripple);

		setTimeout(() => {
			ripple.remove();
		}, 600);
	});
});

// Animação suave do saldo
const balanceElement = document.querySelector('.balance-amount');
if (balanceElement) {
	const finalValue = parseFloat(balanceElement.textContent.replace('R$', '').replace(',', '.'));
	let currentValue = 0;
	const duration = 1500; // 1.5 segundos
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

// Adiciona confirmação no botão de sair
const logoutCard = document.querySelector('.logout-card');
if (logoutCard) {
	logoutCard.addEventListener('click', function(e) {
		if (!confirm('Tem certeza que deseja sair?')) {
			e.preventDefault();
		}
	});
}

// Estilo CSS para o efeito ripple (adicionar dinamicamente)
const style = document.createElement('style');
style.textContent = `
	.menu-card {
		position: relative;
		overflow: hidden;
	}
	
	.ripple {
		position: absolute;
		border-radius: 50%;
		background: rgba(102, 126, 234, 0.3);
		transform: scale(0);
		animation: ripple-animation 0.6s ease-out;
		pointer-events: none;
	}
	
	@keyframes ripple-animation {
		to {
			transform: scale(4);
			opacity: 0;
		}
	}
`;
document.head.appendChild(style);

// Buscar notícias sobre bancos
async function fetchBankNews() {
	const newsContainer = document.getElementById('newsContainer');

	try {
		// Notícias simuladas - Em produção, substituir por uma API backend
		const mockNews = [
			{
				title: "Banco Central anuncia nova taxa Selic",
				description: "O Comitê de Política Monetária decidiu manter a taxa básica de juros. A decisão foi tomada em reunião extraordinária com foco no controle da inflação.",
				source: "Portal G1",
				date: new Date(Date.now() - 2 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=banco+central+taxa+selic&tbm=nws",
				image: "images/noticiabanco.jpg"
			},
			{
				title: "Bancos digitais crescem 40% no último trimestre",
				description: "O setor de bancos digitais continua em expansão com milhões de novos clientes. Especialistas apontam que a tendência deve continuar nos próximos anos.",
				source: "InfoMoney",
				date: new Date(Date.now() - 5 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=bancos+digitais+crescimento&tbm=nws",
				image: "images/noticiabanco.jpg"
			},
			{
				title: "Pix ultrapassa cartão de crédito em transações",
				description: "Sistema de pagamento instantâneo do Banco Central registra recorde histórico. O Pix já representa mais de 50% das transações bancárias no país.",
				source: "Valor Econômico",
				date: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=pix+recorde+transacoes&tbm=nws",
				image: "images/noticiabanco.jpg"
			},
			{
				title: "Novas regras para empréstimos entram em vigor",
				description: "Banco Central implementa mudanças na regulamentação de crédito pessoal. As novas diretrizes visam proteger consumidores e aumentar transparência.",
				source: "Estadão",
				date: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=novas+regras+emprestimos+banco+central&tbm=nws",
				image: "images/noticiabanco.jpg"
			},
			{
				title: "Bancos investem em segurança cibernética",
				description: "Instituições financeiras aumentam investimentos em proteção de dados. Medidas visam combater fraudes e golpes digitais cada vez mais sofisticados.",
				source: "Folha de S.Paulo",
				date: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=bancos+seguranca+cibernetica&tbm=nws",
				image: "images/noticiabanco.jpg"
			},
			{
				title: "Juros de financiamento imobiliário caem",
				description: "Taxas para compra da casa própria atingem menor patamar do ano. Especialistas recomendam aproveitar o momento para realizar o sonho da casa própria.",
				source: "UOL Economia",
				date: new Date(Date.now() - 4 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=juros+financiamento+imobiliario&tbm=nws",
				image: "images/noticiabanco.jpg"
			}
		];

		let newsHTML = '';

		mockNews.forEach((news, index) => {
			// Formatar data
			const now = new Date();
			const diffTime = Math.abs(now - news.date);
			const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
			const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

			let formattedDate = 'Hoje';
			if (diffHours < 24) {
				if (diffHours === 0) {
					formattedDate = 'Agora';
				} else if (diffHours === 1) {
					formattedDate = 'Há 1 hora';
				} else {
					formattedDate = `Há ${diffHours} horas`;
				}
			} else if (diffDays === 1) {
				formattedDate = 'Ontem';
			} else if (diffDays < 7) {
				formattedDate = `Há ${diffDays} dias`;
			} else {
				formattedDate = news.date.toLocaleDateString('pt-BR');
			}

			newsHTML += `
				<a href="${news.link}" class="news-card" target="_blank" rel="noopener">
					<div class="news-content">
						<span class="news-source">${news.source}</span>
						<h4 class="news-title">${news.title}</h4>
						<p class="news-description">${news.description}</p>
						<span class="news-date">🕒 ${formattedDate}</span>
					</div>
				</a>
			`;
		});

		newsContainer.innerHTML = newsHTML;

		// Animar entrada das notícias
		const newsCards = document.querySelectorAll('.news-card');
		newsCards.forEach((card, index) => {
			card.style.opacity = '0';
			card.style.transform = 'translateY(20px)';
			setTimeout(() => {
				card.style.transition = 'all 0.5s ease';
				card.style.opacity = '1';
				card.style.transform = 'translateY(0)';
			}, index * 100);
		});

	} catch (error) {
		console.error('Erro ao carregar notícias:', error);
		newsContainer.innerHTML = '<div class="news-error">⚠️ Não foi possível carregar as notícias. Tente novamente mais tarde.</div>';
	}
}

// Carregar notícias quando a página carregar
if (document.getElementById('newsContainer')) {
	fetchBankNews();
}