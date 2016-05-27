import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
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
		
		/*Rent button*/
		this.buttons[1].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				makeRent();
			}
		});
		
		/*Return button*/
		this.buttons[2].addActionListener(new ActionListener() { 
			@Override	
			public void actionPerformed(ActionEvent e) {
				makeReturn();
			}
		});
		
		this.drawPanel();
	}
	
	
	private void makeRent(){
		if (this.memberJlist.getSelectedValue() != null && this.freeMatJlist.getSelectedValue() != null){
			if (this.lib.rentingMaterial(this.memberJlist.getSelectedValue(), 
										 this.freeMatJlist.getSelectedValue(), 
										 this. date.getText())){
				this.freeMatJlist.setListData(this.lib.getFreeMaterial());
				this.rentedMatJlist.setListData(this.memberJlist.getSelectedValue().getRentMats());
			}
		}
	}
	
	private void makeReturn(){
		if (this.memberJlist.getSelectedValue() != null && this.rentedMatJlist.getSelectedValue() != null){
			RentData rentToRemove = this.lib.returnRent(this.memberJlist.getSelectedValue(), 
														this.rentedMatJlist.getSelectedValue());
			if (rentToRemove != null){
				this.lib.returnMaterial(rentToRemove);
				this.freeMatJlist.setListData(this.lib.getFreeMaterial());
				this.rentedMatJlist.setListData(this.memberJlist.getSelectedValue().getRentMats());
			}
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
		Date string_date = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
		this.date = new JTextField(dt.format(string_date));
		this.date.setHorizontalAlignment(JTextField.CENTER);
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

}
