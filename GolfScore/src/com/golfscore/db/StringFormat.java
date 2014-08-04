package com.golfscore.db;

public class StringFormat {
    
	public static String isNull(Object obj){
		return (obj == null ? "": obj.toString());
	}
}
