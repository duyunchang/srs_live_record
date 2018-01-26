package com.live.recording.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
//转码进度缓存进度

public class CacheMap {
	
	private static  Map<String,Map<String,String>>  cahcheMap;
	
	public CacheMap(){
		 if (cahcheMap == null) {
			synchronized (CacheMap.class) {  		 
				if (cahcheMap == null) {  
					 cahcheMap =new ConcurrentHashMap<String,Map<String,String>>();  
					 
				   }
			 }
		}else{
			 if(cahcheMap.size()>0){
				    Iterator<Entry<String, Map<String, String>>> entries = cahcheMap.entrySet().iterator();  
				   while (entries.hasNext()) {  					   
					    Entry<String, Map<String, String>> entry = entries.next();
					    Map<String, String> value = entry.getValue();
					    String count = count(value);
					    //int v=(int)Float.parseFloat(count);
					    //Integer.parseInt(count);
					    if("1".equals(count)||"1.0".equals(count)){
					   // if(v>=1){
					    	//删除进度为100%元素
						   System.out.println("删除缓存key="+entry.getKey());
					       entries.remove();
					     }
					  						  
					} 
			   }
		}
	}
	
	
	
	//根据key获取value
	public  String getCahche(String key){
		
		if(!cahcheMap.containsKey(key)){			
			System.out.println("返回进度100%;缓存中不存在key="+key);
			return "1";								
		}
		Map<String, String> map = cahcheMap.get(key);	
		
		return count(map);
	}
	
	//添加或更新key value
	public  void  setCahche(String key ,String secondKey,String value){
		Map<String, String> map = cahcheMap.get(key);
		
		if(map==null){
			map=new HashMap<String, String>();
			
		}
		map.put(secondKey, value);
		cahcheMap.put(key, map);
	}
	
	public static String count(Map<String, String> value){
		
		  Iterator<Entry<String, String>> iterator = value.entrySet().iterator();
		    float flag=0;
		    String id="";
		    while (iterator.hasNext()) {  					    	
		    	Entry<String, String> next = iterator.next();	
		    	id =id+ next.getKey()+",";
		    	String value2 = next.getValue();
		    	float parseFloat = Float.parseFloat(value2);
		    	
		    	flag=flag+parseFloat;
		    	
		    }
		    
		    float v = flag/value.size();
		    //System.out.println("缓存中大小(x/y=z)="+flag+"/"+value.size()+"="+v+";id="+id);
		    return v+"";
	}
	
	 
	//全部删除
	public void clearAllCahche(String key ){
         cahcheMap.clear();
		
	}
	public static void main(String[] args) {
		
		System.out.println("1.0".equals("1.00"));
		System.out.println(Double.parseDouble("1.0"));
		
		  int v=(int)Float.parseFloat("1.1");
		  System.out.println(v);
	}
}
