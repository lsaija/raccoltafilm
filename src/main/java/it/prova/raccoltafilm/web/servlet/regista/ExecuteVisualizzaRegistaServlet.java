package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.service.MyServiceFactory;



/**
 * Servlet implementation class ExecuteVisualizzaRegistaServlet
 */
@WebServlet("/ExecuteVisualizzaRegistaServlet")
public class ExecuteVisualizzaRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idRegistaParam = request.getParameter("idRegista");

		if (!NumberUtils.isCreatable(idRegistaParam)) {

			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		try {
			request.setAttribute("visualizza_regista_attr", MyServiceFactory.getRegistaServiceInstance()
					.caricaSingoloElemento(Long.parseLong(idRegistaParam)));
		} catch (Exception e) {

			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/regista/show.jsp").forward(request, response);
	}

}
