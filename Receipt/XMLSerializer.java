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
	}
	
	@Override
	public String openTag (String field){
		return "<" + field + ">";
	}
	
	@Override
	public String closeTag (String field){
		return "</" + field + ">";
		
	}

	@Override
	public void addField(String fieldName, int i) {
		// TODO Auto-generated method stub
		this.objectStart(fieldName);
		this.outStream.print(i);
		this.objectEnd(fieldName);

	}

	@Override
	public void addField(String fieldName, double d) {
		// TODO Auto-generated method stub
		this.objectStart(fieldName);
		this.outStream.print(d);
		this.objectEnd(fieldName);

	}

	@Override
	public void addField(String fieldName, boolean b) {
		// TODO Auto-generated method stub
		this.objectStart(fieldName);
		this.outStream.print(b ? "true" : "false");
		this.objectEnd(fieldName);

	}

	@Override
	public void addField(String fieldName, String s) {
		// TODO Auto-generated method stub
		this.objectStart(fieldName);
		this.outStream.print('"' + s + '"');
		this.objectEnd(fieldName);

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
		this.outStream.print("\n"+this.closeTag(objectName) );
		this.outStream.flush();
	}

}
