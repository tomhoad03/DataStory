import ergast_models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class VisualisationComputer {
    public static ArrayList<LapTime> lapTimes = new ArrayList<>();
    public static ArrayList<Race> races = new ArrayList<>();
    public static ArrayList<Season> seasons = new ArrayList<>();
    public static ArrayList<Result> results = new ArrayList<>();
    public static ArrayList<RaceOvertake> raceOvertakes = new ArrayList<>();
    public static ArrayList<DriverStanding> driverStandings = new ArrayList<>();
    public static ArrayList<ConstructorStanding> constructorStandings = new ArrayList<>();
    public static ArrayList<Constructor> constructors = new ArrayList<>();
    public static ArrayList<Driver> drivers = new ArrayList<>();
    public static ArrayList<Champion> constructorChampions = new ArrayList<>();
    public static ArrayList<Champion> driverChampions = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readLapTimes();
        readRaces();
        readSeasons();
        readResults();
        readRaceOvertakes();
        readDriverStandings();
        readConstructorStandings();
        readConstructors();
        readDrivers();

        calculateRaceTimesBoxPlots();
        calculateSeasonTimesBoxPlots();
        calculateNormalisedResults();
        calculateRaceOvertakes();
        calculateChampionships();

        createVisualisation1();
    }

    // Lap Times
    public static void readLapTimes() throws FileNotFoundException {
        Scanner lapTimesScanner = new Scanner(new File("ergast_dataset\\lap_times.csv"));
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
        seasonsScanner.nextLine();

        while (seasonsScanner.hasNextLine()) {
            String season = seasonsScanner.nextLine();
            seasons.add(new Season(season));
        }
        seasonsScanner.close();
    }

    // Results
    public static void readResults() throws FileNotFoundException {
        Scanner resultsScanner = new Scanner(new File("ergast_dataset\\results.csv"));
        resultsScanner.nextLine();

        while (resultsScanner.hasNextLine()) {
            String result = resultsScanner.nextLine();
            results.add(new Result(result));
        }
        resultsScanner.close();
    }

    // Race overtakes
    public static void readRaceOvertakes() throws FileNotFoundException {
        Scanner raceOvertakesScanner = new Scanner(new File("overtaking_dataset\\race_overtakes.csv"));
        raceOvertakesScanner.nextLine();

        while (raceOvertakesScanner.hasNextLine()) {
            String raceOvertake = raceOvertakesScanner.nextLine();
            RaceOvertake model = new RaceOvertake(raceOvertake);
            if (!model.getName().equals("Season") && !model.getName().contains("Sprint")) {
                raceOvertakes.add(model);
            }
        }
        raceOvertakesScanner.close();
    }

    // Race driver standings
    public static void readDriverStandings() throws FileNotFoundException {
        Scanner driverStandingsScanner = new Scanner(new File("ergast_dataset\\driver_standings.csv"));
        driverStandingsScanner.nextLine();

        while (driverStandingsScanner.hasNextLine()) {
            String driverStanding = driverStandingsScanner.nextLine();
            driverStandings.add(new DriverStanding(driverStanding));
        }
        driverStandingsScanner.close();
    }

    // Race constructor standings
    public static void readConstructorStandings() throws FileNotFoundException {
        Scanner constructorStandingsScanner = new Scanner(new File("ergast_dataset\\constructor_standings.csv"));
        constructorStandingsScanner.nextLine();

        while (constructorStandingsScanner.hasNextLine()) {
            String constructorStanding = constructorStandingsScanner.nextLine();
            constructorStandings.add(new ConstructorStanding(constructorStanding));
        }
        constructorStandingsScanner.close();
    }

    // Constructors
    public static void readConstructors() throws FileNotFoundException {
        Scanner constructorsScanner = new Scanner(new File("ergast_dataset\\constructors.csv"));
        constructorsScanner.nextLine();

        while (constructorsScanner.hasNextLine()) {
            String constructor = constructorsScanner.nextLine();
            constructors.add(new Constructor(constructor));
        }
        constructorsScanner.close();
    }

    // Drivers
    public static void readDrivers() throws FileNotFoundException {
        Scanner driversScanner = new Scanner(new File("ergast_dataset\\Drivers.csv"));
        driversScanner.nextLine();

        while (driversScanner.hasNextLine()) {
            String driver = driversScanner.nextLine();
            drivers.add(new Driver(driver));
        }
        driversScanner.close();
    }

    // Calculate race box plots
    public static void calculateRaceTimesBoxPlots() throws IOException {
        FileWriter racePlotWriter = new FileWriter("computed_dataset\\race_times_box_plots.csv");
        racePlotWriter.write("raceId,max,UQ,median,mean,LQ,min");

        for (Race race : races) {
            ArrayList<LapTime> raceLapTimes = new ArrayList<>();
            int totalTime = 0;

            for (LapTime lapTime : lapTimes) {
                if (lapTime.getRaceId().equals(race.getRaceId())) {
                    raceLapTimes.add(lapTime);
                    totalTime += lapTime.getMilliseconds();
                }
            }

            raceLapTimes.sort(Comparator.comparingInt(LapTime::getMilliseconds));
            int totalLaps = raceLapTimes.size();

            if (totalLaps > 0) {
                int LQ = raceLapTimes.get(totalLaps / 4).getMilliseconds();
                int median = raceLapTimes.get(totalLaps / 2).getMilliseconds();
                int UQ = raceLapTimes.get((totalLaps / 4) * 3).getMilliseconds();
                double IQR = UQ - LQ;
                double max = UQ + (1.5 * IQR);
                double min = LQ - (1.5 * IQR);
                double mean = (double) totalTime / (double) totalLaps;

                race.setBoxPlot(new BoxPlot(max, UQ, median, mean, LQ, min));
            } else {
                race.setBoxPlot(new BoxPlot(0.0, 0, 0, 0.0, 0, 0.0));
            }

            if (race.hasBoxPlot()) {
                BoxPlot raceBoxPlot = race.getBoxPlot();
                racePlotWriter.write("\n" + race.getRaceId() + "," + raceBoxPlot.getMax() + "," + raceBoxPlot.getUQ() + "," + raceBoxPlot.getMedian() + "," + raceBoxPlot.getMean() + "," + raceBoxPlot.getLQ() + "," + raceBoxPlot.getMin());
            }
        }
        racePlotWriter.close();
    }

    // Calculate season plots
    public static void calculateSeasonTimesBoxPlots() throws IOException {
        FileWriter seasonPlotWriter = new FileWriter("computed_dataset\\season_times_box_plots.csv");
        seasonPlotWriter.write("year,max,UQ,median,mean,LQ,min");

        for (Season season : seasons) {
            ArrayList<LapTime> seasonLapTimes = new ArrayList<>();
            int totalTime = 0;

            for (Race race : races) {
                if (race.getYear().equals(season.getYear())) {
                    for (LapTime lapTime : lapTimes) {
                        if (lapTime.getRaceId().equals(race.getRaceId())) {
                            seasonLapTimes.add(lapTime);
                            totalTime += lapTime.getMilliseconds();
                        }
                    }
                }
            }

            seasonLapTimes.sort(Comparator.comparingInt(LapTime::getMilliseconds));
            int totalLaps = seasonLapTimes.size();

            if (totalLaps > 0) {
                int LQ = seasonLapTimes.get(totalLaps / 4).getMilliseconds();
                int median = seasonLapTimes.get(totalLaps / 2).getMilliseconds();
                int UQ = seasonLapTimes.get((totalLaps / 4) * 3).getMilliseconds();
                double IQR = UQ - LQ;
                double max = UQ + (1.5 * IQR);
                double min = LQ - (1.5 * IQR);
                double mean = (double) totalTime / (double) totalLaps;

                season.setBoxPlot(new BoxPlot(max, UQ, median, mean, LQ, min));
            } else {
                season.setBoxPlot(new BoxPlot(0.0, 0, 0, 0.0, 0, 0.0));
            }

            if (season.hasBoxPlot()) {
                BoxPlot seasonBoxPlot = season.getBoxPlot();
                seasonPlotWriter.write("\n" + season.getYear() + "," + seasonBoxPlot.getMax() + "," + seasonBoxPlot.getUQ() + "," + seasonBoxPlot.getMedian() + "," + seasonBoxPlot.getMean() + "," + seasonBoxPlot.getLQ() + "," + seasonBoxPlot.getMin());
            }
        }
        seasonPlotWriter.close();
    }

    // Calculate normalised results
    public static void calculateNormalisedResults() throws IOException {
        FileWriter normalisedResultsWriter = new FileWriter("computed_dataset\\normalised_results.csv");
        normalisedResultsWriter.write("resultId,raceId,driverId,constructorId,position,points,normalisedPoints,laps,rank");

        for (Result result : results) {
            if (result.getLaps() > 2) {
                int fastestLapPoint = 0;
                if (result.getRank() == 1) {
                    fastestLapPoint++;
                }

                switch (result.getPosition()) {
                    case 1 -> result.setNormalisedPoints(25 + fastestLapPoint);
                    case 2 -> result.setNormalisedPoints(18 + fastestLapPoint);
                    case 3 -> result.setNormalisedPoints(15 + fastestLapPoint);
                    case 4 -> result.setNormalisedPoints(12 + fastestLapPoint);
                    case 5 -> result.setNormalisedPoints(10 + fastestLapPoint);
                    case 6 -> result.setNormalisedPoints(8 + fastestLapPoint);
                    case 7 -> result.setNormalisedPoints(6 + fastestLapPoint);
                    case 8 -> result.setNormalisedPoints(4 + fastestLapPoint);
                    case 9 -> result.setNormalisedPoints(2 + fastestLapPoint);
                    case 10 -> result.setNormalisedPoints(1 + fastestLapPoint);
                }
            } else {
                result.setNormalisedPoints(0);
            }

            normalisedResultsWriter.write("\n" + result.getResultId() + "," + result.getRaceId() + "," + result.getDriverId() + "," + result.getConstructorId() + "," + result.getPosition() + "," + result.getPoints() + "," + result.getNormalisedPoints() + "," + result.getLaps() + "," + result.getRank());
        }
        
        normalisedResultsWriter.close();
    }

    // Calculate race overtakes
    public static void calculateRaceOvertakes() throws IOException {
        FileWriter raceOvertakesWriter = new FileWriter("computed_dataset\\race_overtakes.csv");
        raceOvertakesWriter.write("raceId,year,round,circuitId,name,url,overtakes");

        races.sort(Comparator.comparingInt(Race::getYear));
        int count = 0;

        for (Race race : races) {
            RaceOvertake raceOvertake = raceOvertakes.get(count);

            if (race.getYear().equals(raceOvertake.getYear())) {
                race.setOvertakes(raceOvertake.getOvertakes());
                count++;

                raceOvertakesWriter.write("\n" + race.getRaceId() + "," + race.getYear() + "," + race.getRound() + "," + race.getCircuitId() + "," + race.getName() + "," + race.getUrl() + "," + race.getOvertakes());
            }
        }

        raceOvertakesWriter.close();
    }

    // Calculate championships won
    public static void calculateChampionships() throws IOException {
        FileWriter driverChampionshipsWriter = new FileWriter("computed_dataset\\driver_championships.csv");
        driverChampionshipsWriter.write("year,driverId,driverChampionshipWins,driverChampionshipPoints");

        FileWriter constructorChampionshipsWriter = new FileWriter("computed_dataset\\constructors_championships.csv");
        constructorChampionshipsWriter.write("year,constructorId,constructorChampionshipWins,constructorChampionshipPoints");

        races.sort(Comparator.comparingInt(Race::getRound));
        races.sort(Comparator.comparingInt(Race::getYear));
        Collections.reverse(races);

        ArrayList<Integer> finalRounds = new ArrayList<>();
        int year = 2023;

        for (Race race : races) {
            if (race.getYear() < year) {
                finalRounds.add(race.getRaceId());
                year--;
            }
        }

        ArrayList<String> driverChampionsStrings = new ArrayList<>();
        ArrayList<String> constructorChampionsStrings = new ArrayList<>();

        for (DriverStanding driverStanding : driverStandings) {
            if (driverStanding.getPosition() == 1 && finalRounds.contains(driverStanding.getRaceId())) {
                for (Race race : races) {
                    if (race.getRaceId().equals(driverStanding.getRaceId())) {
                        driverChampions.add(new Champion(driverStanding.getDriverId(), race.getYear(), driverStanding.getWins(), driverStanding.getPoints()));
                        driverChampionsStrings.add("\n" + race.getYear() + "," + driverStanding.getDriverId()  + "," + driverStanding.getWins() + "," + driverStanding.getPoints());
                        year++;
                        break;
                    }
                }
            }
        }

        // 776 to 840 missing from constructors standings (1950-1956)
        year = 1958;

        for (ConstructorStanding constructorStanding : constructorStandings) {
            if (constructorStanding.getPosition() == 1 && finalRounds.contains(constructorStanding.getRaceId())) {
                for (Race race : races) {
                    if (race.getRaceId().equals(constructorStanding.getRaceId())) {
                        constructorChampions.add(new Champion(constructorStanding.getConstructorId(), race.getYear(), constructorStanding.getWins(), constructorStanding.getPoints()));
                        constructorChampionsStrings.add("\n" + race.getYear() + "," + constructorStanding.getConstructorId()  + "," + constructorStanding.getWins() + "," + constructorStanding.getPoints());
                        year++;
                        break;
                    }
                }
            }
        }

        for (String driverChampion : driverChampionsStrings) {
            driverChampionshipsWriter.write(driverChampion);
        }
        for (String constructorChampion : constructorChampionsStrings) {
            constructorChampionshipsWriter.write(constructorChampion);
        }
        driverChampionshipsWriter.close();
        constructorChampionshipsWriter.close();
    }

    public static void createVisualisation1() {
        ArrayList<SankeyEntry> sankeyEntries = new ArrayList<>();

        for (Champion driverChampion : driverChampions) {
            outer:
            for (Result result : results) {
                if (result.getDriverId().equals(driverChampion.id())) {
                    for (Race race : races) {
                        if (race.getRaceId().equals(result.getRaceId()) && race.getYear().equals(driverChampion.year())) {
                            Driver driver = null;
                            Constructor constructor = null;

                            for (Driver foundDriver : drivers) {
                                if (foundDriver.getDriverId().equals(driverChampion.id())) {
                                    driver = foundDriver;
                                    break;
                                }
                            }

                            for (Constructor foundConstructor : constructors) {
                                if (foundConstructor.getConstructorId().equals(result.getConstructorId())) {
                                    constructor = foundConstructor;
                                    break;
                                }
                            }

                            String constructorName = constructor.getName();
                            String driverName = switch (driver.getSurname()) {
                                case "Brabham" -> "J Brabham";
                                case "Räikkönen" -> "Raikkonen";
                                case "Häkkinen" -> "Hakkinen";
                                default -> driver.getSurname();
                            };

                            sankeyEntries.add(new SankeyEntry(constructorName, driverName, 1, race.getYear()));

                            String eraName = "null";
                            if (race.getYear() > 2013) {
                                eraName = "Turbo Hybrid V6s";
                            } else if (race.getYear() > 2005) {
                                eraName = "Hybrid V8s";
                            } else if (race.getYear() > 1994) {
                                eraName = "V8s";
                            } else if (race.getYear() > 1988) {
                                eraName = "V10s";
                            } else if (race.getYear() > 1986) {
                                eraName = "NAs";
                            } else if (race.getYear() > 1965) {
                                eraName = "Sub Eras";
                            } else if (race.getYear() > 1960) {
                                eraName = "Rear Engines";
                            } else if (race.getYear() > 1957) {
                                eraName = "Mid Engines";
                            } else if (race.getYear() >= 1950) {
                                eraName = "Front Engines";
                            }

                            sankeyEntries.add(new SankeyEntry(driverName, eraName, 1, race.getYear()));
                            break outer;
                        }
                    }
                }
            }
        }

        sankeyEntries.sort(Comparator.comparingInt(SankeyEntry::year));
        boolean test = true;

        while (test) {
            ArrayList<SankeyEntry> loopSankeyEntries = new ArrayList<>(sankeyEntries);
            test = false;
            outer:
            for (SankeyEntry sankeyEntryA : loopSankeyEntries) {
                for (SankeyEntry sankeyEntryB : loopSankeyEntries) {
                    if (!sankeyEntryA.year().equals(sankeyEntryB.year())) {
                        if (sankeyEntryA.origin().equals(sankeyEntryB.origin()) && sankeyEntryA.target().equals(sankeyEntryB.target())) {
                            sankeyEntries.remove(sankeyEntryA);
                            sankeyEntries.remove(sankeyEntryB);
                            sankeyEntries.add(new SankeyEntry(sankeyEntryA.origin(), sankeyEntryA.target(), sankeyEntryA.weight() + sankeyEntryB.weight(), sankeyEntryA.year()));
                            test = true;
                            break outer;
                        }
                    }
                }
            }
        }

        sankeyEntries.sort(Comparator.comparingInt(SankeyEntry::year));
        // System.out.println(sankeyEntries);

        ArrayList<SankeyEntry> sankeyEntries2 = new ArrayList<>();

        for (Champion constructorChampion : constructorChampions) {
            int driverA = -1;
            int driverB = -1;

            outer:
            for (Result result : results) {
                if (result.getConstructorId().equals(constructorChampion.id())) {
                    for (Race race : races) {
                        if (race.getRaceId().equals(result.getRaceId()) && race.getYear().equals(constructorChampion.year()) && (driverA != result.getDriverId() || driverA == -1)) {
                            Driver driver = null;
                            Constructor constructor = null;

                            for (Driver foundDriver : drivers) {
                                if (foundDriver.getDriverId().equals(result.getDriverId())) {
                                    if (driverA == -1) {
                                        driverA = foundDriver.getDriverId();
                                        driver = foundDriver;
                                    } else {
                                        driverB = foundDriver.getDriverId();
                                        driver = foundDriver;
                                    }
                                    break;
                                }
                            }

                            for (Constructor foundConstructor : constructors) {
                                if (foundConstructor.getConstructorId().equals(result.getConstructorId())) {
                                    constructor = foundConstructor;
                                    break;
                                }
                            }

                            String constructorName = constructor.getName();
                            String driverName = switch (driver.getSurname()) {
                                case "Brabham" -> "J Brabham";
                                case "Räikkönen" -> "Raikkonen";
                                case "Häkkinen" -> "Hakkinen";
                                case "McLaren" -> "B McLaren";
                                case "Pérez" -> "Perez";
                                default -> driver.getSurname();
                            };

                            sankeyEntries2.add(new SankeyEntry(driverName, constructorName, 1, race.getYear()));

                            String eraName = "null";
                            if (race.getYear() > 2013) {
                                eraName = "Turbo Hybrid V6s";
                            } else if (race.getYear() > 2005) {
                                eraName = "Hybrid V8s";
                            } else if (race.getYear() > 1994) {
                                eraName = "V8s";
                            } else if (race.getYear() > 1988) {
                                eraName = "V10s";
                            } else if (race.getYear() > 1986) {
                                eraName = "NAs";
                            } else if (race.getYear() > 1965) {
                                eraName = "Sub Eras";
                            } else if (race.getYear() > 1960) {
                                eraName = "Rear Engines";
                            } else if (race.getYear() > 1957) {
                                eraName = "Mid Engines";
                            } else if (race.getYear() >= 1950) {
                                eraName = "Front Engines";
                            }

                            sankeyEntries2.add(new SankeyEntry(constructorName, eraName, 1, race.getYear()));

                            if (driverB == -1) {
                                break;
                            } else {
                                break outer;
                            }
                        }
                    }
                }
            }
        }

        sankeyEntries2.sort(Comparator.comparingInt(SankeyEntry::year));
        test = true;

        while (test) {
            ArrayList<SankeyEntry> loopSankeyEntries2 = new ArrayList<>(sankeyEntries2);
            test = false;
            outer:
            for (SankeyEntry sankeyEntryA : loopSankeyEntries2) {
                for (SankeyEntry sankeyEntryB : loopSankeyEntries2) {
                    if (!sankeyEntryA.year().equals(sankeyEntryB.year())) {
                        if (sankeyEntryA.origin().equals(sankeyEntryB.origin()) && sankeyEntryA.target().equals(sankeyEntryB.target())) {
                            sankeyEntries2.remove(sankeyEntryA);
                            sankeyEntries2.remove(sankeyEntryB);
                            sankeyEntries2.add(new SankeyEntry(sankeyEntryA.origin(), sankeyEntryA.target(), sankeyEntryA.weight() + sankeyEntryB.weight(), sankeyEntryA.year()));
                            test = true;
                            break outer;
                        }
                    }
                }
            }
        }

        sankeyEntries2.sort(Comparator.comparingInt(SankeyEntry::year));
        System.out.println(sankeyEntries2);
    }
}