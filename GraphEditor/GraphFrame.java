import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GraphFrame extends JFrame{

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 800;
	private static final String TITLE_NAME = "Graph Editor";
	
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel centerPanel;

	private JMenuBar menuBar;
	private JMenu menuFile, menuEdit, menuWindow;
	private JToolBar tb;
	private JButton btnNewVertex, btnDelNode, btnNewEdge, undo, redo;
	private GraphPanel graphPanel;

	private GraphModel model;

	public GraphFrame(GraphModel _graph){
		this.setModel(_graph);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setWindowProperites();
		this.createMenu();
		this.createToolbar();
		this.addPanel();

		//this.addMouseListener(new SelectionController());
		
		this.btnNewVertex.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.addVertex();
			}
		});
		/*this.delNode.addActionListener(new ActionListener() { 
			GraphVertex selectedVertex = GraphFrame.this.model.vertexes.getSelectedValue();
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.model.removeVertex();
			}
		});*/
		this.btnNewEdge.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				/*Open a window if 2 vertexes are selected*/
				GraphFrame.this.addEdge();
			}
		});
		this.undo.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				if (GraphFrame.this.model.getUndoManager().stackOperation.isEmpty())
					GraphFrame.this.undo.setEnabled(false);
			}
		});
		this.redo.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				if (GraphFrame.this.model.getRedoManager().stackOperation.isEmpty())
					GraphFrame.this.redo.setEnabled(false);
			}
		});
		
		this.drawWholeFrame();
	}

	private void setWindowProperites(){
		this.setTitle(TITLE_NAME);
		this.setResizable(false);
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}

	private void createMenu(){
		this.menuBar = new JMenuBar();

		//add first headings
		this.menuFile = new JMenu("File");
		this.menuEdit = new JMenu("Edit");
		this.menuWindow = new JMenu("Window");

		this.menuBar.add(menuFile);
		this.menuBar.add(menuEdit);
		this.menuBar.add(menuWindow);
	}

	private void addPanel(){
		this.graphPanel = new GraphPanel(this.model);
		this.northPanel = new JPanel (new GridLayout(2, 0, 0, 0));
		this.southPanel = new JPanel (new BorderLayout());
		this.centerPanel = new JPanel (new BorderLayout());

		this.northPanel.add(this.menuBar);
		this.northPanel.add(this.tb);
		this.centerPanel.add(graphPanel);
	}

	private void createToolbar(){
		this.tb = new JToolBar();
		tb.setFloatable(false);
		
		this.btnNewVertex = new JButton("New Vertex");
		this.btnDelNode = new JButton("Delete Vertex");
		this.btnNewEdge = new JButton("Add Edge");
		this.undo = new JButton("Undo");
		this.redo = new JButton("Redo");
		this.getUndo().setEnabled(false);
		this.getRedo().setEnabled(false);
		
		this.getTb().add(this.getBtnNewVertex());
		this.getTb().add(this.getBtnDelNode());
		this.getTb().add(this.getBtnNewEdge());
		this.getTb().add(this.getUndo());
		this.getTb().add(this.getRedo());
	}
	
	private void drawWholeFrame(){
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.centerPanel, BorderLayout.CENTER);
	}
	

	private void addVertex(){
		NewGraphVertexDialog vertexDialog = new NewGraphVertexDialog(this.getModel());
		vertexDialog.setVisible(true);
		this.disEnButtons(false);
		vertexDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	//GraphFrame.this.refreshLists();
		    	GraphFrame.this.disEnButtons(true);
		    	GraphFrame.this.undo.setEnabled(true);
		    }
		});
	}
	
	private void addEdge(){
		NewGraphEdgeDialog edgeDialog = new NewGraphEdgeDialog(this.getModel());
		edgeDialog.setVisible(true);
		this.disEnButtons(false);
		edgeDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	//GraphFrame.this.refreshLists();
		    	GraphFrame.this.disEnButtons(true);
		    	GraphFrame.this.undo.setEnabled(true);
		    }
		});
	}
	
	private void disEnButtons(boolean command){
		this.getBtnDelNode().setEnabled(command);
		this.getBtnNewEdge().setEnabled(command);
		this.getBtnDelNode().setEnabled(command);
	}
	
	public JMenu getMenuFile() {
		return menuFile;
	}
	public void setMenuFile(JMenu menuFile) {
		this.menuFile = menuFile;
	}
	public JMenu getMenuEdit() {
		return menuEdit;
	}
	public void setMenuEdit(JMenu menuEdit) {
		this.menuEdit = menuEdit;
	}
	public JMenu getMenuWindow() {
		return menuWindow;
	}
	public void setMenuWindow(JMenu menuWindow) {
		this.menuWindow = menuWindow;
	}
	public JToolBar getTb() {
		return tb;
	}
	public void setTb(JToolBar tb) {
		this.tb = tb;
	}
	public JButton getBtnNewVertex() {
		return btnNewVertex;
	}
	public void setBtnNewVertex(JButton btnNewVertex) {
		this.btnNewVertex = btnNewVertex;
	}
	public JButton getBtnDelNode() {
		return btnDelNode;
	}
	public void setBtnDelNode(JButton btnDelNode) {
		this.btnDelNode = btnDelNode;
	}
	public JButton getBtnNewEdge() {
		return btnNewEdge;
	}
	public void setBtnNewEdge(JButton btnNewEdge) {
		this.btnNewEdge = btnNewEdge;
	}
	public JButton getUndo() {
		return undo;
	}
	public void setUndo(JButton undo) {
		this.undo = undo;
	}
	public JButton getRedo() {
		return redo;
	}
	public void setRedo(JButton redo) {
		this.redo = redo;
	}
	public GraphModel getModel() {
		return model;
	}
	public void setModel(GraphModel model) {
		this.model = model;
	}


}