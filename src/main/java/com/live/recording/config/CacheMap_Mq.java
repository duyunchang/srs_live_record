package com.live.recording.config;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
//存放消息集合

public class CacheMap_Mq {
	
	private static  Map<String,String>  cahcheMap;
	
	public CacheMap_Mq(){
		 if (cahcheMap == null) {
			synchronized (CacheMap.class) {  		 
				if (cahcheMap == null) {  
					 cahcheMap =new ConcurrentHashMap<String,String>();  
				   }
			 }
		}
	}
	
	
	
	//根据key获取value
	public Object getCahche(String key){
				
		return cahcheMap.get(key);
	}
	
	//添加或更新key value
	public void setCahche(String key , String bean){
		
		cahcheMap.put(key, bean);		
	}
	
	//获取map大小
	public int getCahcheSize(){
		
		return cahcheMap.size();		
	}
	
//	//删除key
	public void delCahche(String key ){
		
		 if(cahcheMap!=null&&cahcheMap.size()>0){
			   Iterator<Entry<String, String>> entries = cahcheMap.entrySet().iterator();  
			   while (entries.hasNext()) {  			 
				   Entry<String, String> entry = entries.next();
				   if( entry.getKey().equals(key)){
					   entries.remove();					   
				   }
 						  
				} 
		   }
		}
	
//	//判断是否包含key
//	public boolean containsCahche(String key ){
//				
//		return cahcheMap.containsKey(key);
//	}
	 
	//全部删除
	public void clearAllCahche(String key ){
         cahcheMap.clear();
		
	}
	public static void main(String[] args) {
		
		System.out.println("1.0".equals("1.00"));
		System.out.println(Double.parseDouble("1.0"));
	}
}
