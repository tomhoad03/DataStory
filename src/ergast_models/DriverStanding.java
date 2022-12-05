package ergast_models;

public class DriverStanding {
    private final Integer driverStandingsId;
    private final Integer raceId;
    private final Integer driverId;
    private final Integer position;
    private final Double points;
    private final Integer wins;

    public DriverStanding(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.driverStandingsId = Integer.valueOf(splitLine[0]);
        this.raceId = Integer.valueOf(splitLine[1]);
        this.driverId = Integer.valueOf(splitLine[2]);
        this.position = Integer.valueOf(splitLine[4]);
        this.points = Double.valueOf(splitLine[3]);
        this.wins = Integer.valueOf(splitLine[6]);
    }

    @Override
    public String toString() {
        return "DriverStanding{" +
                "driverStandingsId=" + driverStandingsId +
                ", raceId=" + raceId +
                ", driverId=" + driverId +
                ", position=" + position +
                ", points=" + points +
                ", wins=" + wins +
                '}';
    }

    public Integer getDriverStandingsId() {
        return driverStandingsId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public Integer getPosition() {
        return position;
    }

    public Double getPoints() {
        return points;
    }

    public Integer getWins() {
        return wins;
    }
}
