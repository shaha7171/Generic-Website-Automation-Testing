package com.snpm.operation;

public class Action { // Encapsulate one action on web element

	private String tcName;  // Test Case name
	private String operation;
	private String objName;
	private String objType;
	private String value;
	
	public Action() {
		// TODO Auto-generated constructor stub
	}

	public String getTcName() {
		return tcName;
	}

	public void setTcName(String tcName) {
		this.tcName = tcName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
