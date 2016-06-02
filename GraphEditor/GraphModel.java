import java.util.ArrayList;

public class GraphModel {
	
	ArrayList<GraphVertex> vertexes;
	ArrayList<GraphEdge> edges;
	
	
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
	
	public void removeEdge(){
		this.getEdges().remove(edges);
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
	
	

}
