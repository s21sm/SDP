package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.query.ResultSet;

public class query {

	public ArrayList<ArrayList<String>> Result_food = new ArrayList<ArrayList<String>>();
	public ArrayList<ArrayList<ArrayList<String>>> Result_Ingredient = new ArrayList<ArrayList<ArrayList<String>>>();
	public ArrayList<HashMap<String, String>> re = new ArrayList<HashMap<String, String>>();
//	public ArrayList<String> resp;

	public int result_status;

	public void QueryExec(ArrayList<Resource> ingLIST, String culture,
			OntModel model) throws Exception {

		HashMap<Resource, Float> hm = new HashMap<Resource, Float>();

		for (int i = 0; i < ingLIST.size(); i++) {

			Resource ingURI = ingLIST.get(i);

			String qt = "{ ?recipe rdf:type NS:" + culture + " . "
					+ "?recipe NS:hasIngredient ?ing. "
					+ " ?ing  NS:hasImportance ?importance."
					+ "?ing   NS:hasMapping <" + ingURI + ">"
					+ "BIND (?importance  AS ?w) } "
					+ "UNION {?recipe rdf:type NS:" + culture + " ."
					+ "?recipe NS:hasIngredient ?ing. "
					+ " ?ing NS:hasImportance ?importance."
					+ "?ing  NS:hasReplacement <" + ingURI + "> ."
					+ " BIND ((?importance * xsd:float(0.8)) AS ?w) }";

			HashMap<Resource, Float> map = getInfo(qt, model);

			if (map != null) {

				for (Resource key : map.keySet()) {

					if (hm.containsKey(key)) {

						hm.put(key, hm.get(key) + map.get(key));
					} else {

						hm.put(key, map.get(key));
					}

				}

			}

		}

		LinkedHashMap<Resource, Float> sortedMap = (LinkedHashMap<Resource, Float>) hm
				.entrySet()
				.stream()
				.sorted(Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(
						Collectors.toMap(Entry::getKey, Entry::getValue, (e1,
								e2) -> e1, LinkedHashMap::new));

		ArrayList<String> fName = new ArrayList<String>();
		ArrayList<String> fIUrl = new ArrayList<String>();
		ArrayList<String> fVUrl = new ArrayList<String>();
		ArrayList<String> fIns = new ArrayList<String>();
		 ArrayList<String> fNote = new ArrayList<String>();

		for (Resource key : sortedMap.keySet()) {

			if (sortedMap.get(key) >= 0.001) {

				ResultSet results = getRecipe(culture, key, model);

				if (!results.hasNext()) {

				}

				else {

					while (results.hasNext()) {

						QuerySolution row = results.nextSolution();
						String foodName = row.getLiteral("FoodName")
								.getString();
						String foodImageUrl = row.getLiteral("imageUrl")
								.getString();
						String foodVideoUrl = row.getLiteral("videoUrl")
								.getString();
						String foodInstruction = row.getLiteral("instruction")
								.getString();

						fName.add(foodName);
						fIUrl.add(foodImageUrl);
						fVUrl.add(foodVideoUrl);
						fIns.add(foodInstruction);

						ArrayList<ArrayList<String>> ingInformation = getIngDetails(
								foodName, culture, model, ingLIST);

						ReplacementFinder obj = new ReplacementFinder();
						obj.getDetails(foodName, culture, model, ingLIST);

						// HashMap<String, String> response = ReplacementFinder
						// .getDetails(foodName, culture, model, ingLIST);

						fNote.add(obj.res);
						re.add(obj.map);
						Result_Ingredient.add(ingInformation);

					}

					Result_food.add(fName);
					Result_food.add(fIUrl);
					Result_food.add(fVUrl);
					Result_food.add(fIns);

					 Result_food.add(fNote);

				}

			}

		}

		if (fName.size() != 0) {

			result_status = 1;
		} else {
			result_status = 0;
		}

	}

	private ArrayList<ArrayList<String>> getIngDetails(String foodName,
			String culture, OntModel model, ArrayList<Resource> ingLIST)
			throws Exception {

		String fName = "\"" + foodName + "\"";
		String foodFromClass = "?x  a" + " " + "NS:" + culture + ". ";

		String queryString = "PREFIX NS: <http://sdp2015.org/ontology/>\n"
				+ "PREFIX owl:<http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"
				+ "PREFIX xml:<http://www.w3.org/XML/1998/namespace> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"

				+ "SELECT DISTINCT  ?mappingObject ?ingName ?ingImageURL ?ingUnit ?ingAmount ?ingDescription  "

				+ " WHERE { \n"

				+ "{"

				+ foodFromClass

				+ "?x NS:hasName ?FoodName."

				+ "FILTER (?FoodName = " + fName + "^^xsd:string).\n "

				+ "?x NS:hasIngredient ?ingredient."

				+ "?ingredient NS:hasMapping ?mappingObject."

				+ "?mappingObject NS:hasName ?ingName."

				+ "?mappingObject NS:hasImageUrl ?ingImageURL."

				+ "?ingredient NS:hasUnit ?ingUnit."

				+ "?ingredient NS:hasAmount ?ingAmount."

				+ "?ingredient NS:hasDescription ?ingDescription."

				+ "}"

				+ "} ";

		// System.out.println(queryString);

		Query query = QueryFactory.create(queryString);

		// System.out.println(query);

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

		// ResultSetFormatter.out(System.out, results, query);

		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

		if (!results.hasNext()) {

			System.out
					.println("No Result found According your search, kindly change input parameter");

		}

		else {

			ArrayList<String> ingNameList = new ArrayList<String>();
			ArrayList<String> ingImageURLList = new ArrayList<String>();
			ArrayList<String> ingAmountList = new ArrayList<String>();
			ArrayList<String> ingUnitList = new ArrayList<String>();
			ArrayList<String> ingDiscripList = new ArrayList<String>();
			ArrayList<String> blurInfo = new ArrayList<String>();

			while (results.hasNext()) {

				QuerySolution row = results.nextSolution();
				String ingName = row.getLiteral("ingName").getString();
				// System.out.println(ingName);
				String ingImageURL = row.getLiteral("ingImageURL").getString();
				String ingAmount = row.getLiteral("ingAmount").getString();
				String ingUnit = row.getLiteral("ingUnit").getString();
				String ingDes = row.getLiteral("ingDescription").getString();
				Resource mappingObject = row.getResource("mappingObject");

				if (ingLIST.contains(mappingObject)) {

					blurInfo.add("1");

				} else {

					blurInfo.add("0");
				}

				// System.out.println(ingDes);

				ingNameList.add(ingName);
				ingImageURLList.add(ingImageURL);
				ingAmountList.add(ingAmount);
				ingUnitList.add(ingUnit);
				ingDiscripList.add(ingDes);

			}

			list.add(ingNameList);
			list.add(ingImageURLList);
			list.add(ingAmountList);
			list.add(ingUnitList);
			list.add(ingDiscripList);
			list.add(blurInfo);

		}

		return list;

	}

	private static HashMap<Resource, Float> getInfo(String text, OntModel model) {

		String queryString = "PREFIX NS: <http://sdp2015.org/ontology/>\n"
				+ "PREFIX owl:<http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"
				+ "PREFIX xml:<http://www.w3.org/XML/1998/namespace> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"

				+ "SELECT  Distinct  ?recipe  ?w   "

				+ "  WHERE" + "{"

				+ text

				+ " }";

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// ResultSetFormatter.out(System.out, results, query);
		HashMap<Resource, Float> map = new HashMap<Resource, Float>();
		if (!results.hasNext()) {

			return null;

		} else {

			while (results.hasNext()) {

				QuerySolution row = results.nextSolution();
				Resource recipe = row.getResource("recipe");
				float weight = Float
						.parseFloat(row.getLiteral("w").getString());
				map.put(recipe, weight);

			}

		}

		return map;

	}

	private static ResultSet getRecipe(String culture, Resource abc,
			OntModel model) {

		String queryString = "PREFIX NS: <http://sdp2015.org/ontology/>\n"
				+ "PREFIX owl:<http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"
				+ "PREFIX xml:<http://www.w3.org/XML/1998/namespace> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"

				+ "SELECT  Distinct  ?FoodName ?imageUrl ?videoUrl ?instruction   "

				+ "  WHERE" + "{"

				+ " ?recipe rdf:type NS:" + culture + " . "

				+ " filter(?recipe = <" + abc + ">) "

				+ "?recipe NS:hasName ?FoodName."

				+ "?recipe NS:hasImageUrl ?imageUrl."

				+ "?recipe NS:hasVideoUrl ?videoUrl."

				+ "?recipe NS:hasCookingInstruction ?instruction ."

				+ " }";

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		return results;

	}

}
