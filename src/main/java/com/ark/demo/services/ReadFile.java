package com.ark.demo.services;

import java.io.File;
import java.util.Scanner;

public class ReadFile{
    public static String readFile(String pathToFile){
        String fileText = "";
        try{
            File file = new File(pathToFile);
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNextLine()){
                fileText += fileScanner.nextLine();
            }
            fileScanner.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return fileText;
    }
}
