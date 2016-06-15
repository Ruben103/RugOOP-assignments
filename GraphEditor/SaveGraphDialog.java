import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SaveGraphDialog extends JDialog {
	final int WIDTH_WINDOW = 400;
	final int HEIGHT_WINDOW = 400;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JComboBox<String> cmbFormat;
	private JTextField txtNameFile;
	
	private JButton btnSubmit;
	private JButton btnExit;
	
	private GraphModel graph;
	
	public SaveGraphDialog(GraphModel _graph){
		this.graph = _graph;
		this.setWindow();
		this.setPanel();
		this.drawFields();
		
		this.btnSubmit = new JButton("Save");
		this.btnSubmit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				SaveGraphDialog.this.saveGraph();
			}
		});
		
		this.btnExit = new JButton("Exit");
		this.btnExit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				SaveGraphDialog.this.dispose();
			}
		});
		
		this.southPanel.add(btnSubmit);
		this.southPanel.add(btnExit);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private void saveGraph(){
		String format = this.cmbFormat.getSelectedItem().toString();
		String nameFile = this.txtNameFile.getText();
		if (!nameFile.equals("")){
			try {
				if (format.equals("Standard")){
					nameFile += ".txt";
					this.graph.saveGraph(nameFile, new StandardGraphParser());
				}
				if (format.equals("GraphViz")){
					nameFile += ".dot";
					this.graph.saveGraph(nameFile, new GraphVizGraphParser());
				}
				if (format.equals("Serialized")){
					nameFile += ".obj";
					this.graph.serializeGraph(nameFile);
				}
				String msg = "Graph is saved!";
				JOptionPane.showMessageDialog(null, msg);
				this.dispose();
			}catch (IOException e) {
					String error = "Unable to save the graph!";
					JOptionPane.showMessageDialog(null, error);
					e.printStackTrace();
				}
		}
		else{
			String error = "File name is empty!";
			JOptionPane.showMessageDialog(null, error);
		}
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
		this.setTitle("New Vertex");
		this.setResizable(false);
		this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void drawFields(){
		JLabel lblVertex1 = new JLabel("File name:");
		this.centerPanel.add(lblVertex1);
		this.txtNameFile = new JTextField("name file");
		this.centerPanel.add(this.txtNameFile);
		
		JLabel lblVertex2 = new JLabel("Format:");
		this.centerPanel.add(lblVertex2);
		this.cmbFormat = new JComboBox<String>();
		DefaultComboBoxModel<String> formats = new DefaultComboBoxModel<String>();
		formats.addElement("Standard");
		formats.addElement("GraphViz");
		formats.addElement("Serialized");
		this.cmbFormat.setModel(formats);
		this.centerPanel.add(this.cmbFormat);
	}
}
