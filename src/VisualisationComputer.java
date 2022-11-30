import ergast_models.BoxPlot;
import ergast_models.LapTime;
import ergast_models.Race;
import ergast_models.Season;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class VisualisationComputer {
    public static ArrayList<LapTime> lapTimes;
    public static ArrayList<Race> races = new ArrayList<>();
    public static ArrayList<Season> seasons = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readLapTimes();
        readRaces();
        readSeasons();
        calculateRacePointsBoxPlots();
        calculateSeasonPointsBoxPlots();
    }

    // Lap Times
    public static void readLapTimes() throws FileNotFoundException {
        Scanner lapTimesScanner = new Scanner(new File("ergast_dataset\\lap_times.csv"));
        lapTimes = new ArrayList<>();

        lapTimesScanner.nextLine();
        while (lapTimesScanner.hasNextLine()) {
            String lapTime = lapTimesScanner.nextLine();
            lapTimes.add(new LapTime(lapTime));
        }
        lapTimesScanner.close();
    }

    // Races
    public static void readRaces() throws FileNotFoundException {
        Scanner racesScanner = new Scanner(new File("ergast_dataset\\races.csv"));
        races = new ArrayList<>();

        racesScanner.nextLine();
        while (racesScanner.hasNextLine()) {
            String race = racesScanner.nextLine();
            races.add(new Race(race));
        }
        racesScanner.close();
    }

    // Seasons
    public static void readSeasons() throws FileNotFoundException {
        Scanner seasonsScanner = new Scanner(new File("ergast_dataset\\seasons.csv"));
        seasons = new ArrayList<>();

        seasonsScanner.nextLine();
        while (seasonsScanner.hasNextLine()) {
            String season = seasonsScanner.nextLine();
            seasons.add(new Season(season));
        }
        seasonsScanner.close();
    }

    // Calculate race box plots
    public static void calculateRacePointsBoxPlots() throws IOException {
        FileWriter racePlotWriter = new FileWriter("computed_dataset\\race_times_box_plots.csv");
        racePlotWriter.write("raceId,max,UQ,median,LQ,min");

        for (Race race : races) {
            ArrayList<LapTime> raceLapTimes = new ArrayList<>();
            for (LapTime lapTime : lapTimes) {
                if (lapTime.getRaceId().equals(race.getRaceId())) {
                    raceLapTimes.add(lapTime);
                }
            }

            raceLapTimes.sort(Comparator.comparingInt(LapTime::getMilliseconds));
            int totalLaps = raceLapTimes.size();

            if (totalLaps > 0) {
                int LQ = totalLaps / 4, median = LQ * 2, UQ = LQ * 3;
                double IQR = raceLapTimes.get(UQ).getMilliseconds() - raceLapTimes.get(LQ).getMilliseconds();
                double max = raceLapTimes.get(UQ).getMilliseconds() + (1.5 * IQR), min = raceLapTimes.get(LQ).getMilliseconds() - (1.5 * IQR);
                race.setBoxPlot(new BoxPlot(max,
                        raceLapTimes.get(UQ).getMilliseconds(),
                        raceLapTimes.get(median).getMilliseconds(),
                        raceLapTimes.get(LQ).getMilliseconds(),
                        min));
            } else {
                race.setBoxPlot(new BoxPlot(0.0, 0, 0, 0, 0.0));
            }

            if (race.hasBoxPlot()) {
                BoxPlot raceBoxPlot = race.getBoxPlot();
                racePlotWriter.write("\n" + race.getRaceId() + "," + raceBoxPlot.getMax() + "," + raceBoxPlot.getUQ() + "," + raceBoxPlot.getMedian() + "," + raceBoxPlot.getLQ() + "," + raceBoxPlot.getMin());
            }
        }
        racePlotWriter.close();
    }

    // Calculate season plots
    public static void calculateSeasonPointsBoxPlots() throws IOException {
        FileWriter seasonPlotWriter = new FileWriter("computed_dataset\\season_times_box_plots.csv");
        seasonPlotWriter.write("year,max,UQ,median,LQ,min");

        for (Season season : seasons) {
            ArrayList<LapTime> seasonLapTimes = new ArrayList<>();
            for (Race race : races) {
                if (race.getYear().equals(season.getYear())) {
                    for (LapTime lapTime : lapTimes) {
                        if (lapTime.getRaceId().equals(race.getRaceId())) {
                            seasonLapTimes.add(lapTime);
                        }
                    }
                }
            }

            seasonLapTimes.sort(Comparator.comparingInt(LapTime::getMilliseconds));
            int totalLaps = seasonLapTimes.size();

            if (totalLaps > 0) {
                int LQ = totalLaps / 4, median = LQ * 2, UQ = LQ * 3;
                double IQR = seasonLapTimes.get(UQ).getMilliseconds() - seasonLapTimes.get(LQ).getMilliseconds();
                double max = seasonLapTimes.get(UQ).getMilliseconds() + (1.5 * IQR), min = seasonLapTimes.get(LQ).getMilliseconds() - (1.5 * IQR);
                season.setBoxPlot(new BoxPlot(max,
                        seasonLapTimes.get(UQ).getMilliseconds(),
                        seasonLapTimes.get(median).getMilliseconds(),
                        seasonLapTimes.get(LQ).getMilliseconds(),
                        min));
            } else {
                season.setBoxPlot(new BoxPlot(0.0, 0, 0, 0, 0.0));
            }

            if (season.hasBoxPlot()) {
                BoxPlot seasonBoxPlot = season.getBoxPlot();
                seasonPlotWriter.write("\n" + season.getYear() + "," + seasonBoxPlot.getMax() + "," + seasonBoxPlot.getUQ() + "," + seasonBoxPlot.getMedian() + "," + seasonBoxPlot.getLQ() + "," + seasonBoxPlot.getMin());
            }
        }
        seasonPlotWriter.close();
    }
}

