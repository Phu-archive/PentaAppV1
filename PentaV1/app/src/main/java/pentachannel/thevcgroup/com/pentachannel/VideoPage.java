package pentachannel.thevcgroup.com.pentachannel;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;

import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;


public class VideoPage extends Activity implements YouTubePlayer.OnInitializedListener,Communicator2
{
    public static final String DEVELOPER_KEY = "AIzaSyAdqCkFjmWKUttdQzCyDLkN01Py3Yh2_MY";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String video_id = "fhWaJi1Hsfo";
    int postionOfVideo;
    ArrayList<String> ulink = new ArrayList<String>();
    ArrayList<String> utitle = new ArrayList<String>();

    YouTubePlayerFragment myYouTubePlayerFragment;
    YouTubePlayer uPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        video_id = getIntent().getStringExtra("url");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        myYouTubePlayerFragment = new YouTubePlayerFragment();
        fragmentTransaction.add(R.id.container,myYouTubePlayerFragment);
        fragmentTransaction.commit();
        /*
        myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
                .findFragmentById(R.id.youtubeplayerfragment);
        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, this);
        */
        ulink = VideoInfo.getInstance().getyoutubeLink();
        utitle = VideoInfo.getInstance().gettitleVideoList();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video__view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored)
    {
        uPlayer = player;
        if (!wasRestored) {
            player.loadVideo(video_id);
        }
        player.setPlayerStateChangeListener(playerStateChangeListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtubeplayerfragment);
    }
    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0)
        {

        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded()
        {
            postionOfVideo++;
            video_id = ulink.get(postionOfVideo);
            uPlayer.loadVideo(video_id);
            setTitle(utitle.get(postionOfVideo));
        }
        @Override
        public void onVideoStarted() {
        }
    };
    public void onSendYoutubeLink(int pos)
    {

        postionOfVideo = pos;
        video_id = ulink.get(pos);
        uPlayer.loadVideo(video_id);
        setTitle(utitle.get(pos));
    }
}
