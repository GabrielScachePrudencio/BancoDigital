package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.List;

import edu.br.ifsp.bank.modelo.Investimento;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.InvestimentoDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListarInvestimentosCommand implements Command {

    private InvestimentoDao investimentoDao = new InvestimentoDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Pessoa usuario = (Pessoa) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Investimento> investimentos =
                    investimentoDao.listarPorPessoa(usuario.getId());

            request.setAttribute("investimentos", investimentos);

        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao buscar investimentos: " + e.getMessage());
        }

        request.getRequestDispatcher("/pages/business/configuracoes/investir.jsp")
               .forward(request, response);
    }
}
