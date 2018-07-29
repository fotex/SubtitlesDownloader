import java.util.HashMap;

public class SubtitlesInfo {

    private String language;
    private String downloadsCount;
    private String downloadURL;
    private String userRank;
    private String subtitleFormat;
    private String destinationPath;
    private String languageFormat;
    private String subtitleEncoding;
    private String subtitlesId;

    private boolean blocked;
    private String season;
    private String episode;
    private String fileName;

    private TMDBMovieInfo tmdbMovie;

    SubtitlesInfo(HashMap<String, String> data) {
        language = data.get("LanguageName");
        downloadsCount = data.get("SubDownloadsCnt");
        downloadURL = data.get("SubDownloadLink");
        userRank = data.get("UserRank");
        subtitleFormat = data.get("SubFormat");
        subtitleEncoding = data.get("SubEncoding");
        subtitlesId = data.get("IDSubtitleFile");
        blocked = false;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public String getLanguage() {
        return language;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadsCount() {
        return downloadsCount;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public String getUserRank() {
        return userRank;
    }

    public String getSubtitleFormat() {
        return subtitleFormat;
    }

    public TMDBMovieInfo getTmdbMovie() {
        return tmdbMovie;
    }

    public void setTmdbMovie(TMDBMovieInfo tmdbMovie) {
        this.tmdbMovie = tmdbMovie;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getLanguageFormat() {
        return languageFormat;
    }

    public void setLanguageFormat(String languageFormat) {
        this.languageFormat = languageFormat;
    }

    public String getSubtitleEncoding() {
        return subtitleEncoding;
    }

    public String getSubtitlesId() {
        return subtitlesId;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSubtitleFormat(String subtitleFormat) {
        this.subtitleFormat = subtitleFormat;
    }
}
