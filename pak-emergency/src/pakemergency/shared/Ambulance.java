package pakemergency.shared;

import com.google.gwt.maps.client.geom.LatLng;

public class Ambulance {
	private String code;
	private LatLng coordinates;
	
	public String getCode() {
		return code;
	}

	public LatLng getCoordinates() {
		return coordinates;
	}

	public Ambulance(String code, LatLng coordinates) {
		super();
		this.code = code;
		this.coordinates = coordinates;
	}
}
