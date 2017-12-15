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
 * roads.txt file is attached with this program,please keep it on same path before running
 * There is also a file VertexImpl needed with this file and attached alongwith it
 */


public class SearchRomaniaGraph_BFS {

	
	private TreeMap<VertexImpl, TreeSet<VertexImpl>> adjListMap;
	private static final TreeSet<VertexImpl> EMPTY_SET = new TreeSet<VertexImpl>();
	private int verticesCount;
	private int edgesCount;
	private TreeMap<String, VertexImpl> verticesMap;
	private Queue<VertexImpl> bfsQueue=new LinkedList<VertexImpl>();
	private List<VertexImpl> currentPath=new ArrayList<VertexImpl>();
	
	public SearchRomaniaGraph_BFS()
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
	
	public void BFS(String source,String destination, Queue<VertexImpl> bfsQueue,SearchRomania graph)
	{
		VertexImpl sourceNode=verticesMap.get(source); 
		sourceNode.visited=true;
		bfsQueue.add(sourceNode);
		while(bfsQueue.size()!=0)
		{
			currentPath.add(bfsQueue.peek());
			sourceNode=bfsQueue.poll();
			if (sourceNode.name.equals(destination))
			{
				for(int cntr=0;cntr<currentPath.size();cntr++)
				{
					System.out.print(currentPath.get(cntr)+" ");
				}System.out.println("  length of list is "+currentPath.size());
			  System.out.println();
			}
			
			for (VertexImpl nextNode : graph.adjacentTo(sourceNode.name)) {
				if(!nextNode.visited)
				{
					nextNode.visited=true;
					bfsQueue.add(nextNode);
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SearchRomaniaGraph_BFS graph=new SearchRomaniaGraph_BFS();
		
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
		

			graph.BFS(args[1],args[2],graph.bfsQueue,graph);
	
		
	}
}
