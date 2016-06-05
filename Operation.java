import java.util.ArrayList;

public class Operation{
	
	public enum OperationType {ADD_VERTEX, REMOVE_VERTEX, ADD_EDGE, REMOVE_EDGE};
	
	private OperationType operation;
	private ArrayList<GraphEdge> edges;
	private GraphVertex vertex;
	
	public Operation(OperationType op, GraphVertex vertex){
		this.operation = op;
		this.edges = new ArrayList<GraphEdge>();
		this.vertex = vertex;
	}
	
	public Operation(OperationType op, GraphEdge edge){
		this.operation = op;
		this.vertex = null;
		this.edges = new ArrayList<GraphEdge>();
		this.getEdges().add(edge);
	}
		
	public OperationType getOperation() {
		return operation;
	}
	public void setOperation(OperationType operation) {
		this.operation = operation;
	}
	public ArrayList<GraphEdge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<GraphEdge> edge) {
		this.edges = edge;
	}
	public GraphVertex getVertex() {
		return vertex;
	}
	public void setVertex(GraphVertex vertex) {
		this.vertex = vertex;
	}
}