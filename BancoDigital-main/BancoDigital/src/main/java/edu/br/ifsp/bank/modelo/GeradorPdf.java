package edu.br.ifsp.bank.modelo;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;

public class GeradorPdf {

    public void gerarPdfBoleto(Document pdf, Transferencia t, Pessoa pagador, Pessoa recebedor) throws Exception {

        pdf.add(new Paragraph("COMPROVANTE DE TRANSFERÊNCIA"));
        pdf.add(new Paragraph("==========================================\n"));

        pdf.add(new Paragraph("PAGADOR"));
        pdf.add(new Paragraph("Nome: " + pagador.getNome()));
        pdf.add(new Paragraph("CPF: " + pagador.getCpf()));
        pdf.add(new Paragraph("\n"));

        pdf.add(new Paragraph("DESTINATÁRIO"));	
        pdf.add(new Paragraph("Nome: " + recebedor.getNome()));
        pdf.add(new Paragraph("CPF: " + recebedor.getCpf()));
        pdf.add(new Paragraph("\n"));

        pdf.add(new Paragraph("VALOR TRANSFERIDO: R$ " + t.getValor()));
        pdf.add(new Paragraph("Data: " + t.getHorario()));
        pdf.add(new Paragraph("\n"));
    }
    
    
    public void gerarPdfRetirada(Document pdf, Transferencia retirada, Pessoa usuario) throws Exception {

        pdf.add(new Paragraph("COMPROVANTE DE RETIRADA"));
        pdf.add(new Paragraph(" "));
        pdf.add(new Paragraph("Nome: " + usuario.getNome()));
        pdf.add(new Paragraph("CPF: " + usuario.getCpf()));
        pdf.add(new Paragraph("Valor retirado: R$ " + String.format("%.2f", retirada.getValor())));
        pdf.add(new Paragraph("Data/Hora: " + retirada.getHorario()));
        pdf.add(new Paragraph(" "));
        pdf.add(new Paragraph("Operação realizada com sucesso."));
    }


}
