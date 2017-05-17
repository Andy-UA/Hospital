package Beans;

import Types.LocationType;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Location extends Common {

    private Long id;
    private Long humanID;
    private LocationType type;
    private String postIndex;
    private String country;
    private String state;
    private String area;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String note;

    public Location(Long id, Long humanID, LocationType type, String postIndex, String country, String state, String area, String city, String street, String house, String apartment, String note) {
        this.id = id;
        this.humanID = humanID;
        this.type = type;
        this.postIndex = postIndex;
        this.country = country;
        this.state = state;
        this.area = area;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.note = note;
    }

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHumanID() {
        return humanID;
    }

    public void setHumanID(Long humanID) {
        this.humanID = humanID;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public String getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(String postIndex) {
        this.postIndex = postIndex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(postIndex))
            throw new Exception("PostIndex is empty");
        if(isEmptyValue(country))
            throw new Exception("Country is empty");
        if(isEmptyValue(city))
            throw new Exception("City is empty");
        if(isEmptyValue(street))
            throw new Exception("Street is empty");
        if(isEmptyValue(house))
            throw new Exception("House is empty");
    }

    @Override
    public String toString(){
        String s = "";
        s += isEmptyValue(postIndex) ? "" : postIndex;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(country) ? "" : country;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(state) ? "" : state;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(area) ? "" : area;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(city) ? "" : city;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(street) ? "" : street;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(house) ? "" : house;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(apartment) ? "" : apartment;
        return s;
    }
}
