 package edu.br.ifsp.bank.web.pessoa;
/*
import java.io.IOException;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RetirarPessoaCommand implements Command{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PessoaDao pdao = new PessoaDao();
		TransferenciaDao tdao = new TransferenciaDao();
		
		String cpf = request.getParameter("cpf");
		String valorStr = request.getParameter("valor");
		
		if (valorStr == null || valorStr.isBlank()) {
		    request.setAttribute("erro", "Valor inválido.");
		    request.getRequestDispatcher("/pages/business/depositar.jsp").forward(request, response);
		    return;
		}
		
		float valor = Float.parseFloat(valorStr);

		
		HttpSession session = request.getSession();
		
		try {
			if(cpf != null && valor > 0) {
				tdao.retirar(cpf, valor);
				Pessoa p = pdao.findByCPF(cpf);
				session.setAttribute("usuarioLogado", p);
				request.getRequestDispatcher("/pages/home/home.jsp").forward(request, response);
			    return;
			}  else {
				session.setAttribute("erro", "erro ao retirar");
			}
			
			request.getRequestDispatcher("/pages/business/retirar.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/business/retirar.jsp");
		rd.forward(request, response);
	}
}*/

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

public class RetirarPessoaCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PessoaDao pdao = new PessoaDao();
        TransferenciaDao tdao = new TransferenciaDao();

        HttpSession session = request.getSession();
        Pessoa logado = (Pessoa) session.getAttribute("usuarioLogado");

        String valorStr = request.getParameter("valor");

        if (valorStr == null) {
            request.getRequestDispatcher("/pages/business/retirar.jsp").forward(request, response);
            return;
        }

        try {
            float valor = Float.parseFloat(valorStr);

            // --- REALIZA A RETIRADA ---
            Transferencia retirada = tdao.retirar(logado.getCpf(), valor);

            // Atualiza saldo na sessão
            Pessoa atualizado = pdao.findByCPF(logado.getCpf());
            session.setAttribute("usuarioLogado", atualizado);

            // ------- GERAR PDF DA RETIRADA -------
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                "attachment; filename=retirada_" + logado.getNome() + ".pdf");

            Document pdf = new Document();
            PdfWriter.getInstance(pdf, response.getOutputStream());
            pdf.open();

            GeradorPdf gerador = new GeradorPdf();
            gerador.gerarPdfRetirada(pdf, retirada, logado);

            pdf.close();
            return;

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao realizar retirada.");
            request.getRequestDispatcher("/pages/business/retirar.jsp").forward(request, response);
        }
    }
}