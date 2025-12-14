package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DepositarPessoaCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PessoaDao pdao = new PessoaDao();
        TransferenciaDao tdao = new TransferenciaDao();
        HttpSession session = request.getSession();

        String cpf = request.getParameter("cpf");
        String valorStr = request.getParameter("valor");

        if (valorStr == null || valorStr.isBlank()) {
            request.setAttribute("erro", "Valor inválido.");
            request.getRequestDispatcher("/pages/business/depositar.jsp").forward(request, response);
            return;
        }

        try {
            float valor = Float.parseFloat(valorStr);

            Transferencia deposito = tdao.depositar(cpf, valor);

            Pessoa atualizado = pdao.findByCPF(cpf);
	        session.setAttribute("usuarioLogado", atualizado);
	         session.setAttribute("ultimaTransferencia", deposito);
	         session.setAttribute("tipoPdf", "deposito");
	
	         request.setAttribute("sucesso", true);
	         request.getRequestDispatcher("/pages/business/depositar.jsp")
	                .forward(request, response);
	         return;

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao depositar.");
            request.getRequestDispatcher("/pages/business/depositar.jsp").forward(request, response);
        }
    }
}
