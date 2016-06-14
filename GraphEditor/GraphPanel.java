import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class GraphPanel extends JPanel implements Observer {

	private GraphModel graph;
	private SelectionController selCon;

	public GraphPanel(GraphModel _graph){
		selCon = new SelectionController(_graph, this);
		this.setModel(_graph);
		this.graph.addObserver(this);
		
		
		this.addMouseListener(selCon);
		this.addMouseMotionListener(selCon);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.repaint();
		System.out.println("update: " + arg);
	}
	
	public void paintComponent(Graphics g){
		if (g == null) return;
		this.clear(g);
		this.paintVertexes(g);
		this.paintEdge(g);
	}
	
	private void paintVertexes(Graphics g){
		if (this.graph == null) return;
	
		for (GraphVertex vertex : this.graph.getVertexes()){
			
			int x = vertex.getRect().x, y = vertex.getRect().y, width = vertex.getRect().width;
			int height = vertex.getRect().height;
			g.drawRect(x, y, width, height);
			g.setColor(Color.white);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawString(vertex.getName(), vertex.getRect().x, 
						 vertex.getRect().y + vertex.getRect().height/2);
		}
	}
	
	private void clear(Graphics g){
		g.setColor(UIManager.getColor("Panel.background"));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	private void paintEdge(Graphics g){
		g.setColor(Color.black);
		for(GraphEdge edge : this.graph.getEdges()){
			Rectangle rect1 = edge.getV1().getRect();
			Rectangle rect2 = edge.getV2().getRect();
			g.drawLine(rect1.x + (rect1.width/2) , rect1.y + (rect1.height/2), 
					rect2.x + (rect2.width/2), rect2.y + (rect2.height/2) );
		}
		
	}
	
	public void paintRed(GraphVertex vertex){
		Graphics g = this.getGraphics();
		int x = vertex.getRect().x, y = vertex.getRect().y, width = vertex.getRect().width;
		int height = vertex.getRect().height;
		g.drawRect(x, y, width, height);
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString(vertex.getName(), vertex.getRect().x, 
					 vertex.getRect().y + vertex.getRect().height/2);
	}
	
	public void repaint(){
		this.requestFocus();
		this.paintComponent(this.getGraphics());
	}
	
	public void setModel(GraphModel _graph){
		this.graph = _graph;
		this.selCon.setModel(_graph);
	}
}