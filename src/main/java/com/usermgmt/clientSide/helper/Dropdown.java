package com.usermgmt.clientSide.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class Dropdown {
    private String stringValue;

    private Integer intValue;

    private String text;

    public Dropdown(String stringValue, String text) {
        this.stringValue = stringValue;
        this.text = text;
    }

    public Dropdown(Integer intValue, String text) {
        this.intValue = intValue;
        this.text = text;
    }

    public static List<Dropdown> getType(){
        List<Dropdown> type = new LinkedList<>();
        type.add(new Dropdown("T1","T1"));
        type.add(new Dropdown("T2","T2"));
        type.add(new Dropdown("T3","T3"));
        return type;
    }
}
