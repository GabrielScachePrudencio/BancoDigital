package edu.br.ifsp.bank.web.login;

import java.io.IOException;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import edu.br.ifsp.bank.web.ViewCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerificacaoCodigoCommand implements Command{
	public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String codigo = request.getParameter("codigo");
		
        if (codigo == null || codigo.isBlank()) {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/pages/login/verificacaoCodigo.jsp").forward(request, response);
            return;
        }
        
        Integer codigoInt = Integer.parseInt(codigo);
        PessoaDao dao = new PessoaDao();
        
		
		
		if (dao.validaCodigo(codigoInt)) {
            HttpSession session = request.getSession();
            session.setAttribute("isVerificado", true); 
            
		    request.getRequestDispatcher("/pages/home/home.jsp").forward(request, response);
		    return;
		} else {
		    request.setAttribute("error", true);
		    request.getRequestDispatcher("/pages/login/verificacaoCodigo.jsp").forward(request, response);
		    return;
		}

    }
}