package be.ehb.demomaps.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HoofdStadDAO {
    //Singleton pattern
    private static final HoofdStadDAO ourInstance = new HoofdStadDAO();

    public static HoofdStadDAO getInstance() {
        return ourInstance;
    }

    private HoofdStadDAO() {
    }

    //own code
    ArrayList<Hoofdstad> hoofdsteden;

    public ArrayList<Hoofdstad> getHoofdsteden() {
        hoofdsteden = new ArrayList<>();

        hoofdsteden.add(new Hoofdstad(new LatLng(51.528308, -0.381789), "Londen", Hoofdstad.Continent.EUROPA));
        hoofdsteden.add(new Hoofdstad(new LatLng(41.3947688, 2.0787275), "Barcelona", Hoofdstad.Continent.EUROPA));
        hoofdsteden.add(new Hoofdstad(new LatLng(60.1098678, 24.738504), "Helsinki", Hoofdstad.Continent.EUROPA));
        hoofdsteden.add(new Hoofdstad(new LatLng(30.0594699, 31.188423), "Cairo", Hoofdstad.Continent.AFRIKA));
        hoofdsteden.add(new Hoofdstad(new LatLng(-33.87365, 151.20689), "Sidney", Hoofdstad.Continent.OCEANIE));

        return hoofdsteden;
    }

}
