package nishkarsh.work.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jarvanmo.exoplayerview.media.SimpleMediaSource;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.jarvanmo.exoplayerview.media.ExoMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleQuality;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

import java.util.ArrayList;
import java.util.List;

import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;

public class MainActivity extends AppCompatActivity {


    private ExoVideoView videoView;
    private Button modeFit;
    private Button modeNone;
    private Button modeHeight;
    private Button modeWidth;
    private Button modeZoom;
    private View wrapper;
    private Button play;
    private View contentView;
    private TextView pos1,pos2,pos3,pos4;
    private  boolean p1=false,p2=false,p3=false,p4=true;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = findViewById(R.id.activity_main);

        videoView = findViewById(R.id.videoView);
        modeFit = findViewById(R.id.mode_fit);
        modeNone = findViewById(R.id.mode_none);
        modeHeight = findViewById(R.id.mode_height);
        modeWidth = findViewById(R.id.mode_width);
        modeZoom = findViewById(R.id.mode_zoom);
        wrapper = findViewById(R.id.wrapper);
        play = findViewById(R.id.changeMode);
        pos1=findViewById(R.id.idpos1);
        pos2=findViewById(R.id.idpos2);
        pos3=findViewById(R.id.idpos3);
        pos4=findViewById(R.id.idpos4);

        videoView.setBackListener((view, isPortrait) -> {
            if (isPortrait) {
                finish();
            }
            return false;
        });


//
//


        videoView.setOrientationListener(orientation -> {
            if (orientation == SENSOR_PORTRAIT) {
                changeToPortrait();
            } else if (orientation == SENSOR_LANDSCAPE) {
                changeToLandscape();
            }
        });



//

//       final SimpleMediaSource mediaSource = new SimpleMediaSource("http://video19.ifeng.com/video07/2013/11/11/281708-102-007-1138.mp4");
//        SimpleMediaSource mediaSource  = new SimpleMediaSource("https://tungsten.aaplimg.com/VOD/bipbop_adv_example_v2/master.m3u8");
//        final SimpleMediaSource mediaSource = new SimpleMediaSource("http://playertest.longtailvideo.com/adaptive/bipbop/gear4/prog_index.m3u8");
//        SimpleMediaSource mediaSource  = new SimpleMediaSource(" https://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear0/prog_index.m3u8");
//       SimpleMediaSource mediaSource = new SimpleMediaSource("https://tungsten.aaplimg.com/VOD/bipbop_adv_fmp4_example/master.m3u8");
//        SimpleMediaSource mediaSource = new SimpleMediaSource("http://pullhlsbb8f2e48.live.126.net/live/7de213ebb3dc4db2aa2f32f3da0b028d/playlist.m3u8");
//        SimpleMediaSource mediaSource = new SimpleMediaSource("http://rotation.vod.zlive.cc/channel/1234.m3u8");
//        SimpleMediaSource mediaSource = new SimpleMediaSource("https://media.w3.org/2010/05/sintel/trailer.mp4");
        SimpleMediaSource mediaSource = new SimpleMediaSource("https://gpsonlineclass.s3.ap-south-1.amazonaws.com/Archimedes+Principle-1.m4v");
//        SimpleMediaSource mediaSource = new SimpleMediaSource("http://stream1.hnntv.cn/lywsgq/sd/live.m3u8");

        mediaSource.setDisplayName("VideoPlaying");
        List<ExoMediaSource.Quality> qualities = new ArrayList<>();
        ExoMediaSource.Quality quality;

        for (int i = 0; i < 6; i++) {
            SpannableString spannableString = new SpannableString("Quality" + i);
            if (i % 2 == 0) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.YELLOW);
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            } else {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            quality = new SimpleQuality(spannableString, mediaSource.uri());
            qualities.add(quality);
        }
        mediaSource.setQualities(qualities);

        videoView.play(mediaSource, false);
        play.setOnClickListener(view -> {
            videoView.play(mediaSource);
            play.setVisibility(View.INVISIBLE);

        });


        modeFit.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT));
        modeWidth.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH));
        modeHeight.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT));
        modeNone.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL));
        modeZoom.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM));
    }

    private void changeToPortrait() {

        WindowManager.LayoutParams attr = getWindow().getAttributes();
//        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        window.setAttributes(attr);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        wrapper.setVisibility(View.VISIBLE);
    }


    private void changeToLandscape() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getWindow();
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        wrapper.setVisibility(View.GONE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Build.VERSION.SDK_INT <= 23)) {
            videoView.resume();
        }
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
              if(p1){p1=false; pos1.setVisibility(View.INVISIBLE); p2=true; pos2.setVisibility(View.VISIBLE);}
              else if(p2){p2=false; pos2.setVisibility(View.INVISIBLE);p3=true;pos3.setVisibility(View.VISIBLE);}
              else if(p3){p3=false; pos3.setVisibility(View.INVISIBLE);p4=true; pos4.setVisibility(View.VISIBLE);}
              else if(p4){p4=false; pos4.setVisibility(View.INVISIBLE);p1=true; pos1.setVisibility(View.VISIBLE);}
            }
        }, delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            videoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.releasePlayer();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return videoView.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}