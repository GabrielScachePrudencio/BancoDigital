package edu.br.ifsp.bank.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;


//cria e destroi sessoes e faz os logs de sessoes

@WebServlet
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	private String getZuluTime() {
		String zuluTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
		return zuluTime;
	}

    public void sessionCreated(HttpSessionEvent se)  {
    	System.out.println("[session created] " +  getZuluTime());    	
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    	System.out.println("[session destroyed] " + getZuluTime() + " user: " + se.getSession().getAttribute("user"));
    }
	
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
    	String name = se.getName();
    	if ("user".equals(name)) {
    		System.out.println("[user logged in] " + getZuluTime() + " " + name + ": " + se.getValue());
    	}
    }

}
