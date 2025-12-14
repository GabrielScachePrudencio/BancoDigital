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

            Transferencia retirada = tdao.retirar(logado.getCpf(), valor);

            Pessoa atualizado = pdao.findByCPF(logado.getCpf());
            session.setAttribute("usuarioLogado", atualizado);
            
            session.setAttribute("ultimaTransferencia", retirada);
	         session.setAttribute("tipoPdf", "deposito");
            
	         request.setAttribute("sucesso", true);
	         request.getRequestDispatcher("/pages/business/depositar.jsp")
	                .forward(request, response);
	         return;

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao realizar retirada.");
            request.getRequestDispatcher("/pages/business/retirar.jsp").forward(request, response);
        }
    }
}