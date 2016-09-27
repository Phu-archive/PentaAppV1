package pentachannel.thevcgroup.com.pentachannel;

import java.util.ArrayList;

public class VideoInfo {

    static VideoInfo sInstance;
    ArrayList<String> ImageVideoList = new ArrayList<String>();
    ArrayList<String> titleVideoList = new ArrayList<String>();
    ArrayList<String> youtubeLink = new ArrayList<String>();
    int positon = 0;

    public static VideoInfo getInstance() {
        if(sInstance == null) {
            sInstance = new VideoInfo();
        }
        return sInstance;
    }

    public void setImageVideoList(ArrayList<String> image) {
        ImageVideoList = image;
    }

    public void settitleVideoList(ArrayList<String> title) {
        titleVideoList = title;
    }
    public void setyoutubeLink(ArrayList<String> link) {
        youtubeLink = link;
    }
    public void setPositon(int pos) {
        positon = pos;
    }

    public ArrayList<String> getImageVideoList() {
        return ImageVideoList;
    }

    public ArrayList<String> gettitleVideoList() {
        return titleVideoList;
    }
    public ArrayList<String> getyoutubeLink() {
        return youtubeLink;
    }
    public int getPositon() {
        return positon;
    }
}
