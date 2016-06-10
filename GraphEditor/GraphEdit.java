import java.io.IOException;

public class GraphEdit {
	
	public static void main(String[] argv){
		GraphModel graph;
		try {
			String[] file = argv[argv.length-1].split("\\.");
			if (file[1].equals("txt"))
				graph = new GraphModel(argv[argv.length-1], new StandardGraphParser());
			else
				graph = new GraphModel(argv[argv.length-1], new GraphVizGraphParser());
			System.out.println("Edges:");
			for (GraphEdge edge : graph.getEdges()){
				System.out.println(edge);
			}
			System.out.println("Vertexes:");
			for (GraphVertex vertex : graph.getVertexes()){
				System.out.println(vertex.getName());
			}
			
			graph.saveGraph("ex1.txt", new StandardGraphParser());
			graph.saveGraph("out_graphviz.dot", new GraphVizGraphParser());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			graph = null;
			e.printStackTrace();
		}
	}

}
