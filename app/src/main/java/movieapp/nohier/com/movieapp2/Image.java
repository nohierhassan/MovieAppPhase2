package movieapp.nohier.com.movieapp2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed k on 15/08/2016.
 */
public class Image implements Parcelable {
    private String title;
    private String path;
    private String overview;
    private double vote;
    private String year;
    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public Image(Parcel in) {
        String [] data = new String[6];
        in.readStringArray(data);
        this.title = data[0];
        this.path = data[1];
        this.overview = data[2];
        this.vote = Double.parseDouble(data[3]);
        this.year = data[4];
        this.id = data[5];

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }




    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image(String title, String path,String overview,double vote,String year,String id) {
        this.title = title;
        this.path = path;
        this.overview = overview;
        this.vote=  vote;
        this.year = year;
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
         this.title,this.path,this.overview, String.valueOf(this.vote),this.year,this.id

        });

    }
    public static final Parcelable.Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
