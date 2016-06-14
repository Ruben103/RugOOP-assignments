import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GraphFrame extends JFrame{

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 700;
	private static final String TITLE_NAME = "Graph Editor";
	
	private JPanel northPanel;
	private JPanel centerPanel;

	private JMenuBar menuBar;
	private JMenu menuFile, menuInfo;
	private JMenuItem mnSave, mnLoad, mnCredits;
	private JToolBar tb;
	private JButton btnNewVertex, btnDelNode, btnNewEdge, btnDelEdge, undo, redo;
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
		this.btnDelNode.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphVertex vertexToDelete = GraphFrame.this.model.getSelectedVertex();
				if (vertexToDelete != null)
					GraphFrame.this.model.perfromOperation(new Operation(Operation.OperationType.REMOVE_VERTEX, 
															vertexToDelete));
				else{
					String error = "Select a vertex!";
					JOptionPane.showMessageDialog(null, error);
				}
				GraphFrame.this.graphPanel.repaint();
			}
		});
		
		this.btnDelEdge.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				//Remove edge
				GraphFrame.this.deleteEdge();
			}
		});
		this.btnNewEdge.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.addEdge();
			}
		});
		this.undo.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.model.getUndoManager().undoOperation();
				GraphFrame.this.redo.setEnabled(true);
				if (GraphFrame.this.model.getUndoManager().stackOperation.isEmpty())
					GraphFrame.this.undo.setEnabled(false);
			}
		});
		this.redo.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.model.getRedoManager().redoOperation();
				GraphFrame.this.undo.setEnabled(true);
				if (GraphFrame.this.model.getRedoManager().stackOperation.isEmpty())
					GraphFrame.this.redo.setEnabled(false);
			}
		});
		
		this.mnSave.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.saveGraph();
			}
		});
	
		this.mnLoad.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.loadGraph();
			}
		});
		
		this.mnCredits.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				String error = "Graph Editor 2016 \n Developed by Corradini Matteo (S3051390) and "
								+ "Berke Atac (S3075168)";
				JOptionPane.showMessageDialog(null, error);
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
		this.menuInfo = new JMenu("?");

		this.mnSave = new JMenuItem("Save Graph");
		this.mnLoad = new JMenuItem("Load Graph");
		this.mnCredits = new JMenuItem("Credits");
		
		this.menuFile.add(this.mnSave);
		this.menuFile.add(this.mnLoad);
		
		this.menuInfo.add(this.mnCredits);
		
		this.menuBar.add(menuFile);
		this.menuBar.add(menuInfo);
	}

	private void addPanel(){
		this.graphPanel = new GraphPanel(this.model);
		this.northPanel = new JPanel (new GridLayout(2, 0, 0, 0));
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
		this.btnDelEdge = new JButton("Delete Edge");
		this.undo = new JButton("Undo");
		this.redo = new JButton("Redo");
		this.getUndo().setEnabled(false);
		this.getRedo().setEnabled(false);
		this.btnDelEdge.setEnabled(this.model.getEdges().size() > 0);
		
		this.getTb().add(this.getBtnNewVertex());
		this.getTb().add(this.getBtnDelNode());
		this.getTb().add(this.getBtnNewEdge());
		this.getTb().add(this.getBtnDelEdge());
		this.getTb().add(this.getUndo());
		this.getTb().add(this.getRedo());
	}
	
	private void drawWholeFrame(){
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.centerPanel, BorderLayout.CENTER);
	}
	
	private void saveGraph(){
		SaveGraphDialog saveDialog = new SaveGraphDialog(this.getModel());
		saveDialog.setVisible(true);
		this.disEnButtons(false);
		saveDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	GraphFrame.this.disEnButtons(true);
		    }
		});
	}
	
	private void loadGraph(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    String[] nameFile = selectedFile.getName().split("\\.");
		    try {
		    	boolean fileCorrect = false;
				if (nameFile[1].equals("txt")){
					this.model = new GraphModel(selectedFile.getAbsolutePath(), new StandardGraphParser());
					fileCorrect = true;
				}
				
				if (nameFile[1].equals("dot")){
					this.model = new GraphModel(selectedFile.getAbsolutePath(), new GraphVizGraphParser());
					fileCorrect = true;
				}
				
				if (nameFile[1].equals("obj")){
					this.model.deSerializeGraph(selectedFile.getAbsolutePath());
					fileCorrect = true;
				}
					
				if (!fileCorrect){
					String error = "Select .txt, .dot or .obj file!";
					JOptionPane.showMessageDialog(null, error);
					return;
				}
			}catch (IOException e) {
				// TODO Auto-generated catch block
				String error = "Problems to open the file!";
				JOptionPane.showMessageDialog(null, error);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				String error = "Parsing file failed!";
				JOptionPane.showMessageDialog(null, error);
				e.printStackTrace();
			}
		}
	}

	private void addVertex(){
		NewGraphVertexDialog vertexDialog = new NewGraphVertexDialog(this.getModel());
		vertexDialog.setVisible(true);
		this.disEnButtons(false);
		vertexDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	GraphFrame.this.disEnButtons(true);
		    	GraphFrame.this.undo.setEnabled(true);
		    	GraphFrame.this.redo.setEnabled(false);
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
		    	GraphFrame.this.disEnButtons(true);
		    	GraphFrame.this.undo.setEnabled(true);
		    	GraphFrame.this.redo.setEnabled(false);
		    	if (GraphFrame.this.model.getEdges().size() > 0)
		    		GraphFrame.this.btnDelEdge.setEnabled(true);
		    }
		});
	}
	
	private void deleteEdge(){
		DelGraphEdgeDialog edgeDialog = new DelGraphEdgeDialog(this.getModel());
		edgeDialog.setVisible(true);
		this.disEnButtons(false);
		edgeDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	GraphFrame.this.disEnButtons(true);
		    	GraphFrame.this.undo.setEnabled(true);
		    	GraphFrame.this.redo.setEnabled(false);
		    	if (GraphFrame.this.model.getEdges().size() == 0)
		    		GraphFrame.this.btnDelEdge.setEnabled(false);
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
	public JButton getBtnDelEdge() {
		return btnDelEdge;
	}
	public void setBtnDelEdge(JButton btnDelEdge) {
		this.btnDelEdge = btnDelEdge;
	}


}