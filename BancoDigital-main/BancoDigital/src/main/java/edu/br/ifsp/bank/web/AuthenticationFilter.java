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
		final HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		String path = extractPath(httpRequest);
		System.out.println("[AuthFilter] path: " + httpRequest.getMethod() + " " + path);

		if (!hasAuthenticatedUser(httpRequest)) {
		    if (urlPermitidas(path)) { 
			    final String destination = httpRequest.getContextPath() + "/login?next=" + path;
			    httpResponse.sendRedirect(destination);
			    return;
            }
		} 
		else if (!isUserVerified(httpRequest) && !path.equals("/verificacaoCodigo")) {
		    final String destination = httpRequest.getContextPath() + "/verificacaoCodigo";
		    httpResponse.sendRedirect(destination);
		    return;
		}
		
		chain.doFilter(request, response);
	}

	private String extractPath(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getContextPath().length());
	}
    
	private boolean urlPermitidas(String path) {
	    return !("/login".equals(path) 
	        || "/verificacaoCodigo".equals(path) 
	        || "/pessoa/cadastrar".equals(path)
	        || "/pessoa/trocarsenha".equals(path)   
	        || path.startsWith("/assets/"));
	}

    
	private boolean hasAuthenticatedUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null && session.getAttribute("usuarioLogado") != null);
	}
	
    private boolean isUserVerified(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // Retorna true se a sessão existe E o atributo 'isVerificado' é true.
        return (session != null && Boolean.TRUE.equals(session.getAttribute("isVerificado")));
    }
	
}