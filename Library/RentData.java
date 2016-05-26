import java.util.Date;

public class RentData {

	private AvaibleMaterial mat;
	private Member mem;
	
	private Date startDate;
	private Date endDate;
	
	public RentData(){
		this.setStartDate(null);
	}
	
	public RentData(AvaibleMaterial mat, Member mem, Date startDate){
		this.setMat(mat);
		this.setMem(mem);
		this.setStartDate(startDate);
	}

	public void freeBook(){
		mat.setRent(null);
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
