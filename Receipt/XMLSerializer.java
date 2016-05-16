import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public class XMLSerializer extends Serializer {

	public XMLSerializer(){
		super();
	}
	public XMLSerializer(OutputStream out){
		super(out);
	}
	
	@Override
	public void objectStart(String objectName) {
		// TODO Auto-generated method stub
		this.outStream.print(this.openTag(objectName) + "\n");
		this.iLine.incIndetedLine();
	}
	
	@Override
	public String openTag (String field){
		return this.iLine.printTab() + "<" + field + ">";
	}
	
	@Override
	public String closeTag (String field){
		return "</" + field + ">\n";	
	}
	
	@Override
	public String printFieldTemplate(String fieldName, Object x){
		return this.openTag(fieldName) + x + this.closeTag(fieldName);
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

	@Override
	public void addField(String fieldName, List<? extends Serializable> l) {
		// TODO Auto-generated method stub
		this.objectStart(fieldName);
		
		Iterator<? extends Serializable> iter = l.iterator();
		
		while (iter.hasNext()){
			iter.next().serialize(this);
		}
		
		this.objectEnd(fieldName);
	}

	@Override
	public void addField(String fieldName, Serializable object) {
		// TODO Auto-generated method stub
	}

	@Override
	public void objectEnd(String objectName) {
		// TODO Auto-generated method stub
		this.iLine.decIndetedLine();
		this.outStream.print(this.iLine.printTab() + this.closeTag(objectName));
		this.outStream.flush();
	}

}
