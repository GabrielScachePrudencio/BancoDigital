package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.ArrayList;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TodosUsuariosCommand implements Command {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PessoaDao dao = new PessoaDao();
    	ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
    	lista = dao.findByAll();
        request.setAttribute("todosUsuarios", lista);
        request.getRequestDispatcher("/pages/business/configuracoes/todosUsuarios.jsp").forward(request, response);

    	
    }
}
