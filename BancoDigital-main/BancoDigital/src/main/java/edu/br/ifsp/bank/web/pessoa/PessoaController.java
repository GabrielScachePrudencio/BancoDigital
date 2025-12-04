	package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;

import edu.br.ifsp.bank.web.*;
import edu.br.ifsp.bank.web.PageNotFound;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pessoa/*")
public class PessoaController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    Command cmd = switch (request.getPathInfo()) {
	        
	    	//dentro do login
	    	case "/cadastrar" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/Cadastrar.jsp")
	               .forward(req, res);
	        };
	        
	        
	        //dentro de configuracoes
	        case "/configuracoes" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/configuracoes/configuracoes.jsp")
	               .forward(req, res);
	        }; 
	        case "/editar" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/configuracoes/editar.jsp")
	               .forward(req, res);
	        };
	        
	        case "/pesquisar" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/configuracoes/pesquisar.jsp")
	               .forward(req, res);
	        };
	        
	        case "/todosUsuarios" -> new TodosUsuariosCommand();
	        case "/todosTransferencias" -> new TodosTransferenciasCommand();

	        
	        
	        //no home
	        case "/depositar" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/depositar.jsp")
	               .forward(req, res);
	        };
	        case "/retirar" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/retirar.jsp")
	               .forward(req, res);
	        };
	        case "/transferir" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/transferir.jsp")
	               .forward(req, res);
	        };
	        case "/historico" -> new HistoricoPessoaCommand();
	        case "/extrato" -> new ExtratoPessoaCommand(); // dentro de pessoa
	        
	        case "/emprestimoSac" -> (req, res) -> {
	            req.getRequestDispatcher("/pages/business/emprestimoSac.jsp")
	               .forward(req, res);
	        };
	        
	        case "/investir" -> new ListarInvestimentosCommand();
	      
	        
	        //permite acessar de fato a pagina apartir do email
	        case "/trocarsenha" -> new TrocarSenhaCommand();
	        
	        
	        //so enviar o email de troca de senha
	        case "/enviarEmail" -> new EnviarEmailCommand();
	        
	        case "/habilitarContas" -> new HabilitarContasCommand();
	        case "/desabilitarContas" -> new DesabilitarContasCommand();

	        default -> PageNotFound.getInstance();
	    };

	    cmd.execute(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    Command cmd = switch (request.getPathInfo()) {
	    	case "/cadastrar" -> new AddPessoaCommand();
	        case "/editar" -> new EditarPessoaCommand();
	        case "/deletar" -> new DeletarPessoaCommand();
	        
	        case "/todosUsuarios" -> new TodosUsuariosCommand();
	        case "/todosTransferencias" -> new TodosTransferenciasCommand();
	        
	        case "/depositar" -> new DepositarPessoaCommand();
	        case "/retirar" -> new RetirarPessoaCommand();
	        case "/transferir" -> new TransferirPessoaCommand();
	        
	        case "/emprestimoSac" -> new SimularEmprestimoSacCommand();
	        case "/investir" -> new InvestirPessoaCommand();
	        
	        
	      //permite acessar de fato a pagina apartir do email
	        case "/trocarsenha" -> new TrocarSenhaCommand();
	        
	        
	        //so enviar o email
	        case "/enviarEmail" -> new EnviarEmailCommand();
	        
	        case "/habilitarContas" -> new HabilitarContasCommand();
	        case "/desabilitarContas" -> new DesabilitarContasCommand();

	        default -> PageNotFound.getInstance();
	    };

	    cmd.execute(request, response);
	}

}
