package net.vannadis.testme.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created by viktoriala on 9/23/2014.
 */
public class Product implements Parcelable {

    private int bgImageColor;
    private String description;
    private int price; // id
    private Bug bug = new Bug(Bug.TYPE_NO_BUGS);

    public Product(int bgImageColor, String description, int price) {
        this.bgImageColor = bgImageColor;
        this.description = description;
        this.price = price;
    }

    public int getBgImageColor() {
        return bgImageColor;
    }

    public void setBgImageColor(int bgImageColor) {
        this.bgImageColor = bgImageColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {

        switch (bug.getType()) {
            case Bug.TYPE_WRONG_DESCRIPTION:
                description = String.valueOf(bug.getWrongValue());
                break;
            case Bug.TYPE_WRONG_PRICE:
                price = (Integer)bug.getWrongValue();
                break;
        }
        this.bug = bug;
    }

    public static int generateRandomBgColor(){
        Random rnd = new Random();
        int color = Color.argb(150, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (price != product.price) return false;

        return true;
    }

    public int hashCode() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Product(Parcel in) {
        bgImageColor = in.readInt();
        description  = in.readString();
        price = in.readInt();
        bug = in.readParcelable(Bug.class.getClassLoader());
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bgImageColor);
        parcel.writeString(description);
        parcel.writeInt(price);
        parcel.writeParcelable(bug, 0);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
