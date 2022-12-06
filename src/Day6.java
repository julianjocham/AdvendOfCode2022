import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day6 {

    public static final String INPUTS = "C:/Users/JochamJulian/IdeaProjects/AdventOfCode2022/Inputs/";


    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(INPUTS + "day6.txt"));

        CustomQueue<Character> queue = new CustomQueue<>();

        for (String line : lines) {

            for (int i = 0; i < line.length(); i++) {

                if (isSolved(queue.elements)) {
                    System.out.println(i);
                    break;
                }

                if (queue.size() == 14) {
                    queue.poll();
                }
                queue.offer((line.charAt(i)));

            }

        }


    }

    public static boolean isSolved(List<Character> arrayList) {

        if (arrayList.size() != 14) {
            return false;
        }

        for (char character : arrayList) {

            if (Collections.frequency(arrayList, character) > 1) {
                return false;
            }

        }
        return true;
    }

}

class CustomQueue<T> extends AbstractQueue<T> {

    public ArrayList<T> elements;

    public CustomQueue() {
        this.elements = new ArrayList<>(14);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean offer(T t) {
        if (t == null) return false;
        elements.add(t);
        return true;
    }

    @Override
    public T poll() {
        Iterator<T> iter = elements.iterator();
        T t = iter.next();
        if (t != null) {
            iter.remove();
            return t;
        }
        return null;
    }

    @Override
    public T peek() {
        return elements.get(13);
    }
}
