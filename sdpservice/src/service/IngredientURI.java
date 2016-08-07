package service;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class IngredientURI {

	public OntModel model;
	public ArrayList<Resource> URIList;

	public void URISearch(ArrayList<String> ingNameList) throws Exception {

		URIList = new ArrayList<Resource>();
		Query query;
		String SOURCE = "http://users.jyu.fi/~syibkhan/DB9.rdf";
		model = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);

		model.read(SOURCE, "N3");

		StringBuilder text = new StringBuilder();
	

		for (int i = 0; i < ingNameList.size(); i++) {

			
			String y = "\"" + ingNameList.get(i) + "\"";
				
			text.append("?ID =" + y + "||");
			
			if(i+1==ingNameList.size()){
				
				text.append("?ID =" + y  );
			}

		}

		
		String str1= text.toString();		
		String str2 = "filter (" + str1 + ")";
		String queryString = stringCreator(str2);
		query = QueryFactory.create(queryString);

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
//		 ResultSetFormatter.out(System.out, results, query);

		while (results.hasNext()) {
			QuerySolution row = results.nextSolution();
			Resource URI = row.getResource("URI");
			URIList.add(URI);

		}

	}

	private static String stringCreator(String str2) {

		String qString = "PREFIX NS: <http://sdp2015.org/ontology/>\n"
				+ "PREFIX owl:<http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"
				+ "PREFIX xml:<http://www.w3.org/XML/1998/namespace> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"

				+ "SELECT DISTINCT ?URI "

				+ "WHERE { \n"

				+ "?URI a NS:helperingredient."

				+ "?URI NS:hasID ?ID."

				+ str2

				+ "} ";

		return qString;
	}

}
