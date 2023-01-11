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
            List<Object> listOne = listifyString(lineOne);
            List<Object> listTwo = listifyString(lineTwo);

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

    public static List<Object> listifyString(String line) {

        List<Object> list = new ArrayList<>();
        String substring = line.substring(1, line.lastIndexOf("]"));


        if (substring.isEmpty()) {
            return list;
        }
        if (substring.contains(",")) {

            List<Object> strings = splitWithDepth(substring);

            list.addAll(strings);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof String)
                    if (!isNumeric((String) list.get(i))) {

                        list.set(i, listifyString((String) list.get(i)));

                    }
            }
        }
        if (isNumeric(substring)) {
            list.add(substring);
            return list;
        }
        if (listContainsOnlyNumbers(list)) {
            return list;
        }
        if (substring.matches("^[\\[0-9\\]]+$")) {

            list.add(listifyString(substring));
        }

        return list;
    }

    public static boolean stringOnlyContainsSpecialCharacters(String string) {

        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;

    }

    public static List<Object> splitWithDepth(String string) {

        List<Object> objects = new ArrayList<>();
        int currentDepth = 0;
        int startOfString = 0;
        for (int i = 0; i < string.length(); i++) {

            if (string.charAt(i) == '[') {
                currentDepth++;
            }
            if (string.charAt(i) == ']') {
                currentDepth--;
            }
            if (string.charAt(i) == ',' && currentDepth == 0) {

                objects.add(string.substring(startOfString, i));
                startOfString = i + 1;

            }
            if (i == string.length() - 1) {
                objects.add(string.substring(startOfString));
            }

        }
        return objects;

    }

    public static boolean listContainsOnlyNumbers(List<Object> objects) {

        if (objects.isEmpty()) {
            return false;
        }

        for (Object o : objects) {

            if (o instanceof List<?>) {
                return listContainsOnlyNumbers((List<Object>) o);
            }
            if (!isNumeric((String) o)) {
                return false;
            }

        }
        return true;

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
