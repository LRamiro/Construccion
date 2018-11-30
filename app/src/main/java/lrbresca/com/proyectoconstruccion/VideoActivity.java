package lrbresca.com.proyectoconstruccion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity{

    public static final String TAG = "VideoActivity";
    public static final int MEDIA_RES_ID = R.raw.vid_bigbuckbunny;

    private PlayerAdapter mPlayerAdapter;
    SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        surfaceView = findViewById(R.id.surfaceView1);
        initializePlaybackController();

        Log.d(TAG, "onCreate: finished");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlayerAdapter.loadMedia(MEDIA_RES_ID);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mPlayerAdapter.play(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }
        });

        Log.d(TAG, "onStart: create MediaPlayer");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isChangingConfigurations() && mPlayerAdapter.isPlaying()) {
            Log.d(TAG, "onStop: don't release MediaPlayer as screen is rotating & playing");
        } else {
            mPlayerAdapter.release();
            Log.d(TAG, "onStop: release MediaPlayer");
        }
    }

    private void initializePlaybackController() {
        PlayerHolder mPlayerHolder = new PlayerHolder(this);
        Log.d(TAG, "initializePlaybackController: created MediaPlayerHolder");
        mPlayerHolder.setVideoInfoListener(new PlaybackListener());
        mPlayerAdapter = mPlayerHolder;
        Log.d(TAG, "initializePlaybackController: MediaPlayerHolder progress callback set");
    }

    public class PlaybackListener extends VideoInfoListener {

        @Override
        public void onStateChanged(@State int state) {
            String stateToString = VideoInfoListener.convertStateToString(state);
        }

        @Override
        public void onPlaybackCompleted() {
            cambiarDeActividad();
        }
    }

    private void cambiarDeActividad(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


}
