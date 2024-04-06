package org.example;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestArraysGenerator {
    public static void testArrayGeneration() throws IOException {
        File testArrays = new File("testArrays.txt");
        testArrays.createNewFile();
        int size = 100;
        try {
            FileWriter writer = new FileWriter("testArrays.txt");
            for (int i = 0; i < 100; i++) {
                int[] array = new int[size];
                for (int j = 0; j < size; j++) {
                    array[j] = (int) (Math.random() * size);
                    writer.write(array[j] + " ");
                }
                writer.write("\n");
                size += 100;
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}