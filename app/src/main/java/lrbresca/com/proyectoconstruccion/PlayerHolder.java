package lrbresca.com.proyectoconstruccion;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.widget.MediaController;

public class PlayerHolder implements PlayerAdapter {

    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mResourceId;
    private VideoInfoListener mVideoInfoListener;

    public PlayerHolder(Context context) {
        mContext = context.getApplicationContext();
    }

    public void setVideoInfoListener(VideoInfoListener listener) {
        mVideoInfoListener = listener;
    }

    @Override
    public void loadMedia(int resourceId) {
        mResourceId = resourceId;
        initializeMediaPlayer();
        AssetFileDescriptor assetFileDescriptor = mContext.getResources().openRawResourceFd(mResourceId);
        try {
            mMediaPlayer.setDataSource(assetFileDescriptor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (mVideoInfoListener != null) {
                        mVideoInfoListener.onStateChanged(VideoInfoListener.State.COMPLETED);
                        mVideoInfoListener.onPlaybackCompleted();
                    }
                }
            });
        }
    }

    @Override
    public void play(SurfaceHolder sh) {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.setDisplay(sh);
            mMediaPlayer.start();
            if (mVideoInfoListener != null) {
                mVideoInfoListener.onStateChanged(VideoInfoListener.State.PLAYING);
            }
        }
    }



    @Override
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

//    @Override
//    public MediaController getMediaPlayer(){
//        return mMediaPlayer;
//    }

}
