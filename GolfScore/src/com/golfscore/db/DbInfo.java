package com.golfscore.db;

public class DbInfo {

	public static String TableNames[] = { "paramTable",// ������
			"infoTable",// �ɼ���
	};// ����

	public static String FieldNames[][] = {
			{ "_ID", "paraName", "paraValue", "paraExplain" }, // ������
			{ "_ID", "groupId", "hole","userId", "name", "score", "status" }, // �ɼ���
	};// �ֶ���

	public static String FieldTypes[][] = {
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text" }, // ������
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text", "text",
					"text", "text" } // �ɼ���

	};// �ֶ�����

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
