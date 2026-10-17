import java.io.*;
import java.util.Scanner;
import java.util.Iterator;
import java.util.function.Function;

public class Functions {

    public static boolean isDigits(String inString){
        if (inString == null || inString.isEmpty()) 
            return false;
        for (char c : inString.toCharArray()) {
            if (!Character.isDigit(c)) 
               return false;
        }
        return true;
    }
 
    public static <E extends Number> void processFile(MyLinkedList<E> list, Scanner file, PrintWriter out, Function<String, E> parse){
 
        String inputLine;
        //Process integer file first

        while (file.hasNextLine()){
            inputLine = file.nextLine();
            String[] tokens = inputLine.split(" ");
            try{
                String action = tokens[0];
                E firstValue = null;
                E secondValue = null;
                if (tokens.length >= 2)
                    firstValue = parse.apply(tokens[1]);
                if (tokens.length > 2)
                    secondValue = parse.apply(tokens[2]);  
                processCommand (action, firstValue, secondValue, list, out);

            }
            catch(NumberFormatException e){
                System.out.println("Number format exception: " + e.getMessage());
            }
            catch(Exception e){
                System.out.println("Oops...: " + tokens.length + " : " + inputLine + " " + e.getMessage());
            }
        }

    }


    public static <E extends Number> void processCommand(String action, E first, E second, MyLinkedList<E> list, PrintWriter out){
 
        E item;
 
        if (action.equals( "get")){
            if (first instanceof Integer){
                Integer i = (Integer) first;
                item = list.get(i);
                out.println("get:                  " + item + " at position " + i);
            }
            else if (first instanceof Double){
                int i = ((Double) first).intValue();
                item = list.get(i);
                out.println("get:                  " + item + " at position " + i);
            }
            else{
                out.println("get invalid:          " + first);                
            }
        } 
        else if (action.equals("quit"))
            out.println("END OF PROCESSING CURRENT FILE");

        else
            out.println("Invalid request:      " + action);

    }

}