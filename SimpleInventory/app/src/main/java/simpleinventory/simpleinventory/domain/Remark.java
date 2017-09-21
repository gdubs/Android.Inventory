package simpleinventory.simpleinventory.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gdubs on 6/24/2016.
 */
public class Remark implements Parcelable {
    public Integer Id;
    public String Name;
    public Integer ReportId;

    public Remark (){};
    // non parcelable part
    @Override
    public String toString() {
        return Name.toString();
    }

    protected Remark(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readInt();
        Name = in.readString();
        ReportId = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(Id);
        }
        dest.writeString(Name);
        if (ReportId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(ReportId);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Remark> CREATOR = new Parcelable.Creator<Remark>() {
        @Override
        public Remark createFromParcel(Parcel in) {
            return new Remark(in);
        }

        @Override
        public Remark[] newArray(int size) {
            return new Remark[size];
        }
    };
}
