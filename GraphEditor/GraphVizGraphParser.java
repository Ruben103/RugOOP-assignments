import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GraphVizGraphParser extends GraphParser {

	@Override
	public void loadFromFile(String nameFile, GraphModel graph) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader loadFile = new BufferedReader(new FileReader(nameFile));
		String line = loadFile.readLine();
		while (!(line = loadFile.readLine()).equals("}")){
			String[] vertexes = line.split(" -- ");
			String v1Name = vertexes[0];
			v1Name = v1Name.replace("\t", "");
			
			if (v1Name.contains(";")){
				addVertex(vertexes[0].replace(";", ""), graph);
				continue;
			}
			
			GraphVertex v1 = graph.getVertexOfName(addVertex(vertexes[0].replace("\t", ""), graph));
			for (int i = 1; i < vertexes.length; i++){
				GraphVertex v2 = graph.getVertexOfName(addVertex(vertexes[i].replace(";", "").replace("\t", ""), graph));
				graph.addEdge(new GraphEdge(v1, v2));
			}
				
		}
		loadFile.close();
	}

	@Override
	public void saveGraph(String nameFile, GraphModel graph) throws IOException {
		// TODO Auto-generated method stub
		BufferedWriter graphFile = new BufferedWriter(new FileWriter(nameFile));
		graphFile.write("graph GraphModel {\n");
		
		boolean[] edge_visited = new boolean[graph.getEdges().size()];
		boolean[] vertex_visited = new boolean[graph.getVertexes().size()];
		
		for (int i = 0; i < edge_visited.length; i++)
			edge_visited[i] = false;
		for (int i = 0; i < vertex_visited.length; i++)
			vertex_visited[i] = false;
		
		for (GraphVertex vertex : graph.getVertexes()){
			boolean vertexFound = false;
			ArrayList<GraphVertex> adjVertexes = graph.getAdjVertexes(vertex);
			if (adjVertexes.isEmpty()){
				vertex_visited[graph.getIndexOfVertex(vertex)] = true;
				graphFile.write("\t"+
								vertex.getName()+" [x="+ vertex.getRect().x + ",y=" + vertex.getRect().y
													+",width=" + vertex.getRect().width+ ",height="+ vertex.getRect().height
													+ "]"+ ";\n");
				continue;
			}
			for (GraphVertex adjVertex : adjVertexes){
				int indexEdge = graph.getIndexEdgeOfVertexes(vertex, adjVertex);
				if (!edge_visited[indexEdge]){
					if (!vertexFound){
						graphFile.write("\t"+vertex.getName());
						if (!vertex_visited[graph.getIndexOfVertex(vertex)]){
							graphFile.write(" [x="+ vertex.getRect().x + ",y=" + vertex.getRect().y
											+",width=" + vertex.getRect().width+ ",height="+ vertex.getRect().height + "]");
							vertex_visited[graph.getIndexOfVertex(vertex)] = true;
						}
					}
					vertexFound = true;
					graphFile.write(" -- " + adjVertex.getName());
					if (!vertex_visited[graph.getIndexOfVertex(adjVertex)]){
						graphFile.write(" [x="+ adjVertex.getRect().x + ",y=" + adjVertex.getRect().y
										+",width=" + adjVertex.getRect().width+ ",height="+ adjVertex.getRect().height
										+ "]");
						vertex_visited[graph.getIndexOfVertex(adjVertex)] = true;
					}
					edge_visited[indexEdge] = true;
				}
			}
			if (vertexFound) graphFile.write(";\n");
		}
		
		graphFile.write("}");
		graphFile.flush();
		graphFile.close();
	}

	
	private String addVertex(String vertexString, GraphModel graph){
		if (!vertexString.contains("[")){
			if (!graph.containsVertex(vertexString)) {
				graph.addVertex(new GraphVertex(vertexString));
			}
			return vertexString;
		}
		vertexString = vertexString.replace("]", "");
		String[] vertexInfo = vertexString.split(" \\[");
		GraphVertex vertexToAdd = new GraphVertex(vertexInfo[0]);
		
		vertexInfo = vertexInfo[1].split(",");
		String x = "", y = "", width = "", height = "";
		for (int i = 0; i < vertexInfo.length; i++){
			String[] attribute = vertexInfo[i].split("=");
			if (attribute[0].equals("x")){
				x = attribute[1];
				continue;
			}
			if (attribute[0].equals("y")){
				y = attribute[1];
				continue;
			}
			if (attribute[0].equals("width")){
				width = attribute[1];
				continue;
			}
			if (attribute[0].equals("height")){
				height = attribute[1];
				continue;
			}
		}
		vertexToAdd.setRect(new Rectangle(x.equals("") ? 0 : Integer.parseInt(x),
										  y.equals("") ? 0 : Integer.parseInt(y),
										  width.equals("") ? GraphVertex.DEFAULT_WIDTH : Integer.parseInt(width),
										  height.equals("") ? GraphVertex.DEFAULT_HEIGHT : Integer.parseInt(height))
							);
		graph.addVertex(vertexToAdd);
		return vertexToAdd.getName();
	}
}
