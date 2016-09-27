package pentachannel.thevcgroup.com.pentachannel;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class VideoPageFragment extends Fragment {

    View rootView;

    ArrayList<String> ImageVideoList = new ArrayList<String>();
    ArrayList<String> titleVideoList = new ArrayList<String>();
    ArrayList<String> youtubeLink = new ArrayList<String>();
    int positionclick;
    public ArrayAdapter<String> ListCustom;
    Communicator2 comm2;

    public VideoPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_videoview, container, false);

        getActivity().setTitle(getActivity().getIntent().getStringExtra("title"));

        ImageVideoList = VideoInfo.getInstance().getImageVideoList();
        positionclick = VideoInfo.getInstance().getPositon();
        titleVideoList = VideoInfo.getInstance().gettitleVideoList();
        youtubeLink = VideoInfo.getInstance().getyoutubeLink();

        ListCustom =
                new CustomArrayList(getActivity(),titleVideoList , ImageVideoList);

        comm2 = (Communicator2) getActivity();

        ListView listView = (ListView) rootView.findViewById(R.id.list_video_next);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        comm2.onSendYoutubeLink(position);
                    }
                }
        );
        listView.setAdapter(ListCustom);
        return rootView;
    }
}
