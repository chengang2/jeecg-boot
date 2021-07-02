package org.jeecg.modules.cg.cg_kafka.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class util {

    public static void createShell(String path, String... strings) throws Exception {
        if (strings == null) {
            System.out.println("strings is null");
            return;
        }
        File sh = new File(path);
        boolean createResult = sh.createNewFile();
        if (!createResult) {
            System.out.println("create failed");
        }
        boolean executeResult = sh.setExecutable(true);
        if (!executeResult) {
            System.out.println("execute failed");
        }
        FileWriter fw = new FileWriter(sh);
        BufferedWriter bf = new BufferedWriter(fw);

        for (int i = 0; i < strings.length; i++) {
            bf.write(strings[i]);
            if (i < strings.length - 1) {
                bf.newLine();
            }
        }
        bf.flush();
        bf.close();

    }


}
