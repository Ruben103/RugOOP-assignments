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
	
	//TODO: Add indentetion and comma
	@Override
	public String openTag(String field) {
		// TODO Auto-generated method stub
		String buff = this.printTab() + "{\n";
		this.incIndetedLine();
		buff += this.printTab() + field + ": {";
		this.incIndetedLine();
		return buff;
	}

	@Override
	public String closeTag(String field) {
		// TODO Auto-generated method stub
		String buff = "\b \n" + this.printTab() + "}\n";
		this.decIndetedLine();
		buff += this.printTab() + "}";
		return buff;
	}
	
	@Override
	public String printFieldTemplate(String fieldName, Object x){
		return "\n"+ this.printTab() + fieldName + ":" + x + ",";
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
		this.outStream.print("\n" + this.printTab() + fieldName +": [\n");
		Iterator<? extends Serializable> iter = l.iterator();
		
		this.incIndetedLine();
		
		while (iter.hasNext()){
			iter.next().serialize(this);
			if (iter.hasNext()) {this.outStream.print(",\n");}
		}
		
		this.decIndetedLine();
		
		this.outStream.print("\n" + this.printTab() + "] ");
	}

	@Override
	public void addField(String fieldName, Serializable object) {
		// TODO Auto-generated method stub
	}

	@Override
	public void objectEnd(String objectName) {
		// TODO Auto-generated method stub
		this.decIndetedLine();
		this.outStream.print(this.closeTag(objectName));
	}

}
