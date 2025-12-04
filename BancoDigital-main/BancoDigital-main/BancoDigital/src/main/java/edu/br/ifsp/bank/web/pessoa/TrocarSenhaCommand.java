package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TrocarSenhaCommand implements Command {

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigo = request.getParameter("codigo");
        String ids = request.getParameter("id");
        String senhaNova = request.getParameter("senhaNova");
        String senhaAntiga = request.getParameter("senhaAntiga");

        if (codigo == null || ids == null || codigo.isEmpty() || ids.isEmpty()) {
            erro(request, response, "Link inválido ou incompleto.");
            return;
        }

        int codigoInt = Integer.parseInt(codigo);
        int id = Integer.parseInt(ids);

        PessoaDao dao = new PessoaDao();
        Pessoa p = dao.findById(id);

        if (p == null) {
            erro(request, response, "Usuário não encontrado.");
            return;
        }

        request.setAttribute("pessoa", p);
        request.setAttribute("id", id);
        request.setAttribute("codigo", codigoInt);

        // PRIMEIRA VISITA (GET) so acessando o site pelo email
        if (senhaNova == null && senhaAntiga == null) {

            if (!dao.validaCodigo(codigoInt)) {
                erro(request, response, "Código inválido.");
                return;
            }

            request.setAttribute("podeIrSenhaNova", false);
            request.getRequestDispatcher("/pages/business/configuracoes/trocarSenha.jsp")
                    .forward(request, response);
            return;
        }

        // SEGUNDA VISITA (POST: senha antiga)
        if (senhaNova == null && senhaAntiga != null) {

            if (!dao.validarSenhaAntiga(id, senhaAntiga)) {
                erro(request, response, "Senha antiga incorreta.");
                return;
            }

            request.setAttribute("podeIrSenhaNova", true);
            request.getRequestDispatcher("/pages/business/configuracoes/trocarSenha.jsp")
                    .forward(request, response);
            return;
        }

        // TERCEIRA VISITA (POST: senha nova)
        if (senhaNova != null) {

            dao.alterarSenha(id, senhaNova);
            Pessoa pessoaEditada = dao.findById(id);
            HttpSession session = request.getSession();
		    session.setAttribute("usuarioLogado", pessoaEditada);
            request.getSession().setAttribute("sucesso", "Senha alterada com sucesso!");
            response.sendRedirect(request.getContextPath() + "/pessoa/configuracoes");
            return;
        }

        erro(request, response, "Algo inesperado ocorreu.");
    }

    private void erro(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("erro", msg);
        request.setAttribute("podeIrSenhaNova", false);
        request.getRequestDispatcher("/pages/business/configuracoes/trocarSenha.jsp")
                .forward(request, response);
    }
}
