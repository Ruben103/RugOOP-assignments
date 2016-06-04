import java.awt.Rectangle;

public class GraphVertex {
	
	/*Name suppose to be unique*/
	private String name;
	private Rectangle rect;
	
	/*Default constant*/
	private final String DEFAULT_NAME = "VERTEX";
	private final int DEFAULT_WIDTH = 100;
	private final int DEFAULT_HEIGHT = 75;
	
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
	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	

}
