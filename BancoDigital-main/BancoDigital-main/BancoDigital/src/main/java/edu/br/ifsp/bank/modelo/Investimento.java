package edu.br.ifsp.bank.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Investimento {

    private int id;
    private int idPessoa;
    private float valor;
    private float taxaMensal;     
    private int prazoDias;        
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

    public int getPrazoDias() {
        return prazoDias;
    }

    public void setPrazoDias(int prazoDias) {
        this.prazoDias = prazoDias;
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

    public int getMesesDecorridosSimulado() {
        long minutos = ChronoUnit.MINUTES.between(dataAplicacao, LocalDateTime.now());

        if (minutos < 0) minutos = 0;
        if (minutos > prazoDias) minutos = prazoDias; 

        return (int) minutos;
    }

    public float getLucroAteAgora() {
        int mesesDecorridos = getMesesDecorridosSimulado();
        return (float) (valor * (Math.pow(1 + taxaMensal, mesesDecorridos) - 1));
    }

    public float getLucroFinal() {
        return (float) (valor * (Math.pow(1 + taxaMensal, prazoDias) - 1));
    }

    public float getLucro() {
        return getLucroFinal();
    }

    public boolean isResgatavel() {
        return getMesesDecorridosSimulado() > 0;
    }
}
