package edu.br.ifsp.bank.modelo;

import java.time.LocalDateTime;

public class Transferencia {
	private int id;
	private int id_usuarioQueTransferiu;
	private int id_usuarioQueRecebeu;
	private LocalDateTime horario;
	private float valor;
	
	public Transferencia() {
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_usuarioQueTransferiu() {
		return id_usuarioQueTransferiu;
	}
	public void setId_usuarioQueTransferiu(int id_usuarioQueTransferiu) {
		this.id_usuarioQueTransferiu = id_usuarioQueTransferiu;
	}
	public int getId_usuarioQueRecebeu() {
		return id_usuarioQueRecebeu;
	}
	public void setId_usuarioQueRecebeu(int id_usuarioQueRecebeu) {
		this.id_usuarioQueRecebeu = id_usuarioQueRecebeu;
	}
		public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}


	public LocalDateTime getHorario() {
		return horario;
	}


	public void setHorario(LocalDateTime horario) {
		this.horario = horario;
	}
	
	
	
	
	
	
			
	
}
