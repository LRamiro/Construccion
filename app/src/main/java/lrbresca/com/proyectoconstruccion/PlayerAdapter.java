package lrbresca.com.proyectoconstruccion;

import android.view.SurfaceHolder;
import android.widget.MediaController;

public interface PlayerAdapter {

        void loadMedia(int resourceId);

        void release();

        boolean isPlaying();

        void play(SurfaceHolder sh);

        //MediaController getMediaPlayer();

}
