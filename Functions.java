import java.io.PrintWriter;
import java.util.Scanner;
import java.util.function.Function;

public class Functions {

    public static boolean isDigits(String inString) {
        if (inString == null || inString.isEmpty())
            return false;
        for (char c : inString.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    public static <E extends Number> void processFile(MyLinkedList<E> list,
            Scanner file, PrintWriter out, Function<String, E> parse) {
        while (file.hasNextLine()) {
            String inputLine = file.nextLine().trim();
            if (inputLine.isEmpty())
                continue;

            String[] tokens = inputLine.split("\\s+");
            try {
                if (processCommand(tokens, list, out, parse))
                    break;
            } catch (NumberFormatException e) {
                // Malformed numeric arguments are reported to the console, as in the
                // original driver; they are not part of report.txt.
                System.out.println("Number format exception: " + e.getMessage());
            } catch (IndexOutOfBoundsException | IllegalStateException e) {
                out.println("Invalid request:      " + tokens[0]);
            }
        }
    }

    private static <E extends Number> boolean processCommand(String[] tokens,
            MyLinkedList<E> list, PrintWriter out, Function<String, E> parse) {
        String action = tokens[0];
        switch (action) {
            case "addFirst": {
                E value = parse.apply(tokens[1]);
                list.addFirst(value);
                out.println("addFirst:             " + value);
                break;
            }
            case "addLast": {
                E value = parse.apply(tokens[1]);
                list.addLast(value);
                out.println("addLast:              " + value);
                break;
            }
            case "add": {
                int index = Integer.parseInt(tokens[1]);
                E value = parse.apply(tokens[2]);
                list.add(index, value);
                out.println("added:                " + value + " at position " + index);
                break;
            }
            case "addBefore": {
                E prior = parse.apply(tokens[1]);
                E value = parse.apply(tokens[2]);
                list.addBefore(prior, value);
                out.println("addedBefore:          " + prior + " " + value);
                break;
            }
            case "print":
                out.println("print:                " + list);
                break;
            case "removeLast":
                out.println("removeLast:           " + list.removeLast());
                break;
            case "removeFirst":
                out.println("removeFirst:          " + list.removeFirst());
                break;
            case "indexOf": {
                E value = parse.apply(tokens[1]);
                out.println("indexOf:              " + value + " is " + list.indexOf(value));
                break;
            }
            case "lastIndexOf": {
                E value = parse.apply(tokens[1]);
                out.println("lastIndexOf:          " + value + " is " + list.lastIndexOf(value));
                break;
            }
            case "deleteBefore": {
                E prior = parse.apply(tokens[1]);
                E removed;
                try {
                    removed = list.deleteBefore(prior);
                } catch (IllegalStateException e) {
                    // The reference output represents "nothing before head" as null.
                    removed = null;
                }
                out.println("deleteBefore:         " + prior + " is " + removed);
                break;
            }
            case "set": {
                int index = Integer.parseInt(tokens[1]);
                E value = parse.apply(tokens[2]);
                list.set(index, value);
                out.println("set position:         " + index + " = " + value);
                break;
            }
            case "getFirst":
                out.println("getFirst:             " + list.getFirst());
                break;
            case "getLast":
                out.println("getLast:              " + list.getLast());
                break;
            case "get": {
                // The provided test input uses 0.5; its expected output truncates to 0.
                int index = (int) Double.parseDouble(tokens[1]);
                out.println("get:                  " + list.get(index) + " at position " + index);
                break;
            }
            case "remove": {
                E value = parse.apply(tokens[1]);
                boolean removed = list.remove(value);
                out.println("remove:               " + value + " " + removed);
                break;
            }
            case "quit":
                out.println("END OF PROCESSING CURRENT FILE");
                return true;
            default:
                out.println("Invalid request:      " + action);
        }
        return false;
    }
}
