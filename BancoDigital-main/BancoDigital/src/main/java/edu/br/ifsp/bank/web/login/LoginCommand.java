package edu.br.ifsp.bank.web.login;

import java.io.IOException;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.persistencia.PessoaDao;
import edu.br.ifsp.bank.web.Command;
import edu.br.ifsp.bank.web.ViewCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String path = request.getParameter("next");
        HttpSession session = request.getSession();

        if (user == null || password == null) {
            request.getRequestDispatcher("/pages/login/login.jsp").forward(request, response);
            return;
        }

        try {
            PessoaDao dao = new PessoaDao();
            Pessoa p = dao.login(user, password);
            session.setAttribute("usuarioLogado", p);
            session.setAttribute("role", p.getRole());


            if (p != null) {
                session.setAttribute("usuarioLogado", p);
                if (path == null) path = "/home";
                new ViewCommand("redirect:" + path).execute(request, response);
            } else {
                request.setAttribute("error", true);
                request.getRequestDispatcher("/pages/login/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", true);
            request.getRequestDispatcher("/pages/login/login.jsp").forward(request, response);
        }
    }
}
