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

public class NewGraphVertexDialog extends JDialog {
	final int WIDTH_WINDOW = 500;
	final int HEIGHT_WINDOW = 400;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JTextField txtName;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtWidth;
	private JTextField txtHeight;
	
	private JButton btnSubmit;
	private JButton btnExit;
	
	private GraphModel graph;
	
	public NewGraphVertexDialog(GraphModel _graph){
		this.graph = _graph;
		this.setWindow();
		this.setPanel();
		this.drawFields();
		
		this.btnSubmit = new JButton("Add vertex");
		this.btnSubmit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewGraphVertexDialog.this.addVertex();
				NewGraphVertexDialog.this.clearTextField();
			}
		});
		
		this.btnExit = new JButton("Exit");
		this.btnExit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewGraphVertexDialog.this.dispose();
			}
		});
		
		this.southPanel.add(btnSubmit);
		this.southPanel.add(btnExit);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private void addVertex(){
		String name = this.txtName.getText();
		int height = this.txtHeight.getText().equals("")? 0 : Integer.parseInt(this.txtHeight.getText());
		int width = this.txtWidth.getText().equals("") ? 0 : Integer.parseInt(this.txtWidth.getText());
		int x = this.txtX.getText().equals("") ? 0 : Integer.parseInt(this.txtX.getText());
		int y = this.txtY.getText().equals("") ? 0 : Integer.parseInt(this.txtY.getText());
		
		if (y >= 0 && y< GraphFrame.WINDOW_HEIGHT && x >= 0 && x < GraphFrame.WINDOW_WIDTH
				   && width >= 0 && height >= 0 && graph.getVertexOfName(name) == null){
			width = width != 0 ? width : GraphVertex.DEFAULT_WIDTH;
			height = height != 0 ? height : GraphVertex.DEFAULT_HEIGHT;
			name = name.equals("") ? GraphVertex.DEFAULT_NAME : name;
			graph.perfromOperation(new Operation(Operation.OperationType.ADD_VERTEX, 
												 new GraphVertex(name, new Rectangle(x, y, width, height))));
		}else{
			String error =  "Invalid: " + (x < 0 || x > GraphFrame.WINDOW_WIDTH ? "\nValue of X has to be between 0 and " 
							+ GraphFrame.WINDOW_WIDTH : "") + 
							(y < 0 || y > GraphFrame.WINDOW_HEIGHT ? "\nValue of Y has to be between 0 and " 
							+ GraphFrame.WINDOW_HEIGHT : "") + 
							(width < 0 ? "\nwidth < 0" : "") +
							(height < 0 ? "\nheight < 0" : "") +
							(graph.getIndexOfVertex(graph.getVertexOfName(name)) < 0 ? "\nname already used" : "");
			JOptionPane.showMessageDialog(null, error);
		}	
	}
	
	private void clearTextField(){
		this.txtX.setText("0");
		this.txtY.setText("0");
		this.txtName.setText(GraphVertex.DEFAULT_NAME);
		this.txtWidth.setText(""+GraphVertex.DEFAULT_WIDTH);
		this.txtHeight.setText(""+GraphVertex.DEFAULT_HEIGHT);
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
		JLabel lblName = new JLabel("Vertex Name :");
		this.centerPanel.add(lblName);
		this.txtName = new JTextField();
		this.centerPanel.add(this.txtName);
		
		JLabel lblX = new JLabel("Coordinate X:");
		this.centerPanel.add(lblX);
		this.txtX = new JTextField();
		this.centerPanel.add(this.txtX);
		
		JLabel lblY = new JLabel("Coordinate Y:");
		this.centerPanel.add(lblY);
		this.txtY = new JTextField();
		this.centerPanel.add(this.txtY);
		
		JLabel lblWidth = new JLabel("Vertex Width:");
		this.centerPanel.add(lblWidth);
		this.txtWidth = new JTextField();
		this.centerPanel.add(this.txtWidth);
		
		JLabel lblHeight = new JLabel("Vertex Height:");
		this.centerPanel.add(lblHeight);
		this.txtHeight = new JTextField();
		this.centerPanel.add(this.txtHeight);
		
		this.clearTextField();
	}
}
