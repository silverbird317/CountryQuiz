package edu.uga.cs.countryquiz;

public class CountryList {
    private long cid;
    private String country;
    private String continent;

    public CountryList(){
        this.cid = -1;
        this.country = null;
        this.continent = null;
    }

    public CountryList(String country, String continent) {
    }

    public long getCid () {
        return cid;
    }
    public String getCountry() {
        return country;
    }
    public String getContinent () {
        return continent;
    }

    public void setCid (long cid) {
        this.cid = cid;
    }
    public void setCountry (String country) {
        this.country = country;
    }
    public void setContinent (String continent) {
        this.continent = continent;
    }
}

