package com.filesynch.logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.sql.Timestamp;

public class Logger {
    public static JTextPane logArea;
    public static JTextPane logArea2;

    public synchronized static void log(String stringToLog) {
        logWithColor(stringToLog, null);
    }

    public synchronized static void logYellow(String stringToLog) {
        logWithColor(stringToLog, Color.YELLOW);
    }

    public synchronized static void logBlue(String stringToLog) {
        logWithColor(stringToLog, Color.CYAN);
    }

    public synchronized static void logGreen(String stringToLog) {
        logWithColor(stringToLog, Color.GREEN);
    }

    public synchronized static void logRed(String stringToLog) {
        logWithColor(stringToLog, Color.RED);
    }

    private synchronized static void logWithColor(String stringToLog, Color color) {
        String COLOR = "\033[0;31m";
        String RESET = "\033[0m";
        System.out.println(COLOR + stringToLog + RESET);
        StyledDocument doc = logArea.getStyledDocument();
        StyledDocument doc2 = logArea2.getStyledDocument();
        SimpleAttributeSet attr = null;
        if (color != null) {
            attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, color);
        }
        try {
            doc.insertString(doc.getLength(), getTimesTamp() + stringToLog + "\n", attr);
            doc2.insertString(doc2.getLength(), getTimesTamp() + stringToLog + "\n", attr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static String getTimesTamp() {
        return "[ " + new Timestamp(System.currentTimeMillis()) + " ] ";
    }
}
