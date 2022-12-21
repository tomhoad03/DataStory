package ergast_models;

public class Race {
    private final Integer raceId;
    private final Integer year;
    private final Integer round;
    private final Integer circuitId;
    private final String name;
    private final String url;
    private BoxPlot boxPlot;
    private Integer overtakes;

    public Race(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.raceId = Integer.valueOf(splitLine[0]);
        this.year = Integer.valueOf(splitLine[1]);
        this.round = Integer.valueOf(splitLine[2]);
        this.circuitId = Integer.valueOf(splitLine[3]);
        this.name = splitLine[4].replace("\"", "");
        this.url = splitLine[7].replace("\"", "");
        this.boxPlot = new BoxPlot(0.0, 0, 0, 0.0, 0, 0.0);
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

    public boolean hasBoxPlot() {
        return boxPlot.getMax() > 0;
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

    public void setBoxPlot(BoxPlot boxPlot) {
        this.boxPlot = boxPlot;
    }

    public BoxPlot getBoxPlot() {
        return boxPlot;
    }

    public void setOvertakes(Integer overtakes) {
        this.overtakes = overtakes;
    }

    public Integer getOvertakes() {
        return overtakes;
    }
}
