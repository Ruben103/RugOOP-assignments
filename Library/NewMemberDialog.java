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


public class NewMemberDialog extends JDialog{
	
	final int WIDTH_WINDOW = 400;
	final int HEIGHT_WINDOW = 300;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	
	private JTextField txtSurname;
	private JTextField txtName;
	private JTextField txtAddress;
	
	private JButton btnSubmit;
	private JButton btnExit;
	
	private Library lib;
	
	public NewMemberDialog(Library lib){
		this.lib = lib;
		this.setWindow();
		this.setPanel();
		this.drawFields();
		
		this.btnSubmit = new JButton("Add member");
		this.btnSubmit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewMemberDialog.this.addMember();
				NewMemberDialog.this.clearTextField();
			}
		});
		
		this.btnExit = new JButton("Exit");
		this.btnExit.addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				NewMemberDialog.this.dispose();
			}
		});
		
		this.southPanel.add(btnSubmit);
		this.southPanel.add(btnExit);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private void addMember(){
		String name = this.txtName.getText();
		String surname = this.txtSurname.getText();
		String address = this.txtAddress.getText();
		if (surname != "" && name != "" && address != ""){
			Member newMember = new Member(surname, name, address, this.lib);
			this.lib.addMember(newMember);
			JOptionPane.showMessageDialog(null, "Member added, surname: " + surname + "; name: "+ name + "; address: "+ address);
		}else{
			String error = "The fields " + surname == "" ? "surname" : "" + 
							name == "" ? "name" : "" + 
							address == "" ? "address" : "" + 
							"are compulsory";
			JOptionPane.showMessageDialog(null, error);
		}
	}
	
	private void clearTextField(){
		this.txtSurname.setText("");
		this.txtName.setText("");
		this.txtAddress.setText("");
	}
	
	private void setPanel(){
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridLayout(3, 2, 30, 30));
		this.centerPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		this.southPanel = new JPanel();
		this.southPanel.setLayout(new GridLayout(0,2,30,30));
		this.southPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
	}

	private void setWindow(){
		this.setTitle("New Member");
		this.setResizable(false);
		this.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void drawFields(){
		JLabel lblSurname = new JLabel("Surname");
		this.centerPanel.add(lblSurname);
		txtSurname = new JTextField();
		this.centerPanel.add(txtSurname);
		JLabel lblName = new JLabel("Name");
		this.centerPanel.add(lblName);
		this.txtName = new JTextField();
		this.centerPanel.add(txtName);
		JLabel lblAddress = new JLabel("Address");
		this.centerPanel.add(lblAddress);
		this.txtAddress = new JTextField();
		this.centerPanel.add(txtAddress);
	}
}

