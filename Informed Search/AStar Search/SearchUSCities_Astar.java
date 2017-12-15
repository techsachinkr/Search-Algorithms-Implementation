import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SearchUSCities_Astar {
	
	private TreeMap<VertexImplement, TreeSet<VertexImplement>> adjListMap;
	private TreeMap<String,Double> distanceMap=new TreeMap<String,Double>();
	private static final HashSet<VertexImplement> EMPTY_SET = new HashSet<VertexImplement>();
	private int verticesCount;
	private int edgesCount;
	private HashMap<String, VertexImplement> verticesMap;
	PriorityQueue<VertexImplement> pqueue;
	Queue<VertexImplement> exploreNode=new LinkedList<VertexImplement>();
	HashMap<String,String> distMap=new HashMap<String,String>();
	 final Map<VertexImplement, VertexImplement> path = new HashMap<VertexImplement, VertexImplement>();
	 Set<String> closedSet=null;
	double totaldistance=0;
	public SearchUSCities_Astar() {
		// TODO Auto-generated constructor stub
		verticesCount = edgesCount = 0;
		verticesMap = new HashMap<String, VertexImplement>();
		adjListMap = new TreeMap<VertexImplement, TreeSet<VertexImplement>>();
		
	}
	
	public Iterable<VertexImplement> getVerticesMap() {
		return verticesMap.values();
	}
	public Iterable<VertexImplement> adjacentTo(String name) {
		VertexImplement v = verticesMap.get(name);
		if (v == null) return EMPTY_SET;
		return adjListMap.get(verticesMap.get(name));
	}


	public void makeEdges(String source, String destination,Double distance) {
		VertexImplement vertex1 = getVertexValues(source);
		VertexImplement vertex2 = getVertexValues(destination);
		VertexImplement sourceVertex,destVertex;
		
		if (vertex1 == null || vertex2 == null) return ;
		
				edgesCount += 1;
				sourceVertex = verticesMap.get(source);
				destVertex = verticesMap.get(destination);
				distanceMap.put(source+"##"+destination, distance);
				
	
		adjListMap.get(sourceVertex).add(destVertex);
		adjListMap.get(destVertex).add(sourceVertex);
		
	}
	
	private VertexImplement getVertexValues( String vertexName )
    {
		VertexImplement vertex = (VertexImplement) verticesMap.get( vertexName );
        if( vertex == null )
        {
        	vertex = new VertexImplement( vertexName );
            verticesMap.put( vertexName, vertex );
            adjListMap.put(vertex, new TreeSet<VertexImplement>());
			verticesCount += 1;
        }
        return vertex;
    }
	
	public double distancesCalcMap(VertexImplement nextNode,VertexImplement destination)
	{
		try {
			String Lat1Arr[]=distMap.get(nextNode.name.trim()).split("##");
			double Lat1=Double.parseDouble(Lat1Arr[0]);
			double Long1=Double.parseDouble(Lat1Arr[1]);
			String Lat2Arr[]=distMap.get(destination.name.trim()).split("##");
			double Lat2=Double.parseDouble(Lat2Arr[0]);
			double Long2=Double.parseDouble(Lat2Arr[1]);
			double distance=Math.sqrt(Math.pow(69.5 * (Lat1 - Lat2),2) + (69.5 * Math.cos((Lat1 + Lat2)/360 * Math.PI)) * Math.pow((Long1 - Long2),2));
			return distance;
		}catch(Exception e)
		{
			System.out.println("Please specify cityname in correctcase as specified in latlong file");
		}
		return 0;
	}
	public List<String> astar(VertexImplement source,VertexImplement destination,SearchUSA graph)
	{
		int size = verticesMap.size();
		  Map<String,Double> gScoreVal = new HashMap<String,Double>(); // Cost from start along best known path.
		  HashMap<String,String> cameFromPath = new HashMap<String,String>();
		  final Map<VertexImplement,Double> fScoreVal = new HashMap<VertexImplement,Double>();
		  List<VertexImplement> openSet = new ArrayList<VertexImplement>(size); 
		 closedSet = new HashSet<String>(size);
		 
		 openSet.add(source);
		 gScoreVal.put(source.name, 0.0);
 final Comparator<VertexImplement> comparator = new Comparator<VertexImplement>() {
     /**
      * {@inheritDoc}
      */
     @Override
     public int compare(VertexImplement ver1, VertexImplement ver2) {
         if (fScoreVal.get(ver1) < fScoreVal.get(ver2))
             return -1;
         if (fScoreVal.get(ver2) < fScoreVal.get(ver1))
             return 1;
         return 0;
     }
 };
		source.setgDistance(0);
		for(String vi : verticesMap.keySet())
		{
			 gScoreVal.put(vi, 0.0);
		}
		VertexImplement current=null;
	for(String vi : verticesMap.keySet())
		{
		fScoreVal.put(getVertexValues(vi), Double.MAX_VALUE);
		}
	fScoreVal.put(source, distancesCalcMap(source,destination));
		 while (!openSet.isEmpty()) {
			 current=openSet.get(0);
			 openSet.remove(0);
			 closedSet.add(current.name);
			
	
				 for (VertexImplement nextNode : graph.adjacentTo(current.name)) {
					 
					 if(nextNode.name.equals(destination.name))
						{
						 cameFromPath.put(nextNode.name,current.name+"##"+distValuereturn(nextNode, current));
							return rebuildPath(cameFromPath,source, destination,nextNode.name.equals(destination.name));
						}
					 if (closedSet.contains(nextNode.name))
					 {
		                    continue;
					 }
					 double distanceActual=0.0;
					 if(distanceMap.get(current+"##"+nextNode) == null)
					 {
						 distanceActual=distanceMap.get(nextNode+"##"+current);
					 }else
					 {
						 distanceActual=distanceMap.get(current+"##"+nextNode);
					 }
					 gScoreVal.put(nextNode.name,distanceActual);
					 double tentativeG=distanceActual+gScoreVal.get(current.name);
						
						if (!openSet.contains(nextNode))
						{
		                    openSet.add(nextNode); 
						}// Discover a new node
		                else if (tentativeG >= gScoreVal.get(nextNode.name))
		                {
		                	continue;
		                }
						double hdist=distancesCalcMap(nextNode,destination);
						cameFromPath.put(nextNode.name,current.name+"##"+distanceActual);
						gScoreVal.put(nextNode.name, tentativeG);
						 double estimatedFScore = gScoreVal.get(nextNode.name) + hdist;
						 fScoreVal.put(nextNode, estimatedFScore);
						 Collections.sort(openSet,comparator);
					 
				 
	}
		 }
		 return null;
	}
	
	
	public double distValuereturn(VertexImplement start,VertexImplement end)
	{
		 double distanceActual=0.0;
		 if(distanceMap.get(start+"##"+end) == null)
		 {
			 distanceActual=distanceMap.get(end+"##"+start);
		 }else
		 {
			 distanceActual=distanceMap.get(start+"##"+end);
		 }
		return distanceActual;
	}
	private List<String> rebuildPath(HashMap<String, String> pathMap,VertexImplement source,VertexImplement current,boolean pathfound) {
		
	List<String> completePath = new ArrayList<String>();

	 String currentval=current.name	;
	 completePath.add(currentval);
		 while (currentval != null) {
			 if(!(currentval.equals(source.name)))
			 {
			 String mapval[]=pathMap.get(currentval).split("##");
	           currentval = mapval[0];
	           totaldistance+=Double.parseDouble(mapval[1]);
			 }else
			 {
				 currentval=pathMap.get(currentval);
			 }
	            if (currentval != null) {
	              String edge = currentval;
	              completePath.add(edge);
	            }
	        }
	        Collections.reverse(completePath);
			return completePath;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SearchUSCities_Astar graph=new SearchUSCities_Astar();
		
		BufferedReader br=null;
		BufferedReader br2=null;
		try {
			br = new BufferedReader(new FileReader("roadswithdist.txt"));
			br2 = new BufferedReader(new FileReader("citylatlong.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String lineContents;
			while((lineContents=br.readLine())!=null)
			{
				 Scanner scan = new Scanner(lineContents).useDelimiter(",");  
				 String start= scan.next().trim();
					String end=	 scan.next().trim();
					String distance= scan.next();
					graph.makeEdges(start.trim(),end.trim(),Double.parseDouble(distance));
					scan.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String lineContents2;
			while((lineContents2=br2.readLine())!=null)
			{
				Scanner scan = new Scanner(lineContents2).useDelimiter(",");  
				String cityName= scan.next().trim();
				String latVal= scan.next();
				String longVal=scan.next();
				scan.close();
				graph.distMap.put(cityName.trim(), latVal+"##"+longVal);
			}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		VertexImplement source=new VertexImplement(args[1]);
		VertexImplement destination=new VertexImplement(args[2]);
		List<String> list1=null;
			list1= graph.astar(source, destination, graph);
			
		System.out.println("Nodes expanded or Closed list from : "+source.name+" to "+destination.name);
		int cntr1=0;
		for (String closedNodes : graph.closedSet ){
			 System.out.print(closedNodes);
	            cntr1++;
	            if(cntr1 < graph.closedSet.size())
	            {
	            	System.out.print(",");
	            }
		}
		System.out.println();
		System.out.println("Closed Nodes expanded count : "+source.name+" to "+destination.name);
		System.out.println(graph.closedSet.size());
		System.out.println("Solution path from : "+source.name+" to "+destination.name);
		
		int cntr2=0;
		for (String path2 : list1 ){
            System.out.print(path2);
            cntr2++;
            if(cntr2 < list1.size())
            {
            	System.out.print(",");
            }
        }
		 System.out.println();
		System.out.println("Solution path Nodes count for : "+source.name+" to "+destination.name);
		System.out.println(list1.size());
		System.out.println("Total distance from "+source.name+" to "+destination.name+" is "+graph.totaldistance);
	}
}
