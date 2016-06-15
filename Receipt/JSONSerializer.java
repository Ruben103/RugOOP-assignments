import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public class JSONSerializer extends Serializer {

	public JSONSerializer(){
		super();
	}
	public JSONSerializer(OutputStream out){
		super(out);
	}
	
	@Override
	public String openTag(String field) {
		// TODO Auto-generated method stub
		String buff = this.iLine.printTab() + "{\n";
		this.iLine.incIndetedLine();
		buff += this.iLine.printTab() + field + ": { ";
		this.iLine.incIndetedLine();
		return buff;
	}

	@Override
	public String closeTag(String field) {
		// TODO Auto-generated method stub
		String buff = "\b \n" + this.iLine.printTab() + "}\n";
		this.iLine.decIndetedLine();
		buff += this.iLine.printTab() + "}";
		return buff;
	}
	
	@Override
	public String printFieldTemplate(String fieldName, Object x){
		return "\n"+ this.iLine.printTab() + fieldName + ":" + x + ",";
	}
	
	@Override
	public void objectStart(String objectName) {
		// TODO Auto-generated method stub
		this.outStream.print(this.openTag(objectName));
	}

	@Override
	public void addField(String fieldName, int i) {
		// TODO Auto-generated method stub
		this.outStream.print(this.printFieldTemplate(fieldName, i));
	}

	@Override
	public void addField(String fieldName, double d) {
		// TODO Auto-generated method stub
		this.outStream.print(this.printFieldTemplate(fieldName, d));
	}

	@Override
	public void addField(String fieldName, boolean b) {
		// TODO Auto-generated method stub
		this.outStream.print(this.printFieldTemplate(fieldName, b ? "true" : "false"));
	}

	@Override
	public void addField(String fieldName, String s) {
		// TODO Auto-generated method stub
		this.outStream.print(this.printFieldTemplate(fieldName, '"' + s + '"'));
	}

	//TODO: Add indentetion
	@Override
	public void addField(String fieldName, List<? extends Serializable> l) {
		// TODO Auto-generated method stub
		this.outStream.print("\n" + this.iLine.printTab() + fieldName +": [\n");
		Iterator<? extends Serializable> iter = l.iterator();
		
		this.iLine.incIndetedLine();
		
		while (iter.hasNext()){
			iter.next().serialize(this);
			if (iter.hasNext()) {this.outStream.print(",\n");}
		}
		
		this.iLine.decIndetedLine();
		
		this.outStream.print("\n" + this.iLine.printTab() + "] ");
	}

	@Override
	public void addField(String fieldName, Serializable object) {
		// TODO Auto-generated method stub
	}

	@Override
	public void objectEnd(String objectName) {
		// TODO Auto-generated method stub
		this.iLine.decIndetedLine();
		this.outStream.print(this.closeTag(objectName));
	}

}
