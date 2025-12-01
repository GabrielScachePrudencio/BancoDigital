package edu.br.ifsp.bank.web.pessoa;



import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExtratoPessoaCommand implements Command {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String cpf = req.getParameter("cpf");

        TransferenciaDao dao = new TransferenciaDao();
        List<Transferencia> transacoes = dao.listarPorCpf(cpf);

        if (transacoes.isEmpty()) {
            res.getWriter().println("Nenhuma transferência encontrada.");
            return;
        }

        // pega a ultima transferência da lista
        Transferencia ultima = transacoes.get(transacoes.size() - 1);

        // Se download=true -> baixa
        // Se download não existir -> preview
        boolean download = "true".equals(req.getParameter("download"));

        res.setContentType("application/pdf");

        if (download) {
            res.setHeader("Content-Disposition", "attachment; filename=extrato_" + cpf + ".pdf");
        } else {
            res.setHeader("Content-Disposition", "inline; filename=extrato_" + cpf + ".pdf");
        }

        try {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, res.getOutputStream());
            pdf.open();

            pdf.add(new Paragraph("EXTRATO DA ÚLTIMA TRANSFERÊNCIA\n\n"));
            pdf.add(new Paragraph("CPF: " + cpf + "\n"));
            pdf.add(new Paragraph("-------------------------------------------\n"));

            String tipo = (ultima.getId_usuarioQueTransferiu() == ultima.getId_usuarioQueRecebeu())
                    ? "Depósito"
                    : "Transferência";

            pdf.add(new Paragraph("Data/Hora: " + ultima.getHorario()));
            pdf.add(new Paragraph("Tipo: " + tipo));
            pdf.add(new Paragraph("Valor: R$ " + ultima.getValor()));
            pdf.add(new Paragraph("De: " + ultima.getId_usuarioQueTransferiu()));
            pdf.add(new Paragraph("Para: " + ultima.getId_usuarioQueRecebeu()));
            pdf.add(new Paragraph("\n-------------------------------------------"));
            pdf.add(new Paragraph("Fim do extrato."));

            pdf.close();

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}

