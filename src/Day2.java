import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day2 {

    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day2.txt"));
        int sum = getFirstPart(lines);
        System.out.println(sum);
        int newSum = getSecondPart(lines);
        System.out.println(newSum);
    }

    private static int getSecondPart(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            char leftChar = line.charAt(0);
            char rightChar = line.charAt(2);

            if (rightChar == 'X') {
                switch (leftChar) {
                    case 'A' -> sum += 3;
                    case 'B' -> sum += 1;
                    case 'C' -> sum += 2;
                }
            }
            if (rightChar == 'Y') {
                sum += 3;
                switch (leftChar) {
                    case 'A' -> sum += 1;
                    case 'B' -> sum += 2;
                    case 'C' -> sum += 3;
                }
            }
            if (rightChar == 'Z') {
                sum += 6;
                switch (leftChar) {
                    case 'A' -> sum += 2;
                    case 'B' -> sum += 3;
                    case 'C' -> sum += 1;
                }
            }
        }
        return sum;
    }

    private static int getFirstPart(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            char leftChar = line.charAt(0);
            char rightChar = line.charAt(2);

            if (rightChar == 'X') {
                sum += 1;
                if (leftChar == 'C') {
                    sum += 6;
                }
                if (leftChar == 'A') {
                    sum += 3;
                }
            }
            if (rightChar == 'Y') {
                sum += 2;
                if (leftChar == 'A') {
                    sum += 6;
                }
                if (leftChar == 'B') {
                    sum += 3;
                }
            }
            if (rightChar == 'Z') {
                sum += 3;
                if (leftChar == 'B') {
                    sum += 6;
                }
                if (leftChar == 'C') {
                    sum += 3;
                }
            }
        }
        return sum;
    }
}
