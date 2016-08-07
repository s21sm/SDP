package semanticAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import language.prural2singular;
import service.IngredientURI;
import service.query;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Resource;

import info.sswap.api.model.RIG;
import info.sswap.api.model.SSWAPGraph;
import info.sswap.api.model.SSWAPObject;
import info.sswap.api.model.SSWAPPredicate;
import info.sswap.api.model.SSWAPProperty;
import info.sswap.api.model.SSWAPResource;
import info.sswap.api.model.SSWAPSubject;
import info.sswap.api.servlet.MapsTo;

public class SSWAPService extends MapsTo {
	RIG rigGraph = null;

	//
	ArrayList<ArrayList<String>> Result_food;
	ArrayList<ArrayList<ArrayList<String>>> Result_Ingredient;
	int result_status;
	String ingredient;

	//

	@Override
	protected void initializeRequest(RIG rig) {

		rigGraph = rig;

		SSWAPResource resource = rigGraph.getResource();
		SSWAPGraph graph = resource.getGraph();
		SSWAPSubject subject = graph.getSubject();

		Iterator<SSWAPProperty> iterator = subject.getProperties().iterator();
		while (iterator.hasNext()) {
			SSWAPProperty property = iterator.next();
			SSWAPPredicate predicate = rigGraph.getPredicate(property.getURI());

			if (predicate
					.getURI()
					.toString()
					.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/inputonontology/IngredientName")) {
				ingredient = property.getValue().asString();

			}

		}

		String ing = ingredient.replaceAll("\\s+","").toLowerCase();
		ArrayList<String> ingList = new ArrayList<String>(Arrays.asList(ing.split("\\s*,\\s*")));
		ArrayList<Resource> collectedURI = new ArrayList<Resource>();
		OntModel model = null;
		try {
			IngredientURI object = new IngredientURI();
			object.URISearch(ingList);
			collectedURI = object.URIList;
			model = object.model;

//			System.out.println(collectedURI);
		} catch (Exception e) {

			e.printStackTrace();
		}

		query obj = new query();
		String culture = "general";
		try {
			obj.QueryExec(collectedURI, culture, model);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// start of processing
		Result_food = obj.Result_food;
		Result_Ingredient = obj.Result_Ingredient;
		result_status = obj.result_status;

	}

	@Override
	protected void mapsTo(SSWAPSubject translatedSubject) throws Exception {

		SSWAPSubject subject = translatedSubject;
		SSWAPObject object = translatedSubject.getObject();
		SSWAPObject sswapObject = null;

		if (result_status == 1) {

			ArrayList<String> recipe_name = Result_food.get(0);
			ArrayList<String> recipe_image_url = Result_food.get(1);
			ArrayList<String> recipe_video_url = Result_food.get(2);
			ArrayList<String> recipe_instruction = Result_food.get(3);

			for (int i = 0; i < recipe_name.size(); i++) {

				// String a = recipe_name.get(i);
				// String b = recipe_image_url.get(i);
				// String c = recipe_video_url.get(i);
				// String d = recipe_instruction.get(i);
				ArrayList<ArrayList<String>> ing_info_set = Result_Ingredient
						.get(i);
				// ArrayList<String> ingNameList = ing_info_set.get(0);
				// ArrayList<String> ingImageURLList = ing_info_set.get(1);
				// ArrayList<String> ingAmountList = ing_info_set.get(2);
				// ArrayList<String> ingUnitList = ing_info_set.get(3);
				// ArrayList<String> ingDiscripList = ing_info_set.get(4);

				String ingName = String.join(";", ing_info_set.get(0));
				String ingImageURL = String.join(";", ing_info_set.get(1));
				String ingAmount = String.join(";", ing_info_set.get(2));
				String ingUnit = String.join(";", ing_info_set.get(3));
				String ingDiscrip = String.join(";", ing_info_set.get(4));

				sswapObject = assignObject(subject);

				Iterator<SSWAPProperty> iterator = object.getProperties()
						.iterator();

				while (iterator.hasNext()) {
					SSWAPProperty property = iterator.next();
					SSWAPPredicate predicate = rigGraph.getPredicate(property
							.getURI());

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/RecipeName")) {

						if (i == 0) {

							object.setProperty(predicate, recipe_name.get(i));

						}
						if (i > 0) {

							sswapObject.addProperty(predicate,
									recipe_name.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/RecipeImageUrl")) {

						if (i == 0) {

							object.setProperty(predicate,
									recipe_image_url.get(i));

						}
						if (i > 0) {

							sswapObject.addProperty(predicate,
									recipe_image_url.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/RecipeVideoUrl")) {

						if (i == 0) {

							object.setProperty(predicate,
									recipe_video_url.get(i));

						}
						if (i > 0) {

							sswapObject.addProperty(predicate,
									recipe_video_url.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/RecipeInstruction")) {

						if (i == 0) {

							object.setProperty(predicate,
									recipe_instruction.get(i));

						}
						if (i > 0) {

							sswapObject.addProperty(predicate,
									recipe_instruction.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/IngName")) {

						if (i == 0) {

							object.setProperty(predicate, ingName);

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, ingName);

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/IngImageUrl")) {

						if (i == 0) {

							object.setProperty(predicate, ingImageURL);

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, ingImageURL);

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/IngAmount")) {

						if (i == 0) {

							object.setProperty(predicate, ingAmount);

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, ingAmount);

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/IngUnit")) {

						if (i == 0) {

							object.setProperty(predicate, ingUnit);

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, ingUnit);

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://smartkitchen-env.elasticbeanstalk.com/SDPService/onto/outputonontology/IngDescription")) {

						if (i == 0) {

							object.setProperty(predicate, ingDiscrip);

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, ingDiscrip);

						}

					}

				}

				subject.addObject(sswapObject);

			}

		}

	}
}
