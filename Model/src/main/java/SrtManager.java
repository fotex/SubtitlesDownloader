import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SrtManager {

    private String subtitlePath;
    private String subtitles;

    private ArrayList<SrtSubtitle> subtitlesList;

    public SrtManager(String subtitlePath) {
        this.subtitlePath = subtitlePath;
        subtitlesList = new ArrayList<>();
    }

    public void loadSubtitleArray(int firstSubtitleId, int subtitlesNumber) throws IOException {
        subtitles = null;
        subtitles = new String(Files.readAllBytes(Paths.get(subtitlePath)), StandardCharsets.UTF_8);
        Pattern pattern = Pattern.compile("([0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3} --> [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3})(?s)(.*?)(?=[0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3} --> [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3})");
        Matcher matcher = pattern.matcher(subtitles);

        int index = 1;
        for (int i = 0; i < firstSubtitleId - 1; i++) {
            matcher.find();
        }
        while (matcher.find() && index <= subtitlesNumber) {
            String text = matcher.group(2);
            text = text.replaceAll("(?:\\R.*.*?){2}$", "");

            SrtSubtitle srtSubtitle = new SrtSubtitle();
            srtSubtitle.setStartTimeMs(convertTimeToMs(getSubtitleTime(matcher.group(1), 1)));
            srtSubtitle.setEndTimeMs(convertTimeToMs(getSubtitleTime(matcher.group(1), 2)));
            srtSubtitle.setText(text);

            subtitlesList.add(srtSubtitle);

            index++;
        }
    }

    private int convertTimeToMs(String time) {
        int hours = Integer.parseInt(time.substring(0, 2)) * 60 * 60 * 1000;
        int minutes = Integer.parseInt(time.substring(3, 5)) * 60 * 1000;
        int seconds = Integer.parseInt(time.substring(6, 8)) * 1000;
        int miliseconds = Integer.parseInt(time.substring(9));

        return hours + minutes + seconds + miliseconds;
    }

    private String getSubtitleTime(String timeInfo, int matchIndex) {
        Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}");
        Matcher matcher = pattern.matcher(timeInfo);

        String time = null;
        for (int i = 1; i <= matchIndex && matcher.find(); i++) {
            time = matcher.group();
        }

        return time;
    }

    public void shiftSubtitle(int index, int offset) {
        subtitlesList.get(index).setStartTimeMs(subtitlesList.get(index).getStartTimeMs() + offset);
        subtitlesList.get(index).setEndTimeMs(subtitlesList.get(index).getEndTimeMs() + offset);
    }

    public int getSubtitleStartTime(int index) {
        return subtitlesList.get(index).getStartTimeMs();
    }

    public int getSubtitleEndTime(int index) {
        return subtitlesList.get(index).getEndTimeMs();
    }

    public String getSubtitleText(int index) {
        return subtitlesList.get(index).getText();
    }


    public int getSubtitleListSize() {
        return subtitlesList.size();
    }

    public String getSubtitlePath() {
        return subtitlePath;
    }
}
