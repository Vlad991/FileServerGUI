package com.filesynch.rmi;

import com.filesynch.dto.ClientInfoDTO;
import com.filesynch.logger.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import static com.filesynch.Main.fileSynchronizationServer;

public class ServerGui extends UnicastRemoteObject implements ServerGuiInt {
    private ServerRmiInt serverRmi;
    private HashMap<String, JFrame> newClientIcons = new HashMap<>();

    public ServerGui(ServerRmiInt serverRmiInt) throws RemoteException {
        super();
        this.serverRmi = serverRmiInt;
    }

    @Override
    public void log(String stringToLog) {
        Logger.log(stringToLog);
    }

    @Override
    public void logYellow(String stringToLog) {
        Logger.logYellow(stringToLog);
    }

    @Override
    public void logBlue(String stringToLog) {
        Logger.logBlue(stringToLog);
    }

    @Override
    public void logGreen(String stringToLog) {
        Logger.logGreen(stringToLog);
    }

    @Override
    public void logRed(String stringToLog) {
        Logger.logRed(stringToLog);
    }

    @Override
    public void updateClientList() throws RemoteException {
        HashMap<String, ClientInfoDTO> loginSessionHashMap = serverRmi.getLoginSessionHashMap();
        DefaultTableModel model = (DefaultTableModel) fileSynchronizationServer.getJTableClients().getModel();
        loginSessionHashMap.forEach((login, clientInfoDTO) -> {
            model.addRow(new Object[]{
                    clientInfoDTO.getLogin(),
                    clientInfoDTO.getName(),
                    clientInfoDTO.getExternalIp(),
                    clientInfoDTO.getLocalIp(),
                    clientInfoDTO.getPcName(),
                    clientInfoDTO.getPcModel(),
                    clientInfoDTO.getStatus(),
                    clientInfoDTO.getInputFilesFolder(),
                    clientInfoDTO.getSendFrequency(),
                    clientInfoDTO.getAliveRequestFrequency()});
        });
    }

    @Override
    public void updateFileQueue() throws RemoteException {
//        List<FileInfoReceived> fileInfoReceivedList = server.getFileInfoReceivedRepository().findAll();
//        List<FileInfoSent> fileInfoSentList = server.getFileInfoSentRepository().findAll();
//        //List<FilePartReceived> filePartReceivedList = server.getFilePartReceivedRepository().findAll();
//        //List<FilePartSent> filePartSentList = server.getFilePartSentRepository().findAll();
//
//
//        DefaultListModel demoReceivedList = new DefaultListModel();
//        DefaultListModel demoSentList = new DefaultListModel();
//        for (FileInfoReceived f : fileInfoReceivedList) {
//            demoReceivedList.addElement("" + f.getId() + ". " + f.getName() + " receiving from " + f.getClient().getLogin());
//        }
//        for (FileInfoSent f : fileInfoSentList) {
//
//            demoSentList.addElement("" + f.getId() + ". " + f.getName() + " sending to " + f.getClient().getLogin());
//        }
//
//        fileSynchronizationServer.getJListQueueFileInfo().setModel(demoReceivedList);
//        fileSynchronizationServer.getJListQueueFiles().setModel(demoSentList);
    }

    @Override
    public void updateNewQueue() throws RemoteException {
//        DefaultListModel demoNewList = new DefaultListModel();
//        serverRmi.queueNew.forEach((name, clientInfoDTO) -> {
//            demoNewList.addElement("new: " + name);
//        });
//
//        fileSynchronizationServer.getJListQueueNew().setModel(demoNewList);
    }

    @Override
    public void updateTechnicalQueue() throws RemoteException {
//        DefaultListModel demoNewList = new DefaultListModel();
//        serverRmi.queueTechnical.forEach((message) -> {
//            demoNewList.addElement(message);
//        });
//
//        fileSynchronizationServer.getJListQueueTechnical().setModel(demoNewList);
    }

    @Override
    public void updateAliveQueue() throws RemoteException {
//        DefaultListModel demoNewList = new DefaultListModel();
//        serverRmi.queueAlive.forEach((login, clientInfoDTO) -> {
//            demoNewList.addElement(login);
//        });
//
//        fileSynchronizationServer.getJListQueueAlive().setModel(demoNewList);
    }

    @Override
    public void updateFileInfoQueue() throws RemoteException {
//        DefaultListModel demoNewList = new DefaultListModel();
//        serverRmi.queueFileInfo.forEach((name, fileInfoDTO) -> {
//            demoNewList.addElement(fileInfoDTO);
//        });
//
//        fileSynchronizationServer.getJListQueueFileInfo().setModel(demoNewList);
    }

    @Override
    public void updateFilesQueue() throws RemoteException {
//        DefaultListModel demoNewList = new DefaultListModel();
//        serverRmi.queueFiles.forEach((name, fileInfoDTO) -> {
//            demoNewList.addElement(fileInfoDTO.getName() + ": sending/receiving");
//        });
//
//        fileSynchronizationServer.getJListQueueFiles().setModel(demoNewList);
    }

    @Override
    public void updateFilePartsQueue() throws RemoteException {
//        DefaultListModel demoNewList = new DefaultListModel();
//        serverRmi.queueFileParts.forEach((name, filePartDTO) -> {
//            demoNewList.addElement(filePartDTO);
//        });
//
//        fileSynchronizationServer.getJListQueueFileParts().setModel(demoNewList);
    }

    @Override
    public void updateQueueTable() throws RemoteException {
//        DefaultTableModel model = (DefaultTableModel) fileSynchronizationServer.getJTableQueues().getModel();
//        model.setRowCount(0);
//        model.addRow(new Object[]{"Queue NEW", serverRmi.queueNew.size(), serverRmi.queueNew.size(), "", "", "", "", ""});
//        model.addRow(new Object[]{"Queue TECHNICAL", serverRmi.queueTechnical.size(), serverRmi.queueTechnical.size(), "", "", ""
//                , "", ""});
//        model.addRow(new Object[]{"Queue ALIVE", serverRmi.queueAlive.size(), serverRmi.queueAlive.size(), "", "", "", "", ""});
//        model.addRow(new Object[]{"Queue FILE_INFO", serverRmi.queueFileInfo.size(), serverRmi.queueFileInfo.size(), "", "", "", "", ""});
//        model.addRow(new Object[]{"Queue FILES", serverRmi.queueFiles.size(), serverRmi.queueFiles.size(), "", "", "", "", ""});
//        model.addRow(new Object[]{"Queue FILES_PARTS", serverRmi.queueFileParts.size(), serverRmi.queueFileParts.size(), "", "", "", "", ""});
//
//
//        // All updates
//        updateNewQueue();
//        updateTechnicalQueue();
//        updateAliveQueue();
//        updateFileInfoQueue();
//        updateFilesQueue();
//        updateFilePartsQueue();
    }
}
