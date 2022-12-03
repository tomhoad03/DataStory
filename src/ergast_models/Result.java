package ergast_models;

public class Result {
    private final Integer resultId;
    private final Integer raceId;
    private final Integer driverId;
    private final Integer constructorId;
    private final Integer position;
    private final Double points;
    private Integer normalisedPoints = 0;
    private final Integer laps;
    private final Integer rank;

    public Result(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.resultId = Integer.valueOf(splitLine[0]);
        this.raceId = Integer.valueOf(splitLine[1]);
        this.driverId = Integer.valueOf(splitLine[2]);
        this.constructorId = Integer.valueOf(splitLine[3]);

        int position;
        try {
            position = Integer.parseInt(splitLine[6]);
        } catch (NumberFormatException e) {
            position = 0;
        }
        this.position = position;

        this.points = Double.valueOf(splitLine[9]);
        this.laps = Integer.valueOf(splitLine[10]);

        int rank;
        try {
            rank = Integer.parseInt(splitLine[14]);
        } catch (NumberFormatException e) {
            rank = 0;
        }
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultId=" + resultId +
                ", raceId=" + raceId +
                ", driverId=" + driverId +
                ", constructorId=" + constructorId +
                ", position=" + position +
                ", points=" + points +
                ", normalisedPoints=" + normalisedPoints +
                ", laps=" + laps +
                ", rank=" + rank +
                '}';
    }

    public Integer getResultId() {
        return resultId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public Integer getConstructorId() {
        return constructorId;
    }

    public Integer getPosition() {
        return position;
    }

    public Double getPoints() {
        return points;
    }

    public void setNormalisedPoints(Integer normalisedPoints) {
        this.normalisedPoints = normalisedPoints;
    }

    public Integer getNormalisedPoints() {
        return normalisedPoints;
    }

    public Integer getLaps() {
        return laps;
    }

    public Integer getRank() {
        return rank;
    }
}
