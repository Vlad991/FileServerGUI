package com.filesynch.gui;

import com.filesynch.Main;
import com.filesynch.dto.ClientInfoDTO;
import com.filesynch.dto.ClientStatus;
import com.filesynch.dto.ServerSettingsDTO;
import com.filesynch.dto.ServerStatus;
import com.google.common.base.CaseFormat;
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
import java.lang.reflect.Field;
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
    private JPanel jPanelQueues;
    private JRadioButton ALLRadioButton;
    @Getter
    private JTable jTableClients;
    private JPanel jPanelClientsLog;
    private JLabel jLabelClientsLog;
    private JList jListQueueFileInfo;
    private JList jListQueueFiles;
    private JList jListQueueFileParts;
    private JList jListQueueAlive;
    private JButton updateQueuesButton;
    private JTable jTableQueues;
    private JTextPane textPane1;
    private JPanel jPanelClientSettings;
    private JButton saveServerSettingsButton;
    private JLabel jLabelServerSettingsTitle;
    private JTextField jTextFieldPortValue;
    private JComboBox jComboBoxClients;
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
    private JPanel jPanelServerSettings;
    private JPanel jPanelLogs;
    private JPanel jPanelServerSettingsBlock;
    private JCheckBox serverWSConnectionsStatusesCheckBox;
    private JButton configureLogButton;
    private JPanel jPanelWorkWithClient;
    private JList jListWorkWithClients;
    private JTextPane textPane2;
    private JTable jTableClientsData;
    private JTable jTableClientsDataValues;
    private JPanel jPanelClientsData;
    private JRadioButton NEWRadioButton;
    private JRadioButton WORKRadioButton;
    private JRadioButton ARCHIVERadioButton;
    private JTextField jTextFieldNewClientLoginValue;
    private JButton addNewClientButton;

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
                    Main.sendMessage(jListWorkWithClients.getSelectedValue().toString(), jTextFieldTextMessage.getText());
                }).start();
            }
        });
        jButtonSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    Main.sendFile(jListWorkWithClients.getSelectedValue().toString(), jTextFieldFile.getText());
                }).start();
            }
        });
        jButtonSendAllFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    Main.sendAllFiles(jListWorkWithClients.getSelectedValue().toString());
                }).start();
            }
        });
        tabbedPane1.addChangeListener(new ChangeListener() {
            @SneakyThrows
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane1.getSelectedIndex();
                switch (index) {
                    // Main
                    case 0:
                        break;
                    // Work w/ client
                    case 1:
                        jListWorkWithClients.removeAll();
                        DefaultListModel listModel = new DefaultListModel();
                        listModel.removeAllElements();
                        try {
                            Main.serverRmi.getClientInfoDTOList().forEach(clientInfoDTO -> {
                                listModel.addElement(clientInfoDTO.getLogin());
                            });
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                        jListWorkWithClients.setModel(listModel);
                        break;
                    // Clients Data
                    case 2:
                        // list
                        DefaultTableModel tableModel = (DefaultTableModel) jTableClientsData.getModel();
                        tableModel.setRowCount(0);
                        try {
                            Main.serverRmi.getClientInfoDTOList().forEach(clientInfoDTO -> {
                                tableModel.addRow(new String[]{String.valueOf(clientInfoDTO.getId()), clientInfoDTO.getLogin(), clientInfoDTO.getName()});
                            });
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                        jTableClientsData.setModel(tableModel);
                        break;
                    // Server settings
                    case 3:
                        ServerSettingsDTO settingsDTO = Main.getSettings();
                        jTextFieldPortValue.setText(settingsDTO.getPort());
                        jTextFieldWSReconntectionIter.setText(String.valueOf(settingsDTO.getWsReconnectionIterations()));
                        jTextFieldWSReconnectionInt.setText(String.valueOf(settingsDTO.getWsReconnectionInterval()));
                        break;
                    // Client settings
                    case 4:
                        jComboBoxClients.removeAllItems();
                        try {
                            Main.serverRmi.getClientInfoDTOList().forEach(clientInfoDTO -> {
                                jComboBoxClients.addItem(clientInfoDTO.getLogin());
                            });
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    // Logs
                    case 5:
                        break;
                    // Queues
                    case 6:
                        if (Main.serverRmi.getQueueNewStatus()) jListQueueNew.setBackground(Color.decode("#7BFF68"));
                        else jListQueueNew.setBackground(Color.decode("#FF5665"));
                        break;
                }
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
        jTableClientsData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    if (jTableClientsData.hasFocus()) {
                        String login = (String) jTableClientsData.getValueAt(jTableClientsData.getSelectedRow(), 1);
                        Long id = (Long) jTableClientsData.getValueAt(jTableClientsData.getSelectedRow(), 0);
                        ClientInfoDTO clientInfoDTO;
                        if (login != null) {
                            clientInfoDTO = Main.serverRmi.getClientSettings(login);
                        } else {
                            clientInfoDTO = Main.serverRmi.getClientSettings(id);
                        }
                        Field[] fieldList = ClientInfoDTO.class.getDeclaredFields();
                        DefaultTableModel tableModel = (DefaultTableModel) jTableClientsDataValues.getModel();
                        tableModel.setRowCount(0);
                        for (Field field : fieldList) {
                            field.setAccessible(true);
                            tableModel.addRow(new Object[]{CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, field.getName()), field.get(clientInfoDTO)});
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClientsData.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        tableModel.addRow(new Object[]{
                                clientInfo.getId(),
                                clientInfo.getLogin(),
                                clientInfo.getName(),});
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
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClientsData.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.NEW)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getId(),
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),});
                    });
                }).start();
            }
        });
        WORKRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClientsData.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.CLIENT_WORK)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getId(),
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),});
                    });
                }).start();
            }
        });
        ARCHIVERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    java.util.List<ClientInfoDTO> clientsList = null;
                    try {
                        clientsList = Main.serverRmi.getClientInfoDTOList();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    DefaultTableModel tableModel = (DefaultTableModel) jTableClientsData.getModel();
                    tableModel.setRowCount(0);
                    clientsList.forEach((clientInfo) -> {
                        if (clientInfo.getStatus() == ClientStatus.CLIENT_ARCHIVE)
                            tableModel.addRow(new Object[]{
                                    clientInfo.getId(),
                                    clientInfo.getLogin(),
                                    clientInfo.getName(),});
                    });
                }).start();
            }
        });
        addNewClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.serverRmi.addNewClient(
                            (Long) jTableClientsData.getValueAt(jTableClientsData.getSelectedRow(), 0),
                            jTextFieldNewClientLoginValue.getText());
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
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
        jPanelServer.setToolTipText("Server port.");
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setEnabled(true);
        tabbedPane1.setFocusable(false);
        Font tabbedPane1Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 22, tabbedPane1.getFont());
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
        jPanelLog.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-13224394)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jLabelLog = new JLabel();
        jLabelLog.setText("Log:");
        jPanelLog.add(jLabelLog, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jScrollPaneLog = new JScrollPane();
        jPanelLog.add(jScrollPaneLog, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane1 = new JTextPane();
        textPane1.setText("");
        jScrollPaneLog.setViewportView(textPane1);
        final Spacer spacer1 = new Spacer();
        jPanelMain.add(spacer1, new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jPanelWorkWithClient = new JPanel();
        jPanelWorkWithClient.setLayout(new GridLayoutManager(9, 5, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Work w/ client", jPanelWorkWithClient);
        jPanelTextMessage = new JPanel();
        jPanelTextMessage.setLayout(new GridLayoutManager(2, 2, new Insets(5, 5, 5, 5), -1, -1));
        jPanelWorkWithClient.add(jPanelTextMessage, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), null, 0, false));
        jPanelTextMessage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-10385191)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jTextFieldTextMessage = new JTextField();
        jPanelTextMessage.add(jTextFieldTextMessage, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jButtonTextMessage = new JButton();
        jButtonTextMessage.setText("Send Message");
        jPanelTextMessage.add(jButtonTextMessage, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jLabelTextMessage = new JLabel();
        jLabelTextMessage.setText("Text Message:");
        jPanelTextMessage.add(jLabelTextMessage, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelFile = new JPanel();
        jPanelFile.setLayout(new GridLayoutManager(3, 6, new Insets(5, 5, 5, 5), -1, -1));
        jPanelWorkWithClient.add(jPanelFile, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jPanelFile.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-7939681)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jLabelFileTitle = new JLabel();
        jLabelFileTitle.setText("File:");
        jPanelFile.add(jLabelFileTitle, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        jPanelWorkWithClient.add(jPanelCommand, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jPanelCommand.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2525260)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jLabelCommand = new JLabel();
        jLabelCommand.setText("Command:");
        jPanelCommand.add(jLabelCommand, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldCommand = new JTextField();
        jPanelCommand.add(jTextFieldCommand, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jButtonSendCommand = new JButton();
        jButtonSendCommand.setText("Send Command");
        jPanelCommand.add(jButtonSendCommand, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelClientsLog = new JPanel();
        jPanelClientsLog.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelWorkWithClient.add(jPanelClientsLog, new GridConstraints(4, 1, 4, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jLabelClientsLog = new JLabel();
        jLabelClientsLog.setText("Log:");
        jPanelClientsLog.add(jLabelClientsLog, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        jPanelClientsLog.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane2 = new JTextPane();
        scrollPane1.setViewportView(textPane2);
        final Spacer spacer2 = new Spacer();
        jPanelWorkWithClient.add(spacer2, new GridConstraints(4, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        jPanelWorkWithClient.add(scrollPane2, new GridConstraints(0, 1, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jListWorkWithClients = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("client 1");
        defaultListModel1.addElement("client 2");
        defaultListModel1.addElement("client 3");
        defaultListModel1.addElement("client 4");
        jListWorkWithClients.setModel(defaultListModel1);
        scrollPane2.setViewportView(jListWorkWithClients);
        final Spacer spacer3 = new Spacer();
        jPanelWorkWithClient.add(spacer3, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        jPanelClientsData = new JPanel();
        jPanelClientsData.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Clients Data", jPanelClientsData);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelClientsData.add(panel1, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel1.add(scrollPane3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setViewportView(jTableClientsDataValues);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 14, new Insets(0, 0, 0, 0), -1, -1));
        jPanelClientsData.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel2.add(scrollPane4, new GridConstraints(1, 0, 1, 14, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane4.setBorder(BorderFactory.createTitledBorder(null, "Clients", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        scrollPane4.setViewportView(jTableClientsData);
        ALLRadioButton = new JRadioButton();
        ALLRadioButton.setName("status");
        ALLRadioButton.setText("ALL");
        panel2.add(ALLRadioButton, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ARCHIVERadioButton = new JRadioButton();
        ARCHIVERadioButton.setName("status");
        ARCHIVERadioButton.setText("ARCHIVE");
        panel2.add(ARCHIVERadioButton, new GridConstraints(0, 13, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        WORKRadioButton = new JRadioButton();
        WORKRadioButton.setName("status");
        WORKRadioButton.setText("WORK");
        panel2.add(WORKRadioButton, new GridConstraints(0, 8, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NEWRadioButton = new JRadioButton();
        NEWRadioButton.setName("status");
        NEWRadioButton.setText("NEW");
        panel2.add(NEWRadioButton, new GridConstraints(0, 4, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldNewClientLoginValue = new JTextField();
        jTextFieldNewClientLoginValue.setText("");
        panel2.add(jTextFieldNewClientLoginValue, new GridConstraints(2, 0, 1, 13, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addNewClientButton = new JButton();
        addNewClientButton.setText("Add New Client");
        panel2.add(addNewClientButton, new GridConstraints(2, 13, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        jPanelClientsData.add(spacer4, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        jPanelServerSettings = new JPanel();
        jPanelServerSettings.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Server\nsettings", jPanelServerSettings);
        jPanelServerSettingsBlock = new JPanel();
        jPanelServerSettingsBlock.setLayout(new GridLayoutManager(7, 7, new Insets(0, 0, 0, 0), -1, -1));
        jPanelServerSettings.add(jPanelServerSettingsBlock, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jPanelServerSettingsBlock.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2503371)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jTextFieldPortValue = new JTextField();
        jTextFieldPortValue.setHorizontalAlignment(0);
        jTextFieldPortValue.setText("");
        jPanelServerSettingsBlock.add(jTextFieldPortValue, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Port:");
        jPanelServerSettingsBlock.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelServerSettingsBlock.add(panel3, new GridConstraints(0, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2503371)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        jLabelServerSettingsTitle = new JLabel();
        Font jLabelServerSettingsTitleFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 20, jLabelServerSettingsTitle.getFont());
        if (jLabelServerSettingsTitleFont != null) jLabelServerSettingsTitle.setFont(jLabelServerSettingsTitleFont);
        jLabelServerSettingsTitle.setHorizontalAlignment(0);
        jLabelServerSettingsTitle.setHorizontalTextPosition(0);
        jLabelServerSettingsTitle.setText("Server Settings");
        panel3.add(jLabelServerSettingsTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setHorizontalAlignment(0);
        label2.setHorizontalTextPosition(0);
        label2.setText("field1:");
        jPanelServerSettingsBlock.add(label2, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JTextField textField1 = new JTextField();
        textField1.setHorizontalAlignment(0);
        textField1.setText("");
        jPanelServerSettingsBlock.add(textField1, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer5 = new Spacer();
        jPanelServerSettingsBlock.add(spacer5, new GridConstraints(1, 0, 6, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setHorizontalAlignment(0);
        label3.setHorizontalTextPosition(0);
        label3.setText("WebSocket Reconnection Iterations:");
        label3.setToolTipText("How many times client will try to reconnect to the server due to broken websocket connection.");
        jPanelServerSettingsBlock.add(label3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldWSReconntectionIter = new JTextField();
        jTextFieldWSReconntectionIter.setHorizontalAlignment(0);
        jTextFieldWSReconntectionIter.setText("");
        jPanelServerSettingsBlock.add(jTextFieldWSReconntectionIter, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setHorizontalAlignment(0);
        label4.setHorizontalTextPosition(0);
        label4.setText("WebSocket Reconnection Interval (s):");
        label4.setToolTipText("How much time client will wait to repeat reconnection to the server due to broken websocket connection.");
        jPanelServerSettingsBlock.add(label4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldWSReconnectionInt = new JTextField();
        jTextFieldWSReconnectionInt.setHorizontalAlignment(0);
        jTextFieldWSReconnectionInt.setText("");
        jPanelServerSettingsBlock.add(jTextFieldWSReconnectionInt, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setHorizontalAlignment(0);
        label5.setHorizontalTextPosition(0);
        label5.setText("field2:");
        jPanelServerSettingsBlock.add(label5, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JTextField textField2 = new JTextField();
        textField2.setHorizontalAlignment(0);
        textField2.setText("");
        jPanelServerSettingsBlock.add(textField2, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setHorizontalAlignment(0);
        label6.setHorizontalTextPosition(0);
        label6.setText("field3:");
        jPanelServerSettingsBlock.add(label6, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JTextField textField3 = new JTextField();
        textField3.setHorizontalAlignment(0);
        textField3.setText("");
        jPanelServerSettingsBlock.add(textField3, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer6 = new Spacer();
        jPanelServerSettingsBlock.add(spacer6, new GridConstraints(1, 3, 6, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        saveServerSettingsButton = new JButton();
        saveServerSettingsButton.setText("Save Server Settings");
        jPanelServerSettings.add(saveServerSettingsButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelClientSettings = new JPanel();
        jPanelClientSettings.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Client settings", jPanelClientSettings);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(9, 2, new Insets(0, 0, 0, 0), -1, -1));
        jPanelClientSettings.add(panel4, new GridConstraints(0, 0, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-5938215)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label7 = new JLabel();
        label7.setHorizontalAlignment(0);
        label7.setHorizontalTextPosition(0);
        label7.setText("Output Files Directory:");
        panel4.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldOutputValue = new JTextField();
        jTextFieldOutputValue.setHorizontalAlignment(0);
        jTextFieldOutputValue.setText("");
        panel4.add(jTextFieldOutputValue, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setHorizontalAlignment(0);
        label8.setHorizontalTextPosition(0);
        label8.setText("Input Files Directory:");
        panel4.add(label8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setHorizontalAlignment(0);
        label9.setHorizontalTextPosition(0);
        label9.setText("File Part Size (kB):");
        panel4.add(label9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setHorizontalAlignment(0);
        label10.setHorizontalTextPosition(0);
        label10.setText("Handlers Count:");
        panel4.add(label10, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setHorizontalAlignment(0);
        label11.setHorizontalTextPosition(0);
        label11.setText("Handler Send Timeout (s):");
        panel4.add(label11, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldInputValue = new JTextField();
        jTextFieldInputValue.setHorizontalAlignment(0);
        jTextFieldInputValue.setText("");
        panel4.add(jTextFieldInputValue, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldFPSize = new JTextField();
        jTextFieldFPSize.setHorizontalAlignment(0);
        jTextFieldFPSize.setText("");
        panel4.add(jTextFieldFPSize, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldHandlersCount = new JTextField();
        jTextFieldHandlersCount.setHorizontalAlignment(0);
        jTextFieldHandlersCount.setText("");
        panel4.add(jTextFieldHandlersCount, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jTextFieldHandlerTimeout = new JTextField();
        jTextFieldHandlerTimeout.setHorizontalAlignment(0);
        jTextFieldHandlerTimeout.setText("");
        panel4.add(jTextFieldHandlerTimeout, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-5938215)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label12 = new JLabel();
        Font label12Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 18, label12.getFont());
        if (label12Font != null) label12.setFont(label12Font);
        label12.setHorizontalAlignment(0);
        label12.setHorizontalTextPosition(0);
        label12.setText("Clients Settings");
        panel5.add(label12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jComboBoxClients = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("client1");
        defaultComboBoxModel1.addElement("client2");
        defaultComboBoxModel1.addElement("admin");
        jComboBoxClients.setModel(defaultComboBoxModel1);
        panel5.add(jComboBoxClients, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setHorizontalAlignment(0);
        label13.setHorizontalTextPosition(0);
        label13.setText("Alive Request Frequency (per hour):");
        panel4.add(label13, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldAlive = new JTextField();
        jTextFieldAlive.setHorizontalAlignment(0);
        jTextFieldAlive.setText("");
        panel4.add(jTextFieldAlive, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setHorizontalAlignment(0);
        label14.setHorizontalTextPosition(0);
        label14.setText("Send Frequency (per hour):");
        panel4.add(label14, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldSendFr = new JTextField();
        jTextFieldSendFr.setHorizontalAlignment(0);
        jTextFieldSendFr.setText("");
        panel4.add(jTextFieldSendFr, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setHorizontalAlignment(0);
        label15.setHorizontalTextPosition(0);
        label15.setText("Threads Count:");
        panel4.add(label15, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jTextFieldThreadsCount = new JTextField();
        jTextFieldThreadsCount.setHorizontalAlignment(0);
        jTextFieldThreadsCount.setText("");
        panel4.add(jTextFieldThreadsCount, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        saveClientSettingsButton = new JButton();
        saveClientSettingsButton.setText("Save Client Settings");
        jPanelClientSettings.add(saveClientSettingsButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelLogs = new JPanel();
        jPanelLogs.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Logs", jPanelLogs);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelLogs.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-16777216)));
        serverWSConnectionsStatusesCheckBox = new JCheckBox();
        serverWSConnectionsStatusesCheckBox.setText("Server (WS) Connections (statuses)");
        panel6.add(serverWSConnectionsStatusesCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel6.add(spacer7, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setHorizontalAlignment(0);
        label16.setText("Log Server Info");
        panel6.add(label16, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox1 = new JCheckBox();
        checkBox1.setText("Server Status");
        panel6.add(checkBox1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelLogs.add(panel7, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer8 = new Spacer();
        panel7.add(spacer8, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setHorizontalAlignment(0);
        label17.setText("Log File Info");
        panel7.add(label17, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox2 = new JCheckBox();
        checkBox2.setText("file info dto");
        panel7.add(checkBox2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox3 = new JCheckBox();
        checkBox3.setText("sent file hash status");
        panel7.add(checkBox3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox4 = new JCheckBox();
        checkBox4.setText("file part dto");
        panel7.add(checkBox4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelLogs.add(panel8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-3092263)));
        final Spacer spacer9 = new Spacer();
        panel8.add(spacer9, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setHorizontalAlignment(0);
        label18.setText("Log Client Info");
        panel8.add(label18, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel8.add(panel9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JCheckBox checkBox5 = new JCheckBox();
        checkBox5.setText("client 1");
        panel9.add(checkBox5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox6 = new JCheckBox();
        checkBox6.setText("client 2");
        panel9.add(checkBox6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox7 = new JCheckBox();
        checkBox7.setText("client 3");
        panel9.add(checkBox7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox8 = new JCheckBox();
        checkBox8.setText("client 4");
        panel9.add(checkBox8, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(11, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelLogs.add(panel10, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-16777216)));
        final JLabel label19 = new JLabel();
        label19.setHorizontalAlignment(0);
        label19.setText("Log Async Info");
        panel10.add(label19, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        panel10.add(spacer10, new GridConstraints(9, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JCheckBox checkBox9 = new JCheckBox();
        checkBox9.setText("handler get file part");
        panel10.add(checkBox9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox10 = new JCheckBox();
        checkBox10.setText("handler get thred");
        panel10.add(checkBox10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox11 = new JCheckBox();
        checkBox11.setText("thread sending FP");
        panel10.add(checkBox11, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox12 = new JCheckBox();
        checkBox12.setText("handler starts waiting");
        panel10.add(checkBox12, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox13 = new JCheckBox();
        checkBox13.setText("handelr finishes waiting");
        panel10.add(checkBox13, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox14 = new JCheckBox();
        checkBox14.setText("handlers send iteration status");
        panel10.add(checkBox14, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox15 = new JCheckBox();
        checkBox15.setText("handlers result send status");
        panel10.add(checkBox15, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JCheckBox checkBox16 = new JCheckBox();
        checkBox16.setText("handler timeout expired");
        panel10.add(checkBox16, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        configureLogButton = new JButton();
        configureLogButton.setText("Configure Log");
        jPanelLogs.add(configureLogButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jPanelQueues = new JPanel();
        jPanelQueues.setLayout(new GridLayoutManager(5, 8, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Queues", jPanelQueues);
        jScrollPaneQueueReceiving = new JScrollPane();
        jPanelQueues.add(jScrollPaneQueueReceiving, new GridConstraints(1, 1, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueNew = new JList();
        jListQueueNew.setBackground(new Color(-13882309));
        final DefaultListModel defaultListModel2 = new DefaultListModel();
        defaultListModel2.addElement("client1");
        defaultListModel2.addElement("client2");
        defaultListModel2.addElement("client3");
        jListQueueNew.setModel(defaultListModel2);
        jScrollPaneQueueReceiving.setViewportView(jListQueueNew);
        jScrollPaneQueueSending = new JScrollPane();
        jPanelQueues.add(jScrollPaneQueueSending, new GridConstraints(1, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(128, 162), null, 0, false));
        jListQueueTechnical = new JList();
        final DefaultListModel defaultListModel3 = new DefaultListModel();
        defaultListModel3.addElement("client1");
        defaultListModel3.addElement("client2");
        defaultListModel3.addElement("client3");
        jListQueueTechnical.setModel(defaultListModel3);
        jScrollPaneQueueSending.setViewportView(jListQueueTechnical);
        final JLabel label20 = new JLabel();
        label20.setText("NEW                      ");
        jPanelQueues.add(label20, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("TECHNICAL          ");
        jPanelQueues.add(label21, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(128, 16), null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        jPanelQueues.add(scrollPane5, new GridConstraints(1, 5, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueFileInfo = new JList();
        final DefaultListModel defaultListModel4 = new DefaultListModel();
        defaultListModel4.addElement("client1");
        defaultListModel4.addElement("client2");
        defaultListModel4.addElement("client3");
        jListQueueFileInfo.setModel(defaultListModel4);
        scrollPane5.setViewportView(jListQueueFileInfo);
        final JScrollPane scrollPane6 = new JScrollPane();
        jPanelQueues.add(scrollPane6, new GridConstraints(1, 6, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(111, 162), null, 0, false));
        jListQueueFiles = new JList();
        final DefaultListModel defaultListModel5 = new DefaultListModel();
        defaultListModel5.addElement("client1");
        defaultListModel5.addElement("client2");
        defaultListModel5.addElement("client3");
        jListQueueFiles.setModel(defaultListModel5);
        scrollPane6.setViewportView(jListQueueFiles);
        final JScrollPane scrollPane7 = new JScrollPane();
        jPanelQueues.add(scrollPane7, new GridConstraints(1, 7, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueFileParts = new JList();
        final DefaultListModel defaultListModel6 = new DefaultListModel();
        defaultListModel6.addElement("client1");
        defaultListModel6.addElement("client2");
        defaultListModel6.addElement("client3");
        jListQueueFileParts.setModel(defaultListModel6);
        scrollPane7.setViewportView(jListQueueFileParts);
        final JLabel label22 = new JLabel();
        label22.setText("FILE_PARTS");
        jPanelQueues.add(label22, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane8 = new JScrollPane();
        jPanelQueues.add(scrollPane8, new GridConstraints(1, 4, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListQueueAlive = new JList();
        final DefaultListModel defaultListModel7 = new DefaultListModel();
        defaultListModel7.addElement("client1");
        defaultListModel7.addElement("client2");
        defaultListModel7.addElement("client3");
        jListQueueAlive.setModel(defaultListModel7);
        scrollPane8.setViewportView(jListQueueAlive);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        jPanelQueues.add(panel11, new GridConstraints(4, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        updateQueuesButton = new JButton();
        updateQueuesButton.setText("Update Queues");
        panel11.add(updateQueuesButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        panel11.add(spacer11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("ALIVE");
        jPanelQueues.add(label23, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("FILE_INFO");
        jPanelQueues.add(label24, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("FILES   ");
        jPanelQueues.add(label25, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(111, 16), null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        jPanelQueues.add(panel12, new GridConstraints(3, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane9 = new JScrollPane();
        panel12.add(scrollPane9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane9.setBorder(BorderFactory.createTitledBorder(null, "Queues", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        scrollPane9.setViewportView(jTableQueues);
        final JScrollPane scrollPane10 = new JScrollPane();
        jPanelQueues.add(scrollPane10, new GridConstraints(0, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jListClientQueues = new JList();
        final DefaultListModel defaultListModel8 = new DefaultListModel();
        defaultListModel8.addElement("server");
        defaultListModel8.addElement("admin");
        defaultListModel8.addElement("client1");
        defaultListModel8.addElement("client2");
        defaultListModel8.addElement("client3");
        defaultListModel8.addElement("client4");
        defaultListModel8.addElement("client5");
        defaultListModel8.addElement("client6");
        defaultListModel8.addElement("client7");
        jListClientQueues.setModel(defaultListModel8);
        scrollPane10.setViewportView(jListClientQueues);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(ALLRadioButton);
        buttonGroup.add(NEWRadioButton);
        buttonGroup.add(WORKRadioButton);
        buttonGroup.add(ARCHIVERadioButton);
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
        // Table Clients
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
        jTableClients.setFillsViewportHeight(true);
        jTableClients.setShowHorizontalLines(true);
        jTableClients.setShowVerticalLines(true);

        // Table Queues
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
        jTableQueues.setFillsViewportHeight(true);
        jTableQueues.setShowHorizontalLines(true);
        jTableQueues.setShowVerticalLines(true);

        // Table Clients Data
        Object[] clientsDataTableColumns = {
                "ID",
                "Client Login",
                "Client Name"
        };
        TableModel clientsDataTableModel = new DefaultTableModel(clientsDataTableColumns, 0);
        jTableClientsData = new JTable(clientsDataTableModel);
        jTableClientsData.setFillsViewportHeight(true);
        jTableClientsData.setShowHorizontalLines(true);
        jTableClientsData.setShowVerticalLines(true);

        // Table Clients Data Values
        Object[] clientsDataValueTableColumns = {
                "Property Name",
                "Property Value"
        };
        TableModel clientsDataValueTableModel = new DefaultTableModel(clientsDataValueTableColumns, 0);
        jTableClientsDataValues = new JTable(clientsDataValueTableModel);
        jTableClientsDataValues.setFillsViewportHeight(true);
        jTableClientsDataValues.setShowHorizontalLines(true);
        jTableClientsDataValues.setShowVerticalLines(true);
    }
}