import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class GraphVertex implements Serializable{
	
	/*Name suppose to be unique*/
	private String name;
	private Rectangle rect;
	
	/*Default constant*/
	public static final String DEFAULT_NAME = "VERTEX";
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGHT = 75;
	
	public GraphVertex(){
		this.name = DEFAULT_NAME;
		this.rect = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GraphVertex(String name){
		this.name = name;
		this.rect = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GraphVertex(String name, Rectangle rect){
		this.name = name;
		this.rect = rect;
	}

	public Boolean containsPressedPoint(Point p){
		if (p.getX() > this.getRect().x && p.getX() < (this.getRect().x + this.getRect().getWidth()) 
				&& p.getY() > this.getRect().y && p.getY() < (this.getRect().y + this.getRect().getHeight())){
			return true;
		}
		return false;
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
