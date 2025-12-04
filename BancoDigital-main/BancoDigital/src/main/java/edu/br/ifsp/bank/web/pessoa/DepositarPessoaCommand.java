package edu.br.ifsp.bank.web.pessoa;



import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import edu.br.ifsp.bank.modelo.GeradorPdf;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DepositarPessoaCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PessoaDao pdao = new PessoaDao();
        TransferenciaDao tdao = new TransferenciaDao();
        HttpSession session = request.getSession();

        String cpf = request.getParameter("cpf");
        String valorStr = request.getParameter("valor");

        if (valorStr == null || valorStr.isBlank()) {
            request.setAttribute("erro", "Valor inválido.");
            request.getRequestDispatcher("/pages/business/depositar.jsp").forward(request, response);
            return;
        }

        try {
            float valor = Float.parseFloat(valorStr);

            // ----- REALIZA O DEPÓSITO NO BANCO -----
            Transferencia deposito = tdao.depositar(cpf, valor);

            // Atualiza saldo na sessão
            Pessoa atualizado = pdao.findByCPF(cpf);
            session.setAttribute("usuarioLogado", atualizado);

            // -------- GERAR PDF DO DEPÓSITO ----------
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment; filename=deposito_" + atualizado.getNome() + ".pdf");

            Document pdf = new Document();
            PdfWriter.getInstance(pdf, response.getOutputStream());
            pdf.open();

            GeradorPdf gerador = new GeradorPdf();
            gerador.gerarPdfBoleto(pdf, deposito, atualizado, atualizado); // mesma pessoa

            pdf.close();
            return;

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao depositar.");
            request.getRequestDispatcher("/pages/business/depositar.jsp").forward(request, response);
        }
    }
}

