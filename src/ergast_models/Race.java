package ergast_models;

public class Race {
    private final Integer raceId;
    private final Integer year;
    private final Integer round;
    private final Integer circuitId;
    private final String name;
    private final String url;

    public Race(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.raceId = Integer.valueOf(splitLine[0]);
        this.year = Integer.valueOf(splitLine[1]);
        this.round = Integer.valueOf(splitLine[2]);
        this.circuitId = Integer.valueOf(splitLine[3]);
        this.name = splitLine[4];
        this.url = splitLine[8];
    }

    @Override
    public String toString() {
        return "Race{" +
                "raceId=" + raceId +
                ", year=" + year +
                ", round=" + round +
                ", circuitId=" + circuitId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Integer getRaceId() {
        return raceId;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getRound() {
        return round;
    }

    public Integer getCircuitId() {
        return circuitId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
