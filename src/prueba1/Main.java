/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba1;

import Controller.SwingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { 
            JFrame frame = new JFrame("Lexical Analizer Java");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);

            // Crear y configurar los componentes
            JMenuBar menuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");
            JMenuItem closeMenuItem = new JMenuItem("Close");
            fileMenu.add(closeMenuItem);
            menuBar.add(fileMenu);

            JMenu editMenu = new JMenu("Edit");
            JMenuItem deleteMenuItem = new JMenuItem("Delete");
            editMenu.add(deleteMenuItem);
            menuBar.add(editMenu);

            JMenu helpMenu = new JMenu("Help");
            JMenuItem aboutMenuItem = new JMenuItem("About");
            helpMenu.add(aboutMenuItem);
            menuBar.add(helpMenu);

            JTable tokensTable = new JTable();
            JScrollPane tableScrollPane = new JScrollPane(tokensTable);

            JLabel codeLabel = new JLabel("Code:");
            JTextArea codeTextArea = new JTextArea();
            codeTextArea.setWrapStyleWord(true);
            codeTextArea.setLineWrap(true);
            JScrollPane textAreaScrollPane = new JScrollPane(codeTextArea);

            JButton btnAnalyze = new JButton("Analyze");
            JButton btnClear = new JButton("Clear");
            JButton btnLoadFile = new JButton("Load File");

            SwingController controller = new SwingController(codeTextArea, tokensTable);

            btnAnalyze.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.analyze();
                }
            });

            btnClear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.clearCodeArea();
                }
            });

            btnLoadFile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.loadFile(frame);
                }
            });

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BorderLayout());
            JPanel codePanel = new JPanel();
            codePanel.setLayout(new BorderLayout());
            codePanel.add(codeLabel, BorderLayout.NORTH);
            codePanel.add(textAreaScrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(btnAnalyze);
            buttonPanel.add(btnClear);
            buttonPanel.add(btnLoadFile);

            leftPanel.add(codePanel, BorderLayout.CENTER);
            leftPanel.add(buttonPanel, BorderLayout.SOUTH);

            frame.setJMenuBar(menuBar);
            frame.setLayout(new BorderLayout());
            frame.add(tableScrollPane, BorderLayout.CENTER);
            frame.add(leftPanel, BorderLayout.WEST);

            frame.setVisible(true);
        });
    }
}
