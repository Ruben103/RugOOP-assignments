import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

public class SelectionController implements MouseListener, MouseMotionListener{

	private GraphVertex selectedVertex;
	private GraphVertex draggedVertex;
	private GraphModel model;
	private GraphPanel panel;
	
	private boolean moving = false;
	
	SelectionController(GraphModel _model, GraphPanel _panel){
		this.model = _model;
		this.panel = _panel;
	}
	
	public int coordX(MouseEvent e) {
		return e.getX();
	}
	
	public int coordY(MouseEvent e) {
		return e.getY();
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		if (e.getClickCount() == 2) {
			ChangeVertexNameDialog nameDialog = new ChangeVertexNameDialog(this.model, this.selectedVertex, this.panel);
			nameDialog.setVisible(true);
			return;
		  }
		
		Point pressed = e.getPoint();
		for (GraphVertex vertex : this.model.getVertexes()){
			if (vertex.containsPressedPoint(pressed))  
				if (this.selectedVertex == null || !this.selectedVertex.equals(vertex)){
					this.selectedVertex = vertex;
					this.model.setSelectedVertex(this.selectedVertex);
					this.panel.repaint();
					this.panel.paintRed(this.selectedVertex);
			}
			else{
				this.selectedVertex = null;
				this.model.setSelectedVertex(this.selectedVertex);
				this.panel.repaint();
			}		
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point pressed = e.getPoint();
		double x = pressed.getX();
		double y = pressed.getY();
		for (GraphVertex vertex : this.model.getVertexes()){
			if (vertex.containsPressedPoint(pressed)){
				if (this.selectedVertex != null && !this.selectedVertex.equals(vertex))
					continue;
				
				this.setMoving(true);
				this.draggedVertex = vertex;
				this.selectedVertex = vertex;
				this.model.setSelectedVertex(this.selectedVertex);
				
				this.draggedVertex.getRect().x = (int) ((int) x - (this.draggedVertex.getRect().getWidth()/2));
				this.draggedVertex.getRect().y = (int) ((int) y - (this.draggedVertex.getRect().getHeight()/2));
				this.panel.repaint();
				this.panel.paintRed(this.selectedVertex);
				return;
			}
		}
		if (this.isMoving()){
			this.setMoving(false);
			String error = "Too fast! The mouse lost the reference to the vertex.";
			JOptionPane.showMessageDialog(null, error);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public void setModel(GraphModel _graph){
		this.model = _graph;
	}
	
}
