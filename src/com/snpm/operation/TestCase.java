/**
 * 
 */
package com.snpm.operation;

import java.util.ArrayList;

/**
 * @author jasonzhang
 *
 */
public class TestCase {
	private ArrayList<Action> actions = new ArrayList<Action>();
	private String tcName = "";
	private String tcDesc = "";
	
	/**
	 * Attributes
	 */
	public TestCase() {
		// TODO Auto-generated constructor stub
	}
	
	public String getTcDesc() {
		return tcDesc;
	}

	public void setTcDesc(String tcDesc) {
		this.tcDesc = tcDesc;
	}

	public Action findActionByOperation(String op) {
		Action action = null;
		return action;
	}

	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	
	public String getTcName() {
		return this.tcName;
	}
	
	public void addAction(Action action) {
		actions.add(action);
	}
	
	public Action getAction(int idx) {
		return (Action) actions.get(idx);
	}
	
	public ArrayList<Action> getActions() {
		return this.actions;
	}
}
