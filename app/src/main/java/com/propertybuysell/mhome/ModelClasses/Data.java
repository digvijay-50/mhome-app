package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Data implements Serializable
{

@SerializedName("merchantId")
@Expose
private String merchantId;
@SerializedName("merchantTransactionId")
@Expose
private String merchantTransactionId;
@SerializedName("transactionId")
@Expose
private String transactionId;
@SerializedName("amount")
@Expose
private Integer amount;
@SerializedName("state")
@Expose
private String state;
@SerializedName("responseCode")
@Expose
private String responseCode;
@SerializedName("paymentInstrument")
@Expose
private PaymentInstrument paymentInstrument;
private final static long serialVersionUID = -8984719288335945947L;

public String getMerchantId() {
return merchantId;
}

public void setMerchantId(String merchantId) {
this.merchantId = merchantId;
}

public String getMerchantTransactionId() {
return merchantTransactionId;
}

public void setMerchantTransactionId(String merchantTransactionId) {
this.merchantTransactionId = merchantTransactionId;
}

public String getTransactionId() {
return transactionId;
}

public void setTransactionId(String transactionId) {
this.transactionId = transactionId;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getResponseCode() {
return responseCode;
}

public void setResponseCode(String responseCode) {
this.responseCode = responseCode;
}

public PaymentInstrument getPaymentInstrument() {
return paymentInstrument;
}

public void setPaymentInstrument(PaymentInstrument paymentInstrument) {
this.paymentInstrument = paymentInstrument;
}

}