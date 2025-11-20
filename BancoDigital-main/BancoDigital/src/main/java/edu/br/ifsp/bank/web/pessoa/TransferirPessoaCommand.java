package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import edu.br.ifsp.bank.modelo.GeradorPdf;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.Transferencia;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.persistencia.TransferenciaDao;
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
    	
    	PessoaDao pdao = new PessoaDao();
		TransferenciaDao tdao = new TransferenciaDao();
		
        HttpSession session = request.getSession();

        Pessoa logado = (Pessoa) session.getAttribute("usuarioLogado");
        
        String texto = request.getParameter("texto");
        
        String destinoCpf = request.getParameter("destinoCpf");
        String valorStr = request.getParameter("valor");

     // --- FASE 2: CONFIRMA TRANSFERÊNCIA ---
        if (destinoCpf != null && valorStr != null) {
            try {
                float valor = Float.parseFloat(valorStr);

                Pessoa destino = pdao.findByCPF(destinoCpf);
                if (destino == null) {
                    erro(request, response, "Conta destino não encontrada.");
                    return;
                }

                // Realiza transferência no DAO (retorna transferencia preenchida)
                t = tdao.transferirViaCpf(destino.getCpf(), logado.getCpf(), valor);

                // Atualiza saldo na sessão
                Pessoa atualizado = pdao.findByCPF(logado.getCpf());
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
            if (texto.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}")) {
                destino = pdao.findByCPF(texto);
            }
            // Email
            else if (texto.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
                destino = pdao.findByEmail(texto);
            }
            // Nome
            else {
                destino = pdao.findByNome(texto);
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

