
import java.util.List;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class Serializer {
    protected PrintStream outStream;

    public Serializer(OutputStream out) {
        this.outStream = new PrintStream(out);
    }
    public Serializer() {
        this.outStream = System.out;
    }
    
    /**
     * Begin serializing a new object. Should always be eventually followed by a call to objectEnd
     *   with the identical objectName.
     * @param objectName Name of the object to begin serializing.
     */
    public abstract void objectStart(String objectName);
    
    /**
     *  This couple of utility functions are used to print the specific tag of the
     *  different serializer class.
     * @param field name of the field has to be written in the tag.
     * @return String object with the correct tag of the serializer.
     */
    public abstract String openTag (String field);
    public abstract String closeTag (String field);
    
    /**
     * General template to print a field, used by addField for primitive types.
     * @param fieldName name of the field to print
     * @param x value of the field
     * @return String object with a template of the correct serializer
     */
    public abstract String printFieldTemplate(String fieldName, Object x);
    
    /**
     * Add a field/value pair to the serialization.
     * @param fieldName Name of the field to be added.
     */
    public abstract void addField(String fieldName, int i);
    public abstract void addField(String fieldName, double d);
    public abstract void addField(String fieldName, boolean b);
    public abstract void addField(String fieldName, String s);
    public abstract void addField(String fieldName, List<? extends Serializable> l);
    //Should we do remove it?
    public abstract void addField(String fieldName, Serializable object);
    /**
     * Ends the serialization of an object, potentially continuing serialization on other
     *   containing objects. Should always be proceeded by a call to objectStart with the identical
     *   objectName.
     * @param objectName Name of the object to finish serializing.
     */
    public abstract void objectEnd(String objectName);
}
