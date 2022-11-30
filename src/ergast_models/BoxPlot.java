package ergast_models;

public class BoxPlot {
    private final Double max;
    private final Integer UQ;
    private final Integer median;
    private final Integer LQ;
    private final Double min;

    public BoxPlot(Double max, Integer UQ, Integer median, Integer LQ, Double min) {
        this.max = max;
        this.UQ = UQ;
        this.median = median;
        this.LQ = LQ;
        this.min = min;
    }

    @Override
    public String toString() {
        return "BoxPlot{" +
                "max=" + max +
                ", UQ=" + UQ +
                ", median=" + median +
                ", LQ=" + LQ +
                ", min=" + min +
                '}';
    }

    public Double getMax() {
        return max;
    }

    public Integer getUQ() {
        return UQ;
    }

    public Integer getMedian() {
        return median;
    }

    public Integer getLQ() {
        return LQ;
    }

    public Double getMin() {
        return min;
    }
}
