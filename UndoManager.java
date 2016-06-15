public class UndoManager extends AbstractUndoableEdit {

	public UndoManager(GraphModel graphModel) {
		super(graphModel);
	}
	
	public void undoOperation(){
		Operation op = this.getStackOperation().peek();
		this.getGraphModel().getRedoManager().addOperation(this.doOperation(op));
	}	
	
}
