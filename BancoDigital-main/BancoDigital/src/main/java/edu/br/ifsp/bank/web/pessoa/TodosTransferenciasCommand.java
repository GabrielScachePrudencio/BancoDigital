package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.ArrayList;

import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TodosTransferenciasCommand implements Command{
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	TransferenciaDao dao = new TransferenciaDao();
    	ArrayList<Transferencia> lista = new ArrayList<Transferencia>();
    	
    	lista = dao.findByAll();
        request.setAttribute("todosTransferencias", lista);
        request.getRequestDispatcher("/pages/business/configuracoes/todosTransferencias.jsp").forward(request, response);

    	
    }
}
