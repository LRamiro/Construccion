package lrbresca.com.proyectoconstruccion;

import android.support.annotation.IntDef;

public abstract class VideoInfoListener {

    @IntDef({State.INVALID, State.PLAYING, State.COMPLETED})

    @interface State {
        int INVALID = -1;
        int PLAYING = 0;
        int COMPLETED = 1;
    }

    public static String convertStateToString(@State int state) {
        String stateString;
        switch (state) {
            case State.COMPLETED:
                stateString = "COMPLETED";
                break;
            case State.INVALID:
                stateString = "INVALID";
                break;
            case State.PLAYING:
                stateString = "PLAYING";
                break;
            default:
                stateString = "N/A";
        }
        return stateString;
    }

    void onStateChanged(@State int state) {
    }

    void onPlaybackCompleted() {
    }
}
