
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
     * Add a field/value pair to the serialization.
     * @param fieldName Name of the field to be added.
     */
    public abstract void addField(String fieldName, int i);
    public abstract void addField(String fieldName, double d);
    public abstract void addField(String fieldName, boolean b);
    public abstract void addField(String fieldName, String s);
    public abstract void addField(String fieldName, List<? extends Serializable> l);
    public abstract void addField(String fieldName, Serializable object);
    /**
     * Ends the serialization of an object, potentially continuing serialization on other
     *   containing objects. Should always be proceeded by a call to objectStart with the identical
     *   objectName.
     * @param objectName Name of the object to finish serializing.
     */
    public abstract void objectEnd(String objectName);
}