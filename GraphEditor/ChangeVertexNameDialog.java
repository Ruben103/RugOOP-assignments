import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class ChangeVertexNameDialog extends JDialog{
	final int WIDTH_WINDOW = 500;
	final int HEIGHT_WINDOW = 400;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JTextField txtName;
	
	private JButton btnSubmit;
	private JButton btnExit;
	
	private GraphModel graph;
	private GraphVertex vertex;
	private GraphPanel panel;
	
	private String oldName;
	
	public ChangeVertexNameDialog(GraphModel _graph, GraphVertex _vertex, GraphPanel _panel){
		this.panel = _panel;
		this.graph = _graph;
		this.vertex = _vertex;
		this.oldName = _vertex.getName();
		
		this.setWindow();
		this.setPanel();
		this.drawFields();
		
		this.btnSubmit = new JButton("Change Name");
		this.btnSubmit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				String nameVertex = txtName.getText().equals("") ? GraphVertex.DEFAULT_NAME : txtName.getText();
				if (!ChangeVertexNameDialog.this.graph.containsVertex(nameVertex))
					ChangeVertexNameDialog.this.vertex.setName(nameVertex);
				else{
					String error = "Name already used!";
					JOptionPane.showMessageDialog(null, error);
				}
				ChangeVertexNameDialog.this.dispose();
				panel.repaint();
			}
		});
		
		this.btnExit = new JButton("Exit");
		this.btnExit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				ChangeVertexNameDialog.this.dispose();
			}
		});
		
		this.southPanel.add(btnSubmit);
		this.southPanel.add(btnExit);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	
	private void clearTextField(){
		this.txtName.setText(this.oldName);
	}
	
	private void setPanel(){
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridLayout(5,2,30,30));
		this.centerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
		this.southPanel = new JPanel();
		this.southPanel.setLayout(new GridLayout(0,2,30,30));
		this.southPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
	}

	private void setWindow(){
		this.setTitle("New Vertex");
		this.setResizable(false);
		this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void drawFields(){
		JLabel lblName = new JLabel("Change Vertex Name :");
		this.centerPanel.add(lblName);
		this.txtName = new JTextField();
		this.centerPanel.add(this.txtName);
		
		this.clearTextField();
	}
}
