package edu.br.ifsp.bank.modelo;

public class ParcelaSac {
    private int numero;
    private double amortizacao;
    private double juros;
    private double valorParcela;
    private double saldoDevedor;

    public ParcelaSac(int numero, double amortizacao, double juros,
                      double valorParcela, double saldoDevedor) {
        this.numero = numero;
        this.amortizacao = amortizacao;
        this.juros = juros;
        this.valorParcela = valorParcela;
        this.saldoDevedor = saldoDevedor;
    }

    public int getNumero() {
        return numero;
    }

    public double getAmortizacao() {
        return amortizacao;
    }

    public double getJuros() {
        return juros;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }
}
