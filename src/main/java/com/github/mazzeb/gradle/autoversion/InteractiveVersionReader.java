package com.github.mazzeb.gradle.autoversion;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InteractiveVersionReader {

    private final InputStream inputStream;
    private final PrintStream outputStream;

    private InteractiveVersionReader(InputStream inputStream, PrintStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public static InteractiveVersionReader interactiveVersionReader() {
        return new InteractiveVersionReader(System.in, System.out);
    }

    public static InteractiveVersionReader interactiveVersionReader(InputStream inputStream, PrintStream outputStream) {
        return new InteractiveVersionReader(inputStream, outputStream);
    }

    public Version readVersion(String prompt) {
        Scanner scanner = new Scanner(inputStream);
        outputStream.print(prompt);
        String next = scanner.next();
        return parseVersion(next);
    }

    public Version parseVersion(String versionString) {
        String[] split = versionString.split("\\.", 3);
        Version.Builder builder = Version.versionBuilder();
        if (split.length > 0) {
            builder.withMajor(Long.valueOf(split[0]));
        }
        if (split.length > 1) {
            builder.withMinor(Long.valueOf(split[1]));
        }
        if (split.length > 2) {
            String s = split[2];
            String[] splitLabel = s.split("-", 2);
            if (splitLabel.length > 0) {
                builder.withPatch(Long.valueOf(splitLabel[0]));
            }
            if (splitLabel.length > 1) {
                builder.withLabel(splitLabel[1]);
            }
        }
        return builder.build();
    }
}
