package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;


import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.FilmService;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;
import it.prova.raccoltafilm.utility.UtilityForm;


@WebServlet("/ExecuteUpdateRegistaServlet")
public class ExecuteUpdateRegistaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private FilmService filmService;
	private RegistaService registaService;

	public ExecuteUpdateRegistaServlet() {
		this.filmService = MyServiceFactory.getFilmServiceInstance();
		this.registaService = MyServiceFactory.getRegistaServiceInstance();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String nickNameParam = request.getParameter("nickName");
		String dataDiNascitaParam = request.getParameter("dataDiNascita");
		String sessoParam = request.getParameter("sesso");
		String idInputParam=request.getParameter("idRegista");

		Regista registaInstance = UtilityForm.createRegistaFromParams(nomeParam, cognomeParam, nickNameParam,
				dataDiNascitaParam, sessoParam);
		registaInstance.setId(Long.parseLong(idInputParam));

		if (!UtilityForm.validateRegistaBean(registaInstance)) {
			request.setAttribute("update_regista_attr", registaInstance);
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("/regista/update.jsp").forward(request, response);
			return;
		}

		if (!NumberUtils.isCreatable(idInputParam)) {
			request.setAttribute("errorMessage", "Attenzione id regista non valido.");
			request.getRequestDispatcher("/regista/update.jsp").forward(request, response);
			return;
		}

		try {
			registaService.aggiorna(registaInstance);
			request.setAttribute("update_regista_attr", registaService.listAllElements());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/regista/update.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("ExecuteListRegistaServlet?operationResult=SUCCESS");

	}

}
