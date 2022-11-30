import ergast_models.LapTime;
import ergast_models.Race;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
    }
}

