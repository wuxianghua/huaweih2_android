package com.palmap.demo.huaweih2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wtm on 2017/5/26.
 */

public class SvaLocationRsrpModel implements Parcelable {


    /**
     * prrusignal : [{"gpp":"0_104_0","rsrp":-1037},{"gpp":"0_135_0","rsrp":-989},{"gpp":"0_133_0","rsrp":-1030},{"gpp":"0_131_0","rsrp":-1110},{"gpp":"0_134_0","rsrp":-1170},{"gpp":"0_102_0","rsrp":-1176},{"gpp":"0_132_0","rsrp":-950},{"gpp":"0_105_0","rsrp":-1186},{"gpp":"0_137_0","rsrp":-1046},{"gpp":"0_138_0","rsrp":-1054}]
     * enbid : 834533
     */

    private String enbid;
    private List<PrrusignalBean> prrusignal;

    public String getEnbid() {
        return enbid;
    }

    public void setEnbid(String enbid) {
        this.enbid = enbid;
    }

    public List<PrrusignalBean> getPrrusignal() {
        return prrusignal;
    }

    public void setPrrusignal(List<PrrusignalBean> prrusignal) {
        this.prrusignal = prrusignal;
    }

    public static class PrrusignalBean implements Parcelable {
        /**
         * gpp : 0_104_0
         * rsrp : -1037
         */

        private String gpp;
        private double rsrp;

        public String getGpp() {
            return gpp;
        }

        public void setGpp(String gpp) {
            this.gpp = gpp;
        }

        public double getRsrp() {
            return rsrp;
        }

        public void setRsrp(double rsrp) {
            this.rsrp = rsrp;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.gpp);
            dest.writeDouble(this.rsrp);
        }

        public PrrusignalBean() {
        }

        protected PrrusignalBean(Parcel in) {
            this.gpp = in.readString();
            this.rsrp = in.readDouble();
        }

        public static final Parcelable.Creator<PrrusignalBean> CREATOR = new Parcelable.Creator<PrrusignalBean>() {
            @Override
            public PrrusignalBean createFromParcel(Parcel source) {
                return new PrrusignalBean(source);
            }

            @Override
            public PrrusignalBean[] newArray(int size) {
                return new PrrusignalBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.enbid);
        dest.writeTypedList(this.prrusignal);
    }

    public SvaLocationRsrpModel() {
    }

    protected SvaLocationRsrpModel(Parcel in) {
        this.enbid = in.readString();
        this.prrusignal = in.createTypedArrayList(PrrusignalBean.CREATOR);
    }

    public static final Parcelable.Creator<SvaLocationRsrpModel> CREATOR = new Parcelable.Creator<SvaLocationRsrpModel>() {
        @Override
        public SvaLocationRsrpModel createFromParcel(Parcel source) {
            return new SvaLocationRsrpModel(source);
        }

        @Override
        public SvaLocationRsrpModel[] newArray(int size) {
            return new SvaLocationRsrpModel[size];
        }
    };
}
