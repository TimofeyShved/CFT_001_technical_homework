package com.company;

import java.io.FileInputStream;
import java.util.logging.Logger;

public class LoggClass {
    static Logger LOGGER; // создание логов
    private static final java.util.logging.LogManager LogManager = null; // лог менеджер

    static {
        try(FileInputStream ins = new FileInputStream("log.config")){ //полный путь до файла с конфигами
            LogManager.getLogManager().readConfiguration(ins);  // считывание конфигурации
            LOGGER = Logger.getLogger(Main.class.getName()); // имя иследуемого класса
        }catch (Exception ignore){
            ignore.printStackTrace(); // ошибка
        }
    }
}
