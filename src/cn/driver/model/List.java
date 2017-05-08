package cn.driver.model;

import java.util.ArrayList;

import cn.driver.model.Music;

/**
 * �б�ʵ����
 * @author Dacle
 * @since 2017-5-1
 * @modify by Dacle
 */
public class List {
	/**
	 * ListΨһ��ʾ
	 */
	private int listId;
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
	private ArrayList<Music> list;
	
	/**
	 * ���캯��
	 * @param listId
	 * @param listName
	 */
	public List(int listId,String listName){
		this.listId=listId;
		this.listName=listName;
	}
	public ArrayList<Music> getList() {
		return this.list;
	}

	public void setList(ArrayList<Music> list) {
		this.list = list;
	}

	public Music get(int id){
		return list.get(id);
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
