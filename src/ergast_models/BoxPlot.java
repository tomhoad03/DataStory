package ergast_models;

public class BoxPlot {
    private final Double max;
    private final Integer UQ;
    private final Integer median;
    private final Double mean;
    private final Integer LQ;
    private final Double min;

    public BoxPlot(Double max, Integer UQ, Integer median, Double mean, Integer LQ, Double min) {
        this.max = max;
        this.UQ = UQ;
        this.median = median;
        this.mean = mean;
        this.LQ = LQ;
        this.min = min;
    }

    @Override
    public String toString() {
        return "BoxPlot{" +
                "max=" + max +
                ", UQ=" + UQ +
                ", median=" + median +
                ", mean=" + mean +
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

    public Double getMean() {
        return mean;
    }

    public Integer getLQ() {
        return LQ;
    }

    public Double getMin() {
        return min;
    }
}
