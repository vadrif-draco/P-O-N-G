package core;

import javafx.scene.media.AudioClip;

public class SFX {
    private AudioClip a;

    public SFX(String s) {
        this(s, 1, 1);
    }

    public SFX(String s, int numOfLoops) {
        this(s, numOfLoops, 1);
    }

    public SFX(String s, double playRate) {
        this(s, 1, playRate);
    }

    public SFX(String s, int numOfLoops, double playRate) {
        a = new AudioClip(this.getClass().getResource("/media/" + s).toString());
        a.setCycleCount(numOfLoops);
        a.setRate(playRate);
        a.play();
    }
}
