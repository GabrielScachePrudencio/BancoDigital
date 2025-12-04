package edu.br.ifsp.bank.web.login;

import java.io.IOException;

import edu.br.ifsp.bank.web.login.*;
import edu.br.ifsp.bank.web.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//edu.br.ifsp.bank.web.login.AuthController.java

@WebServlet(urlPatterns = {"/login", "/logout", "/verificacaoCodigo"})
public class AuthController extends HttpServlet {
	// ... (serialVersionUID e imports)

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		Command cmd = switch(request.getServletPath()) {
		case "/login" -> new ViewCommand("forward:/pages/login/login.jsp");
		case "/logout" -> new LogoutCommand();
		case "/verificacaoCodigo" -> new ViewCommand("forward:/pages/login/verificacaoCodigo.jsp"); 
		default -> PageNotFound.getInstance();
		};
		
		cmd.execute(request, response);
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Command cmd = switch (request.getServletPath()) {
		case "/login" -> new LoginCommand();
		case "/verificacaoCodigo" -> new VerificacaoCodigoCommand(); 
		default -> PageNotFound.getInstance();
		};
		
		cmd.execute(request, response);
	
	}
}