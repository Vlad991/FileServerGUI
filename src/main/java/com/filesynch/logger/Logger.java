package com.filesynch.logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class Logger {
    public static JTextPane logArea;

    public synchronized static void log(String stringToLog) {
        String COLOR = "\033[0;31m";
        String RESET = "\033[0m";
        System.out.println(COLOR + stringToLog + RESET);
        StyledDocument doc = logArea.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), stringToLog + "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void logYellow(String stringToLog) {
        String COLOR = "\033[0;31m";
        String RESET = "\033[0m";
        System.out.println(COLOR + stringToLog + RESET);
        StyledDocument doc = logArea.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, Color.YELLOW);
        try {
            doc.insertString(doc.getLength(), stringToLog + "\n", attr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void logBlue(String stringToLog) {
        String COLOR = "\033[0;31m";
        String RESET = "\033[0m";
        System.out.println(COLOR + stringToLog + RESET);
        StyledDocument doc = logArea.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, Color.CYAN);
        try {
            doc.insertString(doc.getLength(), stringToLog + "\n", attr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void logGreen(String stringToLog) {
        String COLOR = "\033[0;31m";
        String RESET = "\033[0m";
        System.out.println(COLOR + stringToLog + RESET);
        StyledDocument doc = logArea.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, Color.GREEN);
        try {
            doc.insertString(doc.getLength(), stringToLog + "\n", attr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void logRed(String stringToLog) {
        String COLOR = "\033[0;31m";
        String RESET = "\033[0m";
        System.out.println(COLOR + stringToLog + RESET);
        StyledDocument doc = logArea.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, Color.RED);
        try {
            doc.insertString(doc.getLength(), stringToLog + "\n", attr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
