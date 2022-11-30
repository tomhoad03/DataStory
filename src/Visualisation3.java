import ergast_models.LapTime;
import ergast_models.Race;
import ergast_models.RaceBoxPlot;

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

        // Calculate box plots
        FileWriter racePlotWriter = new FileWriter("computed_dataset\\box_plots.csv");
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
                race.setBoxPlot(new RaceBoxPlot(raceLapTimes.get(totalLaps - 1).getMilliseconds(),
                        raceLapTimes.get(UQ).getMilliseconds(),
                        raceLapTimes.get(median).getMilliseconds(),
                        raceLapTimes.get(LQ).getMilliseconds(),
                        raceLapTimes.get(0).getMilliseconds()));
            } else {
                race.setBoxPlot(new RaceBoxPlot(0,0,0,0,0));
            }

            if (race.hasBoxPlot()) {
                RaceBoxPlot raceBoxPlot = race.getBoxPlot();
                racePlotWriter.write("\n" + race.getRaceId() + "," + raceBoxPlot.getMax() + "," + raceBoxPlot.getUQ() + "," + raceBoxPlot.getMedian() + "," + raceBoxPlot.getLQ() + "," + raceBoxPlot.getMin());
            }
        }
        racePlotWriter.close();
    }
}

