package mk.klikniobrok.models;

/**
 * Created by gjorgjim on 1/12/17.
 */

public class LatLng {
    private Double latitude;
    private Double longitude;

    private LatLng() {

    }

    public LatLng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
