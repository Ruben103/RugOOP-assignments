import java.awt.Rectangle;

public class GraphVertex {
	
	/*Name suppose to be unique*/
	String name;
	Rectangle rect;
	
	/*Default constant*/
	final String DEFAULT_NAME = "VERTEX";
	final int DEFAULT_WIDTH = 100;
	final int DEFAULT_HEIGHT = 75;
	
	public GraphVertex(String name, Rectangle rect){
		this.name = name;
		this.rect = rect;
	}
	
	public GraphVertex(){
		this.name = DEFAULT_NAME;
		this.rect = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	

	
	/*AUTOgenerate setters and getters*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
