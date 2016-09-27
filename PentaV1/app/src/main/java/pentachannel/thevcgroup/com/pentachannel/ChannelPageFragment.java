package pentachannel.thevcgroup.com.pentachannel;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ChannelPageFragment extends Fragment
{
    String data = "";
    public ArrayAdapter<String> ListCustom;
    OkHttpClient client = new OkHttpClient();
    public String url = "http://www.pentachannel.com/apis/channel/channel_163/";

    String titleCh = "Title";

    ArrayList<String> ImageVideoList = new ArrayList<String>();
    ArrayList<String> titleVideoList = new ArrayList<String>();
    ArrayList<String> youtubeLink = new ArrayList<String>();
    int positionclick;

    TextView titleView;

    public ChannelPageFragment()
    {
    }

    @Override
    public  void onStart()
    {
        super.onStart();

        VideoInfo.getInstance().setImageVideoList(ImageVideoList);
        VideoInfo.getInstance().settitleVideoList(titleVideoList);
        VideoInfo.getInstance().setyoutubeLink(youtubeLink);
        VideoInfo.getInstance().setPositon(positionclick);

        url = "http://www.pentachannel.com/apis/channel/channel_" + getActivity().getIntent().getStringExtra("id") + "/";
        getActivity().setTitle(getActivity().getIntent().getStringExtra("titleCh"));
        LoadData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_channelpage, container, false);



        ListCustom =
                new CustomArrayList(getActivity(),titleVideoList , ImageVideoList);


        ListView listView = (ListView) rootView.findViewById(R.id.listView_Show);;
        listView.setAdapter(ListCustom);

        titleView = (TextView) rootView.findViewById(R.id.title_textView);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        positionclick = position;
                        Intent videoIntent = new Intent(getActivity(),VideoPage.class);
                        videoIntent.putExtra("url",youtubeLink.get(position));
                        videoIntent.putExtra("title",titleVideoList.get(position));
                        startActivity(videoIntent);
                    }
                }
        );

        return rootView;
    }
    private class DownloadData extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... url)
        {
            String result = "";
            try {
                 result = run(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("")) {
                Toast toast = Toast.makeText ( getActivity(), "No Internet connection", Toast.LENGTH_LONG );

                toast.show ( );
            } else {
                data = result;
                ReadJSON();
                ListCustom.notifyDataSetChanged();
                titleView.setText(titleCh);
                // New data is back from the server.  Hooray!
            }
        }
    }
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public void LoadData()
    {
        DownloadData loadData = new DownloadData();

        loadData.execute(url);
    }
    private  void ReadJSON()
    {
        try {
            JSONObject obj = new JSONObject(data);
            titleCh = obj.getString("name");

            JSONArray queue = obj.getJSONArray("queue");

            for(int i = 0; i < queue.length() ; i++)
            {
                JSONObject q = queue.getJSONObject(i);

                titleVideoList.add(q.getString("name"));

                ImageVideoList.add(q.getString("thumbnail"));

                youtubeLink.add(q.getString("video_id"));

                //DescriptionVideoList.add(q.getString("name"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
