package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;

import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.modelo.TipoUsuario;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditarPessoaCommand implements Command{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PessoaDao dao = new PessoaDao();
		
		String ids = request.getParameter("id");
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String endereco = request.getParameter("endereco");
        String saldo = request.getParameter("saldo");
        String role = request.getParameter("role");
        
        
        if (ids == null || cpf == null || cpf.isBlank() || nome == null || nome.isBlank() || senha == null || senha.isBlank() || saldo == null || role == null) {
            request.setAttribute("error", "Todos os campos obrigatórios devem ser preenchidos.");
            request.getRequestDispatcher("/pessoa/cadastrar.jsp").forward(request, response);
            return;
        }
	
        Pessoa pessoaEditada = new Pessoa();
        pessoaEditada.setId(Integer.parseInt(ids));
        pessoaEditada.setCpf(cpf);
        pessoaEditada.setNome(nome);
        pessoaEditada.setSenha(senha); 
        pessoaEditada.setEmail(email);
        pessoaEditada.setTelefone(telefone);
        pessoaEditada.setEndereco(endereco);
        pessoaEditada.setSaldo(Float.parseFloat(saldo));
        pessoaEditada.setRole(TipoUsuario.valueOf(role));
        
        try {
            dao.editar(pessoaEditada); 
            
            request.setAttribute("success", "Conta editada com sucesso! Faça login para continuar.");
            request.getRequestDispatcher("/pages/business/configuracoes/configuracoes.jsp").forward(request, response);

		} catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erro ao tentar cadastrar a pessoa: " + e.getMessage());
            request.getRequestDispatcher("/pessoa/cadastrar.jsp").forward(request, response);
        }

	}
}
