package com.rufino.server.converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class Base64ToFile {

    byte[] decodedBytes;
    private final Path root = Paths.get("uploads");

    public Base64ToFile(String encodedString) {
        this.decodedBytes = Base64.getDecoder().decode(encodedString);
    }

    public String convert(String fileName) throws IOException {
        Path filePath = this.root.resolve(fileName);
        FileUtils.writeByteArrayToFile(new File(filePath.toString()), decodedBytes);
        return filePath.toString();
    }

}
