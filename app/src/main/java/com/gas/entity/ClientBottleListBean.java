package com.gas.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orange on 2017/11/18.
 */

public class ClientBottleListBean {
    public ClientBottleBean list;

    @Override
    public String toString() {
        return "ClientBottleListBean{" +
                "list=" + list +
                '}';
    }

    public class ClientBottleBean {
        public String gas_card;
        public List<BottleState> borrow = new ArrayList<>();
        public List<BottleState> detain = new ArrayList<>();
        public List<BottleState> repay = new ArrayList<>();


    }

    public class BottleState implements Parcelable {
        public int id;
        public String name;
        public int num;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.num);
        }

        public BottleState() {
        }

        protected BottleState(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.num = in.readInt();
        }

        public  final Parcelable.Creator<BottleState> CREATOR = new Parcelable.Creator<BottleState>() {
            @Override
            public BottleState createFromParcel(Parcel source) {
                return new BottleState(source);
            }

            @Override
            public BottleState[] newArray(int size) {
                return new BottleState[size];
            }
        };
    }
}
