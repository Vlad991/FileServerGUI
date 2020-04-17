package com.filesynch.gui;

import com.filesynch.Main;
import com.filesynch.dto.ClientInfoDTO;
import com.filesynch.dto.ClientStatus;
import com.filesynch.dto.ServerSettingsDTO;
import com.filesynch.dto.ServerStatus;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
    private JPanel jPanelSettings;
    private JButton saveServerSettingsButton;
    private JLabel jLabelServerSettingsTitle;
    private JTextField jTextFieldPortValue;
    private JComboBox jComboBoxClients;
    private JTextPane textPane2;
    private JTextField jTextFieldWSReconntectionIter;
    private JTextField jTextFieldWSReconnectionInt;
    private JTextField jTextFieldOutputValue;
    private JTextField jTextFieldInputValue;
    private JTextField jTextFieldFPSize;
    private JTextField jTextFieldHandlersCount;
    private JTextField jTextFieldHandlerTimeout;
    private JTextField jTextFieldThreadsCount;
    private JTextField jTextFieldAlive;
    private JTextField jTextFieldSendFr;
    private JButton saveClientSettingsButton;
    private JList jListClientQueues;
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
                                clientInfo.getInputFilesFolder(),
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
                                    clientInfo.getInputFilesFolder(),
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
                                    clientInfo.getInputFilesFolder(),
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
                                    clientInfo.getInputFilesFolder(),
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
                                    clientInfo.getInputFilesFolder(),
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
                                    clientInfo.getInputFilesFolder(),
                                    clientInfo.getSendFrequency(),
                                    clientInfo.getAliveRequestFrequency()});
                    });
                }).start();
            }
        });
        tabbedPane1.addChangeListener(new ChangeListener() {
            @SneakyThrows
            @Override
            public void stateChanged(ChangeEvent e) {
                ServerSettingsDTO settingsDTO = Main.getSettings();
                jTextFieldPortValue.setText(settingsDTO.getPort());
                jTextFieldWSReconntectionIter.setText(String.valueOf(settingsDTO.getWsReconnectionIterations()));
                jTextFieldWSReconnectionInt.setText(String.valueOf(settingsDTO.getWsReconnectionInterval()));
                jComboBoxClients.removeAllItems();
                try {
                    Main.serverRmi.getClientInfoDTOList().forEach(clientInfoDTO -> {
                        jComboBoxClients.addItem(clientInfoDTO.getLogin());
                    });
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
                if (Main.serverRmi.getQueueNewStatus()) jListQueueNew.setBackground(Color.decode("#7BFF68"));
                else jListQueueNew.setBackground(Color.decode("#FF5665"));
            }
        });
        jComboBoxClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBoxClients.getItemCount() == 0) {
                    return;
                }
                ClientInfoDTO clientInfoDTO = Main.getClientSettings(jComboBoxClients.getSelectedItem().toString());
                jTextFieldOutputValue.setText(clientInfoDTO.getOutputFilesFolder());
                jTextFieldInputValue.setText(clientInfoDTO.getInputFilesFolder());
                jTextFieldFPSize.setText(String.valueOf(clientInfoDTO.getFilePartSize()));
                jTextFieldHandlersCount.setText(String.valueOf(clientInfoDTO.getHandlersCount()));
                jTextFieldHandlerTimeout.setText(String.valueOf(clientInfoDTO.getHandlerTimeout()));
                jTextFieldThreadsCount.setText(String.valueOf(clientInfoDTO.getThreadsCount()));
                jTextFieldAlive.setText(String.valueOf(clientInfoDTO.getAliveRequestFrequency()));
                jTextFieldSendFr.setText(String.valueOf(clientInfoDTO.getSendFrequency()));
            }
        });
        saveServerSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerSettingsDTO serverSettingsDTO = new ServerSettingsDTO();
                serverSettingsDTO.setPort(jTextFieldPortValue.getText());
                serverSettingsDTO.setWsReconnectionIterations(Integer.parseInt(jTextFieldWSReconntectionIter.getText()));
                serverSettingsDTO.setWsReconnectionInterval(Integer.parseInt(jTextFieldWSReconnectionInt.getText()));
                Main.setSettings(serverSettingsDTO);
            }
        });
        saveClientSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientInfoDTO clientInfoDTO = new ClientInfoDTO();
                clientInfoDTO.setLogin(jComboBoxClients.getSelectedItem().toString());
                clientInfoDTO.setOutputFilesFolder(jTextFieldOutputValue.getText());
                clientInfoDTO.setInputFilesFolder(jTextFieldInputValue.getText());
                clientInfoDTO.setFilePartSize(Integer.parseInt(jTextFieldFPSize.getText()));
                clientInfoDTO.setHandlersCount(Integer.parseInt(jTextFieldHandlersCount.getText()));
                clientInfoDTO.setHandlerTimeout(Integer.parseInt(jTextFieldHandlerTimeout.getText()));
                clientInfoDTO.setThreadsCount(Integer.parseInt(jTextFieldThreadsCount.getText()));
                clientInfoDTO.setAliveRequestFrequency(Integer.parseInt(jTextFieldAlive.getText()));
                clientInfoDTO.setSendFrequency(Integer.parseInt(jTextFieldSendFr.getText()));
                Main.setClientSettings(clientInfoDTO);
            }
        });
        jListClientQueues.addListSelectionListener(new ListSelectionListener() {
            @SneakyThrows
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object selectedValue = jListClientQueues.getSelectedValue();
                String selected;
                if (selectedValue == null) {
                    selected = "";
                } else {
                    selected = selectedValue.toString();
                }
                switch (selected) {
                    case "server":
                        if (Main.serverRmi.getQueueNewStatus()) jListQueueNew.setBackground(Color.decode("#7BFF68"));
                        else jListQueueNew.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueTechnicalStatus())
                            jListQueueTechnical.setBackground(Color.decode("#7BFF68"));
                        else jListQueueTechnical.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueAliveStatus())
                            jListQueueAlive.setBackground(Color.decode("#7BFF68"));
                        else jListQueueAlive.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueFileInfoStatus())
                            jListQueueFileInfo.setBackground(Color.decode("#7BFF68"));
                        else jListQueueFileInfo.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueFilesStatus())
                            jListQueueFiles.setBackground(Color.decode("#7BFF68"));
                        else jListQueueFiles.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueFilesParts())
                            jListQueueFileParts.setBackground(Color.decode("#7BFF68"));
                        else jListQueueFileParts.setBackground(Color.decode("#FF5665"));
                        break;
                    default:
                        String login = selected;
                        if (Main.serverRmi.getQueueNewStatus(login))
                            jListQueueNew.setBackground(Color.decode("#7BFF68"));
                        else jListQueueNew.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueTechnicalStatus(login))
                            jListQueueTechnical.setBackground(Color.decode("#7BFF68"));
                        else jListQueueTechnical.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueAliveStatus(login))
                            jListQueueAlive.setBackground(Color.decode("#7BFF68"));
                        else jListQueueAlive.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueFileInfoStatus(login))
                            jListQueueFileInfo.setBackground(Color.decode("#7BFF68"));
                        else jListQueueFileInfo.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueFilesStatus(login))
                            jListQueueFiles.setBackground(Color.decode("#7BFF68"));
                        else jListQueueFiles.setBackground(Color.decode("#FF5665"));
                        if (Main.serverRmi.getQueueFilesParts(login))
                            jListQueueFileParts.setBackground(Color.decode("#7BFF68"));
                        else jListQueueFileParts.setBackground(Color.decode("#FF5665"));
                        break;
                }
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
        tabbedPane1.addTab("Main", jPanelMain);
        jPanelServerInfo = new JPanel();
        jPanelServerInfo.setLayout(new GridLayoutManager(2, 12, new Insets(0, 0, 0, 0), -1, -1));
        jPanelMain.add(jPanelServerInfo, new GridConstraints(0, 1, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        jPanelSettings = new JPanel();
        jPanelSettings.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Settings", jPanelSettings);
        saveServerSettingsButton = new JButton();
        saveServerSettingsButton.setText("Save Server Settings");
        jPanelSettings.add(saveServerSettingsButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        jPanelSettings.add(panel1, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2503371)), null));
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("WebSocket Reconnection Interval (s):");
        panel1.add(label1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setHorizontalAlignment(0);
        label2.setHorizontalTextPosition(0);
        label2.setText("WebSocket Reconnection Iterations:");
        panel1.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldPortValue = new JTextField();
        jTextFieldPortValue.setHorizontalAlignment(0);
        jTextFieldPortValue.setText("");
        panel1.add(jTextFieldPortValue, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldWSReconntectionIter = new JTextField();
        jTextFieldWSReconntectionIter.setHorizontalAlignment(0);
        jTextFieldWSReconntectionIter.setText("");
        panel1.add(jTextFieldWSReconntectionIter, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldWSReconnectionInt = new JTextField();
        jTextFieldWSReconnectionInt.setHorizontalAlignment(0);
        jTextFieldWSReconnectionInt.setText("");
        panel1.add(jTextFieldWSReconnectionInt, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setHorizontalAlignment(0);
        label3.setHorizontalTextPosition(0);
        label3.setText("Port:");
        panel1.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2503371)), null));
        jLabelServerSettingsTitle = new JLabel();
        Font jLabelServerSettingsTitleFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, jLabelServerSettingsTitle.getFont());
        if (jLabelServerSettingsTitleFont != null) jLabelServerSettingsTitle.setFont(jLabelServerSettingsTitleFont);
        jLabelServerSettingsTitle.setHorizontalAlignment(0);
        jLabelServerSettingsTitle.setHorizontalTextPosition(0);
        jLabelServerSettingsTitle.setText("Server Settings");
        panel2.add(jLabelServerSettingsTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(9, 2, new Insets(0, 0, 0, 0), -1, -1));
        jPanelSettings.add(panel3, new GridConstraints(0, 1, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-5938215)), null));
        final JLabel label4 = new JLabel();
        label4.setHorizontalAlignment(0);
        label4.setHorizontalTextPosition(0);
        label4.setText("Output Files Directory:");
        panel3.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldOutputValue = new JTextField();
        jTextFieldOutputValue.setHorizontalAlignment(0);
        jTextFieldOutputValue.setText("");
        panel3.add(jTextFieldOutputValue, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setHorizontalAlignment(0);
        label5.setHorizontalTextPosition(0);
        label5.setText("Input Files Directory:");
        panel3.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setHorizontalAlignment(0);
        label6.setHorizontalTextPosition(0);
        label6.setText("File Part Send Size (kB):");
        panel3.add(label6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setHorizontalAlignment(0);
        label7.setHorizontalTextPosition(0);
        label7.setText("Handlers Count:");
        panel3.add(label7, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setHorizontalAlignment(0);
        label8.setHorizontalTextPosition(0);
        label8.setText("Handler Send Timeout (s):");
        panel3.add(label8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldInputValue = new JTextField();
        jTextFieldInputValue.setHorizontalAlignment(0);
        jTextFieldInputValue.setText("");
        panel3.add(jTextFieldInputValue, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldFPSize = new JTextField();
        jTextFieldFPSize.setHorizontalAlignment(0);
        jTextFieldFPSize.setText("");
        panel3.add(jTextFieldFPSize, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldHandlersCount = new JTextField();
        jTextFieldHandlersCount.setHorizontalAlignment(0);
        jTextFieldHandlersCount.setText("");
        panel3.add(jTextFieldHandlersCount, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldHandlerTimeout = new JTextField();
        jTextFieldHandlerTimeout.setHorizontalAlignment(0);
        jTextFieldHandlerTimeout.setText("");
        panel3.add(jTextFieldHandlerTimeout, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-5938215)), null));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 18, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setHorizontalAlignment(0);
        label9.setHorizontalTextPosition(0);
        label9.setText("Clients Settings");
        panel4.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jComboBoxClients = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("client1");
        defaultComboBoxModel1.addElement("client2");
        defaultComboBoxModel1.addElement("admin");
        jComboBoxClients.setModel(defaultComboBoxModel1);
        panel4.add(jComboBoxClients, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setHorizontalAlignment(0);
        label10.setHorizontalTextPosition(0);
        label10.setText("Alive Request Frequency (per hour):");
        panel3.add(label10, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldAlive = new JTextField();
        jTextFieldAlive.setHorizontalAlignment(0);
        jTextFieldAlive.setText("");
        panel3.add(jTextFieldAlive, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setHorizontalAlignment(0);
        label11.setHorizontalTextPosition(0);
        label11.setText("Send Frequency (per hour):");
        panel3.add(label11, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldSendFr = new JTextField();
        jTextFieldSendFr.setHorizontalAlignment(0);
        jTextFieldSendFr.setText("");
        panel3.add(jTextFieldSendFr, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setHorizontalAlignment(0);
        label12.setHorizontalTextPosition(0);
        label12.setText("Threads Count:");
        panel3.add(label12, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldThreadsCount = new JTextField();
        jTextFieldThreadsCount.setHorizontalAlignment(0);
        jTextFieldThreadsCount.setText("");
        panel3.add(jTextFieldThreadsCount, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        saveClientSettingsButton = new JButton();
        saveClientSettingsButton.setText("Save Client Settings");
        jPanelSettings.add(saveClientSettingsButton, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        jPanelFile.setLayout(new GridLayoutManager(3, 6, new Insets(5, 5, 5, 5), -1, -1));
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
        jPanelQueues.setLayout(new GridLayoutManager(5, 8, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Queues", jPanelQueues);
        jScrollPaneQueueReceiving = new JScrollPane();
        jPanelQueues.add(jScrollPaneQueueReceiving, new GridConstraints(1, 1, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueNew = new JList();
        jListQueueNew.setBackground(new Color(-13882309));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("client1");
        defaultListModel1.addElement("client2");
        defaultListModel1.addElement("client3");
        jListQueueNew.setModel(defaultListModel1);
        jScrollPaneQueueReceiving.setViewportView(jListQueueNew);
        jScrollPaneQueueSending = new JScrollPane();
        jPanelQueues.add(jScrollPaneQueueSending, new GridConstraints(1, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(128, 162), null, 0, false));
        jListQueueTechnical = new JList();
        final DefaultListModel defaultListModel2 = new DefaultListModel();
        defaultListModel2.addElement("client1");
        defaultListModel2.addElement("client2");
        defaultListModel2.addElement("client3");
        jListQueueTechnical.setModel(defaultListModel2);
        jScrollPaneQueueSending.setViewportView(jListQueueTechnical);
        final JLabel label13 = new JLabel();
        label13.setText("NEW                      ");
        jPanelQueues.add(label13, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("TECHNICAL          ");
        jPanelQueues.add(label14, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(128, 16), null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        jPanelQueues.add(scrollPane2, new GridConstraints(1, 5, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueFileInfo = new JList();
        final DefaultListModel defaultListModel3 = new DefaultListModel();
        defaultListModel3.addElement("client1");
        defaultListModel3.addElement("client2");
        defaultListModel3.addElement("client3");
        jListQueueFileInfo.setModel(defaultListModel3);
        scrollPane2.setViewportView(jListQueueFileInfo);
        final JScrollPane scrollPane3 = new JScrollPane();
        jPanelQueues.add(scrollPane3, new GridConstraints(1, 6, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(111, 162), null, 0, false));
        jListQueueFiles = new JList();
        final DefaultListModel defaultListModel4 = new DefaultListModel();
        defaultListModel4.addElement("client1");
        defaultListModel4.addElement("client2");
        defaultListModel4.addElement("client3");
        jListQueueFiles.setModel(defaultListModel4);
        scrollPane3.setViewportView(jListQueueFiles);
        final JScrollPane scrollPane4 = new JScrollPane();
        jPanelQueues.add(scrollPane4, new GridConstraints(1, 7, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueFileParts = new JList();
        final DefaultListModel defaultListModel5 = new DefaultListModel();
        defaultListModel5.addElement("client1");
        defaultListModel5.addElement("client2");
        defaultListModel5.addElement("client3");
        jListQueueFileParts.setModel(defaultListModel5);
        scrollPane4.setViewportView(jListQueueFileParts);
        final JLabel label15 = new JLabel();
        label15.setText("FILE_PARTS");
        jPanelQueues.add(label15, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        jPanelQueues.add(scrollPane5, new GridConstraints(1, 4, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueAlive = new JList();
        final DefaultListModel defaultListModel6 = new DefaultListModel();
        defaultListModel6.addElement("client1");
        defaultListModel6.addElement("client2");
        defaultListModel6.addElement("client3");
        jListQueueAlive.setModel(defaultListModel6);
        scrollPane5.setViewportView(jListQueueAlive);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        jPanelQueues.add(panel5, new GridConstraints(4, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        updateQueuesButton = new JButton();
        updateQueuesButton.setText("Update Queues");
        panel5.add(updateQueuesButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("ALIVE");
        jPanelQueues.add(label16, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("FILE_INFO");
        jPanelQueues.add(label17, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("FILES   ");
        jPanelQueues.add(label18, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(111, 16), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelQueues.add(panel6, new GridConstraints(3, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane6 = new JScrollPane();
        panel6.add(scrollPane6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane6.setBorder(BorderFactory.createTitledBorder("Queues"));
        scrollPane6.setViewportView(jTableQueues);
        final JScrollPane scrollPane7 = new JScrollPane();
        jPanelQueues.add(scrollPane7, new GridConstraints(0, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListClientQueues = new JList();
        final DefaultListModel defaultListModel7 = new DefaultListModel();
        defaultListModel7.addElement("server");
        defaultListModel7.addElement("admin");
        defaultListModel7.addElement("client1");
        defaultListModel7.addElement("client2");
        defaultListModel7.addElement("client3");
        defaultListModel7.addElement("client4");
        defaultListModel7.addElement("client5");
        defaultListModel7.addElement("client6");
        defaultListModel7.addElement("client7");
        jListClientQueues.setModel(defaultListModel7);
        scrollPane7.setViewportView(jListClientQueues);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Logs", panel7);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        Font label19Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, label19.getFont());
        if (label19Font != null) label19.setFont(label19Font);
        label19.setHorizontalAlignment(0);
        label19.setHorizontalTextPosition(0);
        label19.setText("NEW:");
        panel8.add(label19, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane8 = new JScrollPane();
        panel8.add(scrollPane8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane2 = new JTextPane();
        scrollPane8.setViewportView(textPane2);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel9, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        Font label20Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, label20.getFont());
        if (label20Font != null) label20.setFont(label20Font);
        label20.setHorizontalAlignment(0);
        label20.setHorizontalTextPosition(0);
        label20.setText("FILE INFO:");
        panel9.add(label20, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane9 = new JScrollPane();
        panel9.add(scrollPane9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JTextPane textPane3 = new JTextPane();
        scrollPane9.setViewportView(textPane3);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel10, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        Font label21Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, label21.getFont());
        if (label21Font != null) label21.setFont(label21Font);
        label21.setHorizontalAlignment(0);
        label21.setHorizontalTextPosition(0);
        label21.setText("COMMANDS:");
        panel10.add(label21, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane10 = new JScrollPane();
        panel10.add(scrollPane10, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JTextPane textPane4 = new JTextPane();
        scrollPane10.setViewportView(textPane4);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        Font label22Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, label22.getFont());
        if (label22Font != null) label22.setFont(label22Font);
        label22.setHorizontalAlignment(0);
        label22.setHorizontalTextPosition(0);
        label22.setText("FILE PARTS:");
        panel11.add(label22, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane11 = new JScrollPane();
        panel11.add(scrollPane11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JTextPane textPane5 = new JTextPane();
        scrollPane11.setViewportView(textPane5);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel12, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        Font label23Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, label23.getFont());
        if (label23Font != null) label23.setFont(label23Font);
        label23.setHorizontalAlignment(0);
        label23.setHorizontalTextPosition(0);
        label23.setText("TEXT MESSAGE:");
        panel12.add(label23, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane12 = new JScrollPane();
        panel12.add(scrollPane12, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JTextPane textPane6 = new JTextPane();
        scrollPane12.setViewportView(textPane6);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel13, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        Font label24Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, label24.getFont());
        if (label24Font != null) label24.setFont(label24Font);
        label24.setHorizontalAlignment(0);
        label24.setHorizontalTextPosition(0);
        label24.setText("FILES:");
        panel13.add(label24, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane13 = new JScrollPane();
        panel13.add(scrollPane13, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JTextPane textPane7 = new JTextPane();
        scrollPane13.setViewportView(textPane7);
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