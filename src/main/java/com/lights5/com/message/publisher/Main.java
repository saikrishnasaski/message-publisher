package com.lights5.com.message.publisher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/main/resources/file.txt");

        Files.write(path, "This is working".getBytes(), StandardOpenOption.CREATE);

        System.out.println(Files.readString(path));
    }
}
