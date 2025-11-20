package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;

import edu.br.ifsp.bank.web.Command;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.TipoUsuario;
import edu.br.ifsp.bank.persistencia.PessoaDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddPessoaCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String endereco = request.getParameter("endereco");

        if (cpf == null || cpf.isBlank() || nome == null || nome.isBlank() || senha == null || senha.isBlank()) {
            request.setAttribute("error", "Todos os campos obrigatórios devem ser preenchidos.");
            request.getRequestDispatcher("/pessoa/cadastrar.jsp").forward(request, response);
            return;
        }

        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCpf(cpf);
        novaPessoa.setNome(nome);
        novaPessoa.setSenha(senha); 
        novaPessoa.setEmail(email);
        novaPessoa.setTelefone(telefone);
        novaPessoa.setEndereco(endereco);
        novaPessoa.setRole(TipoUsuario.USER);
        novaPessoa.setSaldo(0.0f); 

        PessoaDao dao = new PessoaDao();
        try {
            dao.add(novaPessoa); 
            
            request.setAttribute("success", "Conta criada com sucesso! Faça login para continuar.");
            request.getRequestDispatcher("/pages/login/login.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erro ao tentar cadastrar a pessoa: " + e.getMessage());
            request.getRequestDispatcher("/pessoa/cadastrar.jsp").forward(request, response);
        }
    }
}