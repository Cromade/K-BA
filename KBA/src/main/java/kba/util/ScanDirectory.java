package kba.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.stream.Collectors;

public class ScanDirectory {

    public static void scanDirectory(File dir, FilenameFilter filenameFilter, ScanDirectoryCase scanDirectoryCase) {
        Thread dirScanner = new Thread(() -> {
            Map<String, Long> filenames = dir.exists() ? Arrays.stream(dir.listFiles(filenameFilter)).collect(Collectors.toMap(f -> f.getName(), f -> f.lastModified())) : new HashMap<>();
            while(true) {
                Map<String, Long> newfilenames = dir.exists() ? Arrays.stream(dir.listFiles(filenameFilter)).collect(Collectors.toMap(f -> f.getName(), f -> f.lastModified())) : new HashMap<>();
                Set<String> deletedFiles = new HashSet<>(filenames.keySet());
                for(Map.Entry<String, Long> fn : newfilenames.entrySet()) {
                    if (filenames.containsKey(fn.getKey())) {
                        deletedFiles.remove(fn.getKey());
                    } else {
                        // added file
                        scanDirectoryCase.pluginFileAdded(new File(dir, fn.getKey()));
                    }
                }
                for(String deletedFile : deletedFiles) {
                    scanDirectoryCase.pluginFileRemoved(new File(dir, deletedFile));
                }
                filenames = newfilenames;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "Directory-Plugin-Scanner");
        dirScanner.setDaemon(true);
        dirScanner.start();
    }
}