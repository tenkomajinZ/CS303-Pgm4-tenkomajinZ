/*
 * Student: Jason Nguyen
 * Class: CS303
 * Date: October 17, 2026
 * Assignment: CS303 Program 4 - Linked Lists
 * This codebase was tested using GPT-6 Luna to check missing PRE/POST, make sure the code is correct and to help with debugging logic errors.
 */
import java.util.Scanner;
import java.io.*;

public class Main {


    public static void main(String[] args) {

        System.out.println("\n\nPROGRAM 4: TESTING LINKED LISTS USING 2 INPUT FILES\n");        
        MyLinkedList<Integer> intList = new MyLinkedList<>();
        MyLinkedList<Double> dblList = new MyLinkedList<>();

        try{
            PrintWriter out = new PrintWriter("report.txt");

            out.println("\nPROCESSING INTEGER FILE");
            Scanner intFile = new Scanner(new File("inputInt.txt"));
            Functions.processFile(intList, intFile, out, Integer::valueOf);   

            out.println("\nPROCESSING DOUBLE(FLOAT) FILE");            
            Scanner dblFile = new Scanner(new File("inputDbl.txt"));
            Functions.processFile(dblList, dblFile, out, Double::valueOf);  

            System.out.println("closing scanner");
            intFile.close();
            dblFile.close();
            out.close();

        }
        catch(IOException e){
            System.out.println("Error opening the file: " + e.getMessage());
        }
        
    }
}
