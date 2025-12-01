package edu.br.ifsp.bank.modelo;



import java.io.Serializable;

public class HistoricoItem implements Serializable {
    private Transferencia transferencia;
    private String nomeRemetente;
    private String cpfRemetente;
    private String nomeDestinatario;
    private String cpfDestinatario;

    public HistoricoItem() {}

    public HistoricoItem(Transferencia transferencia, String nomeRemetente, String cpfRemetente,
                         String nomeDestinatario, String cpfDestinatario) {
        this.transferencia = transferencia;
        this.nomeRemetente = nomeRemetente;
        this.cpfRemetente = cpfRemetente;
        this.nomeDestinatario = nomeDestinatario;
        this.cpfDestinatario = cpfDestinatario;
    }

    public Transferencia getTransferencia() { return transferencia; }
    public String getNomeRemetente() { return nomeRemetente; }
    public String getCpfRemetente() { return cpfRemetente; }
    public String getNomeDestinatario() { return nomeDestinatario; }
    public String getCpfDestinatario() { return cpfDestinatario; }

    public void setTransferencia(Transferencia transferencia) { this.transferencia = transferencia; }
    public void setNomeRemetente(String nomeRemetente) { this.nomeRemetente = nomeRemetente; }
    public void setCpfRemetente(String cpfRemetente) { this.cpfRemetente = cpfRemetente; }
    public void setNomeDestinatario(String nomeDestinatario) { this.nomeDestinatario = nomeDestinatario; }
    public void setCpfDestinatario(String cpfDestinatario) { this.cpfDestinatario = cpfDestinatario; }
}
