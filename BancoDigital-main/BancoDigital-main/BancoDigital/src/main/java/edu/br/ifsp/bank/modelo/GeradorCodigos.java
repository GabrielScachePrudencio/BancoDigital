package edu.br.ifsp.bank.modelo;

import java.util.Random;

public class GeradorCodigos {

    private static GeradorCodigos instancia;
    private Integer codigoAtual;

    private GeradorCodigos() {}

    public static GeradorCodigos getInstance() {
        if (instancia == null) {
            instancia = new GeradorCodigos();
        }
        return instancia;
    }

    public void criaCodigo() {
        Random r = new Random();
        codigoAtual = r.nextInt(10000); 
    }

    public String getCodigo() {
        if (codigoAtual == null) {
            criaCodigo();
        }
        return String.format("%04d", codigoAtual);
    }

    public Integer getCodigoInt() {
        return Integer.parseInt(getCodigo());
    }
}
