package org.example;

import java.io.*;

public class Main {

    public static void main(String args[]) throws IOException {
        TestArraysGenerator.testArrayGeneration();

        try {
            FileReader fileReader = new FileReader("testArrays.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();

            File out = new File("output.txt");
            out.createNewFile();
            FileWriter writer = new FileWriter("output.txt");
            long[] arrayOutput = new long[300];
            int c = 0;

            while (line != null){
                String [] string = line.split(" ");
                int [] array = new int[string.length];
                for (int i = 0; i < string.length; i++) {
                    array[i] = Integer.parseInt(string[i]);
                }

                long start = System.currentTimeMillis();
                SmoothSort sortedArr = new SmoothSort();
                sortedArr.sort(array, array.length);
                int a = sortedArr.operations;
                long end = System.currentTimeMillis();

                arrayOutput[0+c] = string.length;
                arrayOutput[1+c] = end-start;
                arrayOutput[2+c] = a;
                writer.write(arrayOutput[0+c] + " " + arrayOutput[1+c] + " " + arrayOutput[2+c]);
                writer.write("\n");
                c += 3;
                line = reader.readLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}