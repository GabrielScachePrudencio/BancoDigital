package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.br.ifsp.bank.modelo.HistoricoItem;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HistoricoPessoaCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String cpf = usuarioLogado.getCpf();

        TransferenciaDao tdao = new TransferenciaDao();
        PessoaDao pdao = new PessoaDao();

        ArrayList<Transferencia> transferencias = tdao.listarPorCpf(cpf);
        List<HistoricoItem> itens = new ArrayList<>();

        for (Transferencia tr : transferencias) {

            if (tr.getId_usuarioQueTransferiu() == tr.getId_usuarioQueRecebeu()) {
                continue;
            }

            Pessoa rem = pdao.findById(tr.getId_usuarioQueTransferiu());
            Pessoa dest = pdao.findById(tr.getId_usuarioQueRecebeu());

            String nomeRem = rem != null ? rem.getNome() : ("#id:" + tr.getId_usuarioQueTransferiu());
            String cpfRem = rem != null ? rem.getCpf() : "";
            String nomeDest = dest != null ? dest.getNome() : ("#id:" + tr.getId_usuarioQueRecebeu());
            String cpfDest = dest != null ? dest.getCpf() : "";

            itens.add(new HistoricoItem(
                tr,
                nomeRem,
                cpfRem,
                nomeDest,
                cpfDest
            ));
        }

        request.setAttribute("itensHistorico", itens);
        request.getRequestDispatcher("/pages/business/historico.jsp").forward(request, response);
    }
}
