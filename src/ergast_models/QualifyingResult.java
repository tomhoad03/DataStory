package ergast_models;

public class QualifyingResult {
    private final Integer qualifyId;
    private final Integer raceId;
    private final Integer driverId;
    private final Integer constructorId;
    private final Integer number;
    private final Integer position;
    private final Long q1;
    private final Long q2;
    private final Long q3;

    public QualifyingResult(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.qualifyId = Integer.valueOf(splitLine[0]);
        this.raceId = Integer.valueOf(splitLine[1]);
        this.driverId = Integer.valueOf(splitLine[2]);
        this.constructorId = Integer.valueOf(splitLine[3]);
        this.number = Integer.valueOf(splitLine[4]);
        this.position = Integer.valueOf(splitLine[5]);

        String q1String = splitLine[6].replace("\"", "");
        String q2String = splitLine[7].replace("\"", "");
        String q3String = splitLine[8].replace("\"", "");

        this.q1 = readTime(q1String);
        this.q2 = readTime(q2String);
        this.q3 = readTime(q3String);
    }

    private Long readTime(String qString) {
        try {
            return (Long.parseLong(qString.split(":")[0]) * 60000) +
                    (Long.parseLong(qString.split(":")[1].split("\\.")[0]) * 1000) +
                    Long.parseLong(qString.split("\\.")[1]);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public String toString() {
        return "QualifyingResult{" +
                "qualifyId=" + qualifyId +
                ", raceId=" + raceId +
                ", driverId=" + driverId +
                ", constructorId=" + constructorId +
                ", number=" + number +
                ", position=" + position +
                ", q1=" + q1 +
                ", q2=" + q2 +
                ", q3=" + q3 +
                ", fastestLap=" + getFastestLap() +
                '}';
    }

    public Integer getQualifyId() {
        return qualifyId;
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

    public Integer getNumber() {
        return number;
    }

    public Integer getPosition() {
        return position;
    }

    public Long getQ1() {
        return q1;
    }

    public Long getQ2() {
        return q2;
    }

    public Long getQ3() {
        return q3;
    }

    public Long getFastestLap() {
        if (q2 == 0 && q3 == 0) {
            return q1;
        }
        if (q3 == 0 && q2 > 0) {
            if (q1 < q2) {
                return q1;
            }
            return q2;
        }
        if (q1 < q2 && q1 < q3) {
            return q1;
        }
        if (q2 < q1 && q2 < q3) {
            return q2;
        }
        return q3;
    }
}
