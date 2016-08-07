package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Resource;

public class ReplacementFinder {
	
	public  String res;
	
	public  HashMap<String, String> map;

	public void getDetails(String foodName, String culture,
			OntModel model, ArrayList<Resource> ingLIST) throws Exception {

		 map = new HashMap<String, String>();
		
//		String res = null;
		String fName = "\"" + foodName + "\"";
		String foodFromClass = "?x  a" + " " + "NS:" + culture + ". ";

		String queryString = "PREFIX NS: <http://sdp2015.org/ontology/>\n"
				+ "PREFIX owl:<http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"
				+ "PREFIX xml:<http://www.w3.org/XML/1998/namespace> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"

				+ "SELECT DISTINCT ?mappingObject ?replacementObject  ?mappingObjectName  ?replacementObjectName ?moIurl ?roIurl "

				+ " WHERE { \n"

				+ foodFromClass

				+ "?x NS:hasName ?FoodName."

				+ "FILTER (?FoodName = " + fName + "^^xsd:string).\n "

				+ "?x NS:hasIngredient ?ingredient."

				+ "?ingredient NS:hasMapping ?mappingObject. "

				+ "?mappingObject NS:hasName ?mappingObjectName. "
				
				+ "?mappingObject NS:hasImageUrl ?moIurl. "

				+ "?ingredient NS:hasReplacement  ?replacementObject. "
				
				+ "?replacementObject NS:hasImageUrl ?roIurl. "

				+ "?replacementObject NS:hasName ?replacementObjectName."

				+ "} ";

		// System.out.println(queryString);

		Query query = QueryFactory.create(queryString);

		// System.out.println(query);

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
		// ResultSetFormatter.out(System.out, results, query);

		if (!results.hasNext()) {

			res = "na";

		}

		else {

			ArrayList<Resource> mappingIng = new ArrayList<Resource>();
			ArrayList<Resource> replacementIng = new ArrayList<Resource>();

			ArrayList<String> MappingName = new ArrayList<String>();
			ArrayList<String> ReplaceName = new ArrayList<String>();
			
			ArrayList<String> MappingURL = new ArrayList<String>();
			ArrayList<String> ReplaceURL = new ArrayList<String>();
			

			while (results.hasNext()) {
				QuerySolution row = results.nextSolution();
				Resource mappingObject = row.getResource("mappingObject");
				Resource replacementObject = row
						.getResource("replacementObject");
				String Mname = row.getLiteral("mappingObjectName").getString();
				String Rname = row.getLiteral("replacementObjectName")
						.getString();
				
				String Murl = row.getLiteral("moIurl").getString();
				String Rurl = row.getLiteral("roIurl")
						.getString();

				
				MappingURL.add(Murl);
				ReplaceURL.add(Rurl);
				
				MappingName.add(Mname);
				ReplaceName.add(Rname);
				mappingIng.add(mappingObject);
				replacementIng.add(replacementObject);

			}
			
//			if(mappingIng.retainAll(c)){
//				
//			}
			

			if (!Collections.disjoint(ingLIST, replacementIng)) {
				
				StringBuilder notice = new StringBuilder();
				for (int i = 0; i < replacementIng.size(); i++) {

					if (ingLIST.contains(replacementIng.get(i))) {

						notice.append(ReplaceName.get(i)
								+ " can be replaced by  " + MappingName.get(i)+"  </br>");
						
						map.put(MappingURL.get(i), ReplaceURL.get(i));

					}

				}

				res = notice.toString();

		
			} else {
				res = "na";
			}

		}

//		return map;

	}

}
