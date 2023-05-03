package edu.uga.cs.countryquiz;

public class CountryList {
    private long cid; // id
    private String country; // country
    private String continent; // continent

    /*
     * Constructor that sets country and continent to given values
     */
    public CountryList(String country, String continent) {
        this.country = country;
        this.continent = continent;
    }

    /*
     * cid getter
     */
    public long getCid () {
        return cid;
    }
    /*
     * country getter
     */
    public String getCountry() {
        return country;
    }
    /*
     * continent getter
     */
    public String getContinent () {
        return continent;
    }

    /*
     * cid setter
     */
    public void setCid (long cid) {
        this.cid = cid;
    }
    /*
     * country setter
     */
    public void setCountry (String country) {
        this.country = country;
    }
    /*
     * continent setter
     */
    public void setContinent (String continent) {
        this.continent = continent;
    }
}

