import ergast_models.BoxPlot;
import ergast_models.LapTime;
import ergast_models.Race;
import ergast_models.Season;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Visualisation3 {
    public static void main(String[] args) throws IOException {
        // Lap Times
        Scanner lapTimesScanner = new Scanner(new File("ergast_dataset\\lap_times.csv"));
        ArrayList<LapTime> lapTimes = new ArrayList<>();

        lapTimesScanner.nextLine();
        while (lapTimesScanner.hasNextLine()) {
            String lapTime = lapTimesScanner.nextLine();
            lapTimes.add(new LapTime(lapTime));
        }
        lapTimesScanner.close();

        // Races
        Scanner racesScanner = new Scanner(new File("ergast_dataset\\races.csv"));
        ArrayList<Race> races = new ArrayList<>();

        racesScanner.nextLine();
        while (racesScanner.hasNextLine()) {
            String race = racesScanner.nextLine();
            races.add(new Race(race));
        }
        racesScanner.close();

        // Seasons
        Scanner seasonsScanner = new Scanner(new File("ergast_dataset\\seasons.csv"));
        ArrayList<Season> seasons = new ArrayList<>();

        seasonsScanner.nextLine();
        while (seasonsScanner.hasNextLine()) {
            String season = seasonsScanner.nextLine();
            seasons.add(new Season(season));
        }
        seasonsScanner.close();

        // Calculate box plots
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
                BoxPlot raceBoxPlot = season.getBoxPlot();
                seasonPlotWriter.write("\n" + season.getYear() + "," + raceBoxPlot.getMax() + "," + raceBoxPlot.getUQ() + "," + raceBoxPlot.getMedian() + "," + raceBoxPlot.getLQ() + "," + raceBoxPlot.getMin());
            }
        }
        seasonPlotWriter.close();
    }
}
