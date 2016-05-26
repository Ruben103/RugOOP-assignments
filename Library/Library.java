import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

public class Library extends Observable{

	private Vector<Member> members;
	private Vector<AvaibleMaterial> materials;
	
	private ArrayList<RentData> rentHistory;
	private ArrayList<RentData> reservations;
	
	
	public Library(){
		members = new Vector<Member>();
		materials = new Vector<AvaibleMaterial>();
		
		rentHistory = new ArrayList<RentData>();
		reservations = new ArrayList<RentData>();
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
		for (Member mem : members){
			buff+= count+") "+mem+"\n";
			count++;
		}
		return buff;
	}
	
	public void returnMaterial(RentData rent){
		rent.setEndDate(new Date());
		rent.freeBook();
		(rent.getMem()).returnMaterial(rent.getMat());
		
		/*Member has to be noticed*/
		
		rentHistory.add(rent);
	}
	
	public boolean rentingMaterial(AvaibleMaterial mat, Member mem, Date startDate){
		if (!mat.isAvaible()) return false;
		
		RentData rent = new RentData(mat, mem, startDate);
		mem.addRent(rent);
		mat.setRent(rent);
		return true;
	}
	
	public boolean makeReservation(Member mem, AvaibleMaterial mat){
		if (mat.isAvaible()) return false;
		
		RentData rent = new RentData(mat, mem, new Date());
		reservations.add(rent);
		return true;
	}
	
}
