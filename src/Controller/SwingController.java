/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Models.Lexeme;
import Models.Lexical_Analyzer;
import Models.Token;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwingController {

    private JTextArea codeTextArea;
    private JTable tokensTable;
    private DefaultTableModel tableModel;

    public SwingController(JTextArea codeTextArea, JTable tokensTable) {
        this.codeTextArea = codeTextArea;
        this.tokensTable = tokensTable;

        // Configurar el modelo de la tabla
        tableModel = new DefaultTableModel(new Object[]{"Line", "Token", "Attribute"}, 0);
        tokensTable.setModel(tableModel);

        // Deshabilitar botones si el área de texto está vacía
        codeTextArea.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update() {
                boolean isEmpty = codeTextArea.getText().trim().isEmpty();
                setButtonsEnabled(!isEmpty);
            }
        });

        setButtonsEnabled(false);  
    }

    public void setButtonsEnabled(boolean enabled) {

    }

    public void loadFile(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pick a file");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                codeTextArea.setText("");  
                Files.lines(selectedFile.toPath(), Charset.forName("UTF-8")).forEach(line -> codeTextArea.append(line + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearCodeArea() {
        codeTextArea.setText("");
        tableModel.setRowCount(0);  // Limpiar la tabla
    }

    public void analyze() {
        String[] lines = codeTextArea.getText().split("\n");
        Map<Integer, String> code = new HashMap<>();
        boolean lock = true;  

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i]; 
            if (!line.strip().startsWith("//") && lock && !line.strip().startsWith("/*") && !line.isBlank())
                code.put(i + 1, line);
            if (line.strip().startsWith("/*"))
                lock = false;
            if (line.strip().endsWith("*/"))
                lock = true;
        }

        List<Lexeme> lexemes = new Lexical_Analyzer().analyzeCode(code);
        tableModel.setRowCount(0);  // Limpiar la tabla antes de agregar nuevas filas
        for (Lexeme lexeme : lexemes) {
            tableModel.addRow(new Object[]{lexeme.getLine(), lexeme.getToken(), lexeme.getValue()});
        }
    }

    private abstract class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        public abstract void update();
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
    }
}
