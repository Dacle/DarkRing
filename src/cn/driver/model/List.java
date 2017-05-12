package cn.driver.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 列表实体类
 * @author Dacle
 * @since 2017-5-1
 * @modify by Dacle
 */
public class List {
	/**
	 * List唯一标示
	 */
	private int listId;
	/**
	 * List名称
	 */
	private String listName;
	/**
	 * List大小
	 */
	private int listSize;
	/**
	 * 音乐列表
	 */
	private JSONArray list;
	
	/**
	 * 构造函数
	 * @param listId
	 * @param listName
	 */
	public List(int listId,String listName){
		this.listId=listId;
		this.listName=listName;
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
	
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
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
