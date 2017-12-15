import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
/*
 * 
 * roads.txt file is attached with this program,please keep it on same path before running
 * There is also a file VertexImpl needed with this file and attached alongwith it
 */


public class SearchRomaniaGraph_DFS {

	
	private TreeMap<VertexImpl, TreeSet<VertexImpl>> adjListMap;
	private static final TreeSet<VertexImpl> EMPTY_SET = new TreeSet<VertexImpl>();
	private int verticesCount;
	private int edgesCount;
	private TreeMap<String, VertexImpl> verticesMap;
	private Stack<VertexImpl> dfsStack=new Stack<VertexImpl>();
	private List<VertexImpl> currentPath=new ArrayList<VertexImpl>();
	
	public SearchRomaniaGraph_DFS()
	{
		verticesCount = edgesCount = 0;
		verticesMap = new TreeMap<String, VertexImpl>();
		adjListMap = new TreeMap<VertexImpl, TreeSet<VertexImpl>>();
		
	}
	public Iterable<VertexImpl> getVerticesMap() {
		return verticesMap.values();
	}
	public Iterable<VertexImpl> adjacentTo(String name) {
		VertexImpl v = verticesMap.get(name);
		if (v == null) return EMPTY_SET;
		return adjListMap.get(verticesMap.get(name));
	}


	public void makeEdge(String source, String destination) {
		VertexImpl vertex1 = getVertexVal(source);
		VertexImpl vertex2 = getVertexVal(destination);
		VertexImpl sourceVertex,destVertex;
		if (vertex1 == null || vertex2 == null) return ;
		
				edgesCount += 1;
				sourceVertex = verticesMap.get(source);
	destVertex = verticesMap.get(destination);
	
		adjListMap.get(sourceVertex).add(destVertex);
		adjListMap.get(destVertex).add(sourceVertex);
		
	}
	private VertexImpl getVertexVal( String vertexName )
    {
		VertexImpl vertex = (VertexImpl) verticesMap.get( vertexName );
        if( vertex == null )
        {
        	vertex = new VertexImpl( vertexName );
            verticesMap.put( vertexName, vertex );
            adjListMap.put(vertex, new TreeSet<VertexImpl>());
			verticesCount += 1;
        }
        return vertex;
    }
	
	public void recursiveDFS(String source,String destination,SearchRomania graph,Stack<VertexImpl> dfsStack)
	{
		
		VertexImpl sourceNode=verticesMap.get(source);
		sourceNode.visited=true;
		//System.out.print(sourceNode.name+" ");
		//dfsStack.add(sourceNode);
		dfsStack.push(sourceNode);
		if(source.equals(destination))
		{
			 for (VertexImpl vert : dfsStack) 
			 {
				System.out.print(vert+" ");
			 } System.out.println("       Length of list is "+dfsStack.size()); 
			 System.out.println();
			 return;
		}
		
		for (VertexImpl nextNode : graph.adjacentTo(sourceNode.name)) {
			if(!nextNode.visited)
			{
				recursiveDFS(nextNode.name,destination,graph,dfsStack);
			}
		}
		dfsStack.pop();
		sourceNode.visited=false;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SearchRomaniaGraph_DFS graph=new SearchRomaniaGraph_DFS();
		
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader("roads.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String lineContents;
			while((lineContents=br.readLine())!=null)
			{
				 Scanner scan = new Scanner(lineContents).useDelimiter(",");  
				 String start= scan.next();
					String end=	 scan.next();
					graph.makeEdge(start,end);
					scan.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(args.length==0)
		{
			System.out.println("Please specify searchtype,sourcecity and destinationcity as first,second and third "
					+ "argument respectively");
		}
		
			graph.recursiveDFS(args[1],args[2],graph,graph.dfsStack);	
		
	}
}
