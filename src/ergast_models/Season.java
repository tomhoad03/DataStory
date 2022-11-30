package ergast_models;

public class Season {
    private final Integer year;
    private final String url;
    private BoxPlot boxPlot;

    public Season(String csvLine) {
        String[] splitLine = csvLine.split(",");

        this.year = Integer.valueOf(splitLine[0]);
        this.url = splitLine[1];
        this.boxPlot = new BoxPlot(0.0, 0, 0, 0.0, 0, 0.0);
    }

    @Override
    public String toString() {
        return "Season{" +
                "seasonId=" + year +
                ", url='" + url + '\'' +
                '}';
    }

    public boolean hasBoxPlot() {
        return boxPlot.getMax() > 0;
    }

    public Integer getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }

    public BoxPlot getBoxPlot() {
        return boxPlot;
    }

    public void setBoxPlot(BoxPlot boxPlot) {
        this.boxPlot = boxPlot;
    }
}
