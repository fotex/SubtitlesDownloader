import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesSyncer {

    private String subtitlePath;
    private String subtitles;

    /*
        Only srt subtitles supported.
     */

    public SubtitlesSyncer(String subtitlePath) {
        this.subtitlePath = subtitlePath;
    }

    public void srtOffset(double time) throws IOException {
        subtitles = null;

        subtitles = new String(Files.readAllBytes(Paths.get(subtitlePath)), StandardCharsets.UTF_8);

        Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}");
        Matcher matcher = pattern.matcher(subtitles);

        String newTime;
        String oldTime;

        while (matcher.find()) {
            oldTime = matcher.group();
            newTime = srtShifter(oldTime, time);

            subtitles = subtitles.replace(oldTime, newTime);
        }
    }

    private String srtShifter(String time, double offset) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(3, 5));
        int seconds = Integer.parseInt(time.substring(6, 8));
        int miliseconds = Integer.parseInt(time.substring(9));

        int msOffset = (int) (offset * 1000);
        int totalSeconds = (int) (msOffset / 1000);

        int addSeconds = (int) (msOffset / 1000) % 60;
        int addMinutes = (int) ((msOffset / (1000 * 60)) % 60);
        int addHours = (int) ((msOffset / (1000 * 60 * 60)));
        int addMiliseconds = msOffset - (totalSeconds * 1000);

        addSeconds += ((miliseconds + addMiliseconds) / 1000);
        addMinutes += ((seconds + addSeconds) / 60);
        addHours += ((minutes + addMinutes) / 60);

        miliseconds = ((miliseconds + addMiliseconds) % 1000);

        if (miliseconds < 0) {
            miliseconds = 1000 + miliseconds;
            --addSeconds;
        }

        seconds = ((seconds + addSeconds) % 60);

        if (seconds < 0) {
            seconds = 60 + seconds;
            --minutes;
        }

        minutes = (minutes + addMinutes) % 60;

        if (minutes < 0) {
            minutes = 60 + minutes;
            --hours;
        }

        hours = (hours + addHours);

        if (hours < 0) {
            miliseconds = 0;
            minutes = 0;
            seconds = 0;
            hours = 0;
        }

        return String.format("%1$02d:%2$02d:%3$02d,%4$03d", hours, minutes, seconds, miliseconds);
    }

    public void save() {
        try {
            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(subtitlePath), "UTF-8"));
            fileWriter.write(subtitles);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
