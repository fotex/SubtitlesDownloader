import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleConverter {

    private String rawName;

    private String episode;
    private String season;
    private String convertedName;
    private boolean isTVShow;

    public TitleConverter(String name) {
        this.rawName = name;
    }

    /**
     * This method remove all 3-4 digit with p at the end pattern. (720p, 1080p)
     * For example: Dark.River.2017.1080p.BluRay.x264-EiDER (1080p.BluRay.x264-EiDER will be deleted).
     */

    public void convert() {
        checkTVShow();

        rawName = rawName.replaceAll("(\\d{3,4})p.+", "");
        rawName = rawName.replaceAll("\\.", " ");

        this.convertedName = rawName;
    }

    /**
     * This method check if the title contains S00E00 mark, if yes this title is tv series.
     */

    private void checkTVShow() {
        String nameWithSeason = "^.*S\\d\\dE\\d\\d";

        Pattern r = Pattern.compile(nameWithSeason);
        Matcher m = r.matcher(rawName);
        if (m.find( )) {
            this.isTVShow = true;
            this.rawName = m.group();
            removeMark();
        }
    }

    /**
     * This method remove S00E00 mark from the title.
     * Also this method is setting a season and episode variables.
     */
    private void removeMark() {
        String pattern = "S\\d\\dE\\d\\d";

        Pattern r1 = Pattern.compile(pattern);
        Matcher m1 = r1.matcher(rawName);
        if (m1.find( )) {
            this.rawName = this.rawName.replaceAll(m1.group(), "");
        }

        this.season = m1.group().substring(1, 3);
        this.episode = m1.group().substring(4, 6);
    }

    public String getEpisode() {
        return episode;
    }

    public String getSeason() {
        return season;
    }

    public String getConvertedName() {
        return convertedName;
    }

    public Boolean isTVShow() {
        return isTVShow;
    }
}
