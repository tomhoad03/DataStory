package ergast_models;

public class RaceBoxPlot {
    private final Integer max;
    private final Integer UQ;
    private final Integer median;
    private final Integer LQ;
    private final Integer min;

    public RaceBoxPlot(Integer max, Integer UQ, Integer median, Integer LQ, Integer min) {
        this.max = max;
        this.UQ = UQ;
        this.median = median;
        this.LQ = LQ;
        this.min = min;
    }

    @Override
    public String toString() {
        return "RaceBoxPlot{" +
                "max=" + max +
                ", UQ=" + UQ +
                ", median=" + median +
                ", LQ=" + LQ +
                ", min=" + min +
                '}';
    }

    public Integer getMax() {
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

    public Integer getMin() {
        return min;
    }
}
