import java.util.List;

/*
 * Name: Sachin Kumar
 * Unityid: skumar26
 * 
 */
public class VertexImpl implements Comparable<VertexImpl>
{
    public String     name;   
    public List       adj;   
    public boolean    visited;

    public VertexImpl( String nm )
      { name = nm; }
    public int compareTo(VertexImpl other) {
    	return name.compareTo(other.name);
    	}
    public String toString() { return name; }
      
}