import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class GraphModel extends Observable implements Serializable{
	
	private ArrayList<GraphVertex> vertexes;
	private ArrayList<GraphEdge> edges;
	transient private UndoManager undoManager;
	transient private RedoManager redoManager;

	transient private GraphVertex selectedVertex;

	public GraphModel(){
		this.vertexes = new ArrayList<GraphVertex>();
		this.edges = new ArrayList<GraphEdge>();
		this.undoManager = new UndoManager(this);
		this.redoManager = new RedoManager(this);
	}
	
	public GraphModel(GraphModel _graph){
		this.vertexes = _graph.getVertexes();
		this.edges = _graph.getEdges();
		this.undoManager = _graph.getUndoManager();
		this.redoManager = _graph.getRedoManager();
	}
	
	public GraphModel(String nameFile, GraphParser parser) throws IOException{
		this();
		parser.loadFromFile(nameFile, this);
	}
	
	public void addEdge(GraphEdge edge){
		this.getEdges().add(edge);
		this.sendNotificationToObs("Add edge, from:" + edge.getV1().getName() + " to:"
													 + edge.getV2().getName());
	}

	public void addVertex(GraphVertex vertex){
		this.getVertexes().add(vertex);
		this.sendNotificationToObs("Add vertex, name :" + vertex.getName());
	}
	
	public void removeVertex(GraphVertex vertex){
		String vertexName = vertex.getName();
		for (Iterator<GraphEdge> it = this.getEdges().iterator(); it.hasNext(); ) {
			GraphEdge edgeToRemove = it.next();
			if (edgeToRemove.containsVertex(vertex)){
				String v1Name = edgeToRemove.getV1().getName(), v2Name = edgeToRemove.getV2().getName();
				it.remove();
				this.sendNotificationToObs("Remove edge, from:" + v1Name + " to:"
						 									    + v2Name);
			}
		}
		this.getVertexes().remove(vertex);
		this.sendNotificationToObs("Remove vertex, name :" + vertexName);
	}
	
	public void removeEdge(GraphEdge edge){
		String v1Name = edge.getV1().getName(), v2Name  = edge.getV2().getName();
		this.getEdges().remove(edge);
		this.sendNotificationToObs("Remove edge, from:" + v1Name + " to:"
				 									    + v2Name);
	}
	
	public void perfromOperation(Operation op){
		// TODO Auto-generated method stub
		switch (op.getOperation()){
			case ADD_VERTEX:{
				this.addVertex(op.getVertex());
				break;
			}
			case REMOVE_VERTEX:{		
				for (GraphEdge edge : this.getEdges()){
					if (edge.containsVertex(op.getVertex()))
						op.getEdges().add(edge);
				}
				this.removeVertex(op.getVertex());
				break;
			}
			case ADD_EDGE:{
				this.addEdge(op.getEdges().get(0));
				break;
			}
			case REMOVE_EDGE:{
				this.removeEdge(op.getEdges().get(0));
				break;
			}
		}
		this.getUndoManager().addOperation(op);
		if (!this.getRedoManager().stackOperation.isEmpty())
			this.getRedoManager().flushRedoStack();
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
	
	public void serializeGraph(String nameFile) throws IOException{
	    FileOutputStream fileOut = new FileOutputStream(nameFile);
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(this);
	    out.close();
	    fileOut.close();
	}
	
	public void deSerializeGraph(String nameFile) throws IOException, ClassNotFoundException{
		 FileInputStream fileIn = new FileInputStream(nameFile);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         GraphModel _graph = new GraphModel((GraphModel) in.readObject());
         this.setEdges(_graph.getEdges());
         this.setVertexes(_graph.getVertexes());
         in.close();
         fileIn.close();
	}
	
	public int getIndexEdgeOfVertexes(GraphVertex v1, GraphVertex v2){
		for (GraphEdge edge : this.getEdges()){
			if (edge.containsVertex(v1) && edge.containsVertex(v2))
				return this.getEdges().indexOf(edge);
		}
		return -1;
	}
	
	public void sendNotificationToObs(String message){
		setChanged();
		this.notifyObservers(message);
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
	public void setSelectedVertex(GraphVertex selected){
		this.selectedVertex = selected;
	}
	public GraphVertex getSelectedVertex(){
		return selectedVertex;
	}
}
