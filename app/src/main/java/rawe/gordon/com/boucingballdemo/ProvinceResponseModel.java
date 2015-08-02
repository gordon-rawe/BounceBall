package rawe.gordon.com.boucingballdemo;

import java.util.ArrayList;

/**
 * Created by gordon on 7/24/15.
 */
public class ProvinceResponseModel {
    private ArrayList<String> provinces;

    public ArrayList<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<String> provinces) {
        this.provinces = provinces;
    }

    @Override
    public String toString() {
        String ret = "";
        for (String str : provinces) {
            ret += str + "\n";
        }
        return ret;
    }
}