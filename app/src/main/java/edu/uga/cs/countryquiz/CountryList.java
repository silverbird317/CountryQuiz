package edu.uga.cs.countryquiz;

public class CountryList {
    private long cid;
    private String country;
    private String continent;

    public CountryList(String country, String continent) {
        this.country = country;
        this.continent = continent;
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

