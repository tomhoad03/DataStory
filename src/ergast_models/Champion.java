package ergast_models;

public record Champion(Integer id, Integer year, Integer wins, Double points) {

    @Override
    public String toString() {
        return "Champion{" +
                "id=" + id +
                ", year=" + year +
                ", wins=" + wins +
                ", points=" + points +
                '}';
    }
}
