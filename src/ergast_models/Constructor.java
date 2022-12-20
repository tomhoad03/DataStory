package ergast_models;

public class Constructor {
    private final Integer constructorId;
    private final String name;

    public Constructor(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.constructorId = Integer.valueOf(splitLine[0]);
        this.name = splitLine[2];
    }

    @Override
    public String toString() {
        return "Constructor{" +
                "constructorId=" + constructorId +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getConstructorId() {
        return constructorId;
    }

    public String getName() {
        return name;
    }
}
