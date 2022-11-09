package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.exceptions.RegistaConFilmException;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;


@WebServlet("/ExecuteDeleteRegistaServelet")
public class ExecuteDeleteRegistaServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private RegistaService registaService;

	public ExecuteDeleteRegistaServelet() {
		this.registaService = MyServiceFactory.getRegistaServiceInstance();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idFilmParam = request.getParameter("idRegista");

		if (!NumberUtils.isCreatable(idFilmParam)) {

			request.setAttribute("errorMessage", "Attenzione si è verificato un provaerrore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}

		try {
			
			registaService.rimuovi(Long.parseLong(idFilmParam)); 
			
		} catch (RegistaConFilmException e) {
			request.getRequestDispatcher("ExecuteListRegistaServlet?operationResult=NO_FIGLI").forward(request, response);
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}

		response.sendRedirect("ExecuteListRegistaServlet?operationResult=SUCCESS");
	}
}
