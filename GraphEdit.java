import java.io.IOException;

public class GraphEdit {
	
	public static void main(String[] argv){
		GraphModel graph;
		try {
			graph = new GraphModel(argv[argv.length-1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			graph = null;
			e.printStackTrace();
		}
		try {
			graph.saveGraph("save_" + argv[argv.length-1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
