import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GraphModel {
	
	private ArrayList<GraphVertex> vertexes;
	private ArrayList<GraphEdge> edges;
	private UndoManager undoManager;
	private RedoManager redoManager;
	
	public GraphModel(){
		this.vertexes = new ArrayList<GraphVertex>();
		this.edges = new ArrayList<GraphEdge>();
		this.undoManager = new UndoManager(this);
		this.redoManager = new RedoManager(this);
	}
	
	public GraphModel(String nameFile) throws IOException{
		this();
		this.loadFromFile(nameFile);
	}
	
	public void addEdge(GraphEdge edge){
		this.getEdges().add(edge);
	}

	public void addVertex(GraphVertex vertex){
		this.getVertexes().add(vertex);
	}
	
	public void removeVertex(GraphVertex vertex){
		for (GraphEdge edge : this.getEdges()){
			if (edge.containsVertex(vertex))
				this.getEdges().remove(edge);
		}
		this.getVertexes().remove(vertex);
	}
	
	public void removeEdge(GraphEdge edge){
		this.getEdges().remove(edge);
	}
	
	public void perfromOperation(Operation op){
		// TODO Auto-generated method stub
		switch (op.getOperation()){
			case ADD_VERTEX:{
				this.addVertex(op.getVertex());
			}
			case REMOVE_VERTEX:{
				for (GraphEdge edge : this.getEdges()){
					if (edge.containsVertex(op.getVertex()))
						op.getEdges().add(edge);
				}
				this.removeVertex(op.getVertex());
			}
			case ADD_EDGE:{
				this.addEdge(op.getEdges().get(0));
			}
			case REMOVE_EDGE:{
				this.removeEdge(op.getEdges().get(0));
			}
		}
		this.getUndoManager().addOperation(op);
	}
	
	public void saveGraph(String nameFile) throws IOException{
			BufferedWriter graphFile = new BufferedWriter(new FileWriter(nameFile));
			graphFile.write(this.getVertexes().size()+" "+this.getEdges().size()+"\n");
			for (GraphVertex vertex : this.getVertexes()){
				graphFile.write((int) vertex.getRect().getX() + " " + (int) vertex.getRect().getY() + " " +
								(int) vertex.getRect().getHeight() + " " + (int) vertex.getRect().getWidth() + " " + 
								vertex.getName() + "\n");
			}
			for (GraphEdge edge : this.getEdges()){
				graphFile.write(this.getIndexOfVertex(edge.getV1()) + " " + this.getIndexOfVertex(edge.getV2())+"\n");
			}
			graphFile.close();
	}
	
	private void loadFromFile(String nameFile) throws IOException{
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
				this.addVertex(new GraphVertex(nameVertex,
											   new Rectangle(Integer.parseInt(infoLine[0]),
													   		 Integer.parseInt(infoLine[1]),
													   		 Integer.parseInt(infoLine[3]),
													   		 Integer.parseInt(infoLine[2])))
				);
			}
			for (int i = 0; i < sizeEdges; i++){
				line = loadFile.readLine();
				infoLine = line.split(" ");
				this.addEdge(new GraphEdge(this.getVertexes().get(Integer.parseInt(infoLine[0])), 
											this.getVertexes().get(Integer.parseInt(infoLine[1]))));
			}
			loadFile.close();
	}
	
	/*private GraphVertex getVertex(String vertexName){
		for (GraphVertex vertex : this.getVertexes()){
			if (vertex.getName().equals(vertexName))
				return vertex;
		}
		return null;
	}*/
	
	
	private int getIndexOfVertex(GraphVertex vertex){
		for (int i = 0; i < this.getVertexes().size(); i++){
			if (this.getVertexes().get(i).getName().equals(vertex.getName()))
				return i;
		}
		return -1;
	}

	/*AUTOgenerate setters and getters*/
	public ArrayList<GraphVertex> getVertexes() {
		return vertexes;
	}
	public void setVertexes(ArrayList<GraphVertex> vertexes) {
		this.vertexes = vertexes;
	}
	public ArrayList<GraphEdge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<GraphEdge> edges) {
		this.edges = edges;
	}
	public UndoManager getUndoManager() {
		return undoManager;
	}
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}
	public RedoManager getRedoManager() {
		return redoManager;
	}
	public void setRedoManager(RedoManager redoManager) {
		this.redoManager = redoManager;
	}
	

}
