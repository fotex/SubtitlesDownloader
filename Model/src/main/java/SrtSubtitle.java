public class SrtSubtitle {

    private int startTimeMs;
    private int endTimeMs;
    private String text;

    public SrtSubtitle() {

    }

    public int getStartTimeMs() {
        return startTimeMs;
    }

    public void setStartTimeMs(int startTimeMs) {
        this.startTimeMs = startTimeMs;
    }

    public int getEndTimeMs() {
        return endTimeMs;
    }

    public void setEndTimeMs(int endTimeMs) {
        this.endTimeMs = endTimeMs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
