package com.filesynch.gui;

import com.filesynch.dto.ClientInfoDTO;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class NewClient {
    private JPanel jPanelMain;
    private JTextField jTextFieldIP;
    private JTextField jTextFieldPort;
    private JTextField jTextFieldAddress;
    private JButton jButtonConnect;
    private JLabel jLabelExternalIP;
    private JLabel jLabelPCName;
    private JLabel jLabelPCModel;
    private JTextField jTextFieldLogin;
    private JButton jButtonReject;
    private JLabel jLabelExternalIPValue;
    private JLabel jLabelPCNameValue;
    private JLabel jLabelLogin;
    private JLabel jLabelPCModelValue;
    private JLabel jLabelName;
    private JLabel jLabelLocalIP;
    private JLabel jLabelStatus;
    private JLabel jLabelFilesFolder;
    private JLabel jLabelSendFrequency;
    private JLabel jLabelAliveRequestFrequency;
    private JLabel jLabelNameValue;
    private JLabel jLabelLocalIPValue;
    private JLabel jLabelStatusValue;
    private JTextField jTextFieldFilesFolder;
    private JTextField jTextFieldSendFrequency;
    private JTextField jTextFieldAliveRequestFrequency;
    private ClientInfoDTO clientInfoDTO;

    public NewClient() {
        jButtonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (clientInfoDTO) {
                    clientInfoDTO.notify();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ConnectToServer");
        frame.setContentPane(new NewClient().jPanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        jPanelMain = new JPanel();
        jPanelMain.setLayout(new GridLayoutManager(11, 2, new Insets(20, 20, 20, 20), -1, -1));
        jPanelMain.setPreferredSize(new Dimension(600, 350));
        jLabelExternalIP = new JLabel();
        jLabelExternalIP.setText("External IP:");
        jPanelMain.add(jLabelExternalIP, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelPCName = new JLabel();
        jLabelPCName.setText("PC Name:");
        jPanelMain.add(jLabelPCName, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelPCModel = new JLabel();
        jLabelPCModel.setText("PC Model:");
        jPanelMain.add(jLabelPCModel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jButtonConnect = new JButton();
        jButtonConnect.setText("Add New Client");
        jPanelMain.add(jButtonConnect, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelExternalIPValue = new JLabel();
        jLabelExternalIPValue.setText("");
        jPanelMain.add(jLabelExternalIPValue, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelPCNameValue = new JLabel();
        jLabelPCNameValue.setText("");
        jPanelMain.add(jLabelPCNameValue, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelPCModelValue = new JLabel();
        jLabelPCModelValue.setText("");
        jPanelMain.add(jLabelPCModelValue, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelLogin = new JLabel();
        jLabelLogin.setText("Login:");
        jPanelMain.add(jLabelLogin, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldLogin = new JTextField();
        jPanelMain.add(jTextFieldLogin, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        jButtonReject = new JButton();
        jButtonReject.setText("Reject New Client");
        jPanelMain.add(jButtonReject, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelLocalIP = new JLabel();
        jLabelLocalIP.setText("Local IP:");
        jPanelMain.add(jLabelLocalIP, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelName = new JLabel();
        jLabelName.setText("Name:");
        jPanelMain.add(jLabelName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelStatus = new JLabel();
        jLabelStatus.setText("Status:");
        jPanelMain.add(jLabelStatus, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelFilesFolder = new JLabel();
        jLabelFilesFolder.setText("Files Folder:");
        jPanelMain.add(jLabelFilesFolder, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelSendFrequency = new JLabel();
        jLabelSendFrequency.setText("Send Fequency:");
        jPanelMain.add(jLabelSendFrequency, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelAliveRequestFrequency = new JLabel();
        jLabelAliveRequestFrequency.setText("Alive Request Frequency");
        jPanelMain.add(jLabelAliveRequestFrequency, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldAliveRequestFrequency = new JTextField();
        jPanelMain.add(jTextFieldAliveRequestFrequency, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        jTextFieldSendFrequency = new JTextField();
        jPanelMain.add(jTextFieldSendFrequency, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        jLabelNameValue = new JLabel();
        jLabelNameValue.setText("");
        jPanelMain.add(jLabelNameValue, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelLocalIPValue = new JLabel();
        jLabelLocalIPValue.setText("");
        jPanelMain.add(jLabelLocalIPValue, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelStatusValue = new JLabel();
        jLabelStatusValue.setText("");
        jPanelMain.add(jLabelStatusValue, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldFilesFolder = new JTextField();
        jPanelMain.add(jTextFieldFilesFolder, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanelMain;
    }

}