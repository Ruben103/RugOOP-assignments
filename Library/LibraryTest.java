import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class LibraryTest implements Observer{
	
	 public static void main (String[] argv){
		
		Library lib = new Library();
		lib.addObserver(new LibraryTest());
		
		lib.addMaterial(new Book("S. King", "It", lib));
		lib.addMaterial(new Book("E. L. Master", "Spoon River", lib));
		lib.addMember(new Member("Berke", "Atac", "Van Houtenlaan 27", lib));
		lib.addMember(new Member("Corradini", "Matteo", "Van Houtenlaan 27", lib));
		
		lib.rentingMaterial(lib.getMembers().get(1), lib.getMaterials().get(1), new Date());
		
		LibraryWindow window = new LibraryWindow(lib);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@Override
	public void update(Observable lib, Object arg1) {
		// TODO Auto-generated method stub
		System.out.println(arg1);
	}

}
