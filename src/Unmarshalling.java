import java.io.FileReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import model.Person;
import dao.PeopleStore;

public class Unmarshalling {  	
	public static PeopleStore people = new PeopleStore();

	public static void main(String[] args) throws Exception {
		JAXBContext ps = JAXBContext.newInstance(PeopleStore.class);
        System.out.println();
        System.out.println("Unmarshalling of our XML File (people-marshalling.xml): ");
        System.out.println("");
        Unmarshaller um = ps.createUnmarshaller();
        //UnMarshalling from XML to java using the output file created with Marshalling (people-marshalling.mxl)
        PeopleStore people = (PeopleStore) um.unmarshal(new FileReader("people-marshalling.xml"));
        List<Person> list = people.getData();
        //For each person, write some details
        for (Person person : list) {
          System.out.println("Person: " + person.getFirstname() +" " + person.getLastname() + " born "
              + person.getBirthdate());
        }

    }
}
