package edu.br.ifsp.bank.web.pessoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.br.ifsp.bank.modelo.ParcelaSac;
import edu.br.ifsp.bank.modelo.Pessoa;
import edu.br.ifsp.bank.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SimularEmprestimoSacCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Pessoa usuario = (Pessoa) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            double valor = Double.parseDouble(request.getParameter("valor"));
            double taxaPercent = Double.parseDouble(request.getParameter("taxa"));
            double taxa = taxaPercent / 100.0; 	
            int meses = Integer.parseInt(request.getParameter("prazo"));

            double amortizacaoConstante = valor / meses;
            double saldoDevedor = valor;

            List<ParcelaSac> parcelas = new ArrayList<>();
            double totalPago = 0.0;
            double totalJuros = 0.0;

            for (int i = 1; i <= meses; i++) {
                double juros = saldoDevedor * taxa;
                double valorParcela = amortizacaoConstante + juros;
                saldoDevedor -= amortizacaoConstante;
                if (saldoDevedor < 0) saldoDevedor = 0; 

                parcelas.add(new ParcelaSac(i, amortizacaoConstante, juros, valorParcela, saldoDevedor));

                totalPago += valorParcela;
                totalJuros += juros;
            }

            request.setAttribute("parcelas", parcelas);
            request.setAttribute("totalPago", totalPago);
            request.setAttribute("totalJuros", totalJuros);
            request.setAttribute("valor", valor);
            request.setAttribute("taxa", taxaPercent);
            request.setAttribute("prazo", meses);

            request.getRequestDispatcher("/pages/business/emprestimoSac.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("erro", "Dados inválidos: " + e.getMessage());
            request.getRequestDispatcher("/pages/business/emprestimoSac.jsp")
                   .forward(request, response);
        }
    }
}
