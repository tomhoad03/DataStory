package ergast_models;

public class ConstructorStanding {
    private final Integer constructorStandingsId;
    private final Integer raceId;
    private final Integer constructorId;
    private final Integer position;
    private final Integer wins;

    public ConstructorStanding(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.constructorStandingsId = Integer.valueOf(splitLine[0]);
        this.raceId = Integer.valueOf(splitLine[1]);
        this.constructorId = Integer.valueOf(splitLine[2]);
        this.position = Integer.valueOf(splitLine[4]);
        this.wins = Integer.valueOf(splitLine[6]);
    }

    @Override
    public String toString() {
        return "ConstructorStanding{" +
                "constructorStandingsId=" + constructorStandingsId +
                ", raceId=" + raceId +
                ", constructorId=" + constructorId +
                ", position=" + position +
                ", wins=" + wins +
                '}';
    }

    public Integer getDriverStandingsId() {
        return constructorStandingsId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public Integer getConstructorId() {
        return constructorId;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getWins() {
        return wins;
    }
}
