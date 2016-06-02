
public class GraphEdge {

	GraphVertex v1, v2;
	
	public GraphEdge(GraphVertex v1, GraphVertex v2){
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public boolean containsVertex(GraphVertex vertex){
		if (v1.getName().equals(vertex.getName()) || 
			v2.getName().equals(vertex.getName())) return true;
		
		return false;
	}

	
	/*AUTOgenerate setters and getters*/
	public GraphVertex getV1() {
		return v1;
	}
	public void setV1(GraphVertex v1) {
		this.v1 = v1;
	}
	public GraphVertex getV2() {
		return v2;
	}
	public void setV2(GraphVertex v2) {
		this.v2 = v2;
	}
	
}
