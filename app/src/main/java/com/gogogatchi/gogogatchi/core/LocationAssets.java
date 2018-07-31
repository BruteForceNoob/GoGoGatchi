package com.gogogatchi.gogogatchi.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationAssets {
    public static class GPSCoordinates implements Parcelable {
        @SerializedName("lat")
        private double lattitude;

        @SerializedName("lon")
        private double longitutde;

        protected GPSCoordinates(Parcel in) {
            lattitude = in.readDouble();
            longitutde = in.readDouble();
        }

        public static final Creator<GPSCoordinates> CREATOR = new Creator<GPSCoordinates>() {
            @Override
            public GPSCoordinates createFromParcel(Parcel in) {
                return new GPSCoordinates(in);
            }

            @Override
            public GPSCoordinates[] newArray(int size) {
                return new GPSCoordinates[size];
            }
        };

        public double getLattitude() { return lattitude; }
        public double getLongitutde() { return longitutde; }

        GPSCoordinates() {
            lattitude = 0.0;
            longitutde = 0.0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(lattitude);
            parcel.writeDouble(longitutde);
        }
    }

    public static class ViewPort implements Parcelable{
        @SerializedName("northeast")
        private GPSCoordinates northeast;

        @SerializedName("southwest")
        private GPSCoordinates southwest;

        protected ViewPort(Parcel in) {
            northeast = in.readParcelable(GPSCoordinates.class.getClassLoader());
            southwest = in.readParcelable(GPSCoordinates.class.getClassLoader());
        }

        public static final Creator<ViewPort> CREATOR = new Creator<ViewPort>() {
            @Override
            public ViewPort createFromParcel(Parcel in) {
                return new ViewPort(in);
            }

            @Override
            public ViewPort[] newArray(int size) {
                return new ViewPort[size];
            }
        };

        public GPSCoordinates getNortheast() { return northeast; }
        public GPSCoordinates getSouthwest() { return southwest; }

        ViewPort() {
            northeast = new GPSCoordinates();
            southwest = new GPSCoordinates();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(northeast, i);
            parcel.writeParcelable(southwest, i);
        }
    }

    public static class Geometry implements Parcelable{
        @SerializedName("location")
        private GPSCoordinates location;

        @SerializedName("viewport")
        private ViewPort viewport;


        protected Geometry(Parcel in) {
            location = in.readParcelable(GPSCoordinates.class.getClassLoader());
            viewport = in.readParcelable(ViewPort.class.getClassLoader());
        }

        public static final Creator<Geometry> CREATOR = new Creator<Geometry>() {
            @Override
            public Geometry createFromParcel(Parcel in) {
                return new Geometry(in);
            }

            @Override
            public Geometry[] newArray(int size) {
                return new Geometry[size];
            }
        };

        public GPSCoordinates getLocation() { return location; }
        public ViewPort getViewport() { return viewport; }

        Geometry() {
            location = new GPSCoordinates();
            viewport = new ViewPort();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(location, i);
            parcel.writeParcelable(viewport, i);
        }
    }

    public static class OpenState implements Parcelable{

        @SerializedName("open_now")
        private boolean open_now;

        protected OpenState(Parcel in) {
            open_now = in.readByte() != 0;
        }

        public static final Creator<OpenState> CREATOR = new Creator<OpenState>() {
            @Override
            public OpenState createFromParcel(Parcel in) {
                return new OpenState(in);
            }

            @Override
            public OpenState[] newArray(int size) {
                return new OpenState[size];
            }
        };

        public boolean getOpenNow() { return open_now; }
        OpenState() {
            open_now = false;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte((byte) (open_now ? 1 : 0));
        }
    }

    public static class Photos implements Parcelable{

        @SerializedName("height")
        private int height;

        @SerializedName("html_attributions")
        private ArrayList<String> html_attributions;

        @SerializedName("width")
        private int width;

        @SerializedName("photo_reference")
        String photo_reference;

        protected Photos(Parcel in) {
            height = in.readInt();
            html_attributions = in.createStringArrayList();
            width = in.readInt();
            photo_reference = in.readString();
        }

        public static Creator<Photos> CREATOR = new Creator<Photos>() {
            @Override
            public Photos createFromParcel(Parcel in) {
                return new Photos(in);
            }

            @Override
            public Photos[] newArray(int size) {
                return new Photos[size];
            }
        };

        public int getHeight() { return height; }
        public int getWidth() { return width; }
        public String getPhotoReference() { return photo_reference; }
        public ArrayList<String> getHtmlAttributions() { return html_attributions; }

        Photos() {
            height = 100;
            width = 100;
            photo_reference = "DEFAULT";
            html_attributions = new ArrayList<>();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(height);
            parcel.writeStringList(html_attributions);
            parcel.writeInt(width);
            parcel.writeString(photo_reference);
        }
    }

    public static class PlusCode implements Parcelable{

        @SerializedName("compound_code")
        private String compound_code;

        @SerializedName("global_code")
        private String global_code;

        protected PlusCode(Parcel in) {
            compound_code = in.readString();
            global_code = in.readString();
        }

        public static Creator<PlusCode> CREATOR = new Creator<PlusCode>() {
            @Override
            public PlusCode createFromParcel(Parcel in) {
                return new PlusCode(in);
            }

            @Override
            public PlusCode[] newArray(int size) {
                return new PlusCode[size];
            }
        };

        public String getCompound_code() { return compound_code; }
        public String getGlobal_code() { return global_code; }

        PlusCode() {
            compound_code = "DEFAULT";
            global_code = "DEFAULT";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(compound_code);
            parcel.writeString(global_code);
        }
    }
}
