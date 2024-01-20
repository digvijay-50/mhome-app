package com.propertybuysell.mhome.Paymentdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GatewayOrderStatus {

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("amount")
    private String amount;

    @SerializedName("id")
    private String paymentID;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
}
