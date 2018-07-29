import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SubtitlesManager {

    private static final String separator = System.getProperty("file.separator");
    public static final String TEMP_SUBTITLE_DIR = ".." + separator + "tmp" + separator + "subtitle.txt";

    public static final int MAX_SUBTITLES = 15;

    private ArrayList<SubtitlesInfo> subtitleList = new ArrayList<>();

    public void download() {
        for (int i = 0; i < subtitleList.size(); i++) {
            try {
                downloadSubtitle(subtitleList.get(i));
                convertToUTF8(subtitleList.get(i));
                moveSubtitle(subtitleList.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadSubtitle(SubtitlesInfo subtitlesInfo) throws IOException {
        String stringUrl = subtitlesInfo.getDownloadURL().replaceAll(".gz", "");
        URL url = new URL(stringUrl);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                url.openStream()));

        String str;

        try (OutputStreamWriter writer =
                     new OutputStreamWriter(new FileOutputStream(TEMP_SUBTITLE_DIR))) {
            while ((str = bufferedReader.readLine()) != null) {
                writer.write(str);
                writer.write(System.lineSeparator());
            }

            writer.close();
        }
    }

    private void moveSubtitle(SubtitlesInfo subtitlesInfo) {
        Path source = Paths.get(TEMP_SUBTITLE_DIR);
        Path destination = Paths.get(subtitlesInfo.getDestinationPath() + separator +
                subtitlesInfo.getFileName() + "." + subtitlesInfo.getLanguageFormat() + "." + subtitlesInfo.getSubtitleFormat());

        try {
            Files.deleteIfExists(destination);
            Files.move(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertToUTF8(SubtitlesInfo subtitlesInfo) {
        File file = new File(SubtitlesManager.TEMP_SUBTITLE_DIR);
        String content = null;
        try {
            content = FileUtils.readFileToString(file, subtitlesInfo.getSubtitleEncoding());
            FileUtils.write(file, content, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(SubtitlesInfo subtitlesInfo) throws OSException {
        if (subtitleList.size() < MAX_SUBTITLES) {
            subtitleList.add(subtitlesInfo);
        } else {
            throw new OSException("OS: Too many subtitles (40 API Request per 10 second limit)");
        }
    }

    public void remove(SubtitlesInfo subtitlesInfo) {
        subtitleList.remove(subtitlesInfo);
    }

    public void clear() {
        subtitleList.clear();
    }

    public int getSize() {
        return subtitleList.size();
    }

    public ArrayList<SubtitlesInfo> getSubtitleList() {
        return subtitleList;
    }
}
