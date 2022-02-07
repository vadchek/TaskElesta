package org.vadim.elesta;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileReadWrite {
    //метод для записи в файл
    public static void writeToFile(String fileName, String text) throws IOException {
        byte buf[] = text.getBytes(StandardCharsets.UTF_8);
        try (FileOutputStream f = new FileOutputStream(fileName)) {
            f.write(buf);
        } catch (IOException e) {
            throw new IOException("Enter correct filename!");
        }
    }

    //метод для чтения из файла
    public static String readFromFile(String fileName) throws FileNotFoundException {
        String text = "";
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
             BufferedReader buf = new BufferedReader(in)) {
            String str;
            while ((str = buf.readLine()) != null) {
                text = text + str + "\n";
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found!");
        }
        return text;
    }

}
