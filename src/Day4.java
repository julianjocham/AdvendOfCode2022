import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day4 {

    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day4.txt"));

        int count = 0;

        for (String line : lines) {
            String[] strings = line.split("[,-]");
            int[] ints = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                ints[i] = Integer.parseInt(strings[i]);
            }
/*
            if (ints[0] >= ints[2] && ints[1] <= ints[3]) {
                count++;
            } else if (ints[2] >= ints[0] && ints[3] <= ints[1]) {
                count++;
            }
            */
            if (ints[1] >= ints[2] && ints[0] <= ints[3]) {
                count++;
            } else if (ints[3] >= ints[0] && ints[2] <= ints[0]) {
                count++;
            }


        }
        System.out.println(count);
    }


}
