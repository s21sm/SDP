package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;

public class GUIservlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OntModel model = null;
		ArrayList<String> ingList = new ArrayList<String>();
		ArrayList<Resource> collectedURI = new ArrayList<Resource>();
		ArrayList<ArrayList<String>> Rfood = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> RIngredient = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<HashMap<String, String>> rep_info = new ArrayList<HashMap<String, String>>();
		int rstatus = 0;

		String ing = request.getParameter("ingredient1").replaceAll("\\s+","").toLowerCase();

		String culture = request.getParameter("culture");
		
		if (ing.trim().length() > 0 && ing != null) {

			ingList = new ArrayList<String>(Arrays.asList(ing
					.split("\\s*,\\s*")));
			
			
		}

		if(ingList.size()!=0){
			
			try {

				IngredientURI object = new IngredientURI();
				object.URISearch(ingList);
				collectedURI = object.URIList;
				model = object.model;

			} catch (Exception e) {

				e.printStackTrace();
			}
			
		}else{
			rstatus = 0;
		}

		if (collectedURI.size() > 0) {
			query obj = new query();
			try {
				obj.QueryExec(collectedURI, culture, model);
				Rfood = obj.Result_food;
				RIngredient = obj.Result_Ingredient;
				rstatus = obj.result_status;
				rep_info = obj.re;
			

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			rstatus = 0;
		}
		


		request.getSession().setAttribute("culture", culture);
		request.setAttribute("recipe", Rfood);
		request.setAttribute("ingredientInfo", RIngredient);
		request.setAttribute("status", rstatus);
		request.setAttribute("reInfo", rep_info);

		RequestDispatcher rd = request.getRequestDispatcher("/output.jsp");
		rd.forward(request, response);

	}

}
