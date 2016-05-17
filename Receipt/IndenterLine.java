
public class IndenterLine {
	private int indentedLine;
	
	public IndenterLine(){
		this.indentedLine = 0;
	}
	
    public void incIndetedLine(){
    	this.indentedLine++;
    }
    
    public void decIndetedLine(){
    	this.indentedLine--;
    }
    
    public int getIndetedLine(){
    	return this.indentedLine;
    }
    
    public String printTab(){
    	String buff = "";
    	for (int i = 0; i<this.getIndetedLine() ; i++) buff += "\t";
    	return buff;
    }
    
}
