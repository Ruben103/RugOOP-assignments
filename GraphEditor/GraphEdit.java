import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GraphEdit{
	
	public static void main(String[] argv){
		GraphModel graph = null;
		GraphFrame graphFrame;
		try {
			String[] file = argv[argv.length-1].split("\\.");
			
			if (file[1].equals("txt"))
				graph = new GraphModel(argv[argv.length-1], new StandardGraphParser());
			if (file[1].equals("dot"))
				graph = new GraphModel(argv[argv.length-1], new GraphVizGraphParser());
			if (file[1].equals("obj")){
				graph = new GraphModel();
				graph.deSerializeGraph(argv[argv.length-1]);
			}

			graphFrame = new GraphFrame(graph);
			graphFrame.setVisible(true);
			
			System.out.println("---------------------------");
			for (GraphVertex vertex : graph.getVertexes())
				System.out.println(vertex.getName());
	
			graph.saveGraph("ex1.txt", new StandardGraphParser());
			graph.saveGraph("out_graphviz.dot", new GraphVizGraphParser());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			graph = null;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
