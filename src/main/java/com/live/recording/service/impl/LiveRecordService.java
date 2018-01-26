package com.live.recording.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.live.recording.config.CacheMap;
import com.live.recording.config.MyConfig;
import com.live.recording.constants.Constants;
import com.live.recording.domain.entity.Livefilelist;
import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.domain.entity.Livereport;
import com.live.recording.domain.entity.V_livefilelist;
import com.live.recording.domain.entity.LiveSourceConf;
import com.live.recording.domain.entity.Livecutlist;
import com.live.recording.domain.vo.input.LiveRecordListReq;
import com.live.recording.domain.vo.input.LiveFileListDataReq;
import com.live.recording.domain.vo.input.SysReq;
import com.live.recording.domain.vo.input.V_Livecutlist;
import com.live.recording.domain.vo.input.V_Livereport;
import com.live.recording.domain.vo.input.V_MultiplePeriodsRecordsReq;
import com.live.recording.domain.vo.output.LivecutlistResp;
import com.live.recording.domain.vo.output.ProgressResp;
import com.live.recording.manager.BaseNativeSqlRepository;
import com.live.recording.manager.LiveCutListManager;
import com.live.recording.manager.LiveReportManager;
import com.live.recording.manager.LiveSourceConfManager;
import com.live.recording.manager.LivefilelistManager;
import com.live.recording.manager.LiverecordlistManager;
import com.live.recording.manager.dao.JpaSpecificationExecutor;
import com.live.recording.manager.dao.LiveCutListDao;
import com.live.recording.manager.dao.LiverecordlistDao;
import com.live.recording.service.ILiveRecordService;
import com.live.recording.service.thread.ExectuerRunnableCallBack;
import com.live.recording.service.thread.ExectuerRunnableCut;
import com.live.recording.service.thread.ExectuerRunnableMultipleRecord;
import com.live.recording.service.thread.ExectuerRunnableRecord;
import com.live.recording.util.DateHelper;
import com.live.recording.util.ExecRuntime;
import com.live.recording.util.UUIDUtil;
import com.live.recording.util.WriterFile;

@Service
public class LiveRecordService implements ILiveRecordService {
	private Logger logger = Logger.getLogger(LiveRecordService.class);
	private static ExecutorService exeService = null; // 并发数
	@Autowired
	private LivefilelistManager livefilelistManager;
	@Autowired
	private LiverecordlistManager liverecordlistManager;
	@Autowired
	public LiveCutListManager liveCutListManager;
	// 原声sql
	@Autowired
	public BaseNativeSqlRepository baseNativeSqlRepository;

	@Autowired
	public LiveReportManager liveReportManager;

	@Autowired
	public LiveSourceConfManager liveSourceConfManager;

	@Autowired
	private JpaSpecificationExecutor<Liverecordlist> jpaSpecificationExecutor_Liverecordlist;
	@Autowired
	private JpaSpecificationExecutor<Livecutlist> jpaSpecificationExecutor_Livecutlist;

	@Autowired
	private MyConfig myConfig;

	// 查询数据
	@Override
	public List<V_livefilelist> findByChannelId(LiveFileListDataReq req) {

		List<V_livefilelist> livefilelists = new ArrayList<>();

		List<Object[]> list = baseNativeSqlRepository.findV_livefilelist(req.getChannelId());

		for (Object[] obj : list) {
			V_livefilelist livefilelist = new V_livefilelist();
			livefilelist.setChannelId(String.valueOf(obj[0]));
			livefilelist.setStartTime(String.valueOf(obj[1]));
			livefilelist.setEndTime(String.valueOf(obj[2]));
			livefilelist.setContentName(String.valueOf(obj[3]));
			livefilelists.add(livefilelist);
		}

		return livefilelists;

	}

	@Override
	public List<Liverecordlist> findLiverecordlistByFileStatus(Integer fileStatus) {
		// 查询Liverecordlist表
		Specification<Liverecordlist> liverecordlist = LiverecordlistDao.getLiverecordlistByFileStatus(fileStatus,
				Constants.ISDELETE_NO);
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		return jpaSpecificationExecutor_Liverecordlist.findAll(liverecordlist, sort);
	}

	@Override
	// 根据ChannelId或RecordId获取Liverecordlist
	public List<Liverecordlist> findLiveRecords(LiveRecordListReq req) {
		int status = exest_is_delete(req.getIsDelete());
		// 查询Liverecordlist表
		Specification<Liverecordlist> liverecordlist = LiverecordlistDao.getLiverecordlist(req.getChannelId(),
				req.getRecordId(), status);

		Sort sort = new Sort(Sort.Direction.DESC, "createTime");

		return jpaSpecificationExecutor_Liverecordlist.findAll(liverecordlist, sort);

	}

	private Integer exest_is_delete(Integer isDelete) {
		int status = Constants.ISDELETE_NO;
		if (isDelete != null) {
			status = isDelete;
		}

		return status;
	}

	// 查询数据Liverecordlist
	@Override
	public List<LivecutlistResp> findLiveRecordByChannelId(LiveRecordListReq req) {
		List<Object[]> list = baseNativeSqlRepository.findV_liverecordlist(req.getChannelId());

		List<LivecutlistResp> listresp = new ArrayList<>();

		for (Object[] obj : list) {
			LivecutlistResp resp = new LivecutlistResp();
			resp.setChannelId(String.valueOf(obj[0]));
			resp.setRecordId(String.valueOf(obj[1]));
			resp.setRecordName(String.valueOf(obj[2]));
			resp.setRecordStartTime(String.valueOf(obj[3]));
			resp.setRecordEndTime(String.valueOf(obj[4]));
			resp.setRecordPath(String.valueOf(obj[5]));
			resp.setFileStatus(Integer.parseInt(String.valueOf(obj[6])));
			List<V_Livecutlist> findV_livecutlist = baseNativeSqlRepository.findV_livecutlist(resp.getRecordId());

			resp.setCutInfo(findV_livecutlist);

			listresp.add(resp);

		}

		return listresp;
	}

	/**
	 * 定时任务
	 */
	@Override
	public void ScheduledFlv() {

		// 获取状态为1数据
		//List<Liverecordlist> Liverecordlist = findLiverecordlistByFileStatus(Constants.TRANS_STATUS_1);
		List<Liverecordlist> Liverecordlist =liverecordlistManager.findByFileStatus(Constants.ISDELETE_NO, Constants.TRANS_STATUS_1,Constants.CREATFLV_locking_task);
		//baseNativeSqlRepository.findLivefilelist
		if (Liverecordlist.size() == 0) {
			logger.info("定时任务end");
			return;
		}
		for (Liverecordlist record : Liverecordlist) {
			record.setRemark(Constants.CREATFLV_locking_task);
			liverecordlistManager.saveAndFlush(record);
			
			// 获取当前时间 如果大于当前时间 就 只插入数据 返回
			long currentTimeMillis = System.currentTimeMillis();
			Long RecordEndTime = timeStrToLong(record.getRecordEndTime());

			//if (RecordEndTime > currentTimeMillis) {
			if ((RecordEndTime + myConfig.getRecordingDelayTime()*60*1000) > currentTimeMillis) {
				logger.info("设置视频合并时间未到：返回");
				record.setRemark("定时任务;初始化");
				liverecordlistManager.saveAndFlush(record);
				continue;
			}
			
			
			
			// 设置请求参数
			LiveRecordListReq req = new LiveRecordListReq();
			req.setChannelId(record.getChannelId());
			req.setRecordStartTime(record.getRecordStartTime());
			req.setRecordEndTime(record.getRecordEndTime());
			req.setCreatFlvFlag(Constants.CREATFLV_Scheduled);
			req.setId(record.getId());
			req.setRecordId(record.getRecordId());
			Livereport findReportByVideoId = liveReportManager.findReportByVideoId(req.getRecordId(),Constants.ISDELETE_NO);
			req.setSourceId(Integer.parseInt(findReportByVideoId.getSourceId()));

			logger.info("获取定时任务参数=" + req.toString());

			logger.info("定时任务start");
			try {
				
				creatRecord(req);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("定时任务结束任务id=" + req.getId());
			
//			if (exeService == null) {
//				exeService = Executors.newFixedThreadPool(myConfig.getDismantleConcurrent());
//
//			}
//
//			exeService.execute(new Runnable() {
//
//				@Override
//				public void run() {
//					try {
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});

		}

	}

	// 查询数据Livecutlist
	@Override
	public List<Livecutlist> findLiveCutList(LiveFileListDataReq req) {// 多个RecordId用逗号隔开

		int status = exest_is_delete(req.getIsDelete());
		String[] RecordIds = null;
		if (req.getRecordId() != null) {
			RecordIds = req.getRecordId().split(",");
		}
		Specification<Livecutlist> Livecutlist = LiveCutListDao.getLiverecordlist(req.getCutId(), RecordIds, status);
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		return jpaSpecificationExecutor_Livecutlist.findAll(Livecutlist, sort);
	}

	@Override
	public List<V_Livecutlist> findLiveCutList_V(LiveFileListDataReq req) {// 多个RecordId用逗号隔开

		return baseNativeSqlRepository.findV_livecutlist(req.getRecordId());

	}

	// 合并flv组
	@Override
	public int execCreatVideos(LiveRecordListReq req) throws Exception {
		// 获取开始结束时间
		Long RecordStartTime = timeStrToLong(req.getRecordStartTime());
		Long RecordEndTime = timeStrToLong(req.getRecordEndTime());

		req.setCreatFlvFlag(Constants.CREATFLV_Record);
		req.setRecordId(UUIDUtil.getUUID());
		req.setSourceCode(req.getSourceCode() == null ? "test" : req.getSourceCode());
		// 获取所有的flv
		List<String> findByChannelIAll = baseNativeSqlRepository.findLivefilelist(req.getChannelId(),Constants.ISDELETE_NO, RecordStartTime + "", RecordEndTime + "");

		// 设置缓存
		CacheMap cahcheMap = new CacheMap();

		List<String> channelIds = baseNativeSqlRepository.findLivefilelistGroupBychannelId(req.getChannelId(),	Constants.ISDELETE_NO);
		
		//存放id
		Map<String, Integer> map=new HashMap<>();
		
		for (String channelId : channelIds) {
			req.setChannelId(channelId);

			Liverecordlist saveBean = liverecordlistManager.saveAndFlush(saverecorddata(req, req.getRecordEndTime(), Constants.TRANS_STATUS_1, "", Constants.CREATFLV_locking_task));
			
			//req.setId(saveBean.getId());
			map.put(channelId, saveBean.getId());
			// 初始化缓存
			cahcheMap.setCahche(saveBean.getRecordId(), saveBean.getId() + "", "0");

		}
		// 插入上报表
		getLivereportBean(req);
		
		//如果 当前查询到视频小于 频道实际个数 返回
		if (findByChannelIAll.size() < channelIds.size()) {
			logger.info("未找到该时间段视频,记录下任务;解锁");
			
			for (String channelId : channelIds) {
				
				liverecordlistManager.updateById(Constants.TRANS_STATUS_1, "初始化任务;时间未到", map.get(channelId));
				
			}
		
			return 1;
		}

		

		for (String ChannelId : findByChannelIAll) {

			req.setChannelId(ChannelId);
			req.setId(map.get(ChannelId));
			// 调用
			creatRecord(req);
		}

		return 1;
	}

	// 插入上报表
	private void getLivereportBean(LiveRecordListReq req) {

		LiveSourceConf findBySourceCode = liveSourceConfManager.findBySourceCode(req.getSourceCode(),
				Constants.ISDELETE_NO, Constants.STATUS_YES);
		// 插入上报表
		Livereport livereportbean = new Livereport();

		livereportbean.setCallBackStatus(Constants.reportstatus_1);

		livereportbean.setSourceId(findBySourceCode.getId() + "");
		livereportbean.setSourceName(findBySourceCode.getSourceName());
		livereportbean.setCallbackUrl(findBySourceCode.getCallbackUrl());

		livereportbean.setVideoId(req.getRecordId());
		livereportbean.setCreater(req.getCreater());

		livereportbean.setIsDelete(Constants.ISDELETE_NO);
		livereportbean.setLastUpdater(req.getCreater());
		livereportbean.setCreateTime(new Date());
		livereportbean.setLastUpdateTime(new Date());
		livereportbean.setRemark("初始化数据;等待上报");
		livereportbean.setReportFailCount(0);

		liveReportManager.save(livereportbean);

	}

	// 合并flv
	@Override
	public int creatRecord(LiveRecordListReq req) throws Exception {

		// 获取开始结束时间
		Long RecordStartTime = timeStrToLong(req.getRecordStartTime());
		Long RecordEndTime = timeStrToLong(req.getRecordEndTime());
		// Constants.CREATFLV_YES 页面请求 Constants.CREATFLV_NO 定时任务
		Liverecordlist saveBean = null;
		//if (req.getCreatFlvFlag()!=null&&req.getCreatFlvFlag() == Constants.CREATFLV_Record) {
			// 数据库创建任务
			//saveBean = liverecordlistManager.saveAndFlush(saverecorddata(req, req.getRecordEndTime(), Constants.TRANS_STATUS_1, "", "初始化任务"));
			
		saveBean=liverecordlistManager.findById(Constants.ISDELETE_NO, req.getId(), Constants.TRANS_STATUS_1);
			
		if (saveBean == null) {
			logger.info("视频已经执行：返回");
			return 1;
		}
//			
//		} else {
//			
//			saveBean=liverecordlistManager.findById(Constants.ISDELETE_NO, req.getId(), Constants.TRANS_STATUS_1);
//			
//			if (saveBean == null||saveBean.getRemark().endsWith(Constants.CREATFLV_locking_task)) {
//				logger.info("视频已经执行：返回");
//				return 1;
//			}			
//		}
		// 设置缓存
		CacheMap cahcheMap = new CacheMap();
		cahcheMap.setCahche(saveBean.getRecordId(), saveBean.getId() + "", "0");

		// 获取当前时间 如果大于当前时间 就 只插入数据 返回
		long currentTimeMillis = System.currentTimeMillis();

		//if ((RecordEndTime - 5*60*1000) > currentTimeMillis) {
		if ((RecordEndTime + myConfig.getRecordingDelayTime()*60*1000) > currentTimeMillis) {
			logger.info("设置视频合并时间未到：返回");
			
			saveBean.setRemark("设置视频合并时间未到：返回");
			liverecordlistManager.saveAndFlush(saveBean);
			return 1;
		}

		logger.info("开始录制。。。");
		saveBean.setRemark("开始录制");
		saveBean.setFileStatus(Constants.TRANS_STATUS_2);
		liverecordlistManager.saveAndFlush(saveBean);
		// 获取所有的flv 真正做事的方法
		List<Livefilelist> findByChannelIAll = livefilelistManager.findByChannelIAll(req.getChannelId(),
				Constants.ISDELETE_NO, (RecordStartTime - myConfig.getRecordingDelayTime()*60*1000) + "", RecordEndTime + "");
		if (findByChannelIAll.size() == 0) {
			logger.info("获取录制任务为空;初始化失败");
			saveBean.setRemark("获取录制任务为空;初始化失败");
			saveBean.setFileStatus(Constants.TRANS_STATUS_5);
			liverecordlistManager.saveAndFlush(saveBean);
			return 2;
		}
		LiveSourceConf findBySourceCode = null;
		if (req.getSourceId() != null) {
			findBySourceCode = liveSourceConfManager.findById(req.getSourceId(), Constants.ISDELETE_NO,
					Constants.STATUS_YES);

		} else {
			findBySourceCode = liveSourceConfManager.findBySourceCode(req.getSourceCode(), Constants.ISDELETE_NO,
					Constants.STATUS_YES);
		}

		// String flvpath = myConfig.getSaveVideoPath() + File.separator +
		// req.getChannelId() + File.separator + DateHelper.getDateDirStr();
		String flvpath = findBySourceCode.getSavePath() + File.separator + req.getChannelId() + File.separator
				+ DateHelper.getDateDirStr();

		// 拼接文件名字
		String concat_path = flvpath + File.separator + req.getChannelId() + System.currentTimeMillis() + ".concat";

		// 执行ffmpeg

		long startLong = timeLongToLong(Long.parseLong(findByChannelIAll.get(0).getStartTime()));

		Long startsize = (RecordStartTime - startLong) / 1000;// longtostr(startLong-RecordStartTime);
		Long allsize = (RecordEndTime - RecordStartTime) / 1000;// longtostr(RecordEndTime-RecordStartTime);

		// 存储flv路径
		StringBuffer cmd = new StringBuffer();
		String flvPathReal = flvpath + File.separator + req.getChannelId() + System.currentTimeMillis() + ".mp4";
		if (startsize <= 1) {
			cmd.append(myConfig.getFfmpegPath() + "ffmpeg").append(" -safe 0 ").append(" -i \"").append(concat_path)
					.append("\" -t ").append(allsize).append(" -c copy ").append(" -movflags faststart ")
					.append(flvPathReal);

		} else {
			cmd.append(myConfig.getFfmpegPath() + "ffmpeg").append(" -ss ").append(startsize).append(" -safe 0 ")
					.append(" -i \"").append(concat_path).append("\" -t ").append(allsize).append(" -c copy ")
					.append(" -movflags faststart ").append(flvPathReal);

		}
		// ffmpeg日志路径
		String ffmpeglogpath = myConfig.getSaveLogPath() + File.separator + req.getChannelId() + File.separator
				+ DateHelper.getDateDirStr();

		// 把需要执行的线程放到线程池中
		if (exeService == null) {

			exeService = Executors.newFixedThreadPool(myConfig.getDismantleConcurrent());
		}

		// 执行拆条线程
		exeService.execute(new ExectuerRunnableRecord(saveBean, myConfig, cmd.toString(), flvPathReal,
				liverecordlistManager, ffmpeglogpath, findByChannelIAll, concat_path, allsize));

		return 1;
	}

	// 2次c拆条录制
	@Override
	public int creatRecordCut(LiveRecordListReq req) throws Exception {

		// 获取所有的flv
		Specification<Liverecordlist> liverecordlist = LiverecordlistDao.getLiverecordlist(null, req.getRecordId(),
				Constants.ISDELETE_NO);
		List<Liverecordlist> findAll = jpaSpecificationExecutor_Liverecordlist.findAll(liverecordlist);

		if (findAll.size() == 0) {
			logger.info("获取录制任务为空;初始化失败");
			return 2;
		}

		req.setCutId(UUIDUtil.getUUID());

		for (Liverecordlist findByChannelIAll : findAll) {

			// 数据库创建任务
			Livecutlist saveBean = liveCutListManager
					.saveAndFlush(savecutdata(req, Constants.TRANS_STATUS_1, "", "初始化任务"));
			// 设置缓存
			CacheMap cahcheMap = new CacheMap();
			cahcheMap.setCahche(saveBean.getCutId(), saveBean.getId() + "", "0");

			System.out.println("2次切片(CutId,Id)=" + saveBean.getCutId() + ";" + saveBean.getId());

			// 设置请求参数
			req.setChannelId(findByChannelIAll.getChannelId());
			req.setRecordName(findByChannelIAll.getRecordName());

			LiveSourceConf findBySourceCode = liveSourceConfManager.findBySourceCode(req.getSourceCode(),
					Constants.ISDELETE_NO, Constants.STATUS_YES);

			// String flvpath = myConfig.getSaveVideoPath() + File.separator +
			// req.getChannelId() + "_cut" + File.separator +
			// DateHelper.getDateDirStr();
			String flvpath = findBySourceCode.getSavePath() + File.separator + req.getChannelId() + "_cut"
					+ File.separator + DateHelper.getDateDirStr();

			// 拼接文件名字
			String concat_path = flvpath + File.separator + req.getChannelId() + System.currentTimeMillis() + ".concat";

			// 执行ffmpeg
			Long startsize = Long.parseLong(req.getCutStartTime());// (RecordStartTime
																	// -
																	// startLong)
																	// / 1000;//
																	// longtostr(startLong-RecordStartTime);
			Long allsize = Long.parseLong(req.getCutEndTime()) - startsize;// (RecordEndTime
																			// -
																			// RecordStartTime)
																			// /
																			// 1000;//
																			// longtostr(RecordEndTime-RecordStartTime);

			// 存储flv路径
			StringBuffer cmd = new StringBuffer();
			String flvPathReal = flvpath + File.separator + req.getChannelId() + System.currentTimeMillis() + ".mp4";
			if (startsize <= 1) {
				cmd.append(myConfig.getFfmpegPath() + "ffmpeg").append(" -safe 0 ").append(" -i \"").append(concat_path)
						.append("\" -t ").append(allsize).append(" -c copy ").append(" -movflags faststart ")
						.append(flvPathReal);

			} else {
				cmd.append(myConfig.getFfmpegPath() + "ffmpeg").append(" -ss ").append(startsize).append(" -safe 0 ")
						.append(" -i \"").append(concat_path).append("\" -t ").append(allsize).append(" -c copy ")
						.append(" -movflags faststart ").append(flvPathReal);

			}
			// ffmpeg日志路径
			String ffmpeglogpath = myConfig.getSaveLogPath() + File.separator + req.getChannelId() + File.separator
					+ DateHelper.getDateDirStr();

			// 把需要执行的线程放到线程池中
			if (exeService == null) {
				exeService = Executors.newFixedThreadPool(myConfig.getDismantleConcurrent());
			}

			// 执行拆条线程
			exeService.execute(new ExectuerRunnableCut(saveBean, myConfig, cmd.toString(), flvPathReal,
					liveCutListManager, ffmpeglogpath, findByChannelIAll, concat_path, allsize));

		}
		return 1;
	}

	// 第一次拆条
	public Liverecordlist saverecorddata(LiveRecordListReq req, String RecordEndTime, int FileStatus, String recordPath,
			String remark) {

		Liverecordlist liverecordlist = new Liverecordlist();
		liverecordlist.setFileStatus(FileStatus);
		liverecordlist.setRecordEndTime(RecordEndTime);
		liverecordlist.setRecordPath(recordPath);
		liverecordlist.setRemark(remark);

		liverecordlist.setRecordId(req.getRecordId() == null ? UUIDUtil.getUUID() : req.getRecordId());

		liverecordlist.setChannelId(req.getChannelId());
		liverecordlist.setCreater(req.getCreater());
		liverecordlist.setCreateTime(new Date());
		liverecordlist.setIsDelete(Constants.ISDELETE_NO);
		liverecordlist.setRecordName(req.getRecordName());
		liverecordlist.setRecordStartTime(req.getRecordStartTime());

		return liverecordlist;
	}

	// 2次拆条
	public Livecutlist savecutdata(LiveRecordListReq req, int FileStatus, String recordPath, String remark) {
		Livecutlist Livecutlist = new Livecutlist();
		Livecutlist.setFileStatus(FileStatus);
		Livecutlist.setCutStartTime(req.getCutStartTime());
		Livecutlist.setCutEndTime(req.getCutEndTime());
		Livecutlist.setRecordId(req.getRecordId());

		Livecutlist.setCutId(req.getCutId() == null ? UUIDUtil.getUUID() : req.getCutId());

		Livecutlist.setCutName(req.getCutName());
		Livecutlist.setCutPath(recordPath);

		Livecutlist.setRemark(remark);

		Livecutlist.setCreater(req.getCreater());
		Livecutlist.setCreateTime(new Date());
		Livecutlist.setIsDelete(Constants.ISDELETE_NO);

		return Livecutlist;
	}

	// 保存flv信息
	@Override
	@Transactional
	public int saveRecord(SysReq req) {
		Livefilelist filelist = new Livefilelist();
		// 设置频道号
		filelist.setChannelId(req.getStream());
		// 设置flv文件路径
		filelist.setPath(req.getFile());
		// Duration不存在 主动查询
		if (StringUtils.isEmpty(req.getDuration())) {
			String countDuration = CountDuration(req.getFile(), req.getStream(), req.getAction());
			filelist.setDuration(countDuration);
		} else {
			filelist.setDuration(req.getDuration());
		}

		// tysxlive_dragon.0909.58.1504228198312.flv
		String StartTime = null;// 开始时间 从文件名字中获取
		String EndTime = null;// 从文件名字中开始时间+5分钟

		String[] split = req.getFile().split("\\/");
		if (split.length > 0) {
			String string = split[split.length - 1];

			String[] split2 = string.split("\\.");
			if (split2.length > 1) {
				String timelong = split2[split2.length - 2];
				long parseLong = Long.parseLong(timelong);
				StartTime = parseLong + "";// DateHelper.getDateStrTimeByDate(new
											// Date(parseLong));
				EndTime = parseLong + 5 * 1000 * 60 + "";// DateHelper.getDateStrTimeByDate(new
															// Date(parseLong+5*1000*60));
			}
		}

		filelist.setStartTime(StartTime);// DateHelper.getDateStrTimeByDate(new
											// Date())
		filelist.setEndTime(EndTime);

		filelist.setCreater(req.getClient_id() + "");
		filelist.setCreateTime(new Date());
		filelist.setIsDelete(Constants.ISDELETE_NO);
		filelist.setRemark(req.toString());
		livefilelistManager.saveAndFlush(filelist);

		return 0;

	}

	// 获取Duration
	public String CountDuration(String flvPathReal, String ChannelId, String recordId) {
		String duration = null;
		String durationCmd = myConfig.getFfmpegPath() + "ffprobe -v 0 -print_format compact -show_format "
				+ flvPathReal;

		String ffmpeglogpath = myConfig.getSaveLogPath() + File.separator + ChannelId + File.separator
				+ DateHelper.getDateDirStr() + File.separator;
		// 创建log路径
		if (!WriterFile.createDir(ffmpeglogpath)) {
			logger.info("日志路径无法创建;录制失败");
			return duration;
		}
		;
		String durationpath = ffmpeglogpath + recordId + "_duration_" + System.currentTimeMillis();
		logger.info("执行视频时间logpath=" + durationpath);
		try {
			boolean executeCmd = new ExecRuntime().executeCmd(durationCmd.toString(), myConfig.getTimeouts(),
					durationpath, 1, null, null);
			if (executeCmd) {
				// 生成文件慢 需要可能等待1秒
				WriterFile writerFile = new WriterFile();
				writerFile.existsFile(durationpath);
				duration = writerFile.readfile(durationpath);
				if (duration == null) {
					writerFile.existsFile(durationpath);
					duration = writerFile.readfile(durationpath);
				} else {
					logger.info("执行视频时间成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return duration;
	}

	public static Long timeStrToLong(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (time.indexOf(" ") < 1) {
			String starttime = time.substring(0, 10);
			String endtime = time.substring(10, 18);
			time = starttime + " " + endtime;
		}
		Date parse = null;
		try {
			parse = sdf.parse(time);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse.getTime();

	}

	public static Long timeLongToLong(Long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String parse = null;
		try {
			parse = sdf.format(time);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeStrToLong(parse);

	}

	// longtostr
	private static String longtostr(Long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String parse = null;
		try {
			parse = sdf.format(time);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse;

	}

	// longtostr
	public static String testlongtostr(Long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String parse = null;
		try {
			parse = sdf.format(time);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse;

	}

	// 删除livecutlist表数据
	@Override
	@Transactional
	public int deleteLiveCutList(LiveFileListDataReq req) {
		// 再次验证
		if (StringUtils.isEmpty(req.getRecordId()) && StringUtils.isEmpty(req.getCutId())) {

			return 0;
		}

		// 查询是否需要删除数据
		List<Livecutlist> findLiveCutList = findLiveCutList(req);

		// 判断是否删除
		if (findLiveCutList.size() != 0) {
			for (Livecutlist cut : findLiveCutList) {
				cut.setUpdater(req.getCreater() == null ? "" : req.getCreater());
				cut.setUpdateTime(new Date());
				cut.setIsDelete(Constants.ISDELETE_YES);
			}
			return liveCutListManager.save(findLiveCutList).size();

		}

		// 失败返回0 成功返回删除数据大小
		return 0;
	}

	// 删除LiveRecordList 和 LiveCutList 表
	@Override
	@Transactional
	public int deleteLiveRecordAndLiveCut(LiveRecordListReq req) {
		// 再次验证
		if (StringUtils.isEmpty(req.getRecordId()) && StringUtils.isEmpty(req.getChannelId())) {

			return 0;
		}

		// 删除Record表
		List<Liverecordlist> findAll = findLiveRecords(req);

		if (findAll.size() == 0) {

			return 0;
		}
		// 拼接需要recordIds
		String recordIds = "";
		int count = 0;

		List<Integer> ids = new ArrayList<>();

		for (Liverecordlist record : findAll) {
			count++;

			if (findAll.size() == count) {
				recordIds = recordIds + record.getRecordId();
			} else {
				recordIds = recordIds + record.getRecordId() + ",";
			}

			ids.add(record.getId());
			record.setUpdater(req.getCreater() == null ? "" : req.getCreater());
			record.setUpdateTime(new Date());
			record.setIsDelete(Constants.ISDELETE_YES);
		}

		liverecordlistManager.save(findAll);

		String[] split = recordIds.split(",");
		if (split.length == 0) {
			return 1;
		}
		// 删除livereport表
		liveReportManager.updateByVideoIds(Constants.ISDELETE_YES, "任务被删除", split,
				req.getCreater() == null ? "" : req.getCreater(), new Date());
		// 删除cut表
		liveCutListManager.updateByRecordId(Constants.ISDELETE_YES, "任务被删除", split,
				req.getCreater() == null ? "" : req.getCreater(), new Date());

		// // 查询cut表
		// //删除cut表
		// LiveFileListDataReq cutreq = new LiveFileListDataReq();
		// cutreq.setRecordId(recordIds);
		// List<Livecutlist> findLiveCutList = findLiveCutList(cutreq);
		// if (findLiveCutList.size() == 0) {
		//
		// return 1;
		// }
		//
		// for (Livecutlist cut : findLiveCutList) {
		//
		// cut.setIsDelete(Constants.ISDELETE_YES);
		// }
		//
		// liveCutListManager.save(findLiveCutList);

		return 1;
	}

	/**
	 * 定时删除文件 删除本地文件 本地拆条数据
	 */
	@Override
	public int ScheduledDelFlv() {

		Integer count = myConfig.getLocalVideoFailureTime() == null ? 3 : myConfig.getLocalVideoFailureTime();
		logger.info("start删除大于" + count + "天任务");

		List<String> recordIds = new ArrayList<>();

		// 删除record表数据 删除已经删除和大于3天的
		long currentTime = System.currentTimeMillis();
		long timeLong = currentTime - (long) count * 24 * 60 * 60 * 1000;
		String time = testlongtostr(timeLong);

		List<Liverecordlist> findLiveRecords = liverecordlistManager.findByAll(Constants.ISDELETE_YES, time);
		try {
			for (Liverecordlist record : findLiveRecords) {
				String recordPath = record.getRecordPath();
				logger.info("可能需要删除路径record_path=" + recordPath);

				// 只更新状态未删除的
				if (record.getFileStatus() == Constants.ISDELETE_NO) {
					record.setFileStatus(Constants.ISDELETE_YES);
					liverecordlistManager.save(record);
					continue;
				}

				if (StringUtils.isEmpty(recordPath)) {
					continue;
				}

				record.setUpdater("ScheduledDelFlv");
				record.setUpdateTime(new Date());

				File file = new File(recordPath);
				if (file.exists() && file.isFile()) {
					if (file.delete()) {
						// 添加record
						recordIds.add(record.getRecordId());
						logger.info("成功删除路径record_path=" + recordPath);
						record.setIsDelete(Constants.ISDELETE_SERVER_YES);
						liverecordlistManager.save(record);
					} else {
						logger.info("失败删除路径record_path=" + recordPath);
						record.setIsDelete(Constants.ISDELETE_SERVER_NO);
						liverecordlistManager.save(record);
					}
				} else {
					logger.info("失败删除路径record_path=" + recordPath);
					record.setIsDelete(Constants.ISDELETE_SERVER_NO);
					liverecordlistManager.save(record);
				}
			}

			if (recordIds.size() == 0) {
				return 1;
			}

			List<Livecutlist> findLiveCutList = liveCutListManager.findByRecordIdIn(recordIds);

			for (Livecutlist cut : findLiveCutList) {
				String cutPath = cut.getCutPath();
				logger.info("可能需要删除路径cut_path=" + cutPath);
				if (StringUtils.isEmpty(cutPath)) {
					continue;
				}
				cut.setUpdater("ScheduledDelFlv");
				cut.setUpdateTime(new Date());
				File file = new File(cutPath);
				if (file.exists() && file.isFile()) {
					if (file.delete()) {
						logger.info("成功删除路径cut_path=" + cutPath);
						cut.setIsDelete(Constants.ISDELETE_SERVER_YES);
						liveCutListManager.save(cut);
					} else {
						logger.info("失败删除路径cut_path=" + cutPath);
						cut.setIsDelete(Constants.ISDELETE_SERVER_NO);
						liveCutListManager.save(cut);
					}
				} else {
					logger.info("失败删除路径cut_path=" + cutPath);
					cut.setIsDelete(Constants.ISDELETE_SERVER_NO);
					liveCutListManager.save(cut);
				}

			}

			// Integer count =
			// liverecordlistManager.updateByStartTime(Constants.ISDELETE_YES,
			// Constants.ISDELETE_NO, time);
			logger.info("更新大于3天的数据个数");

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("end删除路径path");
		return 1;
	}

	@Override
	public ProgressResp selectProgress(String keyId) {
		ProgressResp resp = new ProgressResp();
		CacheMap cahcheMap = new CacheMap();
		resp.setKeyId(keyId);
		resp.setProgressValue(cahcheMap.getCahche(keyId));

		return resp;
	}

	@Override
	public int creatMultiplePeriodsRecordsUP(V_MultiplePeriodsRecordsReq req) throws Exception {
		List<V_Livecutlist> liverecordlist = req.getLiverecordlist();
		// 分开并组装数据
		Map<String, List<V_Livecutlist>> map = new HashMap<String, List<V_Livecutlist>>();
		for (V_Livecutlist listBean : liverecordlist) {

			String cutId = listBean.getCutId();
			// cutId=cutId.replace(",", "','");
			List<V_Livecutlist> findBycutId = baseNativeSqlRepository.findV_livecutlistByCutId(cutId);

			for (V_Livecutlist beans : findBycutId) {

				String cutPath = beans.getCutInfo();
				String[] cutPaths = cutPath.split("\\|");
				for (int i = 0; i < cutPaths.length; i++) {
					V_Livecutlist bean = new V_Livecutlist();
					BeanUtils.copyProperties(beans, bean);
					String[] split = cutPaths[i].split(",");
					bean.setCutInfo(split[0]);// 视频路径
					bean.setCutDuration(split[2]);// 视频时间大小
					String key = split[1];// 视频码率

					if (map.containsKey(key)) {
						List<V_Livecutlist> list = map.get(key);
						list.add(bean);
					} else {
						List<V_Livecutlist> list = new ArrayList<>();
						list.add(bean);
						map.put(key, list);
					}

				}
			}
		}
		req.setRecordId(UUIDUtil.getUUID());
		Iterator<Entry<String, List<V_Livecutlist>>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			Entry<String, List<V_Livecutlist>> entry = it.next();
			String key = entry.getKey();
			req.setLiverecordlist(entry.getValue());

			// req.setChannelId(req.getChannelId()+"_"+key);
			creatMultiplePeriodsRecords(req, key);
		}

		// req.setLiverecordlist(liverecordlist);
		// creatMultiplePeriodsRecords(req);

		return 1;
	}

	/**
	 * 参数： channelId, recordId,cutName,creater cutStartTime,cutEndTime ,cutPath
	 * 
	 * 
	 */
	// 合并不同时间段的视频,数据liverecordlist中插入

	public int creatMultiplePeriodsRecords(V_MultiplePeriodsRecordsReq req, String Bitrate) throws Exception {
		List<V_Livecutlist> findByChannelIAll = req.getLiverecordlist();
		String channelId = req.getChannelId() + "_" + Bitrate;

		// channelId = channelId + "_Multiple";
		// 初始化
		Long startsize = (long) 0;
		float allsize = (long) 0;

		for (V_Livecutlist data : findByChannelIAll) {
			float time = Float.parseFloat(data.getCutDuration());

			allsize = allsize + time;
		}
		// 格式化时间
		String startime = "00:00:00";
		String endtime = IntToDateString((int) allsize);

		// 需要存入数据库数据
		Liverecordlist saveBean = new Liverecordlist();
		saveBean.setFileStatus(Constants.TRANS_STATUS_1);
		saveBean.setRecordId(req.getRecordId() == null ? UUIDUtil.getUUID() : req.getRecordId());
		saveBean.setChannelId(channelId);
		saveBean.setCreater(req.getCreater());
		saveBean.setCreateTime(new Date());
		saveBean.setIsDelete(Constants.ISDELETE_NO);
		saveBean.setRecordName(req.getRecordName());

		saveBean.setRecordPath("");
		saveBean.setRemark("添加任务成功");
		// 需要填写
		saveBean.setRecordStartTime(startime);
		saveBean.setRecordEndTime(endtime);
		saveBean.setRecordDuration(null);

		liverecordlistManager.saveAndFlush(saveBean);

		// 设置缓存
		CacheMap cahcheMap = new CacheMap();
		cahcheMap.setCahche(saveBean.getRecordId(), saveBean.getId() + "", "0");

		LiveSourceConf findBySourceCode = liveSourceConfManager.findBySourceCode(req.getSourceCode(),
				Constants.ISDELETE_NO, Constants.STATUS_YES);

		// 视频存放路径
		// String flvpath = myConfig.getSaveVideoPath() + File.separator +
		// channelId + File.separator + DateHelper.getDateDirStr();
		String flvpath = findBySourceCode.getSavePath() + File.separator + channelId + File.separator
				+ DateHelper.getDateDirStr();

		// 存储flv路径
		StringBuffer cmd = new StringBuffer();
		String flvPathReal = flvpath + File.separator + channelId + System.currentTimeMillis() + ".mp4";

		// 拼接文件名字
		String concat_path = flvpath + File.separator + channelId + System.currentTimeMillis() + ".concat";
		// ffmpeg日志路径
		String ffmpeglogpath = myConfig.getSaveLogPath() + File.separator + channelId + File.separator
				+ DateHelper.getDateDirStr();

		// ........................................................................................................................................//
		// 不需要改
		if (startsize <= 1) {
			cmd.append(myConfig.getFfmpegPath() + "ffmpeg").append(" -safe 0 ").append(" -i \"").append(concat_path)
					.append("\" -t ").append(allsize).append(" -c copy ").append(" -movflags faststart ")
					.append(flvPathReal);

		} else {
			cmd.append(myConfig.getFfmpegPath() + "ffmpeg").append(" -ss ").append(startsize).append(" -safe 0 ")
					.append(" -i \"").append(concat_path).append("\" -t ").append(allsize).append(" -c copy ")
					.append(" -movflags faststart ").append(flvPathReal);

		}

		// 把需要执行的线程放到线程池中
		if (exeService == null) {
			exeService = Executors.newFixedThreadPool(myConfig.getDismantleConcurrent());
		}
		// 执行ffmpeg
		// 执行拆条线程
		exeService.execute(new ExectuerRunnableMultipleRecord(saveBean, myConfig, cmd.toString(), flvPathReal,
				liverecordlistManager, ffmpeglogpath, findByChannelIAll, concat_path));

		return 1;
	}

	public static String IntToDateString(int allSize) {
		int s = (int) allSize % 60;
		int m = (int) Math.floor(allSize / 60);
		int h = (int) Math.floor(m / 60);

		String hh = "" + h;
		String mm = "" + m;
		String ss = "" + s;
		if (h > 0) {
			m = m % 60;
			mm = m + "";
		}
		if (h < 10) {
			hh = "0" + h;
		}
		if (m < 10) {
			mm = "0" + m;
		}
		if (s < 10) {
			ss = "0" + s;
		}

		return hh + ":" + mm + ":" + ss;
	}

	// 删除表Livefilelist过期任务
	@Override
	public Integer ScheduledSqFlv() {

		return updateStartTimeToDisable();
	}

	private Integer updateStartTimeToDisable() {

		Integer flag = 0;
		int count = myConfig.getSrsVideoFailureTime() == null ? 2 : myConfig.getSrsVideoFailureTime();
		logger.info("删除srs video 大于" + count + "天数据");
		try {
			long currentTime = System.currentTimeMillis();
			long time = currentTime - (long) count * 24 * 60 * 60 * 1000;// 默认2天

			String timestr = String.valueOf(time);

			flag = livefilelistManager.updateByStartTime(Constants.ISDELETE_YES, Constants.ISDELETE_NO, timestr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("删除数据count=" + flag);
		return flag;
	}

	// 上报任务
	@Override
	public int ScheduledReportDatas() {

		Integer flag = 0;

		List<V_Livereport> findV_livereport = baseNativeSqlRepository.findV_livereport();

		for (V_Livereport saveBean : findV_livereport) {

			if (saveBean.getReportFailCount() >= 3) {
				// 其他超出最大次数
				continue;
			}

			if (saveBean.getMinfileStatus() <= Constants.TRANS_STATUS_2) {
				// 未完成录制
				continue;
			} else if ((saveBean.getMinfileStatus() == saveBean.getMaxfileStatus())
					&& saveBean.getMinfileStatus() == Constants.TRANS_STATUS_3) {
				// 录制成功
				saveBean.setResultstatus(Constants.CODE_SUCCESS);
				saveBean.setResultsMsg("录制成功");

			} else if (saveBean.getMinfileStatus() == Constants.TRANS_STATUS_4) {
				// 录制失败
				saveBean.setResultstatus(Constants.CODE_FAIL);
				saveBean.setResultsMsg("录制失败");

			} else if (saveBean.getMinfileStatus() == Constants.TRANS_STATUS_5) {
				// 录制文件不存在失败
				saveBean.setResultstatus(Constants.CODE_FAIL);
				saveBean.setResultsMsg("录制失败;视频不存在");
			} else {
				// 其他
				continue;
			}

			// 回调
			ExectuerRunnableCallBack CallBack = new ExectuerRunnableCallBack(liveSourceConfManager, liveReportManager,
					liverecordlistManager, saveBean, baseNativeSqlRepository);
			Thread CallBackThread = new Thread(CallBack);

			CallBackThread.start();

		}

		return flag;
	}

	public static void main(String[] args) {
		// String date = IntToDateString(60*61+1);//01:10:01
		// System.out.println(date);
		// Long timeStrToLong = timeStrToLong("2017-10-13 15:42:19");
		// System.out.println(timeStrToLong);
		//2017-11-16 15:23:37
		//2017-11-16 15:43:37
		
		//2017-11-16 15:23:31
		//2017-11-16 15:38:37
		
		//2017-11-16 15:22:25
		//2017-11-16 15:37:28
		
		long s = Long.parseLong("1510816945559");
		String testlongtostr = testlongtostr(s);
		System.out.println(testlongtostr);
		long s1 = Long.parseLong("1510817848589");
		String testlongtostr1 = testlongtostr(s1);
		System.out.println(testlongtostr1);
		
		Long RecordStartTime = timeStrToLong("2017-11-16 15:23:37");
		
		long startLong = timeLongToLong(Long.parseLong("1510817011786"));

		Long startsize = (RecordStartTime - startLong) / 1000;
		System.out.println(startsize);
		// Integer getMinfileStatus=2;
		// Integer getMaxfileStatus=2;
		// if(getMinfileStatus==getMaxfileStatus){
		// System.out.println("xx");
		// }

	}
}
