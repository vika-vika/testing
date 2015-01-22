package net.vannadis.testme.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by viktoriala on 9/23/2014.
 */
public class Bug implements Parcelable {
    public static final int TYPE_NO_BUGS = 0;
    public static final int TYPE_WRONG_DESCRIPTION = 1;
    public static final int TYPE_WRONG_PRICE       = 2;
    public static final int TYPE_NOT_ADDABLE       = 3;
    public static final int TYPE_NOT_DELETABLE     = 4;
    public static final int TYPE_NOT_DELETABLE_CLEAR_ALL = 5;
    public static final int TYPE_CRASH = 6;

    private int type;
    private Object wrongValue;

    public Bug(int type) {
        this.type = type;
    }

    public Bug(int type, Object wrongValue) {
        this.type = type;
        this.wrongValue = wrongValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getWrongValue() {
        return wrongValue;
    }

    public void setWrongValue(int wrongValue) {
        this.wrongValue = wrongValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bug(Parcel in) {
        type = in.readInt();
        //wrongValue  = in.readParcelable();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
       // parcel.writeString(description);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Bug createFromParcel(Parcel in) {
            return new Bug(in);
        }

        public Bug[] newArray(int size) {
            return new Bug[size];
        }
    };
}
