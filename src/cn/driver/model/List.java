package cn.driver.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * �б�ʵ����
 * @author Dacle
 * @since 2017-5-1
 * @modify by Dacle
 */
public class List {
	/**
	 * List����
	 */
	private String listName;
	/**
	 * List��С
	 */
	private int listSize;
	/**
	 * �����б�
	 */
	private JSONArray list;
	
	/**
	 * ���캯��
	 * @param listId
	 * @param listName
	 */
	public List(){
		this.list = new JSONArray();
		this.listSize=list.size();
	}
	
	public JSONObject getJSONObject(int index){
		return list.getJSONObject(index);
	}
	public JSONArray getList() {
		return this.list;
	}

	public void setList(JSONArray list) {
		this.list = list;
	}
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
}
