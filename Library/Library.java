import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

public class Library extends Observable{

	private Vector<Member> members;
	private Vector<AvaibleMaterial> materials;
	
	private Vector<RentData> rents;
	private Vector<RentData> rentHistory;
	private Vector<RentData> reservations;
	
	
	public Library(){
		members = new Vector<Member>();
		materials = new Vector<AvaibleMaterial>();
		rentHistory = new Vector<RentData>();
		reservations = new Vector<RentData>();
		rents = new Vector<RentData>();
	} 

	public Library(Vector<Member> members, Vector<AvaibleMaterial> materials, 
					Vector<RentData> rentHistory, Vector<RentData> reservations, Vector<RentData> rents){
		this.setMembers(members);
		this.setMaterials(materials);
		this.setRentHistory(rentHistory);
		this.setReservations(reservations);
		this.setRents(rents);
	}
	
	public Library(Library _lib){
		this(_lib.getMembers(), _lib.getMaterials(), _lib.getRentHistory(), _lib.getReservations(), _lib.getRents());
	}
	
	public void sendNotificationToObs(String message){
		setChanged();
		notifyObservers(message);
	}
	
	public void addMaterial(AvaibleMaterial mat){
		materials.add(mat.getPrivateId(), mat);
		this.sendNotificationToObs(this.materialToString());
	}
	
	public void addMember(Member mem){
		members.add(mem.getMemberId(), mem);
		this.sendNotificationToObs(this.memberToString());
	}
	
	public String materialToString(){
		String buff = "";
		int count = 1;
		for (AvaibleMaterial mat : materials){
			buff+= count+") "+mat+"\n";
			count++;
		}
		return buff;
	}
	
	public String memberToString(){
		String buff = "";
		int count = 1;
		for (Member mem : this.members){
			buff+= count+") "+mem+"\n";
			count++;
		}
		return buff;
	}
	
	public RentData returnRent(Member mem, AvaibleMaterial mat){
		for (RentData rent : this.rents)
			if (rent.getMat() == mat && rent.getMem() == mem)
				return rent;
		return null;
	}
	
	public void returnMaterial(RentData rent){
		rent.setEndDate(new Date());
		(rent.getMem()).returnMaterial(rent.getMat());
		this.rents.remove(rent);
		rent.freeMat();

		rentHistory.add(rent);
	}
	
	public boolean rentingMaterial(Member mem, AvaibleMaterial mat, String startDate){
		if (!mat.isAvaible()) return false;
		RentData rent = new RentData(mem, mat, startDate);
		mem.addRent(rent);
		mat.setRent(rent);
		this.rents.add(rent);
		return true;
	}
	
	public boolean rentingMaterial(Member mem, AvaibleMaterial mat, Date startDate){
		if (!mat.isAvaible()) return false;
		RentData rent = new RentData(mem, mat, startDate);
		mem.addRent(rent);
		mat.setRent(rent);
		this.rents.add(rent);
		return true;
	}
	
	public Vector<AvaibleMaterial> getFreeMaterial(){
		Vector<AvaibleMaterial> freeMat = new Vector<AvaibleMaterial> ();
		for (AvaibleMaterial mat : this.getMaterials()){
			if (mat.isAvaible()) freeMat.addElement(mat);
		}
		return freeMat;	
	}
	
	
	public boolean makeReservation(Member mem, AvaibleMaterial mat, String startDate){
		if (mat.isAvaible()) return false;
		
		RentData rent = new RentData(mem, mat, startDate);
		reservations.add(rent);
		return true;
	}
	
	public boolean makeReservation(Member mem, AvaibleMaterial mat){
		if (mat.isAvaible()) return false;
		
		RentData rent = new RentData(mem, mat, new Date());
		reservations.add(rent);
		return true;
	}

	public Vector<Member> getMembers() {
		return members;
	}
	public Vector<AvaibleMaterial> getMaterials() {
		return materials;
	}

	public Vector<RentData> getRentHistory() {
		return rentHistory;
	}

	public void setRentHistory(Vector<RentData> rentHistory) {
		this.rentHistory = rentHistory;
	}

	public Vector<RentData> getReservations() {
		return reservations;
	}

	public void setReservations(Vector<RentData> reservations) {
		this.reservations = reservations;
	}

	public void setMembers(Vector<Member> members) {
		this.members = members;
	}

	public void setMaterials(Vector<AvaibleMaterial> materials) {
		this.materials = materials;
	}

	public Vector<RentData> getRents() {
		return rents;
	}

	public void setRents(Vector<RentData> rents) {
		this.rents = rents;
	}
	
	
}
