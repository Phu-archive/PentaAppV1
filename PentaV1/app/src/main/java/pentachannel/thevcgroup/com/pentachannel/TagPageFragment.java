package pentachannel.thevcgroup.com.pentachannel;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class TagPageFragment extends Fragment
{
    String data = "";
    public ArrayAdapter<String> ListCustom;
    OkHttpClient client = new OkHttpClient();
    public String url = "http://www.pentachannel.com/api/v2/channel/tag/39/?page=1&per_page=20";

    ArrayList<String> IdList = new ArrayList<String>();
    ArrayList<String> ImageVideoList = new ArrayList<String>();
    ArrayList<String> titleChannel = new ArrayList<String>();
    String Tag = "";
    int Page = 1;

    View rootView;

    TextView titleView;

    public void getTagFromNav(String tag)
    {
        Tag = tag;
        url = "http://www.pentachannel.com/api/v2/channel/tag/" + Tag + "/?page="+ Integer.toString(Page) + "&per_page=20";
        LoadData(url);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks he
        // re. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public  void onStart()
    {
        super.onStart();
        LoadData(url);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_tagpage, container, false);

        ListCustom =
                new CustomArrayList(getActivity(), titleChannel, ImageVideoList);


        ListView listView = (ListView) rootView.findViewById(R.id.listView_Show);;
        listView.setAdapter(ListCustom);

        titleView = (TextView) rootView.findViewById(R.id.title_textView);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(),ChannelPage.class);
                        intent.putExtra("id", IdList.get(position));
                        intent.putExtra("titleCh", titleChannel.get(position));
                        startActivity(intent);
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
                //titleView.setText(titleCh);

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
    public void LoadData(String urlLoad)
    {
        DownloadData loadData = new DownloadData();

        loadData.execute(urlLoad);
    }
    private  void ReadJSON()
    {
        try {
            titleChannel.clear();
            ImageVideoList.clear();
            IdList.clear();
            JSONObject obj = new JSONObject(data);
            JSONArray queue = obj.getJSONArray("channels");

            for(int i = 0; i < queue.length() ; i++)
            {
                JSONObject q = queue.getJSONObject(i);

                titleChannel.add(q.getString("name"));

                ImageVideoList.add(q.getString("real_icon"));

                IdList.add(q.getString("id"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
