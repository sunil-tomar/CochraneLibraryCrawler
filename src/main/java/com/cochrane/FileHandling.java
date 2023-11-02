package com.cochrane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandling {


    public static void writeTextDataIntoFile(String fileName, String textToWrite) {
        fileName = fileName+"_cochrane_reviews.txt";
        fileName = getUniqueFileName(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(textToWrite);
            System.out.println("Please Find output in this file : " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO add logger
            System.err.println(e.getMessage());
        }
    }

    private static String getUniqueFileName(String baseFileName) {
        int count = 0;
        String fileName = baseFileName;
        while (fileExists(fileName)) {
            count++;
            fileName = baseFileName.replace(".txt", "") + "(" + count + ").txt";
        }
        return fileName;
    }

    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }
}
