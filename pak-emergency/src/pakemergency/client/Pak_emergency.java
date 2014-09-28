package pakemergency.client;

import java.util.ArrayList;
//import java.util.Calendar;

import java.util.Arrays;
import java.util.List;

import pakemergency.shared.Ambulance;
import pakemergency.shared.AmbulanceRoute;
import pakemergency.shared.Emergency;
import pakemergency.shared.FieldVerifier;
import pakemergency.shared.Responder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.geocode.DirectionResults;
import com.google.gwt.maps.client.geocode.Directions;
import com.google.gwt.maps.client.geocode.DirectionsCallback;
import com.google.gwt.maps.client.geocode.DirectionsPanel;
import com.google.gwt.maps.client.geocode.Distance;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geocode.Waypoint;
import com.google.gwt.maps.client.geocode.DirectionQueryOptions;
import com.google.gwt.maps.client.geocode.Route;

/*import com.google.maps.gwt.client.*;
 import com.google.maps.gwt.client.DirectionsService;
 import com.google.maps.gwt.client.DirectionsRequest;
 import com.google.maps.gwt.client.LatLng;
 import com.google.maps.gwt.client.GoogleMap;
 import com.google.maps.gwt.client.MapPanes;*/
/*import com.google.gwt.maps.client.MapOptions;
 import com.google.gwt.maps.client.MapWidget;
 */

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Pak_emergency implements EntryPoint {
	
	DockLayoutPanel mainPanel;
	MapWidget map;
	DirectionsPanel directions;
	
	// GWT module entry point method.
	public void onModuleLoad() {
		Maps.loadMapsApi("", "2", false, new Runnable() {
			public void run() {
				buildUi();
			}
		});
	}

	void buildUi() {
		LatLng karachiUniv = LatLng.newInstance(24.924492, 67.101916);
		//LatLng karachiSharPak = LatLng.newInstance(24.970796, 67.099175);
		
		//Emergency details
		Emergency emergency = new Emergency(LatLng.newInstance(24.924492, 67.101916), // location of incident
											"2014/01/28 11:00pm", // time of incident
											"2014/09/28 11:30pm", // time of reporting
											//TODO: create enum 
											"Cardiac", 
											"Name: Zafar Khan");
		
		map = new MapWidget(karachiUniv, 10);
		map.setSize("100%", "100%");
		
		// Add some controls for the zoom level

		// Add a marker
		map.addOverlay(new Marker(karachiUniv));

		// Add an info window to highlight a point of interest
		// map.getInfoWindow().open(map.getCenter(),
		// new InfoWindowContent("World's Largest Ball of Sisal Twine"));

		directions = new DirectionsPanel();
		
		/*
		 * RootPanel
		 */
		
		mainPanel = new DockLayoutPanel(Unit.PX);
		mainPanel.addNorth(new Label("United Responders"), 20);
		mainPanel.addNorth(map,500);
		//mainPanel.addWest(directions,100);
		
		mainPanel.addSouth(addAmbulances(emergency), 300);
		
		//addResponders();
		
		// Add the map to the HTML host page
		RootLayoutPanel.get().add(mainPanel);
	}
	
	private VerticalPanel addAmbulances(Emergency emergency){
		VerticalPanel returnPanel = new VerticalPanel();
		
		final CellTable<AmbulanceRoute> table = new CellTable<AmbulanceRoute>(); 
		
		// Add a text column to show the ambulance code.
		TextColumn<AmbulanceRoute> codeColumn = new TextColumn<AmbulanceRoute>() {
			@Override
			public String getValue(AmbulanceRoute ambulanceRoute) {
				return ambulanceRoute.getAmbulanceCode();
			}
		};
		table.addColumn(codeColumn, "Ambulance Code");
		
		// Add a text column to show the ambulance location.
		TextColumn<AmbulanceRoute> coordinatesColumn = new TextColumn<AmbulanceRoute>() {
			@Override
			public String getValue(AmbulanceRoute ambulanceRoute) {
				return ambulanceRoute.getAmbulanceCoordinatesString();
			}
		};
		table.addColumn(coordinatesColumn, "Ambulance Location");
		
		// Add a text column to show the ambulance distance.
		TextColumn<AmbulanceRoute> distanceColumn = new TextColumn<AmbulanceRoute>() {
			@Override
			public String getValue(AmbulanceRoute ambulanceRoute) {
				return "" + ambulanceRoute.getRouteDistance();
			}
		};
		table.addColumn(distanceColumn, "Ambulance Distance");
		
		// Add a text column to show the ambulance trip duration.
		TextColumn<AmbulanceRoute> durationColumn = new TextColumn<AmbulanceRoute>() {
			@Override
			public String getValue(AmbulanceRoute ambulanceRoute) {
				return "" + ambulanceRoute.getRouteTime();
			}
		};
		table.addColumn(durationColumn, "Ambulance Duration");
		
		final List<AmbulanceRoute> ambulanceRoutes = new ArrayList<AmbulanceRoute>();
		
		//table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		ArrayList<Ambulance> ambulances = new ArrayList<Ambulance>();
		
		/*
		 * TODO: Fetch from DB
		 * Populate ambulance records
		*/
		ambulances.add (new Ambulance ("Edhi-001", LatLng.newInstance(24.970796, 67.099175)));
		ambulances.add (new Ambulance ("Khidmat-A1", LatLng.newInstance(24.895709, 67.091216)));
		

		

		for (final Ambulance i : ambulances){
			//Create the row			
			//final LayoutPanel tempPanel = new LayoutPanel();

			Directions.loadFromWaypoints(new Waypoint[] {new Waypoint(i.getCoordinates()), new Waypoint(emergency.getCoordinates())}, 
										 new DirectionQueryOptions(map, directions), 
										 new DirectionsCallback() {
				
				@Override
				public void onSuccess(DirectionResults result) {
/*					
					//Add the ambulance code
					tempPanel.add(new Label (i.getCode()));
					
					//Add the ambulance location
					tempPanel.add(new Label (i.getCoordinates().toString()));
					
					//Add the distance to location
					tempPanel.add(new Label ("" + result.getDistance().inMeters()));
*/					
					//TODO:Add time to reach location
					//Distance dist = result.getRoutes().get(0).getDistance();
					Route route = result.getRoutes().get(0);
					
					ambulanceRoutes.add(new AmbulanceRoute (i, route.getDistance().inMeters(), route.getDuration().inSeconds()));;
					
					
					table.setRowData(0, ambulanceRoutes);
				}
				
				@Override
				public void onFailure(int statusCode) {
					//TODO: Add an erroneous row
					System.out.println("Error getting results for ambulance" + i.getCode());
				}
			});
			
/*			returnPanel.add(tempPanel,
					new Label ("Click to select"),
					4
					);
*/			
		}
		
		returnPanel.add(table);
		
		return returnPanel;
	}
	
	//TODO: Add a responders section
	private void addResponders(){
		/*ArrayList<Responder> responders = new ArrayList<Responder>();
		
		
		
		Waypoint[] waypoints = new Waypoint[2];
		waypoints[0] = new Waypoint(karachiUniv);
		waypoints[1] = new Waypoint(karachiSharPak);

		DirectionQueryOptions queryOptions = new DirectionQueryOptions(map);
		Directions.loadFromWaypoints(waypoints, queryOptions);
		
		for (Responder i: responders)
			mainPanel.add(w);*/
	}

}