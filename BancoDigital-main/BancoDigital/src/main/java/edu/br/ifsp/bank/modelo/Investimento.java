package edu.br.ifsp.bank.modelo;

import java.time.LocalDateTime;

public class Investimento {

	private int id;
	private int idPessoa;
	private float valor;
	private float taxaMensal;
	private int prazoMeses;
	private LocalDateTime dataAplicacao;
	private String nomePessoa;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getTaxaMensal() {
		return taxaMensal;
	}

	public void setTaxaMensal(float taxaMensal) {
		this.taxaMensal = taxaMensal;
	}

	public int getPrazoMeses() {
		return prazoMeses;
	}

	public void setPrazoMeses(int prazoMeses) {
		this.prazoMeses = prazoMeses;
	}

	public LocalDateTime getDataAplicacao() {
		return dataAplicacao;
	}

	public void setDataAplicacao(LocalDateTime dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}
	
	public String getNomePessoa() {
	    return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
	    this.nomePessoa = nomePessoa;
	}
	
	public float getLucro() {
	    return (float)(valor * (Math.pow(1 + taxaMensal, prazoMeses) - 1));
	}
}
