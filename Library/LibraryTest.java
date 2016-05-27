import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class LibraryTest implements Observer{
	
	 public static void main (String[] argv){
		
		Library lib = new Library();
		lib.addObserver(new LibraryTest());
		
		lib.addMaterial(new Book("Stephenk King", "It", lib));
		lib.addMember(new Member("Berke", "Atac", "Van Houtenlaan 27", lib));
		
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
