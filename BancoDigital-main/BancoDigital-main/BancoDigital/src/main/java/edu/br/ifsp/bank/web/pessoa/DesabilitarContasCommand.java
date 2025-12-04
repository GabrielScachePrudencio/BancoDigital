package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.ArrayList;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DesabilitarContasCommand implements Command{
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String ids = request.getParameter("idContaParaSerDesabilitada"); 
        String termoBusca = request.getParameter("termoBusca"); 
        PessoaDao dao = new PessoaDao();
        
        if(ids != null && !ids.isEmpty()) {
            try {
                int id = Integer.parseInt(ids);
                dao.desabilitarConta(id); 
                Pessoa p = dao.findById(id); 
                request.setAttribute("sucesss", "⛔ Conta do usuário **" + p.getNome() + "** foi desabilitada com sucesso.");
            } catch (NumberFormatException e) {
                request.setAttribute("sucesss", "❌ Erro ao processar ID da conta.");
            }
        }
        
        ArrayList<Pessoa> pessoasHabilitadas; 
        
        if (termoBusca != null && !termoBusca.trim().isEmpty()) {
            pessoasHabilitadas = dao.findByNomeHabilitadas(termoBusca.trim());
        } else {
            pessoasHabilitadas = dao.findByAllPessoasHabilitadas();
        }
        
        request.setAttribute("pessoasHabilitadas", pessoasHabilitadas);

        request.getRequestDispatcher("/pages/business/desabilitarContas.jsp").forward(request, response);
    }
}