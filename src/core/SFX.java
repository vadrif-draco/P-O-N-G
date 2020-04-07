package core;

import javafx.scene.media.AudioClip;

class SFX {
    SFX(String s) {
        AudioClip a = new AudioClip(this.getClass().getResource("/media/" + s + ".mp3").toString());
        a.setCycleCount(1);
        a.setRate(1);
        a.play();
    }
    SFX(String s, int numOfLoops) {
        AudioClip a = new AudioClip(this.getClass().getResource("/media/" + s + ".mp3").toString());
        a.setCycleCount(numOfLoops);
        a.setRate(1);
        a.play();
    }
    SFX(String s, double playRate) {
        AudioClip a = new AudioClip(this.getClass().getResource("/media/" + s + ".mp3").toString());
        a.setCycleCount(1);
        a.setRate(playRate);
        a.play();
    }
    SFX(String s, int numOfLoops, double playRate) {
        AudioClip a = new AudioClip(this.getClass().getResource("/media/" + s + ".mp3").toString());
        a.setCycleCount(numOfLoops);
        a.setRate(playRate);
        a.play();
    }
}
