package language;

import java.util.ArrayList;

public class prural2singular {



	public static ArrayList<String> StringConvertion(ArrayList<String> ingList) {

		ArrayList<String> INGLIST = new ArrayList<String>();
		
		for (int i = 0; i < ingList.size(); i++) {

			String str;

			if (ingList.get(i).endsWith("es")) {

				str = removeLast2Char(ingList.get(i));

				INGLIST.add(str);
			}

			else if (ingList.get(i).endsWith("s")) {

				str = removeLastChar(ingList.get(i));

				INGLIST.add(str);

			}

			else {

				str = ingList.get(i);

				INGLIST.add(str);
			}

		}

		return INGLIST;
	}

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	private static String removeLast2Char(String str) {
		return str.substring(0, str.length() - 2);
	}

}
