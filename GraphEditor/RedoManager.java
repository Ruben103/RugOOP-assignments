import java.io.Serializable;

public class RedoManager extends AbstractUndoableEdit{

	public RedoManager(GraphModel graphModel) {
		super(graphModel);
	}

	public void redoOperation(){
		Operation op = this.getStackOperation().pop();
		this.getGraphModel().getUndoManager().addOperation(this.doOperation(op));
	}
	
	public void flushRedoStack(){
		this.getStackOperation().removeAllElements();
	}
	
}
