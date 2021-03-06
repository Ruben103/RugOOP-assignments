import java.io.Serializable;
import java.util.Vector;

public class Member implements Serializable{
	
	private String name;
	private String surname;
	private int memberId;
	private Library lib;
	private String address;
	
	private static int id = 0;
	
	Vector<RentData> rents;
	
	public Member(String name, String surname, String address, Library lib) {
		this.setName(name);
		this.setSurname(surname);
		this.setLib(lib);
		this.setAddress(address);
		this.rents = new Vector<RentData>(lib.getMaterials().size());
		this.setMemberId(Member.getId());
		Member.setId(Member.getId()+1);
	}
	
	@Override
	public String toString(){
		return this.getSurname() + " " + this.getName();
	}
	
	public Vector<AvailableMaterial> getRentMats(){
		Vector<AvailableMaterial> rentMat = new Vector<AvailableMaterial>();
		for (RentData rent : this.getRents())
			rentMat.addElement(rent.getMat());
		return rentMat;
	}
	
	public Vector<RentData> getRents() {
		return rents;
	}

	public void returnMaterial(RentData rent){
		this.rents.remove(rent);
	}
	
	public void addRent(RentData rent){
		this.rents.add(rent);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public static int getId() {
		return id;
	}
	public static void setId(int new_id){
		id = new_id;
	}
	public Library getLib() {
		return lib;
	}
	public void setLib(Library lib) {
		this.lib = lib;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
