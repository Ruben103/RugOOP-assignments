import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*Simple graph parser, doesn't support the attributes of the nodes*/
public class GraphVizGraphParser extends GraphParser {

	@Override
	public void loadFromFile(String nameFile, GraphModel graph) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader loadFile = new BufferedReader(new FileReader(nameFile));
		String line = loadFile.readLine();
		while (!(line = loadFile.readLine()).equals("}")){
			String[] vertexes = line.split(" ");
			String v1Name = vertexes[0];
			int i = 0;
			while (!vertexes[++i].equals("--"))
				v1Name += " " + vertexes[i];
			
			v1Name = v1Name.replace("\t", "");
			System.out.println(v1Name);
			
			if (!graph.containsVertex(v1Name))
				graph.addVertex(new GraphVertex(v1Name));
			
			String v2Name = new String(vertexes[++i]);
			for (i += 1; i < vertexes.length; i++){
				
				if (!vertexes[i].equals("--")){
					if (vertexes[i].contains(";"))
						v2Name += " " + vertexes[i].replace(";", ""); 
					else{	
						v2Name += v2Name.equals("") ? vertexes[i] : " " +vertexes[i] ;
						continue;
					}
				}
				
				System.out.println(v1Name + " " + v2Name);
				
				if (!graph.containsVertex(v2Name))
					graph.addVertex(new GraphVertex(v2Name));
				GraphVertex v1 = graph.getVertexOfName(v1Name), v2 = graph.getVertexOfName(v2Name);
				System.out.println(v1.getName() + " " + v2.getName());
				if (!graph.containsEdge(v1, v2)){
					graph.addEdge(new GraphEdge(v1, v2));
				}
				v2Name = "";
			}
		}
		loadFile.close();
	}

	@Override
	public void saveGraph(String nameFile, GraphModel graph) throws IOException {
		// TODO Auto-generated method stub
		BufferedWriter graphFile = new BufferedWriter(new FileWriter(nameFile));
		graphFile.write("graph GraphModel {\n");
		
		boolean[] visited = new boolean[graph.getEdges().size()];
		for (int i = 0; i < visited.length; i++)
			visited[i] = false;
		
		for (GraphVertex vertex : graph.getVertexes()){
			boolean vertexFound = false;
			ArrayList<GraphVertex> adjVertexes = graph.getAdjVertexes(vertex);
			for (GraphVertex adjVertex : adjVertexes){
				int indexEdge = graph.getIndexEdgeOfVertexes(vertex, adjVertex);
				if (!visited[indexEdge]){
					if (!vertexFound){
						graphFile.write("\t"+vertex.getName());
						vertexFound = true;
					}
					graphFile.write(" -- " + adjVertex.getName());
					visited[indexEdge] = true;
				}
			}
			if (vertexFound)
				graphFile.write(";\n");
		}
		
		graphFile.write("}");
		graphFile.flush();
		graphFile.close();
	}

}
