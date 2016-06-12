import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class GraphPanel extends JPanel implements Observer{

	private GraphModel graph;

	public GraphPanel(GraphModel _graph){
		this.setModel(_graph);
		this.graph.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.repaint();
		System.out.println("Repaint:" + arg);
	}
	
	public void paintComponent(Graphics g){
		this.paintVertexes(g);
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
	
	public void repaint(){
		this.paintComponent(this.getGraphics());
	}
	
	public void setModel(GraphModel _graph){
		this.graph = _graph;
	}
}