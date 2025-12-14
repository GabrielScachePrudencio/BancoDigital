package edu.br.ifsp.bank.web;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import edu.br.ifsp.bank.modelo.GeradorPdf;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GerarPdfCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String tipo = request.getParameter("tipo"); // deposito | saque | transferencia
        Transferencia t = (Transferencia) session.getAttribute("ultimaTransferencia");
        Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");

        if (tipo == null || t == null || usuario == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader(
            "Content-Disposition",
            "attachment; filename=" + tipo + "_" + usuario.getNome() + ".pdf"
        );

        try {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, response.getOutputStream());
            pdf.open();

            GeradorPdf gerador = new GeradorPdf();

            switch (tipo) {
            case "deposito":
                gerador.gerarPdfDepRet(pdf, t, usuario, "DEPÓSITO");
                break;

            case "saque":
                gerador.gerarPdfDepRet(pdf, t, usuario, "SAQUE");
                break;

            case "transferencia":
            	PessoaDao pdao = new PessoaDao();

            	Pessoa pagador = pdao.findById(t.getId_usuarioQueTransferiu());
            	Pessoa recebedor = pdao.findById(t.getId_usuarioQueRecebeu());

            	gerador.gerarPdfBoletoTransf(
            	    pdf,
            	    t,
            	    pagador,
            	    recebedor
            	);;
        }


            pdf.close();


            session.removeAttribute("ultimaTransferencia");
            session.removeAttribute("tipoPdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
