package com.live.recording.controller;


import java.text.ParseException;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.live.recording.constants.Constants;
import com.live.recording.domain.entity.V_livefilelist;
import com.live.recording.domain.vo.input.LiveRecordListReq;
import com.live.recording.domain.vo.input.LiveFileListDataReq;
import com.live.recording.domain.vo.input.SysReq;
import com.live.recording.domain.vo.input.V_Livecutlist;
import com.live.recording.domain.vo.input.V_MultiplePeriodsRecordsReq;
import com.live.recording.domain.vo.output.CallBack;
import com.live.recording.domain.vo.output.LivecutlistResp;
import com.live.recording.domain.vo.output.ProgressResp;
import com.live.recording.domain.vo.output.ResultProxy;
import com.live.recording.service.ILiveRecordService;
import com.live.recording.util.JsonHelper;
import com.live.recording.util.ValidateParamsUtil;

@RestController
@EnableAutoConfiguration
@RequestMapping("/liveRecord")
public class ChartController {
	private Logger logger = Logger.getLogger(ChartController.class);
	@Autowired
	private ILiveRecordService iLiveRecordService;

	//测试专用
	@RequestMapping(value = "/test")
	public ResultProxy Test(@RequestBody CallBack call) {
		System.out.println(">>>>>>>>>>>>\n"+JsonHelper.toJsonStr(call));
		ResultProxy resultProxy = new ResultProxy();
		resultProxy.setCode(0);
		resultProxy.setInfo(null);
		resultProxy.setMsg("测试成功");
		return resultProxy;
	}
	//测试专用
	@RequestMapping(value = "/error")
	public String TestError(@RequestBody CallBack call) {
		System.out.println(">>>>>>>>>>>>\n"+JsonHelper.toJsonStr(call)); 
		
		return "测试失败";
	}
	
	//正常
	/**
	 * 
	 * @param channelId
	 *            可包含多 个服务器编号 用逗号隔开
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/fileSelect")
	public ResultProxy Filedatas(LiveFileListDataReq req) {
		ResultProxy result = new ResultProxy();

		logger.info(req);
		List<V_livefilelist> findByChannelId = null;

		try {
			findByChannelId = iLiveRecordService.findByChannelId(req);

			result.setCode(Constants.CODE_SUCCESS);
			result.setInfo(findByChannelId);
			result.setMsg(Constants.MSG_SUCCESS);
		} catch (Exception e) {
			result.setCode(Constants.CODE_FAIL);
			result.setInfo(findByChannelId);
			result.setMsg(Constants.MSG_FAIL);
			e.printStackTrace();
		}

		// logger.info(result);

		return result;
	}

	/**
	 * 
	 * @param channeld
	 * @param recordId
	 *            无参数 默认全部
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/recordSelect")
	public ResultProxy Recorddatas(LiveRecordListReq req) {
		ResultProxy result = new ResultProxy();

		logger.info(req);
		List<LivecutlistResp> findByChannelId = null;

		try {
			findByChannelId = iLiveRecordService.findLiveRecordByChannelId(req);

			result.setCode(Constants.CODE_SUCCESS);
			result.setInfo(findByChannelId);
			result.setMsg(Constants.MSG_SUCCESS);
		} catch (Exception e) {
			result.setCode(Constants.CODE_FAIL);
			result.setInfo(findByChannelId);
			result.setMsg(Constants.MSG_FAIL);
			e.printStackTrace();
		}

		// logger.info(result);

		return result;
	}

	/**
	 * 
	 * @param channelId
	 * @param recordId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/recordDelete")
	public ResultProxy RecordDelete(LiveRecordListReq req) {
		ResultProxy result = new ResultProxy();
		logger.info(req);
		int delete = 0;

		if (ValidateParamsUtil.validateRecordDelete(req) != null) {

			result.setCode(Constants.CODE_FAIL);
			result.setInfo(ValidateParamsUtil.validateRecordDelete(req));
			result.setMsg(Constants.MSG_FAIL);
		} else {

			try {
				delete = iLiveRecordService.deleteLiveRecordAndLiveCut(req);

				if (delete == 0) {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(delete);
					result.setMsg(Constants.MSG_FAIL);
				} else {
					result.setCode(Constants.CODE_SUCCESS);
					result.setInfo(delete);
					result.setMsg(Constants.MSG_SUCCESS);
				}

			} catch (Exception e) {
				result.setCode(Constants.CODE_FAIL);
				result.setInfo(delete);
				result.setMsg(Constants.MSG_FAIL);
				e.printStackTrace();
			}

		}
		// logger.info(result);

		return result;
	}

	/**
	 * 
	 * @param recordId
	 *            可包含多个 用逗号隔开
	 * @param cutId
	 *            无参数 默认全部
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/cutSelect")
	public ResultProxy Cutdatas(LiveFileListDataReq req) {
		ResultProxy result = new ResultProxy();

		logger.info(req);
		List<V_Livecutlist> Livecutlist = null;

		try {
			Livecutlist = iLiveRecordService.findLiveCutList_V(req);

			result.setCode(Constants.CODE_SUCCESS);
			result.setInfo(Livecutlist);
			result.setMsg(Constants.MSG_SUCCESS);
		} catch (Exception e) {
			result.setCode(Constants.CODE_FAIL);
			result.setInfo(Livecutlist);
			result.setMsg(Constants.MSG_FAIL);
			e.printStackTrace();
		}

		// logger.info(result);

		return result;
	}
	


	/**
	 * 
	 * @param channelId
	 * @param cutId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/cutDelete")
	public ResultProxy CutDelete(LiveFileListDataReq req) {
		ResultProxy result = new ResultProxy();

		logger.info(req);
		int Livecutlist = 0;
		if (ValidateParamsUtil.validateDeleteCut(req) != null) {

			result.setCode(Constants.CODE_FAIL);
			result.setInfo(ValidateParamsUtil.validateDeleteCut(req));
			result.setMsg(Constants.MSG_FAIL);
		} else {
			try {
				Livecutlist = iLiveRecordService.deleteLiveCutList(req);

				if (Livecutlist == 0) {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(Livecutlist);
					result.setMsg(Constants.MSG_FAIL);
				} else {
					result.setCode(Constants.CODE_SUCCESS);
					result.setInfo(Livecutlist);
					result.setMsg(Constants.MSG_SUCCESS);
				}
			} catch (Exception e) {
				result.setCode(Constants.CODE_FAIL);
				result.setInfo(Livecutlist);
				result.setMsg(Constants.MSG_FAIL);
				e.printStackTrace();
			}
		}

		// logger.info(result);

		return result;
	}

	//执行未到时间任务
//	@Scheduled(cron = "0/30 * * * * ?") // 每30秒执行一次
//	@Scheduled(cron = "15/30 * * * * ?") // 每30秒执行一次
	@RequestMapping(value = "/ScheduledFlv")
	public void ScheduledFlv() {

		iLiveRecordService.ScheduledFlv();

	}
	
	//@Scheduled(cron = "0/5 * * * * ?") // 每隔5秒检查上报数据
		//@Scheduled(cron = "3/5 * * * * ?") // 每隔5秒检查上报数据
		
//		@Scheduled(cron = "0/30 * * * * ?") // 每30秒执行一次
//		@Scheduled(cron = "15/30 * * * * ?") // 每30秒执行一次
		@RequestMapping(value = "/ScheduledCallBack")
		public void ScheduledCallBack() {

			iLiveRecordService.ScheduledReportDatas();

		}


	//更新数据失效数据 接收sys数据
//	@Scheduled(cron = "0 0 3 * * ?") // 每天3点执行
//	@Scheduled(cron = "0 1 3 * * ?") // 
	@RequestMapping(value = "/ScheduledDelSlFlv")
	public void ScheduledDelSlFlv() {

		iLiveRecordService.ScheduledSqFlv();

	}
		
	//删除本地文件 本地拆条数据
//	@Scheduled(cron = "0 0 4 * * ?") // 每天4点执行
//	@Scheduled(cron = "0 1 4 * * ?") // 
	
	@RequestMapping(value = "/ScheduledDelFlv")
	public void ScheduledDelFlv() {

		iLiveRecordService.ScheduledDelFlv();

	}
	
	
	
	
	/**
	 * json格式接收
	 * 合并flv
	 * 
	 * @param channelId
	 * @param recordStartTime
	 * @param recordEndTime
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/creat" )
	public ResultProxy saveDatas(@RequestBody LiveRecordListReq req ,String creater,String sourceCode) {
		req.setCreater(req.getCreater()==null?creater:req.getCreater());
		req.setSourceCode(req.getSourceCode()==null?sourceCode:req.getSourceCode());
		
		//System.out.println(">>>>>>="+req.getChannelId());
		
		ResultProxy result = new ResultProxy();
		// 设置第一次任务
		req.setCreatFlvFlag(Constants.CREATFLV_Record);
		logger.info(req);
		int findByChannelId = 0;

		if (ValidateParamsUtil.validateRecords(req) != null) {

			result.setCode(Constants.CODE_FAIL);
			result.setInfo(ValidateParamsUtil.validateRecords(req));
			result.setMsg(Constants.MSG_FAIL);
		} else {
			try {
				findByChannelId = iLiveRecordService.execCreatVideos(req);
				
				if (findByChannelId == 1) {
					result.setCode(Constants.CODE_SUCCESS);
					result.setInfo(req.getRecordId());
					result.setMsg(Constants.MSG_SUCCESS);
				} else if (findByChannelId == 2) {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(null);
					result.setMsg("未找到合适的视频");
				} else {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(null);
					result.setMsg("拆条失败");
				}

			} catch (Exception e) {
				result.setCode(Constants.CODE_FAIL);
				result.setInfo(null);
				result.setMsg(Constants.MSG_FAIL);
				e.printStackTrace();
			}
		}

		logger.info(result);

		return result;
	}

	/**json类型接收
	 * 合并flv
	 * 
	 * @param recordId
	 * @param cutStartTime
	 * @param cutEndTime
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/cut")
	public ResultProxy saveCutDatas(@RequestBody LiveRecordListReq req,String creater,String sourceCode) {
		ResultProxy result = new ResultProxy();
		req.setCreater(req.getCreater()==null?creater:req.getCreater());
		req.setSourceCode(req.getSourceCode()==null?sourceCode:req.getSourceCode());
		
		logger.info(req);
		int findByChannelId = 0;

		if (ValidateParamsUtil.validateCutRecords(req) != null) {

			result.setCode(Constants.CODE_FAIL);
			result.setInfo(ValidateParamsUtil.validateCutRecords(req));
			result.setMsg(Constants.MSG_FAIL);
		} else {
			try {
				findByChannelId = iLiveRecordService.creatRecordCut(req);
				if (findByChannelId == 1) {
					result.setCode(Constants.CODE_SUCCESS);
					result.setInfo(req.getCutId());
					result.setMsg(Constants.MSG_SUCCESS);
				} else if (findByChannelId == 2) {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(null);
					result.setMsg("未找到合适的视频");
				} else {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(null);
					result.setMsg("拆条失败");
				}

			} catch (Exception e) {
				result.setCode(Constants.CODE_FAIL);
				result.setInfo(null);
				result.setMsg(Constants.MSG_FAIL);
				e.printStackTrace();
			}
		}

		logger.info(result);

		return result;
	}

	/**json类型接收
	 * 参数：
	 *  channelId, recordId,cutName,creater, path
	 *  cutStartTime,cutEndTime ,cutPath
	 * 
	 * 
	 * */
	//不同时间段视频合并
	@RequestMapping(value = "/creatMultiple")//@RequestBody
	public ResultProxy CreatMultipleFlv (@RequestBody V_MultiplePeriodsRecordsReq req,String creater,String sourceCode) {

		ResultProxy result = new ResultProxy();
		
		int findByChannelId = 0;

		req.setCreater(req.getCreater()==null?creater:req.getCreater());
		req.setSourceCode(req.getSourceCode()==null?sourceCode:req.getSourceCode());
		
		if (ValidateParamsUtil.validateCreatMultipleFlv(req) != null) {

			result.setCode(Constants.CODE_FAIL);
			result.setInfo(ValidateParamsUtil.validateCreatMultipleFlv(req));
			result.setMsg(Constants.MSG_FAIL);
		} else {
			try {
				findByChannelId = iLiveRecordService.creatMultiplePeriodsRecordsUP(req);
				if (findByChannelId == 1) {
					result.setCode(Constants.CODE_SUCCESS);
					result.setInfo(req.getRecordId());
					result.setMsg(Constants.MSG_SUCCESS);
				} else if (findByChannelId == 2) {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(null);
					result.setMsg("未找到合适的视频");
				} else {
					result.setCode(Constants.CODE_FAIL);
					result.setInfo(null);
					result.setMsg("拆条失败");
				}

			} catch (Exception e) {
				result.setCode(Constants.CODE_FAIL);
				result.setInfo(null);
				result.setMsg(Constants.MSG_FAIL);
				e.printStackTrace();
			}
		} 

		logger.info(result);

		return result;
	}
	
	
	/**
	 * 保存flv文件
	 * 
	 * @param action;//
	 *            "on_dvr",
	 * @param client_id;//
	 *            119,
	 * @param ip;//
	 *            "127.0.0.1",
	 * @param vhost;//
	 *            "__defaultVhost__",
	 * @param app;//"slive",
	 * @param stream;//
	 *            "tysxlive_dragon",
	 * @param cwd;//
	 *            "/opt/pro/srs",
	 * @param file;
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/srsSave")
	public ResultProxy saveDatas(SysReq req) {
		ResultProxy result = new ResultProxy();

		logger.info(req);
		int findByChannelId = 0;

		if (ValidateParamsUtil.validateRecords(req) != null) {

			result.setCode(Constants.CODE_FAIL);
			result.setInfo(ValidateParamsUtil.validateRecords(req));
			result.setMsg(Constants.MSG_FAIL);
		} else {
			try {
				findByChannelId = iLiveRecordService.saveRecord(req);

				result.setCode(Constants.CODE_SUCCESS);
				result.setInfo(findByChannelId);
				result.setMsg(Constants.MSG_SUCCESS);
			} catch (Exception e) {
				result.setCode(Constants.CODE_FAIL);
				result.setInfo(findByChannelId);
				result.setMsg(Constants.MSG_FAIL);
				e.printStackTrace();
			}
		}

		logger.info(result);

		return result;
	}
	
	/**
	 * 进度条查询
	 * @param keyId ： recordId 或cutId
	 *            可包含多个 用逗号隔开
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/progressSelect")
	public ResultProxy SelectProgress (String keyId) {
		ResultProxy result = new ResultProxy();

		//logger.info("keyId="+keyId);
		ProgressResp Livecutlist = null;
		if (ValidateParamsUtil.validateProgresst(keyId) != null) {

			result.setCode(Constants.CODE_FAIL);
			result.setInfo(ValidateParamsUtil.validateProgresst(keyId));
			result.setMsg(Constants.MSG_FAIL);
		} else {
			try {
				Livecutlist = iLiveRecordService.selectProgress(keyId);
	
				result.setCode(Constants.CODE_SUCCESS);
				result.setInfo(Livecutlist);
				result.setMsg(Constants.MSG_SUCCESS);
			} catch (Exception e) {
				result.setCode(Constants.CODE_FAIL);
				result.setInfo(Livecutlist);
				result.setMsg(Constants.MSG_FAIL);
				e.printStackTrace();
			}
		} 
		// logger.info(result);

		return result;
	}
}
