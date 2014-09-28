package pakemergency.shared;

public class AmbulanceRoute {
	private Ambulance ambulance;
	private double routeDistance;
	private double routeTime;
	
	public Ambulance getAmbulance() {
		return ambulance;
	}
	
	public String getAmbulanceCode(){
		return ambulance.getCode();
	}
	
	public String getAmbulanceCoordinatesString(){
		//TODO: Get the written coordintates
		return ambulance.getCoordinates().toString();
	}
	
	public void setAmbulance(Ambulance ambulance) {
		this.ambulance = ambulance;
	}
	public double getRouteDistance() {
		return routeDistance;
	}
	public void setRouteDistance(double routeDistance) {
		this.routeDistance = routeDistance;
	}
	public double getRouteTime() {
		return routeTime;
	}
	public void setRouteTime(double routeTime) {
		this.routeTime = routeTime;
	}
	public AmbulanceRoute(Ambulance ambulance) {
		super();
		this.ambulance = ambulance;
	}
	public AmbulanceRoute(Ambulance ambulance, double routeDistance) {
		super();
		this.ambulance = ambulance;
		this.routeDistance = routeDistance;
	}
	public AmbulanceRoute(Ambulance ambulance, double routeDistance,
			double routeTime) {
		super();
		this.ambulance = ambulance;
		this.routeDistance = routeDistance;
		this.routeTime = routeTime;
	}
}
