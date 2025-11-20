package edu.br.ifsp.bank.web.login;

import java.io.IOException;

import edu.br.ifsp.bank.web.Command;
import edu.br.ifsp.bank.web.ViewCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		new ViewCommand("redirect:/pages/login/login.jsp").execute(request, response);
	}
}
