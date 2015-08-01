package com.gas.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.gas.utils.Utils;

/**
 * Created by Heart on 2015/7/28.
 */
public class User implements Parcelable {
    private String id;
    private String name;
    private String phone;
    private String password;
    private String qq;
    private String areaid;
    private String carid;
    private String in_time;
    private String last_login_time;
    private String create_time;
    private String update_time;

    public String getAreaid() {
        return areaid;
    }

    public String getCarid() {
        return carid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getId() {
        return id;
    }

    public String getIn_time() {
        return in_time;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getQq() {
        return qq;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public void setName(String name) {
        this.name = Utils.decodeUnicode(name);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.password);
        dest.writeString(this.qq);
        dest.writeString(this.areaid);
        dest.writeString(this.carid);
        dest.writeString(this.in_time);
        dest.writeString(this.last_login_time);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.password = in.readString();
        this.qq = in.readString();
        this.areaid = in.readString();
        this.carid = in.readString();
        this.in_time = in.readString();
        this.last_login_time = in.readString();
        this.create_time = in.readString();
        this.update_time = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
