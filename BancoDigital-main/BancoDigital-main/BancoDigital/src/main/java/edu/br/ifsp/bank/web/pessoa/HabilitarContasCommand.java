package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.ArrayList;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HabilitarContasCommand implements Command{
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String ids = request.getParameter("idContaParaSerHabilitada");
        String termoBusca = request.getParameter("termoBusca"); 
        PessoaDao dao = new PessoaDao();
        
        if(ids != null && !ids.isEmpty()) {
            try {
                int id = Integer.parseInt(ids);
                dao.habilitarConta(id);
                Pessoa p = dao.findById(id);
                request.setAttribute("sucesss", "✅ Usuário **" + p.getNome() + "** foi habilitado com sucesso.");
            } catch (NumberFormatException e) {
                request.setAttribute("sucesss", "❌ Erro ao processar ID da conta.");
            }
        }
        
        ArrayList<Pessoa> pessoasNaoHabilitadas;
        
        if (termoBusca != null && !termoBusca.trim().isEmpty()) {
            pessoasNaoHabilitadas = dao.findByNomeDesabilitadas(termoBusca.trim());
        } else {
            pessoasNaoHabilitadas = dao.findByAllPessoasDesabilitadas();
        }
        
        request.setAttribute("pessoasNaoHabilitadas", pessoasNaoHabilitadas);

        request.getRequestDispatcher("/pages/business/habilitarContas.jsp").forward(request, response);
    }
}