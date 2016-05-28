import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LibraryWindow extends JFrame{
	
	private final int NUMBER_BUTTONS = 8;
	/*
	private final int BUTTON_WIDTH = 120;
	private final int BUTTON_HEIGHT = 30;
	private final int TEXTFIELD_WIDTH = 100;
	private final int PADDING_PANEL_NORTH = (WINDOW_WIDTH - 2*PADDING - 5*BUTTON_WIDTH - 4*SPACE_BETWEEN_BUTTONS) / 2;
	private final int PADDING_PANEL_SOUTH = (WINDOW_WIDTH - 2*PADDING - TEXTFIELD_WIDTH + 
												-3*BUTTON_WIDTH - 2*SPACE_BETWEEN_BUTTONS) / 2;
	*/
	
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 350;
	
	private final int WINDOW_PADDING = 30;
	private final int SPACE_BETWEEN_BUTTONS = 30;
	
	
	private String ButtonName[] = {"Report", "Rent", "Return", "Save", "Restore", 
									"New member", "New Book", "IncDay"};		
	
	private JButton buttons[];
	
	private JList<Member> memberJlist;
	private JList<AvaibleMaterial> freeMatJlist;
	private JList<AvaibleMaterial> rentedMatJlist;
	
	JPanel panel;
	
	private JTextField date;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel centerPanel;
	
	private Library lib;
	
	public LibraryWindow(Library _lib){
		lib = new Library(_lib);
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
				rentedMatJlist.setListData(memberJlist.getSelectedValue().getRentMats());
			}
		});
		
		/*Report button*/
		this.buttons[0].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		/*Restore button*/
		this.buttons[4].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
			}
		});
		/*New Member button*/
		this.buttons[5].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
			}
		});
		/*New Book button*/
		this.buttons[6].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
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
		AvaibleMaterial mat = this.rentedMatJlist.getSelectedValue();
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
			c.setTime(date); // Now use today date.
			c.add(Calendar.DATE, 1); // Adding 5 days
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
		
		this.freeMatJlist = new JList<AvaibleMaterial>(this.lib.getFreeMaterial());
		this.centerPanel.add(freeMatJlist);
		
		this.rentedMatJlist = new JList<AvaibleMaterial>();
		this.centerPanel.add(rentedMatJlist);
	}

	private void refreshLists(){
		this.freeMatJlist.setListData(this.lib.getFreeMaterial());
		this.rentedMatJlist.setListData(this.memberJlist.getSelectedValue().getRentMats());
	}
}
