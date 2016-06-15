import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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

public class NewMaterialDialog extends JDialog {
	final int WIDTH_WINDOW = 300;
	final int HEIGHT_WINDOW = 300;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JTextField txtAuthor;
	private JTextField txtTitle;
	
	private JButton btnSubmit;
	private JButton btnExit;
	
	private Library lib;
	
	public NewMaterialDialog(Library lib){
		this.lib = lib;
		this.setWindow();
		this.setPanel();
		this.drawFields();
		
		this.btnSubmit = new JButton("Add book");
		this.btnSubmit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewMaterialDialog.this.addMaterial();
				NewMaterialDialog.this.clearTextField();
			}
		});
		
		this.btnExit = new JButton("Exit");
		this.btnExit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewMaterialDialog.this.dispose();
			}
		});
		
		this.southPanel.add(btnSubmit);
		this.southPanel.add(btnExit);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private void addMaterial(){
		String author = this.txtAuthor.getText();
		String title = this.txtTitle.getText();
		if (author != "" && title != ""){
			AvailableMaterial newMat = new Book(author, title, this.lib);
			this.lib.addMaterial(newMat);
			JOptionPane.showMessageDialog(null, "Book added, author: " + author + "; title: " + title);
		}else{
			String error = "The fields " + author == "" ? "author" : "" + 
							title == "" ? "title" : "" +
							"are compulsory";
			JOptionPane.showMessageDialog(null, error);
		}
		
	}
	
	private void clearTextField(){
		this.txtAuthor.setText("");
		this.txtTitle.setText("");
	}
	
	private void setPanel(){
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridLayout(2,2,30,30));
		this.centerPanel.setBorder(new EmptyBorder(50, 30, 50, 30));
		this.southPanel = new JPanel();
		this.southPanel.setLayout(new GridLayout(0,2,30,30));
		this.southPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
	}

	private void setWindow(){
		this.setTitle("New Book");
		this.setResizable(false);
		this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void drawFields(){
		JLabel lblAuthor = new JLabel("Author");
		this.centerPanel.add(lblAuthor);
		this.txtAuthor = new JTextField();
		this.centerPanel.add(txtAuthor);
		JLabel lblTitle = new JLabel("Title");
		this.centerPanel.add(lblTitle);
		this.txtTitle = new JTextField();
		this.centerPanel.add(txtTitle);
	}
}
