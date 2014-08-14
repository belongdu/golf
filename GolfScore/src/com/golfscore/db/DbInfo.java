package com.golfscore.db;

public class DbInfo {

	public static String TableNames[] = { "paramTable",// 参数表
			"infoTable",// 成绩表
	};// 表名

	public static String FieldNames[][] = {
			{ "_ID", "paraName", "paraValue", "paraExplain" }, // 参数表
			{ "_ID", "CompetitorID","CompetitorCode","CompetitorName","CompetitorGroup","Tee","CurHole","CurHolePar","CurHoleResult","status" }, // 成绩表
	};// 字段名

	public static String FieldTypes[][] = {
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text" }, // 参数表
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text", "text",
					"text", "text","text", "text","text" } // 成绩表

	};// 字段类型

	public String[] getTableNames() {
		return TableNames;
	}

	public String[][] getFieldNames() {
		return FieldNames;
	}

	public String[][] getFieldTypes() {
		return FieldTypes;
	}

}
