package com.gqt.converter;

import java.io.*;

public class TxtToXmlConverter {

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\anvit\\Downloads\\input.txt" ; // path of the already stored input file 
        String outputFilePath = "C:\\Users\\anvit\\Downloads\\output.xml"; // path of the output file where you want to store

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<people>\n");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 3) {
                    writer.write("  <person>\n");
                    writer.write("    <name>" + parts[0].trim() + "</name>\n");
                    writer.write("    <age>" + parts[1].trim() + "</age>\n");
                    writer.write("    <city>" + parts[2].trim() + "</city>\n");
                    writer.write("  </person>\n");
                }
            }

            writer.write("</people>\n");
            System.out.println("TXT file has been converted to XML successfully!");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
           
        }
    }
}
