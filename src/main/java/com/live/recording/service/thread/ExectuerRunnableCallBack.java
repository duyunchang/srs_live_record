package com.live.recording.service.thread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.live.recording.constants.Constants;
import com.live.recording.domain.entity.LiveSourceConf;
import com.live.recording.domain.entity.Livequlityconf;
import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.domain.vo.input.V_Livereport;
import com.live.recording.domain.vo.output.CallBack;
import com.live.recording.domain.vo.output.ResultProxy;
import com.live.recording.domain.vo.output.Videos;
import com.live.recording.manager.BaseNativeSqlRepository;
import com.live.recording.manager.LiveReportManager;
import com.live.recording.manager.LiveSourceConfManager;
import com.live.recording.manager.LiverecordlistManager;

import com.live.recording.util.*;

public class ExectuerRunnableCallBack implements Runnable {
	private Logger logger = Logger.getLogger(ExectuerRunnableCallBack.class);
	private LiveSourceConfManager liveSourceConfManager;

	private LiveReportManager liveReportManager;

	private LiverecordlistManager liverecordlistManager;

	public BaseNativeSqlRepository baseNativeSqlRepository;
	
	private V_Livereport saveBean;
	

	public ExectuerRunnableCallBack() {

	}

	public ExectuerRunnableCallBack(LiveSourceConfManager liveSourceConfManager, LiveReportManager liveReportManager,
			LiverecordlistManager liverecordlistManager, V_Livereport saveBean,BaseNativeSqlRepository baseNativeSqlRepository) {
		super();
		this.liveSourceConfManager = liveSourceConfManager;
		this.liveReportManager = liveReportManager;
		this.liverecordlistManager = liverecordlistManager;
		this.saveBean = saveBean;
		this.baseNativeSqlRepository=baseNativeSqlRepository;
	}

	@Override
	public void run() {
		logger.info("start 上报数据");
		int fallCount = saveBean.getReportFailCount();
		String recordId= saveBean.getVideoId();
		try {
			
			List<Liverecordlist> findReport = liverecordlistManager.findReport(recordId);
			
			liveReportManager.updateByReportStatus(Constants.reportstatus_1, fallCount, "开始上报", recordId);
			
			// 填充返回值
			CallBack respBean = getRespBean(findReport, saveBean);

			// 设置回调url
			String httpUrl = saveBean.getCallbackUrl();
			// 设置回调参数
			String paramsJson = JsonHelper.toJsonStr(respBean);

			logger.info("上报数据="+paramsJson);
			
			String sendHttpPostJson = HttpClientUtil.sendHttpPostJson(httpUrl, paramsJson);
			logger.info("回调返回信息="+sendHttpPostJson);
			ResultProxy bean = JsonHelper.toJSONObject(sendHttpPostJson, ResultProxy.class);

			if (bean != null && bean.getCode() == Constants.CODE_SUCCESS) {
					liveReportManager.updateByReportStatus(Constants.reportstatus_2, fallCount, "上报成功", recordId);
					
			} else {
				fallCount++;

				if (bean == null) {
					logger.info("上报失败次数=" + fallCount + ";失败原因=返回为空");
					liveReportManager.updateByReportStatus(Constants.reportstatus_3, fallCount, "失败原因;返回为空或返回字符串无法解析;返回内容=" + sendHttpPostJson, recordId);
				} else {
					logger.info("上报失败次数=" + fallCount + "返回内容=" + sendHttpPostJson);
				    liveReportManager.updateByReportStatus(Constants.reportstatus_3, fallCount, bean.getMsg()+";", recordId);
				}
			}

		} catch (Exception e) {
			liveReportManager.updateByReportStatus(Constants.reportstatus_3, fallCount, e.getLocalizedMessage()+";", recordId);
			e.printStackTrace();
		}finally{
			logger.info("end 上报数据");
			Thread.currentThread().interrupt();
		}

	}

	// 填充返回值
	private CallBack getRespBean(List<Liverecordlist> findReport, V_Livereport saveBean) {
		CallBack call = new CallBack();

		List<Livequlityconf> qulityconf = baseNativeSqlRepository.findLivequlityconf(Constants.STATUS_YES, Constants.ISSTOP_NO);
		
		List<Videos> videos = new ArrayList<>();
		call.setVideos(videos);
		for (Liverecordlist baens : findReport) {
		
			call.setCode(saveBean.getResultstatus());
			call.setMsg(saveBean.getResultsMsg());		
			call.setRecordId(baens.getRecordId());
			
			
			call.setVideos(getBean(baens,qulityconf,call.getVideos()));

		}

		return call;
	}

	//视频参数设置
	private List<Videos> getBean(Liverecordlist baens,List<Livequlityconf> qulityconf,List<Videos> videos) {

		String recordPath = baens.getRecordPath()==null?"": baens.getRecordPath();
	
		Videos videoInfo = new Videos();

		// 视频相对路径
		LiveSourceConf findBySourceCode = liveSourceConfManager.findOne(Integer.parseInt(saveBean.getSourceId()));
		
		String filepath = recordPath.replaceFirst(findBySourceCode.getSavePath(), "");
		videoInfo.setFilepath(filepath);
		// 视频码率
		String[] split = baens.getChannelId().split("\\_");
		String bitrate = split[split.length - 1].toLowerCase().replace("k", "");
		
		videoInfo.setBitrate(StringUtils.isNumeric(bitrate)?Integer.parseInt(bitrate):null);
		// 视频时间duration
		String duration = baens.getRecordDuration();
		if("null".equals(duration)||duration==null||"".equals(duration)){
			duration="0";
		}
		videoInfo.setDuration((int) Float.parseFloat(duration));
		// format视频格式
		String[] split2 = recordPath.split("\\.");
		String format = split2[split2.length - 1];
		videoInfo.setFormat(format);
		// filesize 视频大小
		try {				
			
			videoInfo = setWidthAndHeightAndFileSize(baens, videoInfo);

		} catch (IOException e) {
			e.printStackTrace();
		}
		int count=0;
		for(Livequlityconf qulity:qulityconf){
			count++;
			Integer qulityRangeHigh =Integer.parseInt( qulity.getQulityRangeHigh());
			Integer qulityRangeLow = Integer.parseInt(qulity.getQulityRangeLow());
			Integer bitrate2 = videoInfo.getBitrate();
			if(bitrate2!=null&&bitrate2>=qulityRangeLow&&bitrate2<=qulityRangeHigh){
				videoInfo.setQulityId(Integer.parseInt(qulity.getQulityCode()));
								
				break;
			}else{
				videoInfo.setQulityId(count);
			}
			
		}
		
		if(videos.size()==0){
			videos.add(videoInfo);
		}else{
		
			//清晰度一样 取最大返回
			for(int i=0;i<videos.size();i++ ){
				if(videos.get(i).getQulityId()==videoInfo.getQulityId()){
					if(videoInfo.getBitrate()<=videos.get(i).getBitrate()){					
						return videos;
					}			
					videos.remove(i);
					break;
					
											
				}
			}
							
			videos.add(videoInfo);
			
		}

		return videos;
	}
	
	public  Videos setWidthAndHeightAndFileSize(Liverecordlist baens ,Videos videoInfo) throws IOException {
		String content = baens.getRemark();
		//|width=1024|height=768|
		Pattern p = Pattern.compile("\\|width=(\\d+)\\|height=(\\d+)\\|");
		Matcher m = p.matcher(content);
		String width="0";
		String higth="0";
		while (m.find()) {
			width = m.group(1);
			higth = m.group(2);
			break;
		}
		
		
		videoInfo.setWidth(Integer.parseInt(width));
		videoInfo.setHeight(Integer.parseInt(higth));
		
		//|duration=600.032000|size=45413656|
		Pattern p1 = Pattern.compile("\\|duration=\\d+\\.\\d+\\|size=(\\d+)\\|bit_rate=(\\d+)\\|");
		Matcher m1 = p1.matcher(content);
		String size="0";
		String bit_rate="";
		while (m1.find()) {
			size = m1.group(1);
			bit_rate = m1.group(2);
			break;
		}
		
		//凑成一个整数
		if(videoInfo.getBitrate()==null&&bit_rate!=null&&!"".equals(bit_rate)){
			bit_rate=Integer.parseInt(bit_rate)/1024+"";
			
//			String first = bit_rate.substring(0, 1);
//			for(int k=0;k<bit_rate.length()-1;k++){
//				String substring = bit_rate.substring(k,k+1);
//				int value = Integer.parseInt(substring);
//				if(value>=5){
//					first+="5"; 
//				}else{
//					first+="0";
//				}
//			}
//			
//			videoInfo.setBitrate(Integer.parseInt(first));
			videoInfo.setBitrate(Integer.parseInt(bit_rate));
		}else{
			
			if(videoInfo.getBitrate()==null){
				videoInfo.setBitrate(0);
			}
		}
		
		
		videoInfo.setFilesize(Long.parseLong(size));
		
		//视频格式
		//stream|index=0|codec_name=h264|codec_long_name=H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10|profile=Main|codec_type=video|
		Pattern p2 = Pattern.compile("\\|codec_name=(.+?)\\|codec_long_name=.+?\\|codec_type=video\\|");
		Matcher m2 = p2.matcher(content);
		String vcodec="0";
		while (m2.find()) {
			vcodec = m2.group(1);
			break;
		}
		videoInfo.setVcodec(vcodec);
		//音频格式 可能会有多个 用逗号隔开
		//stream|index=1|codec_name=aac|codec_long_name=AAC (Advanced Audio Coding)|profile=LC|codec_type=audio|codec_time_base=1/44100|codec_tag_string=mp4a|codec		
		Pattern p3 = Pattern.compile("\\|codec_name=(.+?)\\|codec_long_name=.+?\\|codec_type=audio\\|");
		Matcher m3 = p3.matcher(content);
		String acodec="";
		while (m3.find()) {
			acodec = m3.group(1)+" "+acodec;
	
		}
		videoInfo.setAcodec(acodec.trim().replace(" ", ","));
		
		return videoInfo;
    }
	
	
	public static long sizeOf(File file) throws IOException {
		  return Files.asByteSource(file).size();
    }
	
	public static void main(String[] args) {
		String bit_rate="706041";
		bit_rate=Integer.parseInt(bit_rate)/1024+"";
		
		String first = bit_rate.substring(0, 1);
		for(int k=0;k<bit_rate.length()-1;k++){
			String substring = bit_rate.substring(k,k+1);
			int value = Integer.parseInt(substring);
			
			if(value>=5){
				first+="5"; 
			}else{
				first+="0";
			}
		}
		
		System.out.println(first);
	}
	
}
