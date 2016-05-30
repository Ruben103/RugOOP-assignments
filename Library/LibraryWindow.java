import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LibraryWindow extends JFrame{
	
	private final int NUMBER_BUTTONS = 8;
	
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 350;
	
	private final int WINDOW_PADDING = 30;
	private final int SPACE_BETWEEN_BUTTONS = 30;
	
	
	private String ButtonName[] = {"Report", "Rent", "Return", "Save", "Restore", 
									"New member", "New Book", "IncDay"};		
	
	private JButton buttons[];
	
	private JList<Member> memberJlist;
	private JList<AvailableMaterial> freeMatJlist;
	private JList<AvailableMaterial> rentedMatJlist;
	
	JPanel panel;
	
	private JTextField date;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel centerPanel;
	
	private Library lib;
	
	public LibraryWindow(Library _lib){
		this.lib = new Library(_lib);
		this.setWindowProperites();
		
		this.drawPanels();
		this.drawButtons();
		this.drawTextField();
		
		/*Add scrollbar*/
		this.drawLists(lib);
		
		/*Show rented book by clicked member*/
		this.memberJlist.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent event){
				Member selectedMember = LibraryWindow.this.memberJlist.getSelectedValue();
				if (selectedMember != null)
					LibraryWindow.this.rentedMatJlist.setListData(selectedMember.getRentMats());
			}
		});
		
		/*Report button*/
		this.buttons[0].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.printReport();
			}
		});
		/*Rent button*/
		this.buttons[1].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.makeRent();
			}
		});
		/*Return button*/
		this.buttons[2].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.makeReturn();
			}
		});
		/*Save button*/
		this.buttons[3].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.saveLibrary();
			}
		});
		/*Restore button*/
		this.buttons[4].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.restoreLibrary();
			}
		});
		/*New Member button*/
		this.buttons[5].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.addNewMember();
			}
		});
		/*New Book button*/
		this.buttons[6].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.addNewBook();
			}
		});
		
		/*IncDay button*/
		this.buttons[7].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				LibraryWindow.this.incDate();
			}
		});
		
		this.drawPanel();
	}

	private void restoreLibrary(){
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("lib.obj");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.lib = (Library) in.readObject();
	         in.close();
	         fileIn.close();
	         this.refreshLists();
	         JOptionPane.showMessageDialog(null, "Library restored from lib.obj");
	      }catch(IOException i)
	      {
	    	  System.out.println(i);
	    	  JOptionPane.showMessageDialog(null, "Something went wrong...");
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
	    	  System.out.println(e);
	    	  JOptionPane.showMessageDialog(null, "Something went wrong...");
		}
	}
	
	private void saveLibrary(){
	    try{
	         FileOutputStream fileOut =
	         new FileOutputStream("lib.obj");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.lib);
	         out.close();
	         fileOut.close();
	         JOptionPane.showMessageDialog(null, "Library saved in lib.obj");
	     }catch(IOException i)
	     {
	    	 System.out.println(i);
	    	JOptionPane.showMessageDialog(null, "Something went wrong...");
	     }
	}
	
	private void addNewBook(){
		NewMaterialDialog matDialog = new NewMaterialDialog(this.lib);
		matDialog.setVisible(true);
		this.disableButtons();
		matDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	LibraryWindow.this.refreshLists();
		    	LibraryWindow.this.enableButtons();
		    }
		});
	}
	
	private void addNewMember(){
		NewMemberDialog memberDialog = new NewMemberDialog(this.lib);
		memberDialog.setVisible(true);
		this.disableButtons();
		memberDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	LibraryWindow.this.refreshLists();
		    	LibraryWindow.this.enableButtons();
		    }
		});
	}
	
	private void printReport(){
		String report = "Members:\n";
		for (Member mem : this.lib.getMembers()){
			report += mem+"\n";
		}
		report += "\nAll material:\n";
		for (AvailableMaterial mat : this.lib.getMaterials()){
			report += mat+"\n";
		}
		report += "\nAvaible material:\n";
		for (AvailableMaterial mat : this.lib.getFreeMaterial()){
			report += mat+"\n";
		}
		report += "\nRenting situation:\n";
		for (Member mem : this.lib.getMembers()){
			for (RentData rent : mem.getRents())
				report += rent+"\n";
		}
		this.disableButtons();
		JOptionPane.showMessageDialog(this, report, "Report", JOptionPane.PLAIN_MESSAGE);
		this.enableButtons();
	}
	
	private void makeRent(){
		if (this.memberJlist.getSelectedValue() != null && this.freeMatJlist.getSelectedValue() != null){
			if (this.lib.rentingMaterial(this.memberJlist.getSelectedValue(), 
										 this.freeMatJlist.getSelectedValue(), 
										 this.date.getText())){
				this.refreshLists();
			}
		}
	}
	
	private void makeReturn(){
		Member mem = this.memberJlist.getSelectedValue();
		AvailableMaterial mat = this.rentedMatJlist.getSelectedValue();
		if (mem != null && mat  != null){
			RentData rentToRemove = mat.getRent();
			if (rentToRemove != null){
				this.lib.returnMaterial(rentToRemove);
				this.refreshLists();
			}
		}
	}
	
	private void incDate(){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = format.parse(this.date.getText());
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			this.date.setText(format.format(c.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void setWindowProperites(){
		this.setTitle("BSM");
		this.setResizable(false);
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void drawPanels(){
		this.panel = new JPanel (new BorderLayout()) ;
		panel.setBorder(new EmptyBorder(WINDOW_PADDING/2, 2*WINDOW_PADDING, WINDOW_PADDING/2, 2*WINDOW_PADDING));
		this.northPanel = new JPanel (new GridLayout(0, 5, SPACE_BETWEEN_BUTTONS, 0));
		this.southPanel = new JPanel (new GridLayout(0, 4, SPACE_BETWEEN_BUTTONS, 0));
		this.centerPanel = new JPanel (new GridLayout(0, 3, SPACE_BETWEEN_BUTTONS, 0));
		centerPanel.setBorder(new EmptyBorder(WINDOW_PADDING, 0, WINDOW_PADDING, 0));
	}
	
	private void drawButtons(){
		this.buttons = new JButton[NUMBER_BUTTONS];
		for (int i = 0; i < NUMBER_BUTTONS; i++){
			this.buttons[i] = new JButton(ButtonName[i]);
			if (i < 5) 	northPanel.add(buttons[i]);
			else 		this.southPanel.add(buttons[i]);
		}
	}
	
	private void drawTextField(){
		Date date = new Date();
		System.out.println(date.toString());
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		this.date = new JTextField(dt.format(date));
		this.date.setHorizontalAlignment(JTextField.CENTER);
		this.date.setEditable(false);
		this.southPanel.add(this.date);
	}
	
	private void drawPanel(){
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(northPanel, BorderLayout.NORTH);
		panel.add(southPanel, BorderLayout.SOUTH);
		this.add(panel);
	}
	
	private void drawLists(Library lib){
		this.memberJlist = new JList<Member>(lib.getMembers());
		this.centerPanel.add(memberJlist);
		
		this.freeMatJlist = new JList<AvailableMaterial>(this.lib.getFreeMaterial());
		this.centerPanel.add(freeMatJlist);
		
		this.rentedMatJlist = new JList<AvailableMaterial>();
		this.centerPanel.add(rentedMatJlist);
	}

	public void refreshLists(){
		this.freeMatJlist.setListData(this.lib.getFreeMaterial());
		if (this.memberJlist.getSelectedValue() != null)
			this.rentedMatJlist.setListData(this.memberJlist.getSelectedValue().getRentMats());
		this.memberJlist.setListData(this.lib.getMembers());
	}
	
	private void disableButtons(){
		for (JButton button : this.buttons)
			button.setEnabled(false);
	}
	
	private void enableButtons(){
		for (JButton button : this.buttons)
			button.setEnabled(true);
	}
	
}
