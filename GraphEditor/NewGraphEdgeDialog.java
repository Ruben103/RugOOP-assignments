import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NewGraphEdgeDialog extends JDialog {
	final int WIDTH_WINDOW = 400;
	final int HEIGHT_WINDOW = 400;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JComboBox<String> cmbV1;
	private JComboBox<String> cmbV2;
	
	private JButton btnSubmit;
	private JButton btnExit;
	
	private GraphModel graph;
	
	public NewGraphEdgeDialog(GraphModel _graph){
		this.graph = _graph;
		this.setWindow();
		this.setPanel();
		this.drawFields();
		
		this.btnSubmit = new JButton("Add edge");
		this.btnSubmit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewGraphEdgeDialog.this.addEdge();
				NewGraphEdgeDialog.this.clearTextField();
			}
		});
		
		this.btnExit = new JButton("Exit");
		this.btnExit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewGraphEdgeDialog.this.dispose();
			}
		});
		
		this.cmbV1.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e){
	        	if(e.getStateChange() == ItemEvent.SELECTED) {
	        		NewGraphEdgeDialog.this.updateComboV2(NewGraphEdgeDialog.this.cmbV1.getSelectedItem().toString());
	        	}
	        }
	    });
		
		this.southPanel.add(btnSubmit);
		this.southPanel.add(btnExit);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private void addEdge(){
		String nameV1 = this.cmbV1.getSelectedItem().toString();
		if (nameV1.equals("")){
			String error = "First vertex is not seleceted!";
			JOptionPane.showMessageDialog(null, error);
			return;
		}
		String nameV2 = this.cmbV2.getSelectedItem() == null ? "" : this.cmbV2.getSelectedItem().toString();
		
		if (!nameV2.equals("")){
			GraphVertex v1 = graph.getVertexOfName(nameV1);
			GraphVertex v2 = graph.getVertexOfName(nameV2);
			graph.perfromOperation(new Operation(Operation.OperationType.ADD_EDGE, new GraphEdge(v1, v2)));
		}else{
			//Generate string error
			String error = "Second vertex is not seleceted!";
			JOptionPane.showMessageDialog(null, error);
		}
	}
	
	private void updateComboV2(String nameV1){
		this.cmbV2.setEnabled(true);
		GraphVertex v1 = graph.getVertexOfName(nameV1);
		DefaultComboBoxModel<String> vertexes = new DefaultComboBoxModel<String>();
		ArrayList<GraphVertex> adjVertexes = graph.getAdjVertexes(v1);
		for (GraphVertex vertex : graph.getVertexes()){
			if (!adjVertexes.contains(vertex) && !vertex.getName().equals(nameV1))
				vertexes.addElement(vertex.getName());
		}
		if (vertexes.getSize() == 0)
			this.cmbV2.setEnabled(false);
		this.cmbV2.setModel(vertexes);
	}
	
	private void clearTextField(){
		DefaultComboBoxModel<String> vertexes = new DefaultComboBoxModel<String>();
		for (GraphVertex vertex : graph.getVertexes()){
			if (this.graph.getAdjVertexes(vertex).size() != this.graph.getVertexes().size() - 1)
				vertexes.addElement(vertex.getName());
		}
		
		if (vertexes.getSize() == 0){
			vertexes.addElement("The graph is connected");
			this.cmbV1.setModel(vertexes);
			this.cmbV2.setModel(new DefaultComboBoxModel<String>());
			this.btnSubmit.setEnabled(false);
			return;
		}
		
		this.cmbV1.setModel(vertexes);
		this.cmbV1.setSelectedIndex(0);
		this.updateComboV2(this.cmbV1.getSelectedItem().toString());
	}
	
	private void setPanel(){
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridLayout(2,2,30,30));
		this.centerPanel.setBorder(new EmptyBorder(100, 30, 100, 30));
		this.southPanel = new JPanel();
		this.southPanel.setLayout(new GridLayout(0,2,30,30));
		this.southPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
	}
	
	private void setWindow(){
		this.setTitle("New Edge");
		this.setResizable(false);
		this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void drawFields(){
		JLabel lblVertex1 = new JLabel("Vertex 1:");
		this.centerPanel.add(lblVertex1);
		this.cmbV1 = new JComboBox<String>();
		this.centerPanel.add(this.cmbV1);
		
		JLabel lblVertex2 = new JLabel("Vertex 2:");
		this.centerPanel.add(lblVertex2);
		this.cmbV2 = new JComboBox<String>();
		this.centerPanel.add(this.cmbV2);
		this.clearTextField();
	}
}
