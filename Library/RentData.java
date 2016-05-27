import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentData {

	private AvaibleMaterial mat;
	private Member mem;
	
	private Date startDate;
	private Date endDate;
	
	public RentData(){
		this.setStartDate(null);
	}
	
	public RentData(Member mem, AvaibleMaterial mat, Date startDate){
		this.setMat(mat);
		this.setMem(mem);
		this.setStartDate(startDate);
	}
	
	public RentData(Member mem, AvaibleMaterial mat, String startDate){
		this.setMat(mat);
		this.setMem(mem);
		DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
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
	
	public void setMat(AvaibleMaterial mat) {
		this.mat = mat;
	}
	public void setMem(Member mem) {
		this.mem = mem;
	}
	public AvaibleMaterial getMat() {
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
