package edu.br.ifsp.bank.web;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {	
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		String path = extractPath(httpRequest);
		System.out.println("[AuthFilter] path: " + httpRequest.getMethod() + " " + path);

		//e aqui se o usuario estiver certo recebera um false
		if (shouldAuthenticate(httpRequest)) {
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			final String destination = httpRequest.getContextPath() + "/login?next=" + path;
			httpResponse.sendRedirect(destination);
			return;
		}	
		
		
		// se nao entrar no if ira executar isso que significa que pode seguir
		chain.doFilter(request, response);
	}

	private String extractPath(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getContextPath().length());
	}

	private boolean shouldAuthenticate(HttpServletRequest request) {
		String path = extractPath(request);

		if ("/login".equals(path) || "/pessoa/cadastrar".equals(path)) {
			return false;
		}
		
		// se usuario retorna true aqui o boolean recebera um false
		boolean authenticationNeeded = !hasAuthenticatedUser(request);

		return authenticationNeeded;
	}

	//retorna true se o usuario esta logado ou false no contrario
	private boolean hasAuthenticatedUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null && session.getAttribute("usuarioLogado") != null);
	}
	
}
