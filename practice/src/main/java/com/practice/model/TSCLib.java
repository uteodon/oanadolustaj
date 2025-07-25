package com.practice.model;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface TSCLib extends Library {
    TSCLib INSTANCE = (TSCLib) Native.load("TSCLIB", TSCLib.class); //TSCLIB.dll'yi yükler

    int openport(String printerName);         //yazıcıyı aç
    int closeport();                          //yazıcıyı kapat
    int sendcommand(String command);          //komut gönder
    int openethernet(String ip, int port);    //ethernet üzerinden
}

