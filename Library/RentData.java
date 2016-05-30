import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentData implements Serializable{

	private AvailableMaterial mat;
	private Member mem;
	
	private Date startDate;
	private Date endDate;
	
	public RentData(){
		this.setStartDate(null);
	}
	
	public RentData(Member mem, AvailableMaterial mat, Date startDate){
		this.setMat(mat);
		this.setMem(mem);
		this.setStartDate(startDate);
	}
	
	public RentData(Member mem, AvailableMaterial mat, String startDate){
		this.setMat(mat);
		this.setMem(mem);
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = format.parse(startDate);
			this.setStartDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void freeMat(){
		this.mat.setRent(null);
	}	
	
	@Override
	public String toString(){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return this.getMem() + " rents " + this.getMat() 
				+ " from: " + format.format(this.getStartDate());
	}
	public void setMat(AvailableMaterial mat) {
		this.mat = mat;
	}
	public void setMem(Member mem) {
		this.mem = mem;
	}
	public AvailableMaterial getMat() {
		return mat;
	}
	public Member getMem() {
		return mem;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
