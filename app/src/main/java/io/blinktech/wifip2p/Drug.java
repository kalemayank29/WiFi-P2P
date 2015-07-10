package io.blinktech.wifip2p;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Drug {

    public String name, type, dosage, date;

    public Drug(String name, String type, String dosage, String date) {
        this.name = name;
        this.type = type;
        this.dosage = dosage;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void toBytes(Drug element) throws IOException {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput out = null;
        Log.e("Inside","To Bytes");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("yo", "whats up");

        try{
            Log.e("Inside","try");
            out = new ObjectOutputStream(bStream);
            out.writeObject(map);
            Log.e("Inside","try OUT");
            byte[] bytes = bStream.toByteArray();
            Log.e("Byte Array", String.valueOf(bytes.length));
            String data = new String(bytes, "UTF-8");
            Log.e("DATA:", data);
        } catch (IOException e){Log.e("Inside CTCH","YUS");}
    }
}
