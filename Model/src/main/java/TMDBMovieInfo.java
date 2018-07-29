public class TMDBMovieInfo {

    private String movieName;
    private String imdbId;
    private String posterImagePath;

    private boolean isTVShow;

    public TMDBMovieInfo() {
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public boolean isTVShow() {
        return isTVShow;
    }

    public void setTVShow(boolean TVShow) {
        isTVShow = TVShow;
    }
}
