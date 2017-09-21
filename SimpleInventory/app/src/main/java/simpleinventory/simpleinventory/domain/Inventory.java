package simpleinventory.simpleinventory.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

/**
 * Created by gdubs on 6/24/2016.
 */
public class Inventory  implements  Parcelable{
    public Integer Id;
    public String TagId;
    public String Description;
    public Integer Quantity;
    public Double UnitValue;
    public Double TotalValue;
    public Integer RemarkId;
    public Integer ReportId;
    public String ImageFileName;

    public Remark Remark;

    public Inventory(){}

    protected Inventory(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readInt();
        TagId = in.readString();
        Description = in.readString();
        Quantity = in.readByte() == 0x00 ? null : in.readInt();
        UnitValue = in.readByte() == 0x00 ? null : in.readDouble();
        TotalValue = in.readByte() == 0x00 ? null : in.readDouble();
        RemarkId = in.readByte() == 0x00 ? null : in.readInt();
        ReportId = in.readByte() == 0x00 ? null : in.readInt();
        ImageFileName = in.readString();
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
        dest.writeString(TagId);
        dest.writeString(Description);
        if (Quantity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(Quantity);
        }
        if (UnitValue == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(UnitValue);
        }
        if (TotalValue == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(TotalValue);
        }
        if (RemarkId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(RemarkId);
        }
        if (ReportId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(ReportId);
        }
        dest.writeString(ImageFileName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Inventory> CREATOR = new Parcelable.Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel in) {
            return new Inventory(in);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };
}
