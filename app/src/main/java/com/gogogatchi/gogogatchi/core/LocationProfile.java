package com.gogogatchi.gogogatchi.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationProfile implements Parcelable {
    /*** Parcel Mechanisms ***/
    protected LocationProfile(Parcel in) {
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        icon = in.readString();
        id = in.readString();
        name = in.readString();
        opening_hours = in.readParcelable(OpenState.class.getClassLoader());
        photos = in.readParcelable(Photos.class.getClassLoader());
        place_id = in.readString();
        plus_code = in.readParcelable(PlusCode.class.getClassLoader());
        rating = in.readInt();
        reference = in.readString();
        scope = in.readString();
        types = in.readParcelable(Types.class.getClassLoader());
        vicinity = in.readString();
    }

    public static final Creator<LocationProfile> CREATOR = new Creator<LocationProfile>() {
        @Override
        public LocationProfile createFromParcel(Parcel in) {
            return new LocationProfile(in);
        }

        @Override
        public LocationProfile[] newArray(int size) {
            return new LocationProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(geometry, i);
        parcel.writeString(icon);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeParcelable(opening_hours, i);
        parcel.writeParcelable(photos, i);
        parcel.writeString(place_id);
        parcel.writeParcelable(plus_code, i);
        parcel.writeInt(rating);
        parcel.writeString(reference);
        parcel.writeString(scope);
        parcel.writeParcelable(types, i);
        parcel.writeString(vicinity);
    }

    /*** Class Declarations ***/
    private class GPSCoordinates implements Parcelable{
        @SerializedName("lat")
        @Expose
        private double lattitude;

        @SerializedName("lng")
        @Expose
        private double longitutde;

        protected GPSCoordinates(Parcel in) {
            lattitude = in.readDouble();
            longitutde = in.readDouble();
        }

        public final Creator<GPSCoordinates> CREATOR = new Creator<GPSCoordinates>() {
            @Override
            public GPSCoordinates createFromParcel(Parcel in) {
                return new GPSCoordinates(in);
            }

            @Override
            public GPSCoordinates[] newArray(int size) {
                return new GPSCoordinates[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(lattitude);
            parcel.writeDouble(longitutde);
        }

        public double getLattitude() { return lattitude; }

        public double getLongitutde() { return longitutde; }
    }

    private class ViewPort implements Parcelable{
        @SerializedName("northwest")
        @Expose
        private GPSCoordinates northeast;

        @SerializedName("southwest")
        @Expose
        private GPSCoordinates southwest;

        protected ViewPort(Parcel in) {
            northeast = in.readParcelable(GPSCoordinates.class.getClassLoader());
            southwest = in.readParcelable(GPSCoordinates.class.getClassLoader());
        }

        public final Creator<ViewPort> CREATOR = new Creator<ViewPort>() {
            @Override
            public ViewPort createFromParcel(Parcel in) {
                return new ViewPort(in);
            }

            @Override
            public ViewPort[] newArray(int size) {
                return new ViewPort[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(northeast, i);
            parcel.writeParcelable(southwest, i);
        }

        public GPSCoordinates getNortheast() { return northeast; }
        public GPSCoordinates getSouthwest() { return southwest; }
    }

    private class Geometry implements Parcelable{
        @SerializedName("location")
        @Expose
        private GPSCoordinates location;

        @SerializedName("viewport")
        @Expose
        private ViewPort viewport;

        protected Geometry(Parcel in) {

            location = in.readParcelable(GPSCoordinates.class.getClassLoader());
            viewport = in.readParcelable(ViewPort.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(location, flags);
            dest.writeParcelable(viewport, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final Creator<Geometry> CREATOR = new Creator<Geometry>() {
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
    }

    private class OpenState implements Parcelable{

        @SerializedName("open_now")
        @Expose
        private boolean open_now;

        protected OpenState(Parcel in) {
            open_now = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (open_now ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final Creator<OpenState> CREATOR = new Creator<OpenState>() {
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
    }

    private class HTMLAttribution implements Parcelable{
        private String[] address;

        protected HTMLAttribution(Parcel in) {
            address = in.createStringArray();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringArray(address);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final Creator<HTMLAttribution> CREATOR = new Creator<HTMLAttribution>() {
            @Override
            public HTMLAttribution createFromParcel(Parcel in) {
                return new HTMLAttribution(in);
            }

            @Override
            public HTMLAttribution[] newArray(int size) {
                return new HTMLAttribution[size];
            }
        };

        public String[] getAddress() { return address; }
    }

    private class Photos implements Parcelable{

        @SerializedName("height")
        @Expose
        private int height;

        @SerializedName("width")
        @Expose
        private int width;


        @SerializedName("photo_reference")
        @Expose
        private String photo_reference;

        @SerializedName("html_attributions")
        @Expose
        private HTMLAttribution html_attributions;

        protected Photos(Parcel in) {
            height = in.readInt();
            width = in.readInt();
            photo_reference = in.readString();
            html_attributions = in.readParcelable(HTMLAttribution.class.getClassLoader());
        }

        public final Creator<Photos> CREATOR = new Creator<Photos>() {
            @Override
            public Photos createFromParcel(Parcel in) {
                return new Photos(in);
            }

            @Override
            public Photos[] newArray(int size) {
                return new Photos[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(height);
            parcel.writeInt(width);
            parcel.writeString(photo_reference);
            parcel.writeParcelable(html_attributions, i);
        }

        public int getHeight() { return height; }
        public int getWidth() { return width; }
        public String getPhotoReference() { return photo_reference; }
        public HTMLAttribution getHtmlAttributions() { return html_attributions; }
    }

    private class PlusCode implements Parcelable{

        @SerializedName("compound_code")
        @Expose
        private String compound_code;


        @SerializedName("global_code")
        @Expose
        private String global_code;

        protected PlusCode(Parcel in) {
            compound_code = in.readString();
            global_code = in.readString();
        }

        public final Creator<PlusCode> CREATOR = new Creator<PlusCode>() {
            @Override
            public PlusCode createFromParcel(Parcel in) {
                return new PlusCode(in);
            }

            @Override
            public PlusCode[] newArray(int size) {
                return new PlusCode[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(compound_code);
            parcel.writeString(global_code);
        }

        public String getCompound_code() { return compound_code; }
        public String getGlobal_code() { return global_code; }
    }

    private class Types implements Parcelable{
        private String[] list;

        protected Types(Parcel in) {
            list = in.createStringArray();
        }

        public final Creator<Types> CREATOR = new Creator<Types>() {
            @Override
            public Types createFromParcel(Parcel in) {
                return new Types(in);
            }

            @Override
            public Types[] newArray(int size) {
                return new Types[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeStringArray(list);
        }

        public String[] getList() { return list; }
    }

    /*** Variable Declarations ***/
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("id")
    @Expose
    private String id; // deprecated, not unique

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("opening_hours")
    @Expose
    private OpenState opening_hours;

    @SerializedName("photos")
    @Expose
    private Photos photos;

    @SerializedName("place_id")
    @Expose
    private String place_id; // unique identifier

    @SerializedName("plus_code")
    @Expose
    private PlusCode plus_code;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("reference")
    @Expose
    private String reference;

    @SerializedName("scope")
    @Expose
    private String scope;

    @SerializedName("types")
    @Expose
    private Types types;

    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    /*** Methods ***/
    public Geometry getGeometry() { return geometry; }

    public String getLocationIcon() { return icon; }

    public String getID() { return id; }

    public String getLocationName() { return name; }

    public OpenState getOpenState() { return opening_hours; }

    public Photos getPhoto() { return photos; }

    public String getPlaceID() { return place_id; }

    public PlusCode getPlusCode() { return plus_code; }

    public int getRating() { return rating; }

    public String getLocationReference() { return reference; }

    public String getScope() { return scope; }

    public Types getTypes() { return types; }

    public String getVicinity() { return vicinity; }

    public String getImageUrl() {
        String maxWidth = "400";

        String imgURL = "https://maps.googleapis.com/maps/api/place/photo?";
        imgURL += "maxwidth=" + photos.getWidth();
        imgURL += "&photoreference=" + maxWidth;
        imgURL += "&key=" + "AIzaSyC9Vxp4CoTdVejqx70_JAjTv67g7lbXFFQ";

        return imgURL;
    }
}
