import java.io.Serializable;

public abstract class AvailableMaterial implements Serializable{
	
	protected String author;
	protected String title;
	protected RentData rent;
	protected int privateId;
	private Library lib;
	
	private static int id = 0;
	
	public AvailableMaterial(String author, String title, Library lib){
		this.setAuthor(author);
		this.setTitle(title);
		this.setLib(lib);
		this.setPrivateId(AvailableMaterial.getId());
		AvailableMaterial.setId(AvailableMaterial.getId()+1);
	}
	
	public static int getId(){
		return id;
	}
	
	public static void setId(int new_id){
		id = new_id;
	}
	
	public boolean isAvaible(){
		return rent == null ? true : false;
	}
	
	@Override
	public String toString(){
		return this.getAuthor()+ ": "+this.getTitle(); 
	}
	
	/* Begin getters and setters*/
	public int getPrivateId() {
		return privateId;
	}
	public void setPrivateId(int privateId) {
		this.privateId = privateId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public RentData getRent() {
		return rent;
	}
	public void setRent(RentData rent) {
		this.rent = rent;
	}
	public Library getLib() {
		return lib;
	}
	public void setLib(Library lib) {
		this.lib = lib;
	}
	/*End getters and setters*/
}
