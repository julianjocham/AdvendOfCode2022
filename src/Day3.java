import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day3 {

    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";

    public static void main(String[] args) throws IOException {


        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day3.txt"));

        partOne(lines);

        partTwo(lines);

    }

    public static void partTwo(List<String> lines) {
        int sum = 0;

        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {

            if (i % 3 == 0) {
                Group group = new Group();
                group.setLines(lines.get(i), lines.get(i + 1), lines.get(i + 2));
                groups.add(group);
            }
        }

        for (Group group : groups) {
            List<Character> charsOne = new ArrayList<>();
            List<Character> charsTwo = new ArrayList<>();
            List<Character> charsThree = new ArrayList<>();
            String lineOne = group.lines[0];
            String lineTwo = group.lines[1];
            String lineThree = group.lines[2];
            for (int i = 0; i < lineOne.length(); i++) {
                charsOne.add(lineOne.charAt(i));
            }
            for (int i = 0; i < lineTwo.length(); i++) {
                charsTwo.add(lineTwo.charAt(i));
            }
            for (int i = 0; i < lineThree.length(); i++) {
                charsThree.add(lineThree.charAt(i));
            }

            for (char character : charsThree) {
                if (charsOne.contains(character) && charsTwo.contains(character)) {
                    if (Character.isUpperCase(character)) {
                        sum += (character - 38);
                    } else {
                        sum += (character - 96);
                    }
                    break;
                }
            }

        }
        System.out.println(sum);
    }

    private static void partOne(List<String> lines) {
        int sum = 0;

        for (String line : lines) {
            String one = line.substring(0, line.length() / 2);
            String two = line.substring(line.length() / 2);

            List<Character> charsOne = new ArrayList<>();
            List<Character> charsTwo = new ArrayList<>();

            for (int i = 0; i < one.length(); i++) {
                charsOne.add(one.charAt(i));
            }
            for (int i = 0; i < two.length(); i++) {
                charsTwo.add(two.charAt(i));
            }

            for (char character : charsTwo) {
                if (charsOne.contains(character)) {
                    if (Character.isUpperCase(character)) {
                        sum += (character - 38);
                    } else {
                        sum += (character - 96);
                    }
                    break;
                }
            }

        }
        System.out.println(sum);
    }

    static class Group {

        private final String[] lines = new String[3];

        public String[] getLines() {
            return lines;
        }

        public void setLines(String s, String s1, String s2) {
            this.lines[0] = s;
            this.lines[1] = s1;
            this.lines[2] = s2;
        }
    }

}
