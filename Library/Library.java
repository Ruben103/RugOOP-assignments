import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Library {

	public Vector<Member> members;
	public Vector<AvaibleMaterial> materials;
	
	public ArrayList<RentData> rentHistory;
	public ArrayList<RentData> reservations;
	
	public Library(){
		members = new Vector<Member>();
		materials = new Vector<AvaibleMaterial>();
		
		rentHistory = new ArrayList<RentData>();
		reservations = new ArrayList<RentData>();
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
