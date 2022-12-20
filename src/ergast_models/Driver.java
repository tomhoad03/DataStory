package ergast_models;

public class Driver {
    private final Integer driverId;
    private final Integer number;
    private final String code;
    private final String forename;
    private final String surname;

    public Driver(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.driverId = Integer.valueOf(splitLine[0]);

        int number;
        try {
            number = Integer.parseInt(splitLine[2]);
        } catch (Exception e) {
            number = -1;
        }
        this.number = number;

        this.code = splitLine[3];
        this.forename = splitLine[4];
        this.surname = splitLine[3];
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", number=" + number +
                ", code='" + code + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    public Integer getDriverId() {
        return driverId;
    }

    public Integer getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }
}
