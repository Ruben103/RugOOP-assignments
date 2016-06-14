import java.io.Serializable;

public class UndoManager extends AbstractUndoableEdit {

	public UndoManager(GraphModel graphModel) {
		super(graphModel);
	}
	
	public void undoOperation(){
		Operation op = this.getStackOperation().pop();
		this.getGraphModel().getRedoManager().addOperation(this.doOperation(op));
	}	
	
}
