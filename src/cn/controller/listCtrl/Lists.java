package cn.controller.listCtrl;
import java.util.LinkedList;

import cn.driver.model.List;
/**
 * 
 * @author Dacle
 *
 */
public class Lists {
	private static LinkedList<List> lists;
	
	static{
		if (lists==null) {
			lists=new LinkedList<List>();
		}
		lists.add(0,new List(0,"Ä¬ÈÏÁĞ±í"));
	}
	
	public void addList(List list){
		lists.add(list);
	}
	
	public void deleteList(List list){
		if(lists.isEmpty()){
			return;
		}else{
			lists.remove(list.getListId());
		}
	}
	
	public LinkedList<List> getLists() {
		return lists;
	}

}
