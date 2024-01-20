package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PaymentInstrument implements Serializable
{

@SerializedName("type")
@Expose
private String type;
@SerializedName("pgTransactionId")
@Expose
private String pgTransactionId;
@SerializedName("pgServiceTransactionId")
@Expose
private String pgServiceTransactionId;
@SerializedName("bankTransactionId")
@Expose
private Object bankTransactionId;
@SerializedName("bankId")
@Expose
private String bankId;
private final static long serialVersionUID = 4906323746804622070L;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getPgTransactionId() {
return pgTransactionId;
}

public void setPgTransactionId(String pgTransactionId) {
this.pgTransactionId = pgTransactionId;
}

public String getPgServiceTransactionId() {
return pgServiceTransactionId;
}

public void setPgServiceTransactionId(String pgServiceTransactionId) {
this.pgServiceTransactionId = pgServiceTransactionId;
}

public Object getBankTransactionId() {
return bankTransactionId;
}

public void setBankTransactionId(Object bankTransactionId) {
this.bankTransactionId = bankTransactionId;
}

public String getBankId() {
return bankId;
}

public void setBankId(String bankId) {
this.bankId = bankId;
}

}