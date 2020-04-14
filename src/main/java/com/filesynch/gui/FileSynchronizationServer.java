package com.filesynch.gui;

import com.filesynch.Main;
import com.filesynch.dto.ClientInfoDTO;
import com.filesynch.dto.ClientStatus;
import com.filesynch.dto.ServerStatus;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

@Getter
public class FileSynchronizationServer {
    @Getter
    private JPanel jPanelServer;
    private JTabbedPane tabbedPane1;
    private JPanel jPanelMain;
    private JPanel jPanelTextMessage;
    private JPanel jPanelLog;
    private JPanel jPanelFile;
    private JPanel jPanelCommand;
    private JTextField jTextFieldTextMessage;
    private JButton jButtonTextMessage;
    private JLabel jLabelTextMessage;
    private JTextField jTextFieldFile;
    private JButton jButtonSendFile;
    private JTextField jTextFieldCommand;
    private JButton jButtonSendCommand;
    private JLabel jLabelFileTitle;
    private JLabel jLabelFile;
    private JLabel jLabelCommand;
    @Getter
    private JProgressBar jProgressBarFile;
    private JButton jButtonStartServer;
    private JButton jButtonStopServer;
    @Getter
    private JList jListQueueTechnical;
    private JLabel jLabelClientStatusValue;
    private JLabel jLabelIPValue;
    private JLabel jLabelPCNameValue;
    private JLabel jLabelPCModelValue;
    private JLabel jLabelServerInfo;
    private JPanel jPanelServerInfo;
    private JLabel jLabelServerStatus;
    @Getter
    private JLabel jLabelServerInfoValue;
    private JLabel jLabelServerStatusValue;
    private JLabel jLabelLog;
    private JScrollPane jScrollPaneLog;
    private JScrollPane jScrollPaneQueueSending;
    private JButton jButtonSendAllFiles;
    private JScrollPane jScrollPaneQueueReceiving;
    @Getter
    private JList jListQueueNew;
    private JPanel jPanelClients;
    private JPanel jPanelQueues;
    private JRadioButton ALLRadioButton;
    private JRadioButton NEWRadioButton;
    private JRadioButton CLIENT_FIRSTRadioButton;
    private JRadioButton CLIENT_WORKRadioButton;
    private JRadioButton CLIENT_PAUSERadioButton;
    @Getter
    private JTable jTableClients;
    private JScrollPane JScrollPaneClientsTable;
    private JPanel JPanelClientsTable;
    private JRadioButton CLIENT_ARCHIVERadioButton;
    private JPanel jPanelClientsLog;
    private JLabel jLabelClientsLog;
    private JTextArea jTextAreaClientsLog;
    private JList jListQueueFileInfo;
    private JList jListQueueFiles;
    private JList jListQueueFileParts;
    private JList jListQueueAlive;
    private JButton updateQueuesButton;
    private JButton updateListButton;
    private JTable jTableQueues;
    private JTextPane textPane1;
    private JButton jButtonSendAllFilesFast;
    private JButton jButtonSendFileFast;
    private Object[] columns;

    public FileSynchronizationServer() {
        $$$setupUI$$$();
        jButtonStopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.stopServer();
                jLabelServerStatusValue.setText(ServerStatus.SERVER_STOP.getStatus());
                jLabelServerStatusValue.setForeground(Color.RED);
            }
        });
        jButtonStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.startServer();
                jLabelServerStatusValue.setText(ServerStatus.SERVER_WORK.getStatus());
                jLabelServerStatusValue.setForeground(Color.GREEN);
            }
        });
        jButtonTextMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    Main.sendMessage(jTableClients.getValueAt(jTableClients.getSelectedRow(), 0).toString(), jTextFieldTextMessage.getText());
                }).start();
            }
        });
        jButtonSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    Main.sendFile(jTableClients.getValueAt(jTableClients.getSelectedRow(), 0).toString(), jTextFieldFile.getText());
                }).start();
            }
        });
        jButtonSendAllFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    Main.sendAllFiles(jTableClients.getValueAt(jTableClients.getSelectedRow(), 0).toString());
                }).start();
            }
        });
        ALLRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClients.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        tableModel.addRow(new Object[]{
                                clientInfo.getLogin(),
                                clientInfo.getName(),
                                clientInfo.getExternalIp(),
                                clientInfo.getLocalIp(),
                                clientInfo.getPcName(),
                                clientInfo.getPcModel(),
                                clientInfo.getStatus(),
                                clientInfo.getFilesFolder(),
                                clientInfo.getSendFrequency(),
                                clientInfo.getAliveRequestFrequency()});
                    });
                }).start();
            }
        });
        NEWRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClients.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.NEW)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),
                                    clientInfo.getExternalIp(),
                                    clientInfo.getLocalIp(),
                                    clientInfo.getPcName(),
                                    clientInfo.getPcModel(),
                                    clientInfo.getStatus(),
                                    clientInfo.getFilesFolder(),
                                    clientInfo.getSendFrequency(),
                                    clientInfo.getAliveRequestFrequency()});
                    });
                }).start();
            }
        });
        CLIENT_FIRSTRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClients.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.CLIENT_FIRST)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),
                                    clientInfo.getExternalIp(),
                                    clientInfo.getLocalIp(),
                                    clientInfo.getPcName(),
                                    clientInfo.getPcModel(),
                                    clientInfo.getStatus(),
                                    clientInfo.getFilesFolder(),
                                    clientInfo.getSendFrequency(),
                                    clientInfo.getAliveRequestFrequency()});
                    });
                }).start();
            }
        });
        CLIENT_WORKRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClients.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.CLIENT_WORK)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),
                                    clientInfo.getExternalIp(),
                                    clientInfo.getLocalIp(),
                                    clientInfo.getPcName(),
                                    clientInfo.getPcModel(),
                                    clientInfo.getStatus(),
                                    clientInfo.getFilesFolder(),
                                    clientInfo.getSendFrequency(),
                                    clientInfo.getAliveRequestFrequency()});
                    });
                }).start();
            }
        });
        CLIENT_PAUSERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClients.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.CLIENT_PAUSE)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),
                                    clientInfo.getExternalIp(),
                                    clientInfo.getLocalIp(),
                                    clientInfo.getPcName(),
                                    clientInfo.getPcModel(),
                                    clientInfo.getStatus(),
                                    clientInfo.getFilesFolder(),
                                    clientInfo.getSendFrequency(),
                                    clientInfo.getAliveRequestFrequency()});
                    });
                }).start();
            }
        });
        CLIENT_ARCHIVERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClients.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.CLIENT_ARCHIVE)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),
                                    clientInfo.getExternalIp(),
                                    clientInfo.getLocalIp(),
                                    clientInfo.getPcName(),
                                    clientInfo.getPcModel(),
                                    clientInfo.getStatus(),
                                    clientInfo.getFilesFolder(),
                                    clientInfo.getSendFrequency(),
                                    clientInfo.getAliveRequestFrequency()});
                    });
                }).start();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Synchronization Client");
        frame.setContentPane(new FileSynchronizationServer().jPanelServer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        jPanelServer = new JPanel();
        jPanelServer.setLayout(new GridLayoutManager(1, 1, new Insets(10, 20, 10, 20), -1, -1));
        jPanelServer.setPreferredSize(new Dimension(1200, 800));
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setEnabled(true);
        tabbedPane1.setFocusable(false);
        Font tabbedPane1Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 28, tabbedPane1.getFont());
        if (tabbedPane1Font != null) tabbedPane1.setFont(tabbedPane1Font);
        tabbedPane1.setTabLayoutPolicy(0);
        tabbedPane1.setTabPlacement(2);
        tabbedPane1.putClientProperty("html.disable", Boolean.FALSE);
        jPanelServer.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        tabbedPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, tabbedPane1.getFont()), new Color(-3092263)));
        jPanelMain = new JPanel();
        jPanelMain.setLayout(new GridLayoutManager(4, 7, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Server", jPanelMain);
        jPanelServerInfo = new JPanel();
        jPanelServerInfo.setLayout(new GridLayoutManager(2, 12, new Insets(0, 0, 0, 0), -1, -1));
        jPanelMain.add(jPanelServerInfo, new GridConstraints(0, 1, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jPanelServerInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-11911975)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, jPanelServerInfo.getFont())));
        jLabelServerStatus = new JLabel();
        Font jLabelServerStatusFont = this.$$$getFont$$$(null, -1, -1, jLabelServerStatus.getFont());
        if (jLabelServerStatusFont != null) jLabelServerStatus.setFont(jLabelServerStatusFont);
        jLabelServerStatus.setText("Server Status:");
        jPanelServerInfo.add(jLabelServerStatus, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelServerStatusValue = new JLabel();
        Font jLabelServerStatusValueFont = this.$$$getFont$$$(null, -1, -1, jLabelServerStatusValue.getFont());
        if (jLabelServerStatusValueFont != null) jLabelServerStatusValue.setFont(jLabelServerStatusValueFont);
        jLabelServerStatusValue.setForeground(new Color(-2537940));
        jLabelServerStatusValue.setText("SERVER_STOP");
        jPanelServerInfo.add(jLabelServerStatusValue, new GridConstraints(0, 6, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelServerInfoValue = new JLabel();
        Font jLabelServerInfoValueFont = this.$$$getFont$$$(null, -1, -1, jLabelServerInfoValue.getFont());
        if (jLabelServerInfoValueFont != null) jLabelServerInfoValue.setFont(jLabelServerInfoValueFont);
        jLabelServerInfoValue.setText("");
        jPanelServerInfo.add(jLabelServerInfoValue, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jButtonStartServer = new JButton();
        jButtonStartServer.setFocusTraversalPolicyProvider(false);
        Font jButtonStartServerFont = this.$$$getFont$$$(null, -1, -1, jButtonStartServer.getFont());
        if (jButtonStartServerFont != null) jButtonStartServer.setFont(jButtonStartServerFont);
        jButtonStartServer.setHideActionText(false);
        jButtonStartServer.setText("Start Server");
        jPanelServerInfo.add(jButtonStartServer, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jButtonStopServer = new JButton();
        Font jButtonStopServerFont = this.$$$getFont$$$(null, -1, -1, jButtonStopServer.getFont());
        if (jButtonStopServerFont != null) jButtonStopServer.setFont(jButtonStopServerFont);
        jButtonStopServer.setText("Stop Server");
        jPanelServerInfo.add(jButtonStopServer, new GridConstraints(1, 5, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelServerInfo = new JLabel();
        jLabelServerInfo.setFocusTraversalPolicyProvider(false);
        Font jLabelServerInfoFont = this.$$$getFont$$$(null, -1, -1, jLabelServerInfo.getFont());
        if (jLabelServerInfoFont != null) jLabelServerInfo.setFont(jLabelServerInfoFont);
        jLabelServerInfo.setText("Server Info:");
        jPanelServerInfo.add(jLabelServerInfo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelLog = new JPanel();
        jPanelLog.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
        jPanelLog.setDoubleBuffered(true);
        jPanelMain.add(jPanelLog, new GridConstraints(1, 1, 3, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(100, 200), null, 0, false));
        jPanelLog.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-13224394)), null));
        jLabelLog = new JLabel();
        jLabelLog.setText("Log:");
        jPanelLog.add(jLabelLog, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jScrollPaneLog = new JScrollPane();
        jPanelLog.add(jScrollPaneLog, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane1 = new JTextPane();
        jScrollPaneLog.setViewportView(textPane1);
        final Spacer spacer1 = new Spacer();
        jPanelMain.add(spacer1, new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jPanelClients = new JPanel();
        jPanelClients.setLayout(new GridLayoutManager(7, 8, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Clients", jPanelClients);
        jPanelTextMessage = new JPanel();
        jPanelTextMessage.setLayout(new GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        jPanelClients.add(jPanelTextMessage, new GridConstraints(2, 0, 3, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), null, 0, false));
        jPanelTextMessage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-10385191)), null));
        jTextFieldTextMessage = new JTextField();
        jPanelTextMessage.add(jTextFieldTextMessage, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jButtonTextMessage = new JButton();
        jButtonTextMessage.setText("Send Message");
        jPanelTextMessage.add(jButtonTextMessage, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelTextMessage = new JLabel();
        jLabelTextMessage.setText("Text Message:");
        jPanelTextMessage.add(jLabelTextMessage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelFile = new JPanel();
        jPanelFile.setLayout(new GridLayoutManager(4, 6, new Insets(5, 5, 5, 5), -1, -1));
        jPanelClients.add(jPanelFile, new GridConstraints(5, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jPanelFile.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-7939681)), null));
        jLabelFileTitle = new JLabel();
        jLabelFileTitle.setText("File:");
        jPanelFile.add(jLabelFileTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelFile = new JLabel();
        jLabelFile.setText("File Name:");
        jPanelFile.add(jLabelFile, new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldFile = new JTextField();
        jPanelFile.add(jTextFieldFile, new GridConstraints(1, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jProgressBarFile = new JProgressBar();
        jPanelFile.add(jProgressBarFile, new GridConstraints(3, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jButtonSendAllFiles = new JButton();
        jButtonSendAllFiles.setText("Send All Files");
        jPanelFile.add(jButtonSendAllFiles, new GridConstraints(1, 3, 2, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jButtonSendFile = new JButton();
        jButtonSendFile.setText("Send File");
        jPanelFile.add(jButtonSendFile, new GridConstraints(1, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelCommand = new JPanel();
        jPanelCommand.setLayout(new GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        jPanelClients.add(jPanelCommand, new GridConstraints(6, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jPanelCommand.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2525260)), null));
        jLabelCommand = new JLabel();
        jLabelCommand.setText("Command:");
        jPanelCommand.add(jLabelCommand, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldCommand = new JTextField();
        jPanelCommand.add(jTextFieldCommand, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jButtonSendCommand = new JButton();
        jButtonSendCommand.setText("Send Command");
        jPanelCommand.add(jButtonSendCommand, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JPanelClientsTable = new JPanel();
        JPanelClientsTable.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelClients.add(JPanelClientsTable, new GridConstraints(1, 0, 1, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        JScrollPaneClientsTable = new JScrollPane();
        JPanelClientsTable.add(JScrollPaneClientsTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        JScrollPaneClientsTable.setBorder(BorderFactory.createTitledBorder("Clients"));
        jTableClients.putClientProperty("Table.isFileList", Boolean.FALSE);
        JScrollPaneClientsTable.setViewportView(jTableClients);
        ALLRadioButton = new JRadioButton();
        ALLRadioButton.setText("ALL");
        jPanelClients.add(ALLRadioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NEWRadioButton = new JRadioButton();
        NEWRadioButton.setText("NEW");
        jPanelClients.add(NEWRadioButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CLIENT_FIRSTRadioButton = new JRadioButton();
        CLIENT_FIRSTRadioButton.setText("CLIENT_FIRST");
        jPanelClients.add(CLIENT_FIRSTRadioButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CLIENT_WORKRadioButton = new JRadioButton();
        CLIENT_WORKRadioButton.setText("CLIENT_WORK");
        jPanelClients.add(CLIENT_WORKRadioButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CLIENT_PAUSERadioButton = new JRadioButton();
        CLIENT_PAUSERadioButton.setText("CLIENT_PAUSE");
        jPanelClients.add(CLIENT_PAUSERadioButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelClientsLog = new JPanel();
        jPanelClientsLog.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelClients.add(jPanelClientsLog, new GridConstraints(2, 5, 5, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jLabelClientsLog = new JLabel();
        jLabelClientsLog.setText("Log:");
        jPanelClientsLog.add(jLabelClientsLog, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        jPanelClientsLog.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jTextAreaClientsLog = new JTextArea();
        scrollPane1.setViewportView(jTextAreaClientsLog);
        CLIENT_ARCHIVERadioButton = new JRadioButton();
        CLIENT_ARCHIVERadioButton.setText("CLIENT_ACHIVE");
        jPanelClients.add(CLIENT_ARCHIVERadioButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateListButton = new JButton();
        updateListButton.setText("Update List");
        jPanelClients.add(updateListButton, new GridConstraints(0, 6, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelQueues = new JPanel();
        jPanelQueues.setLayout(new GridLayoutManager(5, 7, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Queues", jPanelQueues);
        jScrollPaneQueueReceiving = new JScrollPane();
        jPanelQueues.add(jScrollPaneQueueReceiving, new GridConstraints(1, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueNew = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        jListQueueNew.setModel(defaultListModel1);
        jScrollPaneQueueReceiving.setViewportView(jListQueueNew);
        jScrollPaneQueueSending = new JScrollPane();
        jPanelQueues.add(jScrollPaneQueueSending, new GridConstraints(1, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueTechnical = new JList();
        final DefaultListModel defaultListModel2 = new DefaultListModel();
        jListQueueTechnical.setModel(defaultListModel2);
        jScrollPaneQueueSending.setViewportView(jListQueueTechnical);
        final JLabel label1 = new JLabel();
        label1.setText("NEW");
        jPanelQueues.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("TECHNICAL");
        jPanelQueues.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        jPanelQueues.add(scrollPane2, new GridConstraints(1, 4, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueFileInfo = new JList();
        scrollPane2.setViewportView(jListQueueFileInfo);
        final JScrollPane scrollPane3 = new JScrollPane();
        jPanelQueues.add(scrollPane3, new GridConstraints(1, 5, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueFiles = new JList();
        scrollPane3.setViewportView(jListQueueFiles);
        final JScrollPane scrollPane4 = new JScrollPane();
        jPanelQueues.add(scrollPane4, new GridConstraints(1, 6, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueFileParts = new JList();
        scrollPane4.setViewportView(jListQueueFileParts);
        final JLabel label3 = new JLabel();
        label3.setText("FILE_PARTS");
        jPanelQueues.add(label3, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        jPanelQueues.add(scrollPane5, new GridConstraints(1, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueAlive = new JList();
        scrollPane5.setViewportView(jListQueueAlive);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        jPanelQueues.add(panel1, new GridConstraints(4, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        updateQueuesButton = new JButton();
        updateQueuesButton.setText("Update Queues");
        panel1.add(updateQueuesButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("ALIVE");
        jPanelQueues.add(label4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("FILE_INFO");
        jPanelQueues.add(label5, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("FILES");
        jPanelQueues.add(label6, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelQueues.add(panel2, new GridConstraints(3, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane6 = new JScrollPane();
        panel2.add(scrollPane6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane6.setBorder(BorderFactory.createTitledBorder("Queues"));
        scrollPane6.setViewportView(jTableQueues);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(NEWRadioButton);
        buttonGroup.add(ALLRadioButton);
        buttonGroup.add(CLIENT_FIRSTRadioButton);
        buttonGroup.add(CLIENT_WORKRadioButton);
        buttonGroup.add(CLIENT_PAUSERadioButton);
        buttonGroup.add(CLIENT_ARCHIVERadioButton);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanelServer;
    }

    private void createUIComponents() {
        Object[] columns = {
                "Login",
                "Name",
                "External IP",
                "Local IP",
                "PC Name",
                "PC Model",
                "Status",
                "Files Folder",
                "Send Frequency",
                "Alive Request Frequency"};
        TableModel tableModel = new DefaultTableModel(columns, 0);
        jTableClients = new JTable(tableModel);
        //jTableClients.setPreferredScrollableViewportSize(new Dimension(450, 63));
        jTableClients.setFillsViewportHeight(true);
        jTableClients.setShowHorizontalLines(true);
        jTableClients.setShowVerticalLines(true);

        Object[] queuesTableColumns = {
                "Queue Name",
                "Now",
                "10.02.2020",
                "xx.xx.xxxx",
                "xx.xx.xxxx",
                "xx.xx.xxxx",
                "xx.xx.xxxx",
                "xx.xx.xxxx",
        };
        TableModel queuesTableModel = new DefaultTableModel(queuesTableColumns, 0);
        jTableQueues = new JTable(queuesTableModel);
        //jTableClients.setPreferredScrollableViewportSize(new Dimension(450, 63));
        jTableQueues.setFillsViewportHeight(true);
        jTableQueues.setShowHorizontalLines(true);
        jTableQueues.setShowVerticalLines(true);
    }
}