package edu.br.ifsp.bank.web.pessoa;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;

public class DepositarPessoaCommand implements Command {

       
  
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
		
		try {
			if(cpf != null && valor > 0) {
			    tdao.depositar(cpf, valor);
			    Pessoa p = pdao.findByCPF(cpf);
			    HttpSession session = request.getSession();
			    session.setAttribute("usuarioLogado", p);

			    request.getRequestDispatcher("/pages/home/home.jsp").forward(request, response);
			    return;
			} else {
			    HttpSession session = request.getSession();
			    session.setAttribute("erro", "erro ao depositar");
			}

			request.getRequestDispatcher("/pages/business/depositar.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/business/depositar.jsp");
		rd.forward(request, response);
	}
}
