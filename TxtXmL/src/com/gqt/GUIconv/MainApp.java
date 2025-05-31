package com.gqt.GUIconv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class MainApp extends JFrame {

    private JTextField selectedFileField;
    private JTextArea statusArea;
    private File selectedFile;

    public MainApp() {
        setTitle("TXT to XML Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panel);

        JPanel topPanel = new JPanel();
        JButton selectButton = new JButton("Select TXT File");
        JButton convertButton = new JButton("Convert Now");

        selectButton.addActionListener(this::onSelectFile);
        convertButton.addActionListener(this::onConvertFile);

        topPanel.add(selectButton);
        topPanel.add(convertButton);
        panel.add(topPanel, BorderLayout.NORTH);

        selectedFileField = new JTextField("No file selected");
        selectedFileField.setEditable(false);
        selectedFileField.setBackground(Color.WHITE);
        panel.add(selectedFileField, BorderLayout.CENTER);

        statusArea = new JTextArea(5, 40);
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(statusArea);
        panel.add(scroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void onSelectFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            selectedFileField.setText(selectedFile.getAbsolutePath());
            updateStatus("File selected: " + selectedFile.getName());
        } else {
            updateStatus("File selection canceled.");
        }
    }

    private void onConvertFile(ActionEvent e) {
        if (selectedFile == null || !selectedFile.exists()) {
            updateStatus("Please select a valid TXT file first.");
            return;
        }

        try {
            File xmlFile = new File("C:\\Users\\anvit\\Downloads\\output.xml");
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile));

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
            reader.close();
            writer.close();

            updateStatus("Conversion successful! Output saved to output.xml.");

        } catch (IOException ex) {
            updateStatus("Error: " + ex.getMessage());
        }
    }

    private void updateStatus(String message) {
        statusArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}
