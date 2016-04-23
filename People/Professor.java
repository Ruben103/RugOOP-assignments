
public class Professor extends Lecturer {

	private String title;
	
	public Professor(String name, String firstName, String address, String employeeNumber, String phoneNumber, String title) {
		super(name, firstName, address, phoneNumber, employeeNumber);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString(){
		return concat("Title: ",this.getTitle(),","," ",super.toString());
	}

}
