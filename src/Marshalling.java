import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;

/*
 * Exercise 2 Based on Lab04
 * Marshalling on java people to XML
 */


public class Marshalling {

	//initialize the list of people
	public static PeopleStore people = new PeopleStore();

	public static void initializeDB() {

	    //Create 3 people 
		Person alessio = new Person();
		alessio.setPersonId(new Long(1));
	    alessio.setFirstname("Alessio");
	    alessio.setLastname("Spallino");
	    alessio.setBirthdate("1993-10-20");
	    HealthProfile hp = new HealthProfile(68.0, 1.72);
	    alessio.setHProfile(hp);
	    
	    Person federica = new Person();
		federica.setPersonId(new Long(2));
	    federica.setFirstname("Federica");
	    federica.setLastname("Fattorelli");
	    federica.setBirthdate("1993-09-29");
	    HealthProfile hp2 = new HealthProfile(102.0, 1.20);
	    federica.setHProfile(hp2);
	    
	    Person mario = new Person();
		mario.setPersonId(new Long(3));
	    mario.setFirstname("Mario");
	    mario.setLastname("Benini");
	    mario.setBirthdate("1992-08-12");
	    HealthProfile hp3 = new HealthProfile(97.0, 1.45);
	    mario.setHProfile(hp3);

	    //Add people in the list
		people.getData().add(alessio);
		people.getData().add(federica);
		people.getData().add(mario);
	}


	public static void main(String[] args) throws Exception {
		
		initializeDB();
		
		JAXBContext ps = JAXBContext.newInstance(PeopleStore.class);
        Marshaller m = ps.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        m.marshal(people,new File("people-marshalling.xml")); // marshalling into a file
        m.marshal(people, System.out);			  // marshalling into the system default output
    }
}
