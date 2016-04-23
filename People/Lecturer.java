
public class Lecturer extends Person {

	private String employeeNumber;

	public Lecturer(String name, String firstName, String address,
			String employeeNumber, String phoneNumber) {
		super(name, firstName, address, phoneNumber);
		this.employeeNumber = employeeNumber;
	}


	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String number) {
		employeeNumber = number;
	}
	
	@Override
	public String toString(){
		return concat(super.toString(),",", " Employee Number: ", this.getEmployeeNumber());
	}


}
