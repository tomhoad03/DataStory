package ergast_models;

public class LapTime {
    private final Integer raceId;
    private final Integer driverId;
    private final Integer lap;
    private final Integer position;
    private final String time;
    private final Integer milliseconds;

    public LapTime(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.raceId = Integer.valueOf(splitLine[0]);
        this.driverId = Integer.valueOf(splitLine[1]);
        this.lap = Integer.valueOf(splitLine[2]);
        this.position = Integer.valueOf(splitLine[3]);
        this.time = splitLine[4];
        this.milliseconds = Integer.valueOf(splitLine[5]);
    }

    @Override
    public String toString() {
        return "LapTime{" +
                "raceId=" + raceId +
                ", driverId=" + driverId +
                ", lap=" + lap +
                ", position=" + position +
                ", time='" + time + '\'' +
                ", milliseconds=" + milliseconds +
                '}';
    }

    public Integer getRaceId() {
        return raceId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public Integer getLap() {
        return lap;
    }

    public Integer getPosition() {
        return position;
    }

    public String getTime() {
        return time;
    }

    public Integer getMilliseconds() {
        return milliseconds;
    }
}
