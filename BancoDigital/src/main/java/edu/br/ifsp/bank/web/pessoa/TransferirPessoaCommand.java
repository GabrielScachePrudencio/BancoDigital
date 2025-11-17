package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import edu.br.ifsp.bank.modelo.GeradorPdf;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TransferirPessoaCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	Transferencia t = new Transferencia();
    	
        PessoaDao dao = new PessoaDao();
        HttpSession session = request.getSession();

        Pessoa logado = (Pessoa) session.getAttribute("usuarioLogado");
        
        String texto = request.getParameter("texto");
        
        String destinoCpf = request.getParameter("destinoCpf");
        String valorStr = request.getParameter("valor");

     // --- FASE 2: CONFIRMA TRANSFERÊNCIA ---
        if (destinoCpf != null && valorStr != null) {
            try {
                float valor = Float.parseFloat(valorStr);

                Pessoa destino = dao.findByCPF(destinoCpf);
                if (destino == null) {
                    erro(request, response, "Conta destino não encontrada.");
                    return;
                }

                // Realiza transferência no DAO (retorna transferencia preenchida)
                t = dao.transferirViaCpf(destino.getCpf(), logado.getCpf(), valor);

                // Atualiza saldo na sessão
                Pessoa atualizado = dao.findByCPF(logado.getCpf());
                session.setAttribute("usuarioLogado", atualizado);

                // -------- GERAR PDF ----------
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                        "attachment; filename=transferencia_" + logado.getNome() + "_para_" + destino.getNome() + ".pdf");

                Document pdf = new Document();
                PdfWriter.getInstance(pdf, response.getOutputStream());
                pdf.open();

                GeradorPdf geradorPdf = new GeradorPdf();
                geradorPdf.gerarPdfBoleto(pdf, t, logado, destino);

                pdf.close();
                return; // IMPORTANTE! ENCERRA A RESPOSTA

            } catch (Exception e) {
                e.printStackTrace();
                erro(request, response, "Erro ao transferir.");
                return;
            }
        }


        // --- FASE 1: BUSCAR o destinatario ---
        if (texto != null) {

            Pessoa destino = null;

            // CPF
            if (texto.matches("^\\d{11}$")) {
                destino = dao.findByCPF(texto);
            }
            // Email
            else if (texto.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
                destino = dao.findByEmail(texto);
            }
            // Nome
            else {
                destino = dao.findByNome(texto);
            }

            if (destino == null) {
                erro(request, response, "Nenhum usuário encontrado.");
                return;
            }

            request.setAttribute("destino", destino);
        }

        request.getRequestDispatcher("/pages/business/transferir.jsp").forward(request, response);
    }

    private void erro(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("erro", msg);
        request.getRequestDispatcher("/pages/business/transferir.jsp").forward(request, response);
    }
}

