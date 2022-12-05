import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day1.txt"));
        List<Integer> caloriesPerCarrier = new ArrayList<>();

        int currentValue = 0;

        for (String line : lines) {

            if (line.isEmpty()) {
                caloriesPerCarrier.add(currentValue);
                currentValue = 0;
                continue;
            }
            currentValue += Integer.parseInt(line);
        }

        int currentHighest = -1;
        for (int i : caloriesPerCarrier) {
            if (i > currentHighest) {
                currentHighest = i;
            }
        }
        System.out.println(currentHighest);
        Collections.sort(caloriesPerCarrier);
        Collections.reverse(caloriesPerCarrier);

        System.out.println(caloriesPerCarrier.get(0) + caloriesPerCarrier.get(1) + caloriesPerCarrier.get(2));
    }

}
