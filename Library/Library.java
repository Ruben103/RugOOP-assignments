import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

public class Library extends Observable implements Serializable{

	private Vector<Member> members;
	private Vector<AvailableMaterial> materials;
	private Vector<RentData> rentHistory;
	
	public Library(){
		members = new Vector<Member>();
		materials = new Vector<AvailableMaterial>();
		rentHistory = new Vector<RentData>();
	} 

	public Library(Vector<Member> members, Vector<AvailableMaterial> materials, 
					Vector<RentData> rentHistory){
		this.setMembers(members);
		this.setMaterials(materials);
		this.setRentHistory(rentHistory);
	}
	
	public Library(Library _lib){
		this(_lib.getMembers(), _lib.getMaterials(), _lib.getRentHistory());
	}
	
	public void sendNotificationToObs(String message){
		setChanged();
		this.notifyObservers(message);
	}
	
	public void addMaterial(AvailableMaterial mat){
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
		for (AvailableMaterial mat : this.getMaterials()){
			buff+= count+") "+mat+"\n";
			count++;
		}
		return buff;
	}
	
	public String memberToString(){
		String buff = "";
		int count = 1;
		for (Member mem : this.getMembers()){
			buff+= count+") "+mem+"\n";
			count++;
		}
		return buff;
	}
	
	public void returnMaterial(RentData rent){
		rent.setEndDate(new Date());
		(rent.getMem()).returnMaterial(rent);
		rent.freeMat();
		rentHistory.add(rent);
	}
	
	public boolean rentingMaterial(Member mem, AvailableMaterial mat, String startDate){
		if (!mat.isAvaible()) return false;
		RentData rent = new RentData(mem, mat, startDate);
		mem.addRent(rent);
		mat.setRent(rent);
		return true;
	}

	public Vector<AvailableMaterial> getFreeMaterial(){
		Vector<AvailableMaterial> freeMat = new Vector<AvailableMaterial> ();
		for (AvailableMaterial mat : this.getMaterials()){
			if (mat.isAvaible()) freeMat.addElement(mat);
		}
		return freeMat;	
	}

	public Vector<Member> getMembers() {
		return members;
	}
	public Vector<AvailableMaterial> getMaterials() {
		return materials;
	}

	public Vector<RentData> getRentHistory() {
		return rentHistory;
	}

	public void setRentHistory(Vector<RentData> rentHistory) {
		this.rentHistory = rentHistory;
	}

	public void setMembers(Vector<Member> members) {
		this.members = members;
	}

	public void setMaterials(Vector<AvailableMaterial> materials) {
		this.materials = materials;
	}
}
