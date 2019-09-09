package idv.jerry.dramalist.data;

import android.os.Parcel;
import android.os.Parcelable;

public class DramaData implements Parcelable {

    public int drama_id;
    public String name;
    public long total_views;
    public String created_at; // format: 2017-10-21T12:34:41.000Z,
    public String thumb;
    public double rating;

    public DramaData(Parcel in) {
        drama_id = in.readInt();
        name = in.readString();
        total_views = in.readLong();
        created_at = in.readString();
        thumb = in.readString();
        rating = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drama_id);
        dest.writeString(name);
        dest.writeLong(total_views);
        dest.writeString(created_at);
        dest.writeString(thumb);
        dest.writeDouble(rating);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<DramaData>(){

        @Override
        public DramaData createFromParcel(Parcel source) {
            return new DramaData(source);
        }

        @Override
        public DramaData[] newArray(int size) {
            return new DramaData[size];
        }
    };
}
