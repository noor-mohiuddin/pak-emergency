package pakemergency.shared;

import java.util.Calendar;

import com.google.gwt.maps.client.geom.LatLng;

public class Emergency {
	private LatLng coordinates;
	//TODO: create calendars
	private String timeOfIncident;
	private String timeOfReporting;
	private String type;
	private String notes;
	
	public LatLng getCoordinates() {
		return coordinates;
	}

	public String getTimeOfIncident() {
		return timeOfIncident;
	}

	public String getTimeOfReporting() {
		return timeOfReporting;
	}

	public String getType() {
		return type;
	}

	public String getNotes() {
		return notes;
	}

	public Emergency(LatLng coordinates, String timeOfIncident,
			String timeOfReporting, String type, String notes) {
		super();
		this.coordinates = coordinates;
		this.timeOfIncident = timeOfIncident;
		this.timeOfReporting = timeOfReporting;
		this.type = type;
		this.notes = notes;
	}
}
