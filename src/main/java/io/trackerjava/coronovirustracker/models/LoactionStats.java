package io.trackerjava.coronovirustracker.models;

public class LoactionStats {
    private String state;
    private String country;
    private int latestTotalStats;
    private int differenceCases;

    public int getDifferenceCases() {
        return differenceCases;
    }

    public void setDifferenceCases(int differenceCases) {
        this.differenceCases = differenceCases;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState(){
        return state;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry(){
        return country;
    }
    public void setLatestTotalStats(int latestTotalStats) {
        this.latestTotalStats = latestTotalStats;
    }

    public int getLatestTotalStats(){
        return latestTotalStats;
    }
    @Override
    public String toString() {
        return "LoactionStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalStats=" + latestTotalStats +
                '}';
    }


}