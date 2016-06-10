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
	
	public GraphModel(String nameFile, GraphParser parser) throws IOException{
		this();
		parser.loadFromFile(nameFile, this);
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
	
	public boolean containsVertex(String vertexName){
		for (GraphVertex vertex : this.getVertexes()){
			if (vertex.getName().equals(vertexName))
				return true;
		}
		return false;
	}
	
	public boolean containsEdge(GraphVertex v1, GraphVertex v2){
		for (GraphEdge edges : this.getEdges()){
			String v1Name = edges.getV1().getName(), v2Name = edges.getV2().getName();
			if (v1Name.equals(v1.getName()) && v2Name.equals(v2.getName()))
				return true;
		}
		return false;
	}

	public int getIndexOfVertex(GraphVertex vertex){
		for (int i = 0; i < this.getVertexes().size(); i++){
			if (this.getVertexes().get(i).getName().equals(vertex.getName()))
				return i;
		}
		return -1;
	}
	
	public GraphVertex getVertexOfName(String vertexName){
		for (GraphVertex vertex : this.getVertexes()){
			if (vertex.getName().equals(vertexName))
				return vertex;
		}
		return null;
	}
	
	public ArrayList<GraphVertex> getAdjVertexes(GraphVertex vertex){
		ArrayList<GraphVertex> adj = new ArrayList<GraphVertex>();
		for (GraphEdge edge : this.getEdges()){
			if (edge.containsVertex(vertex)){
				if (edge.getV1().getName().equals(vertex.getName()))
					adj.add(edge.getV2());
				else
					adj.add(edge.getV1());
			}
		}
		return adj;
	}

	public void saveGraph(String nameFile, GraphParser parser) throws IOException{
		parser.saveGraph(nameFile, this);
	}
	
	public int getIndexEdgeOfVertexes(GraphVertex v1, GraphVertex v2){
		for (GraphEdge edge : this.getEdges()){
			if (edge.containsVertex(v1) && edge.containsVertex(v2))
				return this.getEdges().indexOf(edge);
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
