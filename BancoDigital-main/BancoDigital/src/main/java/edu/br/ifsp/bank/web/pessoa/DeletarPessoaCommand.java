package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;

import edu.br.ifsp.bank.*;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeletarPessoaCommand implements Command{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PessoaDao dao = new PessoaDao();
		String ids = request.getParameter("id");

	    if(ids == null) {
	    	request.setAttribute("erro", "Valor inválido.");
		    request.getRequestDispatcher("/pages/business/configuracoes/configuracoes.jsp").forward(request, response);
		    return;
	    }
	    
	    try {
			dao.deletar(Integer.parseInt(ids));
			HttpSession session = request.getSession();
			request.getSession().invalidate();
			new ViewCommand("redirect:/pages/login/login.jsp").execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
