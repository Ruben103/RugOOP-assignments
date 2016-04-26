import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class Main {

	public static void main(String[] argv) {

		ArrayList<Person> persons = new ArrayList<Person>();
		persons.add(new Lecturer("Smedinga", "Rein", "Broadway 32", "2345", "0123456789"));
		persons.add(new Student("Styles", "Oliver", "George St 5", "1231231", "1111111111"));
		persons.add(new Student("Horan", "Harry", "Regent St 11", "4564564", "2222222222"));
		persons.add(new Lecturer("Doe", "John", "Main St 153", "6789", "333333333"));
		persons.add(new Student("Payne", "Jack", "Seven Bridges Way 3", "7897897", "444444444"));
		persons.add(new Lecturer("White", "Sow", "Fairy Ln 1", "0123", "555555555"));
		persons.add(new Student("Malik", "Charlie", "York Rd 27", "1011121", "6666666666"));
		
		persons.add(new Professor("Corradini", "Matteo", "York Rd 27", "1011121", "777777777", "Dr."));
		persons.add(new Professor("Atac", "Berke", "York Rd 27", "1011121", "888888888", "Dr."));
		persons.add(new Professor("Palmieri", "Viola", "York Rd 27", "1011121", "9999999999", "Dr."));

		for (Person person : persons) {
			System.out.println(person);
		}

		System.out.println("\nafter sorting:\n");
		Collections.sort(persons);
		for (Person person : persons) {
			System.out.println(person);
		}

		Collections.sort(persons, Person.compareFirstName());
		System.out.println("\nafter sorting by first name:\n");
		for (Person person : persons) {
			System.out.println(person);
		}
		
	}
}
