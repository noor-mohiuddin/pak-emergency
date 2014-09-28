package pakemergency.shared;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.ui.Image;

public class Responder {
	
	private String fullName;
	private LatLng coordinates;
	private Image picture;
	
	public Responder(String fullName, LatLng coordinates) {
		super();
		this.fullName = fullName;
		this.coordinates = coordinates;
	}

	public Responder(String fullName, LatLng coordinates, Image picture) {
		super();
		this.fullName = fullName;
		this.coordinates = coordinates;
		this.picture = picture;
	}

}
