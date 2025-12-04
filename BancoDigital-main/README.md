🏦 Banco Digital — Projeto Java (Web)

Um sistema de banco digital desenvolvido em Java, permitindo operações como cadastro de clientes, contas, transferências e geração de extratos em PDF.
O projeto segue arquitetura em camadas e utiliza padrões que facilitam manutenção e evolução.

🚀 Tecnologias Utilizadas

Java (Servlets/JSP)

Jakarta EE

MVC

BootStrap

DAO Pattern

iTextPDF (geração de PDF)

HTML / CSS / JavaScript

Banco de dados relacional (MySQL recomendado)

📌 Funcionalidades

Cadastro e gerenciamento de clientes

Cadastro de contas bancárias

Transferências entre contas

Consulta de saldo

Geração de extratos em PDF

Organização em camadas (web, modelo, persistência)

Navegação baseada em comandos (Command pattern)

📁 Estrutura do Projeto
src/
 ├── edu.br.ifsp.bank.web/           # Servlets, controllers e comandos
 ├── edu.br.ifsp.bank.modelo/        # Entidades (Pessoa, Conta, Transferencia)
 ├── edu.br.ifsp.bank.persistencia/  # DAOs e conexão com o BD
 ├── resources/                      # Scripts e configs
 └── webapp/                         # JSPs, CSS, HTML e JS

🗄️ Banco de Dados

Tabelas recomendadas:

pessoa

conta

transferencia

Inclua no repositório seu arquivo .sql para fácil configuração.

🧪 Como Executar

Clone o repositório:

git clone https://github.com/seu-usuario/seu-repositorio.git


Importe o projeto na IDE de sua preferência.

Configure o servidor (Tomcat, Jetty ou outro compatível).

Ajuste as credenciais do banco de dados.

Inicie o servidor e acesse:

http://localhost:8080/bank

📄 Geração de PDF

O sistema utiliza a biblioteca iTextPDF para criar documentos PDF, como extratos bancários.

Exemplo de uso no projeto:

Document document = new Document();
PdfWriter.getInstance(document, response.getOutputStream());
document.open();
// conteúdo do PDF
document.close();

📦 Melhorias Futuras

API REST

Dashboard com gráficos

Autenticação com JWT

Logs de auditoria

Validações aprimoradas

👨‍💻 Autor

Gabriel Scache Prudencio
Jonas Gonçalves Fernandes
Wesley Martins
Thomaz
Estudante de Sistemas para Internet — IFSP
