package ergast_models;

public class RaceOvertake {
    private final Integer year;
    private final String name;
    private final Integer overtakes;
    private final Integer tv;

    public RaceOvertake(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.year = Integer.valueOf(splitLine[0]);
        this.name = splitLine[1].replace("\"", "");
        this.overtakes = Integer.valueOf(splitLine[2]);
        this.tv = Integer.valueOf(splitLine[3]);
    }

    @Override
    public String toString() {
        return "RaceOvertake{" +
                "year=" + year +
                ", name='" + name + '\'' +
                ", overtakes=" + overtakes +
                ", tv=" + tv +
                '}';
    }

    public Integer getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public Integer getOvertakes() {
        return overtakes;
    }

    public Integer getTv() {
        return tv;
    }
}
