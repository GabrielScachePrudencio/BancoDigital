// ===== MENU TOGGLE =====
const menuToggle = document.getElementById('menuToggle');
const sidebar = document.querySelector('.sidebar');

if (menuToggle) {
	menuToggle.addEventListener('click', () => {
		sidebar.classList.toggle('active');
	});
}

// ===== BALANCE ANIMATION =====
const balanceElement = document.getElementById('mainBalance');
if (balanceElement) {
	const text = balanceElement.textContent;
	const valueMatch = text.match(/[\d,.]+/);
	
	if (valueMatch) {
		const finalValue = parseFloat(valueMatch[0].replace(/\./g, '').replace(',', '.'));
		let currentValue = 0;
		const duration = 2000;
		const steps = 80;
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
			
			const formatted = currentValue.toLocaleString('pt-BR', {
				minimumFractionDigits: 2,
				maximumFractionDigits: 2
			});
			balanceElement.textContent = 'R$ ' + formatted;
		}, stepDuration);
	}
}

// ===== LOAD NEWS =====
async function fetchBankNews() {
	const newsContainer = document.getElementById('newsContainer');
	
	try {
		const mockNews = [
			{
				title: "Banco Central mantém taxa Selic em 11,75% ao ano",
				description: "Comitê de Política Monetária decide manter taxa de juros pela terceira reunião consecutiva. Decisão visa controlar inflação e estabilizar economia.",
				source: "Valor Econômico",
				date: new Date(Date.now() - 2 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=banco+central+taxa+selic&tbm=nws"
			},
			{
				title: "Bancos digitais ganham 5 milhões de novos clientes",
				description: "Fintechs continuam crescimento acelerado no Brasil. Especialistas apontam mudança no comportamento do consumidor bancário.",
				source: "InfoMoney",
				date: new Date(Date.now() - 5 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=bancos+digitais+crescimento&tbm=nws"
			},
			{
				title: "Pix quebra recorde com 3,5 bilhões de transações",
				description: "Sistema de pagamento instantâneo atinge marca histórica no último mês. Volume movimentado supera R$ 1 trilhão.",
				source: "Portal G1",
				date: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=pix+recorde+transacoes&tbm=nws"
			},
			{
				title: "Novas regras de crédito entram em vigor",
				description: "Banco Central implementa mudanças para proteger consumidores. Medidas aumentam transparência em operações de empréstimo.",
				source: "Estadão",
				date: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=novas+regras+credito&tbm=nws"
			},
			{
				title: "Investimento em cibersegurança cresce 45%",
				description: "Bancos aumentam proteção digital contra fraudes. Tecnologias de IA e machine learning são implementadas.",
				source: "Folha de S.Paulo",
				date: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=bancos+ciberseguranca&tbm=nws"
			},
			{
				title: "Taxa de financiamento imobiliário atinge mínima",
				description: "Juros para compra da casa própria chegam ao menor patamar em 5 anos. Momento favorável para realizar o sonho.",
				source: "UOL Economia",
				date: new Date(Date.now() - 4 * 24 * 60 * 60 * 1000),
				link: "https://www.google.com/search?q=financiamento+imobiliario&tbm=nws"
			}
		];
		
		let newsHTML = '';
		
		mockNews.forEach((news) => {
			const now = new Date();
			const diffTime = Math.abs(now - news.date);
			const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
			const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
			
			let formattedDate = 'Hoje';
			if (diffHours < 24) {
				if (diffHours === 0) {
					formattedDate = 'Agora mesmo';
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
				<div class="col-md-6 col-lg-4">
					<a href="${news.link}" class="news-card" target="_blank" rel="noopener">
						<div class="news-content">
							<span class="news-source">${news.source}</span>
							<h4 class="news-title">${news.title}</h4>
							<p class="news-description">${news.description}</p>
							<div class="news-footer">
								<i class="bi bi-clock me-2"></i>
								<span class="news-date">${formattedDate}</span>
							</div>
						</div>
					</a>
				</div>
			`;
		});
		
		newsContainer.innerHTML = newsHTML;
		
		// Animate news cards
		const newsCards = document.querySelectorAll('.news-card');
		newsCards.forEach((card, index) => {
			card.style.opacity = '0';
			card.style.transform = 'translateY(30px)';
			setTimeout(() => {
				card.style.transition = 'all 0.6s cubic-bezier(0.4, 0, 0.2, 1)';
				card.style.opacity = '1';
				card.style.transform = 'translateY(0)';
			}, index * 100);
		});
		
	} catch (error) {
		console.error('Erro ao carregar notícias:', error);
		newsContainer.innerHTML = `
			<div class="col-12 text-center py-5">
				<i class="bi bi-exclamation-circle text-danger" style="font-size: 3rem;"></i>
				<p class="text-muted mt-3">Não foi possível carregar as notícias.</p>
			</div>
		`;
	}
}

// ===== ANIMATE CARDS ON LOAD =====
document.addEventListener('DOMContentLoaded', () => {
	// Animate balance card
	const balanceCard = document.querySelector('.balance-card-main');
	if (balanceCard) {
		balanceCard.style.opacity = '0';
		balanceCard.style.transform = 'translateY(30px)';
		setTimeout(() => {
			balanceCard.style.transition = 'all 0.6s cubic-bezier(0.4, 0, 0.2, 1)';
			balanceCard.style.opacity = '1';
			balanceCard.style.transform = 'translateY(0)';
		}, 100);
	}
	
	// Animate action cards
	const actionCards = document.querySelectorAll('.action-card');
	actionCards.forEach((card, index) => {
		card.style.opacity = '0';
		card.style.transform = 'scale(0.9)';
		setTimeout(() => {
			card.style.transition = 'all 0.5s cubic-bezier(0.4, 0, 0.2, 1)';
			card.style.opacity = '1';
			card.style.transform = 'scale(1)';
		}, 300 + (index * 80));
	});
	
	// Animate info cards
	const infoCards = document.querySelectorAll('.info-card');
	infoCards.forEach((card, index) => {
		card.style.opacity = '0';
		card.style.transform = 'translateY(30px)';
		setTimeout(() => {
			card.style.transition = 'all 0.6s cubic-bezier(0.4, 0, 0.2, 1)';
			card.style.opacity = '1';
			card.style.transform = 'translateY(0)';
		}, 800 + (index * 200));
	});
	
	// Load news
	if (document.getElementById('newsContainer')) {
		fetchBankNews();
	}
});

// ===== CLOSE SIDEBAR ON OUTSIDE CLICK (MOBILE) =====
document.addEventListener('click', (e) => {
	if (window.innerWidth <= 768) {
		if (!sidebar.contains(e.target) && !menuToggle.contains(e.target)) {
			sidebar.classList.remove('active');
		}
	}
});