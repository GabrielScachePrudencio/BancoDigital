package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.List;

import edu.br.ifsp.bank.modelo.Investimento;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.InvestimentoDao;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class InvestirPessoaCommand implements Command {

    private PessoaDao pessoaDao = new PessoaDao();
    private InvestimentoDao investimentoDao = new InvestimentoDao();   
    

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Pessoa usuario = (Pessoa) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        float valor = Float.parseFloat(request.getParameter("valor"));
        float taxaPercent = Float.parseFloat(request.getParameter("taxa"));
        float taxaMensal = taxaPercent / 100f;   // transforma em decimal
        int prazo = Integer.parseInt(request.getParameter("prazo"));

        try {
            // aplica investimento (já debita o saldo no banco)
            pessoaDao.investir(usuario.getCpf(), valor, taxaMensal, prazo);

            // atualiza saldo na sessão
            usuario.setSaldo(usuario.getSaldo() - valor);
            request.getSession().setAttribute("usuarioLogado", usuario);

            // BUSCA TODOS OS INVESTIMENTOS DESSE USUÁRIO
            List<Investimento> investimentos =
                    investimentoDao.listarPorPessoa(usuario.getId());

            request.setAttribute("investimentos", investimentos);
            request.setAttribute("msg", "Investimento realizado com sucesso!");

            request.getRequestDispatcher("/pages/business/configuracoes/investir.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/pages/business/configuracoes/investir.jsp")
                   .forward(request, response);
        }
    }
}
