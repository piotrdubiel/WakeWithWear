package io.wake.wear.common.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelablePreset implements Parcelable {
    private transient Preset preset;

    public ParcelablePreset(Preset preset) {
        this.preset = preset;
    }

    public ParcelablePreset(Parcel in) {
        String name = in.readString();
        double latitude = in.readDouble();
        double longitude = in.readDouble();
        String mac = in.readString();
        preset = new Preset(name, latitude, longitude, mac);
    }

    public Preset getPreset() {
        return preset;
    }

    public String getName() {
        return preset.getName();
    }

    public void setName(String name) {
        preset.setName(name);
    }

    public double getLatitude() {
        return preset.getLatitude();
    }

    public void setLatitude(double latitude) {
        preset.setLatitude(latitude);
    }

    public double getLongitude() {
        return preset.getLongitude();
    }

    public void setLongitude(double longitude) {
        preset.setLongitude(longitude);
    }

    public String getMac() {
        return preset.getMac();
    }

    public void setMac(String mac) {
        preset.setMac(mac);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(preset.getName());
        dest.writeDouble(preset.getLatitude());
        dest.writeDouble(preset.getLongitude());
        dest.writeString(preset.getMac());
    }

    public static final Parcelable.Creator<ParcelablePreset> CREATOR = new Parcelable.Creator<ParcelablePreset>() {
        public ParcelablePreset createFromParcel(Parcel in) {
            return new ParcelablePreset(in);
        }

        public ParcelablePreset[] newArray(int size) {
            return new ParcelablePreset[size];
        }
    };

    public byte[] marshall() {
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static ParcelablePreset unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return CREATOR.createFromParcel(parcel);
    }
}

