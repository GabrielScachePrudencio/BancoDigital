package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EnviarEmailCommand implements Command{
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PessoaDao dao = new PessoaDao();
        HttpSession session = request.getSession();
        Pessoa logado = (Pessoa) session.getAttribute("usuarioLogado");
        
        int id = logado.getId(); 
        
        try {

            dao.EnviarEmailParaTrocarSenha(logado.getEmail(), id);

            session.setAttribute("mensagemSucesso", "O link de troca de senha foi enviado para o seu e-mail. Por favor, verifique a sua caixa de entrada.");
            
            	
            response.sendRedirect(request.getContextPath() + "/pessoa/configuracoes");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensagemErro", "Ocorreu um erro ao enviar o e-mail. Tente novamente.");
            response.sendRedirect(request.getContextPath() + "/pessoa/configuracoes");
        }
    }
}