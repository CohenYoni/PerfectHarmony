package com.synel.perfectharmony.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    /**
     * Read UTF-8 content of a file
     *
     * @param filePath an absolut path of the file
     * @return the UTF-8 encoded content of the file
     */
    public static String readFile(String filePath) throws IOException {

        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }
}
