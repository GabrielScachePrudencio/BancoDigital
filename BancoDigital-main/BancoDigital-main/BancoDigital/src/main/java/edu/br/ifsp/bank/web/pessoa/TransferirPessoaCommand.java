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

public class TransferirPessoaCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PessoaDao pdao = new PessoaDao();
        TransferenciaDao tdao = new TransferenciaDao();

        HttpSession session = request.getSession();
        Pessoa logado = (Pessoa) session.getAttribute("usuarioLogado");

        String texto = request.getParameter("texto");
        String destinoCpf = request.getParameter("destinoCpf");
        String valorStr = request.getParameter("valor");

        // FASE 2 - CONFIRMAR TRANSFERÊNCIA
        if (destinoCpf != null && valorStr != null) {
            try {
                float valor = Float.parseFloat(valorStr);

                if (valor <= 0) {
                    erro(request, response, "Valor inválido.");
                    return;
                }

                if (valor > logado.getSaldo()) {
                    erro(request, response, "Saldo insuficiente.");
                    return;
                }

                Pessoa destino = pdao.findByCPF(destinoCpf);
                if (destino == null) {
                    erro(request, response, "Conta destino não encontrada.");
                    return;
                }

                Transferencia transferencia = tdao.transferirViaCpf(
                        destino.getCpf(),
                        logado.getCpf(),
                        valor
                );

                Pessoa atualizado = pdao.findByCPF(logado.getCpf());
                session.setAttribute("usuarioLogado", atualizado);

                session.setAttribute("ultimaTransferencia", transferencia);
                session.setAttribute("tipoPdf", "transferencia");

                request.getRequestDispatcher("/pages/business/transferir.jsp")
                .forward(request, response);
                
                return;

            } catch (Exception e) {
                e.printStackTrace();
                erro(request, response, "Erro ao realizar a transferência.");
                return;
            }
        }

        // FASE 1 - BUSCAR DESTINATÁRIO
        if (texto != null) {

            Pessoa destino = null;

            if (texto.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}")) {
                destino = pdao.findByCPF(texto);
            } else if (texto.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
                destino = pdao.findByEmail(texto);
            } else {
                destino = pdao.findByNome(texto);
            }

            if (destino == null) {
                erro(request, response, "Nenhum usuário encontrado.");
                return;
            }

            request.setAttribute("destino", destino);
        }

        request.getRequestDispatcher("/pages/business/transferir.jsp")
               .forward(request, response);
    }

    private void erro(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {

        request.setAttribute("erro", msg);
        request.getRequestDispatcher("/pages/business/transferir.jsp")
               .forward(request, response);
    }
}
