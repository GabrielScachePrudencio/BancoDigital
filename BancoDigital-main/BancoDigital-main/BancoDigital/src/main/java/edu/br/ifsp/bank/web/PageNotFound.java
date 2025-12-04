package edu.br.ifsp.bank.web;

import java.io.IOException;
import edu.br.ifsp.bank.web.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PageNotFound implements Command {
	private static PageNotFound instance = new PageNotFound();

	private PageNotFound() {
	}

	public static PageNotFound getInstance() {
		return instance;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

}
