package mk.klikniobrok.models;

/**
 * Created by gjorgjim on 1/12/17.
 */

public class Address {
    private String name;
    private String number;
    private String city;
    private String country;
    private long postalCode;

    public Address() {

    }

    public Address(String name, String number, String city, String country, long postalCode) {
        this.name = name;
        this.number = number;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }
}
