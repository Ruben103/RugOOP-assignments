import java.util.Stack;

public abstract class AbstractUndoableEdit {

	public GraphModel graphModel;
	public Stack<Operation> stackOperation;
	
	public AbstractUndoableEdit(){
		this.graphModel = null;
		this.stackOperation = new Stack<Operation>();
	}
	
	public AbstractUndoableEdit(GraphModel graphModel){
		this.graphModel = graphModel;
		this.stackOperation = new Stack<Operation>();
	}
	
	public Operation doOperation(Operation op){
		switch (op.getOperation()){
			case ADD_VERTEX:{
				/*Remove added vertex*/
				this.getGraphModel().removeVertex(op.getVertex());
				op.setOperation(Operation.OperationType.REMOVE_VERTEX);
				break;
			}
			case REMOVE_VERTEX:{
				/*Add removed vertex*/
				this.getGraphModel().addVertex(op.getVertex());
				for (GraphEdge egde : op.getEdges()){
					this.getGraphModel().addEdge(egde);
				}
				op.setOperation(Operation.OperationType.ADD_VERTEX);
				break;
			}
			case ADD_EDGE:{
				/*Remove added edge*/
				this.getGraphModel().removeEdge(op.getEdges().get(0));
				op.setOperation(Operation.OperationType.REMOVE_EDGE);
				break;
			}
			case REMOVE_EDGE:{
				/*Add removed e*/
				this.getGraphModel().addEdge(op.getEdges().get(0));
				op.setOperation(Operation.OperationType.ADD_EDGE);
				break;
			}
		}
		
		return op;
	}

	public void addOperation(Operation op) {
		// TODO Auto-generated method stub
		this.getStackOperation().push(op);
	}
	
	/*AUTOgenerate setters and getters*/
	public GraphModel getGraphModel() {
		return graphModel;
	}
	public void setGraphModel(GraphModel graphModel) {
		this.graphModel = graphModel;
	}
	public Stack<Operation> getStackOperation() {
		return stackOperation;
	}
	public void setStackOperation(Stack<Operation> stackOperation) {
		this.stackOperation = stackOperation;
	}
	
}
