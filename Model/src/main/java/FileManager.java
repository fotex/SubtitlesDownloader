import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    public static final String separator = System.getProperty("file.separator");

    public ArrayList<File> getMoviePaths(List<File> files) {
        File[] dirFiles;
        ArrayList<File> filePaths = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).isDirectory()) {
                dirFiles = files.get(i).listFiles();

                for (int j = 0; j < dirFiles.length; j++) {
                    if (isMovie(dirFiles[j])) {
                        filePaths.add(dirFiles[j]);
                    }
                }
            }
            if (files.get(i).isFile()) {
                if (isMovie(files.get(i))) {
                    filePaths.add(files.get(i));
                }
            }
        }

        return filePaths;
    }

    public boolean isMovie(File file) {
        String[] movieExtensions = new String[]{"mkv", "avi", "mov", "mp4", "flv"};

        if (Arrays.asList(movieExtensions).contains(FilenameUtils.getExtension(file.getAbsolutePath()).toLowerCase())) {
            return true;
        }
        return false;
    }

    public boolean isSubtitle(File file) {
        String[] movieExtensions = new String[]{"srt"};

        if (Arrays.asList(movieExtensions).contains(FilenameUtils.getExtension(file.getAbsolutePath()).toLowerCase())) {
            return true;
        }
        return false;
    }

    public static void createConfigFile(File file) {
        if (!file.getParentFile().exists()) {
            File directory = new File("config");
            directory.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTempDir() {
        File directory = new File("tmp");
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}