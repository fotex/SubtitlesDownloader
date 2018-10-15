import ws.schild.jave.*;

import java.io.File;

public class MovieConverter {

    private final String TEMP_SOUND_DIR = "tmp" + FileManager.separator + "moviesound.wav";

    private int samplingRate = 44100;
    private int bitRate = 128000;
    private int channels = 2;
    private int duration = 60;

    public MovieConverter() {

    }

    public void convertToWav(File movie, float offset, float duration) throws EncoderException {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        audio.setBitRate(bitRate);
        audio.setSamplingRate(samplingRate);
        audio.setChannels(channels);

        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("wav");
        encodingAttributes.setOffset(offset);
        encodingAttributes.setDuration(duration);
        encodingAttributes.setAudioAttributes(audio);

        Encoder encoder = new Encoder();

        encoder.encode(new MultimediaObject(movie), new File(TEMP_SOUND_DIR), encodingAttributes);
    }

}
