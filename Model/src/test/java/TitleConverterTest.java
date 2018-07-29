import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TitleConverterTest {

    @Test
    public void convertTVShowTest() {
        String tvShowTitle = "Test.tvshow.title.S01E05.1080p.NF.WEBRip.x265.HEVC.6CH-MRN";

        TitleConverter titleConverter = new TitleConverter(tvShowTitle);
        titleConverter.convert();

        assertEquals("Test tvshow title ", titleConverter.getConvertedName());
        assertEquals(true, titleConverter.isTVShow());
        assertEquals("01", titleConverter.getSeason());
        assertEquals("05", titleConverter.getEpisode());
    }

    @Test
    public void convertMovieTest() {
        String movieTitle = "Test.tvshow.title.1080p.NF.WEBRip.x265.HEVC.6CH-MRN";

        TitleConverter titleConverter = new TitleConverter(movieTitle);
        titleConverter.convert();

        assertEquals("Test tvshow title ", titleConverter.getConvertedName());
        assertEquals(false, titleConverter.isTVShow());
        assertEquals(null, titleConverter.getSeason());
        assertEquals(null, titleConverter.getEpisode());
    }

}
