package com.live.recording.controller;



import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.live.recording.config.CacheMap_Mq;
import com.live.recording.domain.vo.input.LiveFileListDataReq;
import com.live.recording.domain.vo.output.ResultProxy;
import com.live.recording.util.JsonHelper;

@RestController
@EnableAutoConfiguration
@RequestMapping("/liveRecord")
public class TestController {
	private Logger logger = Logger.getLogger(TestController.class);
	
	//测试专用
	@RequestMapping(value = "/add")
	public ResultProxy Test(Integer code,String msg) {
		
		ResultProxy resultProxy = new ResultProxy();
		resultProxy.setCode(code);
		resultProxy.setInfo(null);
		resultProxy.setMsg(msg);
		CacheMap_Mq mq=new CacheMap_Mq();
		mq.setCahche(resultProxy.getCode()+"", JsonHelper.toJsonStr(resultProxy));
		return resultProxy;
	}
	//测试专用
	@RequestMapping(value = "/del")
	public int TestError(Integer code) {
		
		CacheMap_Mq mq=new CacheMap_Mq();
		mq.delCahche(code+"");
		return mq.getCahcheSize();
	}
	
	@RequestMapping(value = "/get")
	public int test(Integer code) {
		
		CacheMap_Mq mq=new CacheMap_Mq();
		Object cahche = mq.getCahche(code+"");
		if(cahche!=null){
		
			ResultProxy ss=null;
			ss=JsonHelper.toJSONObject(cahche.toString(), ResultProxy.class);
			
			System.out.println(">>>>>>>>>>>>\n"+JsonHelper.toJsonStr(ss));
		}
		return mq.getCahcheSize();
	}
	
	//测试专用
		@RequestMapping(value = "/addtest")
		public ResultProxy Test_test(LiveFileListDataReq req) {//String recordId,String channelId, ,HttpServletRequest res @RequestBody
			
			//System.out.println( recordId+";"+ channelId);
			String channelId = req.getChannelId();
			System.out.println(channelId);
			//String recordId = res.getParameter("recordId");
			//System.out.println(recordId);
			
			ResultProxy resultProxy = new ResultProxy();
			resultProxy.setCode(0);
			resultProxy.setInfo(null);
			resultProxy.setMsg("success");
			//CacheMap_Mq mq=new CacheMap_Mq();
			//mq.setCahche(resultProxy.getCode()+"", JsonHelper.toJsonStr(resultProxy));
			return resultProxy;
		}
	
	
}
