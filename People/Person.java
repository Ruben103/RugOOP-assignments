import java.util.*;

public abstract class Person implements Comparable<Person> {

	private String name;
	private String firstName;
	private String address;
	private String phoneNumber;

	public Person(String name, String firstName, String address, String phoneNumber) {
		this.name = name;
		this.firstName = firstName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	/* Return a String as concatenation of the input ones */
	public String concat(String... stringToConcat){
		String buffer = new String("");
		for (String string : stringToConcat)
			buffer += string;
		return buffer;
	}
	
	@Override
	public String toString(){
		return concat("Name : ", this.getName(), ",", " First name: ", this.getFirstName(),",",
				" Phone Number: ", this.getPhoneNumber()); 
	}
	
	/* Implement sort by name */
	public int compareTo(Person other){
		return this.getName().compareTo(other.getName());
	}
	
	/* Implement sort by first name */
	public static Comparator<Person> compareFirstName(){
		return new Comparator<Person>(){
			public int compare(Person person1, Person person2){
				return person1.getFirstName().compareTo(person2.getFirstName());
			}
		};
	}
}
