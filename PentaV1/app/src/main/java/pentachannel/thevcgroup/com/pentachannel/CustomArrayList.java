package pentachannel.thevcgroup.com.pentachannel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomArrayList extends ArrayAdapter<String>
{
    ArrayList<String> image;
    ArrayList<String> description;

    public CustomArrayList(Context context, ArrayList<String> name,ArrayList<String> imageArray) {
        super(context, R.layout.adapter , name);
        this.image = imageArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflaterCustom = LayoutInflater.from(getContext());
        View customView = inflaterCustom.inflate(R.layout.adapter, parent, false);

        String nameItem = getItem(position);

        TextView itemName = (TextView) customView.findViewById(R.id.nameViewInAdapter);
        ImageView itemImage = (ImageView) customView.findViewById(R.id.imageInAdapter);
        //TextView itemDescription = (TextView) customView.findViewById(R.id.descriptionViewInAdapter);

        itemName.setText(nameItem);
        Picasso.with(getContext()).load(image.get(position)).into(itemImage);
        //itemDescription.setText(description.get(position));
        return customView;

    }

}

