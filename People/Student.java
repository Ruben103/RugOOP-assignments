
public class Student extends Person {

	private String studentNumber;

	public Student(String name, String firstName, String address,
			String studentNumber, String phoneNumber) {
		super(name, firstName, address, phoneNumber);
		this.studentNumber = studentNumber;
	}


	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentnumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	
	@Override
	public String toString(){
		return concat(super.toString(),",", " Student Number: ", this.getStudentNumber());
	}
	
}
