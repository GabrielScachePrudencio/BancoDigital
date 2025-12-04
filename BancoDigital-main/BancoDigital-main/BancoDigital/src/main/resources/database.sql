DROP DATABASE IF EXISTS bank;
CREATE DATABASE bank;
USE bank;

-- Tabela Pessoa
CREATE TABLE pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    saldo DECIMAL(10, 2) DEFAULT 0.00,
    role ENUM('ADMIN', 'USER', 'GERENTE') NOT NULL DEFAULT 'USER',
    habilitadoPeloGerente BOOLEAN NOT NULL DEFAULT FALSE
);



DROP TABLE pessoa;

CREATE TABLE investimento (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    id_pessoa     INT NOT NULL,
    valor         DECIMAL(10,2) NOT NULL,
    taxa_mensal   DECIMAL(5,4)  NOT NULL,  -- 0.0100 = 1% ao mês
    prazo_meses   INT NOT NULL,
    data_aplicacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
);

-- Tabela Transferência
CREATE TABLE transferencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuarioQueTransferiu INT NOT NULL,
    id_usuarioQueRecebeu INT NOT NULL,
    horario DATETIME NOT NULL,
    valor DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_transferiu FOREIGN KEY (id_usuarioQueTransferiu)
        REFERENCES pessoa(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_recebeu FOREIGN KEY (id_usuarioQueRecebeu)
        REFERENCES pessoa(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Inserções de exemplo (senha deve virar hash depois)
INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) 
VALUES 
('123.456.789-00', 'João Silva', 'senha123', 'joao@example.com', '1234567890', 'Rua A, 123', 1000.50, 'ADMIN');

INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) 
VALUES 
('432.456.789-00', 'gerente', 'gerente', 'bielscache@gmail.com', '1234567890', 'Rua A, 123', 0, 'GERENTE');

INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) 
VALUES 
('323.456.789-00', '1', '1', 'bielscache@gmail.com', '1234567890', 'Rua A, 123', 1000.50, 'ADMIN');


INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) 
VALUES 
('987.654.321-00', 'Maria Souza', 'senha456', 'maria@example.com', '0987654321', 'Avenida B, 456', 500.75, 'USER');

INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) 
VALUES 
('111.222.333-44', 'Carlos Oliveira', 'senha789', 'carlos@example.com', '1122334455', 'Rua C, 789', 0.00, 'USER');

INSERT INTO pessoa (cpf, nome, senha, email, telefone, endereco, saldo, role) 
VALUES 
('211.222.333-44', 'Usuário Teste', '1', 'teste@example.com', '1122334455', 'Rua C, 789', 0.00, 'USER');

-- Consultas
SELECT * FROM pessoa;
SELECT * FROM transferencia;	
