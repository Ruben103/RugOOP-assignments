import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GraphEdit{
	
	public static void main(String[] argv){
		GraphModel graph;
		GraphFrame graphFrame;
		try {
			String[] file = argv[argv.length-1].split("\\.");
			
			if (file[1].equals("txt"))
				graph = new GraphModel(argv[argv.length-1], new StandardGraphParser());
			else
				graph = new GraphModel(argv[argv.length-1], new GraphVizGraphParser());

			graphFrame = new GraphFrame(graph);
			graphFrame.setVisible(true);
			
			graph.saveGraph("ex1.txt", new StandardGraphParser());
			graph.saveGraph("out_graphviz.dot", new GraphVizGraphParser());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			graph = null;
			e.printStackTrace();
		}
	}

}
