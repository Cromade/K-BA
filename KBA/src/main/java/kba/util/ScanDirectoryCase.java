package kba.util;

import java.io.File;

public interface ScanDirectoryCase {
    void pluginFileAdded(File newFile);
    void pluginFileRemoved(File removedFile);
}
