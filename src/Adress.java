import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class Adress {
    private String countryName;
    private String cityName;
    private String streetName;
    private String buildingNumber;
    private String apartmentNumber;

    public Adress() {
    }

    private Adress(String countryName, String cityName, String streetName, String buildingNumber, String apartmentNumber) {
        if (countryName.isBlank() | cityName.isBlank() | streetName.isBlank() | buildingNumber.isBlank())
            throw new NullPointerException("This fields can not be empty.");

        this.countryName = countryName.substring(0, 1).toUpperCase() + countryName.substring(1);
        this.cityName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1);
        this.streetName = streetName.substring(0, 1).toUpperCase() + streetName.substring(1);
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public static Adress addAdress(String countryName, String cityName, String streetName, String buildingNumber, String apartmentNumber) {
        return new Adress(countryName, cityName, streetName, buildingNumber, apartmentNumber);
    }

    public static Adress addAdress(String countryName, String cityName, String streetName, String buildingNumber) {
        return new Adress(countryName, cityName, streetName, buildingNumber, "");
    }

    @Basic(optional = false)
    public String getCountryName() {
        return countryName;
    }

    @Basic(optional = false)
    public String getCityName() {
        return cityName;
    }

    @Basic(optional = false)
    public String getStreetName() {
        return streetName;
    }

    @Basic(optional = false)
    public String getBuildingNumber() {
        return buildingNumber;
    }

    @Column(nullable = true)
    public String getApartmentNumber() {
        return apartmentNumber + "";
    }

    public void setCountryName(String countryName) {
        if (!countryName.isBlank())
            this.countryName = countryName;
    }


    public void setCityName(String cityName) {
        if (!cityName.isBlank())
            this.cityName = cityName;
    }


    public void setStreetName(String streetName) {
        if (!streetName.isBlank())
            this.streetName = streetName;
    }


    public void setBuildingNumber(String buildingNumber) {
        if (!buildingNumber.isBlank())
            this.buildingNumber = buildingNumber;
    }


    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public String toString() {
        return "Country: " + getCountryName() + ", city: " + getCityName() + ", " +
                "Street: " + getStreetName() + " " + getBuildingNumber() + (apartmentNumber.length() > 0 ? "/" + getApartmentNumber() : "");
    }
}
