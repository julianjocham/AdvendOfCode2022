package days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 {

    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";
    public static final int MAX = 4000000;
    public static final int MIN = 0;

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day15.txt"));


        List<Coordinate> coordinates = new ArrayList<>();
        List<Sensor> sensors = new ArrayList<>();
        List<Coordinate> beacons = new ArrayList<>();

        int[][] sizes = new int[][]{{Integer.MAX_VALUE, Integer.MIN_VALUE}, {Integer.MAX_VALUE, Integer.MIN_VALUE}};

        for (String line : lines) {
            Coordinate s = new Coordinate(Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(","))), Integer.parseInt(line.substring(line.indexOf("y=") + 2, line.indexOf(":"))));

            if (!coordinateExistsAlready(s, coordinates)) {
                coordinates.add(s);
            }

            Coordinate b = new Coordinate(Integer.parseInt(line.substring(line.lastIndexOf("x=") + 2, line.lastIndexOf(","))), Integer.parseInt(line.substring(line.lastIndexOf("y=") + 2)));

            if (!coordinateExistsAlready(b, coordinates)) {
                coordinates.add(b);
                beacons.add(b);
            }
            Sensor e = new Sensor(s, b);
            e.manhattanDistance = calculateManhattanDistance(s, b);
            sensors.add(e);
        }

        for (Coordinate coordinate : coordinates) {

            if (coordinate.y < sizes[MIN][MIN]) {
                sizes[MIN][MIN] = coordinate.y;
            }
            if (coordinate.y > sizes[MIN][1]) {
                sizes[MIN][1] = coordinate.y;
            }
            if (coordinate.x < sizes[1][MIN]) {
                sizes[1][MIN] = coordinate.x;
            }
            if (coordinate.x > sizes[1][1]) {
                sizes[1][1] = coordinate.x;
            }

        }
        System.out.println("Part 1: " + calculateAmountOfPositionsNotContainingABeaconForRow(2000000, sensors, beacons, sizes));


        HashMap<Integer, ConcurrentHashMap<Integer, Character>> rows = fillAndGetRows(MIN, MAX);


        drawDistanceToClosestBeaconForEachSensorForEachRow(rows, sensors, beacons, sizes);

        Coordinate coordinate = findDistressBeacon(rows, MIN, MAX);

        System.out.println("Part 2: " + (coordinate.x * MAX) + coordinate.y);

    }

    public static Coordinate findDistressBeacon(HashMap<Integer, ConcurrentHashMap<Integer, Character>> rows, int min, int max) {

        Iterator<Integer> it = rows.keySet().iterator();

        while (it.hasNext()) {

            Integer row = it.next();

            if (row < min || row > max) {
                continue;
            } else {

                for (int i = min; i < max; i++) {
                    if (rows.get(row).get(i) == '.') {
                        return new Coordinate(i, row);
                    }
                }
            }
        }
        return null;
    }


    public static HashMap<Integer, ConcurrentHashMap<Integer, Character>> fillAndGetRows(int min, int max) {
        HashMap<Integer, ConcurrentHashMap<Integer, Character>> rows = new HashMap<>();

        int i = min;
        
        while (i < max) {
            ConcurrentHashMap<Integer, Character> integerCharacterHashMap = IntStream.range(min, max).boxed().collect(Collectors.toMap(j -> j, j -> '.', (a, b) -> b, ConcurrentHashMap::new));
            rows.put(i, integerCharacterHashMap);
            i++;
        }


        return rows;
    }


    public static void drawDistanceToClosestBeaconForEachSensorForEachRow(HashMap<Integer, ConcurrentHashMap<Integer, Character>> rows, List<Sensor> sensors, List<Coordinate> beacons, int[][] sizes) {

        for (int key : rows.keySet()) {
            for (Sensor sensor : sensors) {

                int distanceToRow = sensor.coordinate.y - key;

                if (distanceToRow < MIN) {
                    distanceToRow *= -1;
                }

                if (sensor.manhattanDistance >= distanceToRow) {

                    int amountOfOverlapForThisRow = 2 * sensor.manhattanDistance;
                    amountOfOverlapForThisRow -= 2 * distanceToRow;

                    for (int i = MIN; i <= amountOfOverlapForThisRow / 2; i++) {

                        int x = sensor.coordinate.x;
                        if (x + i <= sizes[1][1]) {

                            rows.get(key).put(x + i, '#');
                        }
                        if (x - i >= sizes[1][MIN]) {
                            rows.get(key).put(x - i, '#');
                        }
                    }
                }
            }
        }
        Iterator<ConcurrentHashMap<Integer, Character>> iter = rows.values().iterator();
        while (iter.hasNext()) {

            ConcurrentHashMap<Integer, Character> row = iter.next();

            Iterator<Integer> iterator = row.keySet().iterator();
            while (iterator.hasNext()) {
                int key = iterator.next();

                for (Coordinate beacon : beacons) {
                    if (beacon.y == key) {
                        row.put(beacon.x, 'B');
                    }

                }

            }
        }


    }


    public static int calculateAmountOfPositionsNotContainingABeaconForRow(int row, List<Sensor> sensors, List<Coordinate> beacons, int[][] sizes) {

        HashMap<Integer, Character> thisRow = new HashMap<>();

        for (int i = sizes[1][MIN]; i < sizes[1][1]; i++) {
            thisRow.put(i, '.');
        }


        for (Sensor sensor : sensors) {

            int distanceToRow = sensor.coordinate.y - row;

            if (distanceToRow < MIN) {
                distanceToRow *= -1;
            }

            if (sensor.manhattanDistance > distanceToRow) {

                int amountOfOverlapForThisRow = 2 * sensor.manhattanDistance;
                amountOfOverlapForThisRow -= 2 * distanceToRow;

                for (int i = MIN; i <= amountOfOverlapForThisRow / 2; i++) {

                    thisRow.put(sensor.coordinate.x + i, '#');
                    thisRow.put(sensor.coordinate.x - i, '#');
                }

            }

        }

        for (Coordinate beacon : beacons) {

            if (beacon.y == row) {
                thisRow.put(beacon.x, 'B');
            }

        }

        int count = MIN;


        Iterator<Character> iterator = thisRow.values().iterator();
        while (iterator.hasNext()) {
            Character entry = iterator.next();
            if (entry == '#') {
                count++;
            }
        }

        return count;

    }

    public static boolean coordinateExistsAlready(Coordinate c, List<Coordinate> coordinates) {

        for (Coordinate coord : coordinates) {

            if (coord.x == c.x && coord.y == c.y) {
                return true;
            }

        }
        return false;
    }

    public static int calculateManhattanDistance(Coordinate s, Coordinate b) {

        int y = s.y - b.y;
        int x = s.x - b.x;

        if (x < MIN) {
            x *= -1;
        }
        if (y < MIN) {
            y *= -1;
        }

        return x + y;

    }


    static class Coordinate {

        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return
                    "[x=" + x +
                            ", y=" + y + "]"
                    ;
        }
    }

    static class Sensor {

        Coordinate coordinate;
        Coordinate nearestBeacon;
        int manhattanDistance;

        public Sensor(Coordinate coordinate, Coordinate nearestBeacon) {
            this.coordinate = coordinate;
            this.nearestBeacon = nearestBeacon;
        }

        @Override
        public String toString() {
            return "Sensor{" +
                    "coordinate=" + coordinate +
                    ", nearestBeacon=" + nearestBeacon +
                    '}';
        }
    }

}
