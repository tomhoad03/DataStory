package ergast_models;

public record SankeyEntry(String origin, String target, Integer weight, Integer year) {

    @Override
    public String toString() {
        return "\n['" + origin + '\'' + ",'" + target + '\'' + "," + weight + "]";
    }
}
