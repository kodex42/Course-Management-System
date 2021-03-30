package com.comp3000.project.cms.BLL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Utils {
    public static Path joinPaths(String... paths) {
        String[] rest = Arrays.copyOfRange(paths, 1, paths.length);
        return Paths.get(
                paths[0],
                rest
        );
    }
}
