import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StandardGraphParser extends GraphParser {

	@Override
	public void saveGraph(String nameFile, GraphModel graph) throws IOException{
		// TODO Auto-generated method stub
		BufferedWriter graphFile = new BufferedWriter(new FileWriter(nameFile));
		graphFile.write(graph.getVertexes().size()+" "+graph.getEdges().size()+"\n");
		for (GraphVertex vertex : graph.getVertexes()){
			graphFile.write((int) vertex.getRect().getX() + " " + (int) vertex.getRect().getY() + " " +
							(int) vertex.getRect().getHeight() + " " + (int) vertex.getRect().getWidth() + " " + 
							vertex.getName() + "\n");
		}
		for (GraphEdge edge : graph.getEdges()){
			graphFile.write(graph.getIndexOfVertex(edge.getV1()) + " " + graph.getIndexOfVertex(edge.getV2())+"\n");
		}
		graphFile.flush();
		graphFile.close();
	}

	@Override
	public void loadFromFile(String nameFile, GraphModel graph) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader loadFile = new BufferedReader(new FileReader(nameFile));
		String line = loadFile.readLine();
		String[] infoLine = line.split(" ");
		int sizeVertexes = Integer.parseInt(infoLine[0]);;
		int sizeEdges = Integer.parseInt(infoLine[1]);
		
		for (int i = 0; i < sizeVertexes; i++){
			line = loadFile.readLine();
			infoLine = line.split(" ");
			String nameVertex = "";
			for (int j = 4; j < infoLine.length; j++){
				if (j != 4) nameVertex += " ";
				nameVertex += infoLine[j];
			}
			graph.addVertex(new GraphVertex(nameVertex,
										   new Rectangle(Integer.parseInt(infoLine[0]),
												   		 Integer.parseInt(infoLine[1]),
												   		 Integer.parseInt(infoLine[3]),
												   		 Integer.parseInt(infoLine[2])))
			);
		}
		for (int i = 0; i < sizeEdges; i++){
			line = loadFile.readLine();
			infoLine = line.split(" ");
			graph.addEdge(new GraphEdge(graph.getVertexes().get(Integer.parseInt(infoLine[0])), 
										graph.getVertexes().get(Integer.parseInt(infoLine[1]))));
		}
		loadFile.close();
	}

}
