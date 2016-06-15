import java.io.IOException;

public abstract class GraphParser {
	public abstract void saveGraph(String nameFile, GraphModel graph) throws IOException;
	public abstract void loadFromFile(String nameFile, GraphModel graph) throws IOException;
}
