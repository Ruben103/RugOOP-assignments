import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
	
	private JList<Member> member_jlist;
	private JList<AvaibleMaterial> avaible_mat_jlist;
	private JList<AvaibleMaterial> rented_mat_jlist;
	
	JPanel panel;
	
	private JTextField date;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel centerPanel;
	
	
	public LibraryWindow(Library lib){
		
		this.setWindowProperites();
		
		this.setPanels();
		this.setButtons();
		this.setTextField();
		
		this.drawPanel();
	}
	
	
	private void setWindowProperites(){
		this.setTitle("BSM");
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void setPanels(){
		this.panel = new JPanel (new BorderLayout()) ;
		panel.setBorder(new EmptyBorder(WINDOW_PADDING/2, 2*WINDOW_PADDING, WINDOW_PADDING/2, 2*WINDOW_PADDING));
		this.northPanel = new JPanel (new GridLayout(0, 5, SPACE_BETWEEN_BUTTONS, 0));
		this.southPanel = new JPanel (new GridLayout(0, 4, SPACE_BETWEEN_BUTTONS, 0));
		this.centerPanel = new JPanel (new GridLayout(0, 3, SPACE_BETWEEN_BUTTONS, 0));
		centerPanel.setBorder(new EmptyBorder(WINDOW_PADDING, 0, WINDOW_PADDING, 0));
	}
	
	private void setButtons(){
		this.buttons = new JButton[NUMBER_BUTTONS];
		for (int i = 0; i < NUMBER_BUTTONS; i++){
			this.buttons[i] = new JButton(ButtonName[i]);
			
			if (i < 5)
				northPanel.add(buttons[i]);
			else
				this.southPanel.add(buttons[i]);
		}
	}
	
	private void setTextField(){
		Date string_date = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("dd-yyyy-mm");
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

}
