import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day5 {
    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day3.txt"));

        List<Spot> listOfSpots = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            listOfSpots.add(new Spot());
        }

        fillBoxes(lines, listOfSpots);

        sortBoxes(listOfSpots);

        printBoxes(listOfSpots);

        for (String line : lines) {
            if (!line.startsWith("move")) {
                continue;
            }

            int amount = Integer.parseInt(line.substring(4, line.indexOf("from")).strip());
            int from = Integer.parseInt(line.substring(line.indexOf("from") + 4, line.indexOf("to")).strip()) - 1;
            int to = Integer.parseInt(line.substring(line.indexOf("to") + 2).strip()) - 1;


            listOfSpots.get(from).moveMultipleToAndRemove(amount, listOfSpots.get(to));
            /*for (int i = 0; i < amount; i++) {
                listOfSpots.get(to).addToBoxes(listOfSpots.get(from).getAndRemoveFromBox());
            }*/
        }
        printBoxes(listOfSpots);


        StringBuilder result = new StringBuilder();
        for (Spot spot : listOfSpots) {
            result.append((char) spot.boxes.get(spot.boxes.size() - 1));
        }
        System.out.println(result);
    }

    private static void printBoxes(List<Spot> listOfSpots) {
        for (Spot spot : listOfSpots) {
            System.out.println(spot.boxes);
        }
    }

    private static void sortBoxes(List<Spot> listOfSpots) {
        for (Spot spot : listOfSpots) {
            Collections.reverse(spot.boxes);
        }
    }

    private static void fillBoxes(List<String> lines, List<Spot> listOfSpots) {
        for (String line : lines) {

            if (line.isEmpty()) {
                break;
            }

            int index = 0;
            int count = 0;
            for (int i = 0; i < line.length(); i++) {
                if (count == 4) {
                    index++;
                    count = 0;
                }
                if (line.charAt(i) == ' ') {
                    count++;
                }
                if (line.charAt(i) == '[') {
                    listOfSpots.get(index).addToBoxes(line.charAt(++i));
                    count = 0;
                    index++;
                }
            }

        }
    }


    static class Spot {

        private final List<Character> boxes;

        public Spot() {
            this.boxes = new ArrayList<>();
        }

        public void moveMultipleToAndRemove(int amount, Spot spot) {

            List<Character> chars = new ArrayList<>();

            for (int i = 0; i < amount; i++) {
                chars.add(this.getAndRemoveFromBox());
            }
            for (int i = chars.size() - 1; i >= 0; i--) {
                spot.boxes.add(chars.get(i));
            }
        }

        public void addToBoxes(char character) {
            this.boxes.add(character);
        }

        public char getAndRemoveFromBox() {
            char ret = this.boxes.get(this.boxes.size() - 1);
            this.boxes.remove(this.boxes.size() - 1);
            return ret;
        }
    }

}
