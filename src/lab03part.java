import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class lab03part {
	
	//Create a static variable document only one time x all the functions
	static Document document;

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {		
		int argCount = args.length;
		if (argCount == 0) {
			System.out.println("No arguments!");
		} else if (argCount < 1) {
			System.out.println("Are you sure you gave me ALL the information I need?");
		} else {
			
			/*
			 * If the variable "document" is null, we have to parse the XML file inside the ParseInformation() function
			 * for the first time.
			 */
			if(document == null){
				ParseInformation();
			}
			
			
			String method = args[0];
			
			/*
			 * BASED ON LAB 03 
			 * 
			 * Here we call ONLY method that are requested for the Assignment01, Based on Lab 3.
			 * 
			 * Here we can find the control of the args[0]
			 * If there are no problems we call functions
			 */
			if (method.equals("getWeight")) {
				Long personId = Long.parseLong(args[1]);
				getWeight(personId);
			} else if (method.equals("getHeight")) {
				Long personId = Long.parseLong(args[1]);
				getHeight(personId);
			} else if (method.equals("printAllPeople")) {				
				printAllPeople();
			} else if (method.equals("displayWeightOperator")){
				String operator = args[1];
				displayWeightOperator(operator);
			} else if (method.equals("displayHealthProfile")) { 
				Long personId = Long.parseLong(args[1]);
				displayHealthProfile(personId);
			} else {
				System.out.println("The system did not find the method '" + method + "'");
			}
		}
	}
	
	/*
	 * EXTRA_FUNCTION
	 * This function parse the XML Document and assign it to the variable document
	 * We call it only one time inside of the main.
	 * Using this we have no repetitive rows inside functions
	 */
	public static void ParseInformation() throws ParserConfigurationException, SAXException, IOException{
		
		// System.out.println("Loading XML with people inside");
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		
		document = builder.parse("people.xml");
	}
	
	
	/*
	 * Function for the assignment01, based on Lab03
	 * - printAllPeople
	 * - getHeight
	 * - getWeight
	 * - displayHealthProfile
	 * - displayWeightOperator
	 */
	
	
	/*
	 * !!! runs instruction 4 based on Lab 3 with weight > 90 !!!
	 * 4-based on lab03) function which accepts a weight and an operator (=, > , <) as parameters 
	 * and prints people that fulfill that condition (i.e., >80Kg, =75Kg, etc.).
	 */
	public static void displayWeightOperator(String operator) throws XPathExpressionException{

		// create the XPath object using XPathFactory instance
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		// search for people with a specific weight using operator; 
	    XPathExpression exp = xpath.compile("/people/person[healthprofile/weight"+operator+"]");

	    NodeList nList = (NodeList) exp.evaluate(document, XPathConstants.NODESET);
        
        System.out.println("People with weight " + operator + ":");
        System.out.println("==========================================");
        
        
		//for each person in the nList with <90 weight:
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			 Node node = nList.item(temp);
			 System.out.println("");    //Just a separator

			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {					 
			    //Print people
			    Element eElement = (Element) node;
			    System.out.println("Firstname : "  + eElement.getElementsByTagName("firstname").item(0).getTextContent());
				System.out.println("Lastname : "    + eElement.getElementsByTagName("lastname").item(0).getTextContent());
			    System.out.println("Weight : "   + eElement.getElementsByTagName("weight").item(0).getTextContent());
			 }
		}
        
        	  	
	}
	
	/*
	 * !!! Runs instruction 3 based on Lab 3 with id = 5 !!!
	 * 3-based on lab03) Provide a function that accepts id as parameter and prints the HealthProfile of the person with that id
	 */
	public static void displayHealthProfile(Long personIdLong) throws XPathExpressionException {
		/* Cast Long --> String
	 	Method used to pad the String with "0" to fill the String and have a length of 4.
	 	If we use Long.toString we put in the variable a number without 0
		 */
		String personID = String.format ("%04d", personIdLong);
		
			
		// create the XPath object using XPathFactory instance
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		// search for the health profile of a specific person with "personID" 
	    XPathExpression exp = xpath.compile("people/person[@id='" + personID + "']/healthprofile");

	    Node node = (Node) exp.evaluate(document, XPathConstants.NODE);
        Element eElement = (Element) node;
        
        System.out.println("Information about person with ID: " + personID);
        System.out.println("==========================================");
        
		System.out.println("Lastupdate : "  + eElement.getElementsByTagName("lastupdate").item(0).getTextContent());
	    System.out.println("Weight : "   + eElement.getElementsByTagName("weight").item(0).getTextContent());
	    System.out.println("Height : "    + eElement.getElementsByTagName("height").item(0).getTextContent());
	    System.out.println("Bmi : "  + eElement.getElementsByTagName("bmi").item(0).getTextContent());		
    	    
	}
	

	/*
	 * !!! Runs instruction 2 based on Lab 3 !!!
	 * 2-based on lab03) Make a function that prints all people in the list with details
	 */
	public static void printAllPeople(){

		//Normalize the XML Structure
		document.getDocumentElement().normalize();
					
		//Here comes the root node
		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName().toUpperCase());
		
		//Get all people
		NodeList nList = document.getElementsByTagName("person");
		System.out.println("Number of people: " + nList.getLength());
		System.out.println("==============================================");

		
		//for each person in the nList:
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			 Node node = nList.item(temp);
			 System.out.println("");    //Just a separator

			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {					 
			    //Print each people's detail
			    Element eElement = (Element) node;
			    System.out.println("First Name : "  + eElement.getElementsByTagName("firstname").item(0).getTextContent());
			    System.out.println("Last Name : "   + eElement.getElementsByTagName("lastname").item(0).getTextContent());
			    System.out.println("Birthdate : "    + eElement.getElementsByTagName("birthdate").item(0).getTextContent());
			    System.out.println("Lastupdate : "  + eElement.getElementsByTagName("lastupdate").item(0).getTextContent());
			    System.out.println("Weight : "   + eElement.getElementsByTagName("weight").item(0).getTextContent());
			    System.out.println("Height : "    + eElement.getElementsByTagName("height").item(0).getTextContent());
			    System.out.println("Bmi : "  + eElement.getElementsByTagName("bmi").item(0).getTextContent());
			 }
		}
	} 


	/*
	 * 1-based on lab03) Use xpath to implement getHeight(personID) returns height of a given person
	 */
	public static void getHeight(Long personIdLong) {
		/* Cast Long --> String
	 	We are telling the method to pad the String with "0" to fill the String and have a length of 4.
	 	If we use Long.toString we put in the variable a number without 0
		 */
		String personID = String.format ("%04d", personIdLong);
		
		try {
						
			// create the XPath object using XPathFactory instance
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			// search for the height of specific person with 
		    XPathExpression exp = xpath.compile("people/person[@id='" + personID + "']/healthprofile/height");

			//create the object with all the person found during the compile
			Object ObjHeights = exp.evaluate(document, XPathConstants.NODESET);
			//create a collections of Nodes "person"
		    NodeList person = (NodeList) ObjHeights;
		    //Print all the person with "personID"
		    for (int i = 0; i < person.getLength(); i++) {
		    	System.out.println("The height of the choosen person is: " + person.item(i).getTextContent());
		        
		    }
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 1-based on lab03) Use xpath to implement getWeight(personID) returns weight of a given person
	 */
	public static void getWeight(Long personIdLong) {
		/* Cast Long --> String
		 	We are telling the method to pad the String with "0" to fill the String and have a length of 4.
		 	If we use Long.toString we put in the variable a number without 0
		*/
		String personID = String.format ("%04d", personIdLong);
		
		try {
						
			// create the XPath object using XPathFactory instance
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			// search for the weight of specific person with 
		    XPathExpression exp = xpath.compile("people/person[@id='" + personID + "']/healthprofile/weight");

			//create the object with all the person found during the compile
			Object ObjWeights = exp.evaluate(document, XPathConstants.NODESET);
			//create a collections of Nodes "person"
		    NodeList person = (NodeList) ObjWeights;
		    
		    
		    //Print the person with "personID"
		    for (int i = 0; i < person.getLength(); i++) {
		        System.out.println("The weight of the choosen person is: " + person.item(i).getTextContent());
		        
		    }
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}