package simpleinventory.simpleinventory.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by gwulfwud on 8/5/2016.
 */
public class Report implements Parcelable{
    public int Id;
    public String Name;
    public Date CreatedAt;

    public Report (){}

    protected Report(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        long tmpCreatedAt = in.readLong();
        CreatedAt = tmpCreatedAt != -1 ? new Date(tmpCreatedAt) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeLong(CreatedAt != null ? CreatedAt.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Report> CREATOR = new Parcelable.Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
}
