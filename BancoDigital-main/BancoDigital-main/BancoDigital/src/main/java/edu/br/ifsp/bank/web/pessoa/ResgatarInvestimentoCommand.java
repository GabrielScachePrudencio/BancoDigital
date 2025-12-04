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

public class ResgatarInvestimentoCommand implements Command {

    private InvestimentoDao investimentoDao = new InvestimentoDao();
    private PessoaDao pessoaDao = new PessoaDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Pessoa usuario = (Pessoa) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int idInvestimento = Integer.parseInt(request.getParameter("idInvestimento"));

            Investimento inv = investimentoDao.buscarPorId(idInvestimento);

            if (inv == null || inv.getIdPessoa() != usuario.getId()) {
                request.setAttribute("erro", "Investimento não encontrado.");
            } else if (!inv.isResgatavel()) {
                request.setAttribute("erro",
                        "Ainda não é possível resgatar. Aguarde pelo menos 1 minuto (1 mês simulado).");
            } else {
                float lucro = inv.getLucroAteAgora();
                float totalResgate = inv.getValor() + lucro;

                pessoaDao.depositar(usuario.getCpf(), totalResgate);

                usuario.setSaldo(usuario.getSaldo() + totalResgate);
                request.getSession().setAttribute("usuarioLogado", usuario);

                investimentoDao.excluir(idInvestimento);

                request.setAttribute("msg", "Resgate realizado com sucesso! Valor creditado: R$ "
                        + String.format("%.2f", totalResgate));
            }

            List<Investimento> investimentos =
                    investimentoDao.listarPorPessoa(usuario.getId());
            request.setAttribute("investimentos", investimentos);

            request.getRequestDispatcher("/pages/business/configuracoes/investir.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao resgatar investimento: " + e.getMessage());
            request.getRequestDispatcher("/pages/business/configuracoes/investir.jsp")
                   .forward(request, response);
        }
    }
}
