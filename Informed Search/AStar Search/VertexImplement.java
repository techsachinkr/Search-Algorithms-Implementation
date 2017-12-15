import java.util.List;

/*
 * Name: Sachin Kumar
 * Unityid: skumar26
 * 
 */
public class VertexImplement implements Comparable<VertexImplement>
{
    public String     name;   
    public List       adj;   
    public boolean    visited;
  public double hdistance;
  public double approxDist;
  public double gDistance;
  public double distancetoStart=0.0;
  public double predictedDistance=0.0;
    
    public double getPredictedDistance() {
	return predictedDistance;
}
public void setPredictedDistance(double predictedDistance) {
	this.predictedDistance = predictedDistance;
}
	public double getDistancetoStart() {
	return distancetoStart;
}
public void setDistancetoStart(double distancetoStart) {
	this.distancetoStart = distancetoStart;
}
	public double getgDistance() {
	return gDistance;
}
public void setgDistance(double gDistance) {
	this.gDistance = gDistance;
}
	public double getApproxDist() {
	return approxDist;
}
public void setApproxDist(double approxDist) {
	this.approxDist = approxDist;
}
	
    public double getHdistance() {
		return hdistance;
	}
	public void setHdistance(double hdistance) {
		this.hdistance = hdistance;
	}

    public VertexImplement( String nm )
      { name = nm; }
    public int compareTo(VertexImplement other) {
    	return name.compareTo(other.name);
    	}
    public String toString() { return name; }
      
}