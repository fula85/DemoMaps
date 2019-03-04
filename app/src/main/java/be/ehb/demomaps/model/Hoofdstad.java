package be.ehb.demomaps.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Hoofdstad implements Serializable {

    public enum Continent {EUROPA, USA, ZUID_AMERIKA, AFRIKA, AZIE, OCEANIE, ANTARTICA}

    private LatLng coord;
    private String cityName;
    private Continent continent;

    public Hoofdstad() {
    }

    public Hoofdstad(LatLng coord, String cityName, Continent continent) {
        this.coord = coord;
        this.cityName = cityName;
        this.continent = continent;
    }

    public LatLng getCoord() {
        return coord;
    }

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }
}




