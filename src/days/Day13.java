package days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day13 {

    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day13.txt"));

        int sum = 0;

        for (int i = 0; i < lines.size() - 1; i++) {

            String lineOne = lines.get(i++);
            String lineTwo = lines.get(i++);

            int sum1 = (i / 3) + 1;

            List<Object> listOne = parseInput(lineOne);
            List<Object> listTwo = parseInput(lineTwo);

            if (linesAreInRightOrder(listOne, listTwo) < 0) {
                System.out.println(sum1);
                sum += sum1;
            }

        }
        System.out.println(sum);


    }

    public static int linesAreInRightOrder(List<Object> listOne, List<Object> listTwo) {

        for (int i = 0; i < listOne.size(); i++) {

            if (i >= listTwo.size()) {
                return 1;
            }

            Object one = listOne.get(i);
            Object two = listTwo.get(i);

            if (one instanceof String && two instanceof String) {

                int i1 = Integer.compare(Integer.parseInt((String) one), Integer.parseInt((String) two));
                if (i1 != 0) {
                    return i1;
                }

            } else if (one instanceof List<?> && two instanceof List<?>) {

                int i1 = linesAreInRightOrder((List<Object>) one, (List<Object>) two);
                if (i1 != 0) {
                    return i1;
                }

            } else if (one instanceof String && two instanceof List<?>) {
                List<Object> newList = new ArrayList<>();
                newList.add(one);

                int i1 = linesAreInRightOrder(newList, (List<Object>) two);
                if (i1 != 0) {
                    return i1;
                }
            } else if (one instanceof List<?> && two instanceof String) {
                List<Object> newList = new ArrayList<>();
                newList.add(two);

                int i1 = linesAreInRightOrder((List<Object>) one, newList);
                if (i1 != 0) {
                    return i1;
                }
            }

        }
        if (listOne.size() < listTwo.size()) {
            return -1;
        }
        return 0;


    }

    public static List<Object> parseInput(String line) {

        List<Object> list = new ArrayList<>();
        parseInput(1, line, list);
        return list;
    }

    public static int parseInput(int index, String line, List<Object> list) {
        for (int i = index; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                continue;
            } else if (line.charAt(i) == '[') {
                List<Object> newList = new ArrayList<>();
                i = parseInput(i + 1, line, newList);
                list.add(newList);
            } else if (line.charAt(i) == ']') {
                return i;
            } else if (Character.isDigit(line.charAt(i))) {
                int currentNumber = 0;
                do {
                    currentNumber = (currentNumber * 10) + Character.getNumericValue(line.charAt(i));
                }
                while (Character.isDigit(line.charAt(++i)));
                i--;
                list.add(String.valueOf(currentNumber));
            }
        }
        return line.length();
    }
}
