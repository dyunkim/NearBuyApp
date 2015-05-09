package com.appwrench.nearbuy;

/**
 * Created by DHwong on 5/8/2015.
 */
import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

}