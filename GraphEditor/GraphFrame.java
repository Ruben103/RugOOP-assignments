import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
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

	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 700;
	private static final String TITLE_NAME = "Graph Editor";
	
	private JPanel northPanel;
	private JPanel centerPanel;

	private JMenuBar menuBar;
	private JMenu menuFile, menuInfo;
	private JMenuItem mnSave, mnLoad, mnCredits, mnTips;
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
		    	GraphFrame.this.undo.setEnabled(true);
		    	GraphFrame.this.redo.setEnabled(false);
				GraphFrame.this.graphPanel.repaint();
			}
		});
		
		this.btnDelEdge.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				//Remove edge
				GraphFrame.this.deleteEdge();
				GraphFrame.this.graphPanel.repaint();
			}});
		this.btnNewEdge.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				GraphFrame.this.addEdge();
				GraphFrame.this.graphPanel.repaint();
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
				String msg = "Graph Editor 2016 \n Developed by Corradini Matteo (S3051390) and "
								+ "Berke Atac (S3075168)";
				JOptionPane.showMessageDialog(null, msg);
			}
		});
		this.mnTips.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				String msg = "Keyboard shortcut, when no buttons are selected: \n"
							+ "CTRL+V : create a new vertex;\n"
							+ "CTRL+E : create a new edge;\n"
							+ "CTRL+S : save;\n"
							+ "CTRL+L : load;\n"
							+ "CTRL+U : undo;\n"
							+ "CTRL+Y : redo;\n"
							+ "CTRL+D : remove vertex.\n";
				JOptionPane.showMessageDialog(null, msg);
			}
		});

		this.graphPanel.addKeyListener(new KeyListener() { 
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					GraphFrame.this.addVertex();
					return;
                }
				if ((e.getKeyCode() == KeyEvent.VK_E) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					GraphFrame.this.addEdge();
					return;
                }
				if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					GraphFrame.this.saveGraph();
					return;
                }
				if ((e.getKeyCode() == KeyEvent.VK_L) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					GraphFrame.this.loadGraph();
					return;
                }
				if ((e.getKeyCode() == KeyEvent.VK_U) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					if (!GraphFrame.this.model.getUndoManager().getStackOperation().isEmpty()){
						GraphFrame.this.redo.setEnabled(true);
						if (GraphFrame.this.model.getUndoManager().stackOperation.isEmpty())
							GraphFrame.this.undo.setEnabled(false);
					}
					return;
                }
				if ((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					if (!GraphFrame.this.model.getRedoManager().getStackOperation().isEmpty()){
						GraphFrame.this.model.getRedoManager().redoOperation();
						GraphFrame.this.model.getUndoManager().undoOperation();
						GraphFrame.this.undo.setEnabled(true);
						if (GraphFrame.this.model.getRedoManager().stackOperation.isEmpty())
							GraphFrame.this.redo.setEnabled(false);
					}
                }
				if ((e.getKeyCode() == KeyEvent.VK_D) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					GraphVertex vertexToDelete = GraphFrame.this.model.getSelectedVertex();
					if (vertexToDelete != null){
						GraphFrame.this.model.perfromOperation(new Operation(Operation.OperationType.REMOVE_VERTEX, 
																vertexToDelete));
						GraphFrame.this.undo.setEnabled(true);
				    	GraphFrame.this.redo.setEnabled(false);
						GraphFrame.this.graphPanel.repaint();
					}
					return;
                }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
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
		this.mnTips = new JMenuItem("Tips");
		
		this.menuFile.add(this.mnSave);
		this.menuFile.add(this.mnLoad);
		
		this.menuInfo.add(this.mnCredits);
		this.menuInfo.add(this.mnTips);
		
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
				this.graphPanel.setModel(this.model);
				this.graphPanel.repaint();
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
		vertexDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	GraphFrame.this.undo.setEnabled(true);
		    	GraphFrame.this.redo.setEnabled(false);
		    }
		});
	}
	
	private void addEdge(){
		NewGraphEdgeDialog edgeDialog = new NewGraphEdgeDialog(this.getModel());
		edgeDialog.setVisible(true);
		edgeDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
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
		edgeDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	GraphFrame.this.undo.setEnabled(true);
		    	GraphFrame.this.redo.setEnabled(false);
		    	if (GraphFrame.this.model.getEdges().size() == 0)
		    		GraphFrame.this.btnDelEdge.setEnabled(false);
		    }
		});
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