public class Entry {
    private boolean isValid;
    private String frame_number;

    public Entry(boolean isValid, String frame_number) {
        this.isValid = isValid;
        this.frame_number = frame_number;
    }

    public boolean getValid() {
        return isValid;
    }


    public String getFrame_number() {
        return frame_number;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }

    public void setFrame_number(String frame_number) {
        this.frame_number = frame_number;
    }
}
