package com.filesynch;

import com.bulenkov.darcula.DarculaLaf;
import com.filesynch.dto.ClientInfoDTO;
import com.filesynch.dto.ServerSettingsDTO;
import com.filesynch.dto.ServerStatus;
import com.filesynch.gui.FileSynchronizationServer;
import com.filesynch.logger.Logger;
import com.filesynch.rmi.ServerGui;
import com.filesynch.rmi.ServerRmiInt;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Main {
    public static JFrame serverFrame;
    public static FileSynchronizationServer fileSynchronizationServer;
    public static ServerGui serverGui;
    public static ServerRmiInt serverRmi;

    public static void main(String[] args) throws Exception {
        BasicLookAndFeel darcula = new DarculaLaf();
        UIManager.setLookAndFeel(darcula);
        ServerStatus currentServerStatus = ServerStatus.SERVER_STOP;
        String currentAddress = null;
        try {
            serverRmi = (ServerRmiInt) Naming.lookup("rmi://localhost:8089/gui");
            serverGui = new ServerGui(serverRmi);
            serverRmi.connectGuiToServer(serverGui);
            String port = serverRmi.getServerStatus();
            if (port != null) {
                currentServerStatus = ServerStatus.SERVER_WORK;
                currentAddress = InetAddress.getLocalHost().getHostAddress() + ":" + port;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        serverFrame = new JFrame("File Synchronization Server");
        fileSynchronizationServer = new FileSynchronizationServer();
        Logger.logArea = fileSynchronizationServer.getTextPane1();
        Logger.logArea2 = fileSynchronizationServer.getTextPane2();
        serverFrame.setContentPane(fileSynchronizationServer.getJPanelServer());
        serverFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        serverFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(serverFrame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    // todo send textmessages to all clients
                    //stopServer();
                    System.exit(0);
                }
            }
        });
        if (currentServerStatus == ServerStatus.SERVER_WORK) {
            fileSynchronizationServer.getJLabelServerStatusValue().setText(ServerStatus.SERVER_WORK.toString());
            fileSynchronizationServer.getJLabelServerStatusValue().setForeground(Color.GREEN);
            fileSynchronizationServer.getJLabelServerInfoValue().setText(currentAddress);
        } else {
            fileSynchronizationServer.getJLabelServerStatusValue().setText(ServerStatus.SERVER_STOP.toString());
            fileSynchronizationServer.getJLabelServerStatusValue().setForeground(Color.RED);
            fileSynchronizationServer.getJLabelServerInfoValue().setText("");
        }
        serverFrame.pack();
        serverFrame.setLocationRelativeTo(null);
        serverFrame.setVisible(true);
        Logger.log("Server GUI connected");
    }

    public static void startServer() {
        int port;
        try {
            port = serverRmi.startServer();
            fileSynchronizationServer.getJLabelServerInfoValue().setText(InetAddress.getLocalHost().getHostAddress() + ":" + port);
        } catch (RemoteException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        try {
            serverRmi.stopServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String login, String message) {
        try {
            serverRmi.sendMessage(login, message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void sendFile(String login, String file) {
        try {
            serverRmi.sendFile(login, file);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void sendAllFiles(String login) {
        try {
            serverRmi.sendAllFiles(login);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ServerSettingsDTO getSettings() {
        try {
            return serverRmi.getSettings();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ServerSettingsDTO();
    }

    public static void setSettings(ServerSettingsDTO settings) {
        try {
            serverRmi.setSettings(settings);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ClientInfoDTO getClientSettings(String login) {
        try {
            return serverRmi.getClientSettings(login);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setClientSettings(ClientInfoDTO clientInfoDTO) {
        try {
            serverRmi.setClientSettings(clientInfoDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
