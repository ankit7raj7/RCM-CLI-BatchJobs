package com.techprimers.springbatchexample1.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class FileListFromFolder {

    public static  void createWriters(String dirPath,String destPath) throws IOException {

        System.out.println("Inside CreateWriters ...........");

        FileWriter myWriter = new FileWriter(destPath);
        PrintWriter printWriter = new PrintWriter(myWriter);

        File maindir = new File(dirPath);

        if (maindir.exists() && maindir.isDirectory()) {
            File arr[] = maindir.listFiles();
            RecursivePrint(arr, 0, 0,printWriter);
            myWriter.close();
            printWriter.close();

        }


    }

    public static void RecursivePrint(File[] arr, int index, int level,PrintWriter printWriter) throws IOException {
        // terminate condition
        if (index == arr.length) {
            return;
        }

        // tabs for internal levels
        for (int i = 0; i < level; i++) {
            //System.out.print("\t |->");
            printWriter.print("\t");
        }

        // for files
        if (arr[index].isFile()) {
            //System.out.println(arr[index].getName());
            printWriter.println(arr[index].getName());

        }

        // for sub-directories
        else if (arr[index].isDirectory())
        {
            //System.out.println("[" + arr[index].getName() + "]");
            printWriter.println("[" + arr[index].getName() + "]");

            // recursion for sub-directories
            RecursivePrint(arr[index].listFiles(), 0, level + 1,printWriter);
        }

        // recursion for main directory
        RecursivePrint(arr, ++index, level,printWriter);
    }
}
