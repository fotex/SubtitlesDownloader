import com.google.gson.JsonObject;

public class SubtitlesInfo {

    private TMDBMovieInfo tmdbMovie;

    private String language;
    private String downloadURL;
    private String subtitleFormat;
    private String destinationPath;
    private String languageFormat;
    private String subtitleEncoding;
    private String subtitlesId;
    private String subtitleFileName;

    private String season;
    private String episode;
    private String fileName;

    private boolean isExtended;
    private boolean isBlocked;

    SubtitlesInfo(JsonObject data) {
        if(data.get("LanguageName") != null) {
            language = data.get("LanguageName").getAsString();
        }
        if(data.get("SubDownloadLink") != null) {
            downloadURL = data.get("SubDownloadLink").getAsString();
        }
        if(data.get("SubFormat") != null) {
            subtitleFormat = data.get("SubFormat").getAsString();
        }
        if(data.get("SubEncoding") != null) {
            subtitleEncoding = data.get("SubEncoding").getAsString();
        }
        if(data.get("IDSubtitleFile") != null) {
            subtitlesId = data.get("IDSubtitleFile").getAsString();
        }
        if(data.get("SubFileName") != null) {
            subtitleFileName = data.get("SubFileName").getAsString();
        }
        if(data.get("subid") != null) {
            subtitlesId = data.get("subid").getAsString();
        }

        isBlocked = false;
        isExtended = false;
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

    public String getDownloadURL() {
        return downloadURL;
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
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSubtitleFormat(String subtitleFormat) {
        this.subtitleFormat = subtitleFormat;
    }

    public boolean isExtended() {
        return isExtended;
    }

    public void setExtended(boolean extended) {
        isExtended = extended;
    }

    public String getSubtitleFileName() {
        return subtitleFileName;
    }
}
