import ergast_models.LapTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Visualisation3 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner lapTimesScanner = new Scanner(new File("ergast_dataset\\lap_times.csv"));
        ArrayList<LapTime> lapTimes = new ArrayList<>();

        lapTimesScanner.nextLine();
        while (lapTimesScanner.hasNextLine()) {
            String lapTime = lapTimesScanner.nextLine();
            lapTimes.add(new LapTime(lapTime));
        }

        System.out.println(lapTimes);
    }
}

