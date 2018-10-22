package ca.bcit.ass1.alvar_zhang;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Country implements Parcelable {

    // Private Instance Variables
    private String name;
    private String capital;
    private String region;
    private String population;
    private String area;
    private String borders;
    private String flag;

    // Public Regions
    public static ArrayList<Country> AFRICA = new ArrayList<>();
    public static ArrayList<Country> AMERICAS = new ArrayList<>();
    public static ArrayList<Country> ASIA = new ArrayList<>();
    public static ArrayList<Country> EUROPE = new ArrayList<>();
    public static ArrayList<Country> OCEANIA = new ArrayList<>();

    // Default Constructor
    public Country() {}

    // Getters
    public String getName() { return name; }
    public String getCapital() { return capital; }
    public String getRegion() { return region; }
    public String getPopulation() { return population; }
    public String getArea() { return area; }
    public String getBorders() { return borders; }
    public String getFlag() { return flag; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setCapital(String capital) { this.capital = capital; }
    public void setRegion(String region) { this.region = region; }
    public void setPopulation(String population) { this.population = population; }
    public void setArea(String area) { this.area = area; }
    public void setBorders(String borders) { this.borders = borders; }
    public void setFlag(String flag) { this.flag = flag; }

    // toString Method
    @Override
    public String toString() {
        return name;
    }

    // Parcel Constructor
    protected Country(Parcel in) {
        name = in.readString();
        capital = in.readString();
        region = in.readString();
        population = in.readString();
        area = in.readString();
        borders = in.readString();
//        if (in.readByte() == 0x01) {
//            borders = new ArrayList<String>();
//            in.readList(borders, String.class.getClassLoader());
//        } else {
//            borders = null;
//        }
        flag = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(capital);
        dest.writeString(region);
        dest.writeString(population);
        dest.writeString(area);
        dest.writeString(borders);
//        if (borders == null) {
//            dest.writeByte((byte) (0x00));
//        } else {
//            dest.writeByte((byte) (0x01));
//            dest.writeList(borders);
//        }
        dest.writeString(flag);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
