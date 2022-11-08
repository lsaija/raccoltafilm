package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.FilmService;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;
import it.prova.raccoltafilm.utility.UtilityForm;


@WebServlet("/ExecuteUpdateFilmServlet")
public class ExecuteUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	private FilmService filmService;
	private RegistaService registaService;

	public ExecuteUpdateFilmServlet() {
		this.filmService = MyServiceFactory.getFilmServiceInstance();
		this.registaService = MyServiceFactory.getRegistaServiceInstance();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titoloParam = request.getParameter("titolo");
		String genereParam = request.getParameter("genere");
		String dataPublicazioneParam = request.getParameter("dataPubblicazione");
		String minutiDurataParam = request.getParameter("minutiDurata");
		String idRegistaParam = request.getParameter("regista.id");
		String idInputParam=request.getParameter("idFilm");
		

		Film filmInstance = UtilityForm.createFilmFromParams(titoloParam, genereParam, minutiDurataParam,
				dataPublicazioneParam, idRegistaParam);
		filmInstance.setId(Long.parseLong(idInputParam));
		
	
	
		try {
			// se la validazione non risulta ok
			if (!UtilityForm.validateFilmBean(filmInstance)) {
				request.setAttribute("update_film_attr", filmInstance);
				// questo mi serve per la select di registi in pagina
				request.setAttribute("film_list_attribute", filmService.listAllElements());
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.getRequestDispatcher("/film/update.jsp").forward(request, response);
				return;
			}
		
			filmService.aggiorna(filmInstance);
			request.setAttribute("film_list_attribute", filmService.listAllElements());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/film/update.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("ExecuteListFilmServlet?operationResult=SUCCESS");

	}


}
