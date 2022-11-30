import ergast_models.LapTime;
import ergast_models.Race;
import ergast_models.RaceBoxPlot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Predicate;

public class Visualisation3 {
    public static void main(String[] args) throws FileNotFoundException {
        // Lap Times
        Scanner lapTimesScanner = new Scanner(new File("ergast_dataset\\lap_times.csv"));
        ArrayList<LapTime> lapTimes = new ArrayList<>();

        lapTimesScanner.nextLine();
        while (lapTimesScanner.hasNextLine()) {
            String lapTime = lapTimesScanner.nextLine();
            lapTimes.add(new LapTime(lapTime));
        }

        // Races
        Scanner racesScanner = new Scanner(new File("ergast_dataset\\races.csv"));
        ArrayList<Race> races = new ArrayList<>();

        racesScanner.nextLine();
        while (racesScanner.hasNextLine()) {
            String race = racesScanner.nextLine();
            races.add(new Race(race));
        }

        System.out.println(lapTimes);
        System.out.println(races);
        System.out.println("Laps: " + lapTimes.size() + " Races: " + races.size());

        // Calculate box plots
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
                System.out.println(race.getBoxPlot());
            }
        }
    }
}

