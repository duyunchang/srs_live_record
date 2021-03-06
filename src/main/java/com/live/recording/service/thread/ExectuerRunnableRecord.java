package com.live.recording.service.thread;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.jandex.Main;

import com.live.recording.config.MyConfig;
import com.live.recording.config.CacheMap;
import com.live.recording.constants.Constants;
import com.live.recording.domain.entity.Livefilelist;
import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.manager.LiverecordlistManager;
import com.live.recording.util.ExecRuntime;
import com.live.recording.util.HttpClientUtil;
import com.live.recording.util.WriterFile;

public class ExectuerRunnableRecord implements Runnable {
	private Logger logger = Logger.getLogger(ExectuerRunnableRecord.class);
	private Liverecordlist saveBean;
	private MyConfig myConfig;

	private String cmd;// ffmpeg指令
	private String flvPathReal;// 生成视频地址
	private String ffmpeglogpath;// 生成ffmpeg日志地址
	private LiverecordlistManager liverecordlistManager;

	private List<Livefilelist> findByChannelIAll;
	private String concat_path;
	private Long allsize;

	public ExectuerRunnableRecord() {

	}

	public ExectuerRunnableRecord(Liverecordlist saveBean, MyConfig myConfig, String cmd, String flvPathReal, LiverecordlistManager liverecordlistManager, String ffmpeglogpath, List<Livefilelist> findByChannelIAll, String concat_path, Long allsize) {
		super();
		this.saveBean = saveBean;
		this.myConfig = myConfig;
		this.cmd = cmd;
		this.flvPathReal = flvPathReal;
		this.liverecordlistManager = liverecordlistManager;
		this.ffmpeglogpath = ffmpeglogpath;
		this.findByChannelIAll = findByChannelIAll;
		this.concat_path = concat_path;
		this.allsize = allsize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */// 执行ffmpeg指令
	@Override	
	public void run() {

		boolean executeCmd = false;
		CacheMap cahcheMap = new CacheMap();
		try {

			// 设置缓存
			cahcheMap.setCahche(saveBean.getRecordId(), saveBean.getId() + "", "0");

			//获取当前任务状态
			saveBean = liverecordlistManager.findOne(saveBean.getId());

			if (saveBean.getIsDelete() == Constants.ISDELETE_YES) {
				cahcheMap.setCahche(saveBean.getRecordId(), saveBean.getId() + "", "1");
				
				logger.info("这条数据已经被删除;liverecordlist表;id=" + saveBean.getId());
				return;
			}

			

			// 写concat文件// 创建视频路径和concat文件路径
			String path = WriterFile.createCmdFlv(findByChannelIAll, concat_path, myConfig.getMultipleMountFlvpath());
			if (path == null) {
				logger.info("创建视频存储路径失败;初始化失败");								
				liverecordlistManager.updateById(Constants.TRANS_STATUS_4, "视频存储路径无法;创建视频路径失败;初始化失败", saveBean.getId());
				return;
			}

			// 创建log路径
			if (!WriterFile.createDir(ffmpeglogpath)) {				
				logger.info("日志路径无法创建;录制失败");				
				liverecordlistManager.updateById(Constants.TRANS_STATUS_4, "日志路径无法创建;录制失败;初始化失败", saveBean.getId());
				return;
			};
			// ffmpeg日志存放path
			ffmpeglogpath = ffmpeglogpath + File.separator + saveBean.getRecordId() + saveBean.getId();
			logger.info("ffmpeg转码日志路径ffmpeglogpath=" + ffmpeglogpath);

			
			
			liverecordlistManager.updateById(Constants.TRANS_STATUS_2, "开始录制", saveBean.getId());

			executeCmd = new ExecRuntime().executeCmd(cmd.toString(), myConfig.getTimeouts(), ffmpeglogpath, allsize, cahcheMap, saveBean.getRecordId());

			//日志返回信息
			String readfileALl ="";	
			//读取日志的对象
			WriterFile writerFile = new WriterFile();
						
			if(executeCmd){
				//执行 fcheck 指令
				String fchecklogname="_fcheck_log_" + System.currentTimeMillis();
				String[] fPaths=ffmpeglogpath.split("\\"+File.separator);
				String fPath="";
				for(int l=0;l<fPaths.length-1;l++){
					fPath+=fPaths[l]+File.separator;
				}
				
				//String fchecklogPath = ffmpeglogpath + "_fcheck_log_" + System.currentTimeMillis();
				
				String fcheckResultPath=ffmpeglogpath + "_fcheck_result_" + System.currentTimeMillis();
				
				String FCheckCmd = "python "+myConfig.getFfmpegPath()+"fcheck.py "+flvPathReal+" " + fPath+" "+fPaths[fPaths.length-1]+fchecklogname+" > "+fcheckResultPath; 
				
				executeCmd = new ExecRuntime().executeCmd(FCheckCmd.toString(), myConfig.getTimeouts(), fcheckResultPath, 1, null, null);
				
				writerFile.existsFile(fcheckResultPath);
				readfileALl = writerFile.readfileALl(fcheckResultPath);
				
				if (executeCmd&&readfileALl!=null&&readfileALl.startsWith("0")) {
					
					liverecordlistManager.updateById(Constants.TRANS_STATUS_2, "fcheck成功;"+readfileALl, saveBean.getId());
					
				}else{
					logger.info("fcheck失败;"+readfileALl);
					
					liverecordlistManager.updateById(Constants.TRANS_STATUS_4, "fcheck失败;"+readfileALl, saveBean.getId());
					return;
				}
			
			} else {
				logger.info("cmd执行失败!!");
				
				liverecordlistManager.updateById(Constants.TRANS_STATUS_4, "录制失败;"+readfileALl, saveBean.getId());
				return;
			}
			
			if (executeCmd) {
				logger.info("录制视频成功;开始获取视频duration"); 
				liverecordlistManager.updateById(Constants.TRANS_STATUS_2, "录制视频成功;开始获取视频duration", saveBean.getId());
				// duration指令
				String durationCmd = myConfig.getFfmpegPath() + "ffprobe -v 0 -print_format compact -show_format -show_streams " + flvPathReal;
				// double duration = ReadVideo.getDuration(flvPathReal);
				String durationpath = ffmpeglogpath + "_duration_" + System.currentTimeMillis();

				durationCmd = durationCmd + " > " + durationpath;

				executeCmd = new ExecRuntime().executeCmd(durationCmd.toString(), myConfig.getTimeouts(), durationpath, 1, null, null);
				
					
				if (executeCmd) {
					logger.info("执行视频时间成功");
					// 生成文件慢 需要可能等待1秒
					
					writerFile.existsFile(durationpath);
					
					readfileALl = writerFile.readfileALl(durationpath);
					String duration = writerFile.prasefile(readfileALl);
					
					if (duration != null) {
						saveBean.setRecordDuration(duration);
						saveBean.setFileStatus(Constants.TRANS_STATUS_3);
						saveBean.setRemark("录制成功;"+readfileALl);
						saveBean.setRecordPath(flvPathReal);
					} else {
						// 第二次获取duration
						writerFile.existsFile(durationpath);
						duration = writerFile.readfile(durationpath);
						if (duration != null) {
							saveBean.setRecordDuration(duration );
							saveBean.setFileStatus(Constants.TRANS_STATUS_3);
							saveBean.setRemark("录制成功;"+readfileALl);
							saveBean.setRecordPath(flvPathReal);
						} else {
							saveBean.setFileStatus(Constants.TRANS_STATUS_4);
							saveBean.setRecordDuration(duration);
							saveBean.setRemark("读取生成的Duration失败;"+readfileALl);
							saveBean.setRecordPath(flvPathReal);
						}

					}
				} else {
					logger.info("获取视频时间失败");
					saveBean.setRecordDuration(null);
					saveBean.setFileStatus(Constants.TRANS_STATUS_4);
					saveBean.setRemark("获取视频时间失败;"+readfileALl);
					saveBean.setRecordPath(flvPathReal);

				}
			} else {
				logger.info("cmd执行失败!!");
				saveBean.setRecordDuration(null);
				saveBean.setFileStatus(Constants.TRANS_STATUS_4);
				saveBean.setRemark("录制失败;"+readfileALl);
			}

			
			
			liverecordlistManager.updateByIdrd(saveBean.getFileStatus(), saveBean.getRemark(), saveBean.getRecordDuration(),
					saveBean.getRecordPath() ,saveBean.getId());

		} catch (Exception e) {
			//cahcheMap.setCahche(saveBean.getRecordId(), saveBean.getId() + "", "1");
			e.printStackTrace();
		}finally{
			cahcheMap.setCahche(saveBean.getRecordId(), saveBean.getId() + "", "1");
		}

	}
	
	public static void main(String[] args) {
		String ffmpeglogpath="\\data2\\log/ffmpeglogs/10_21/TVzblztest6_550k/2017/11/21/20171121111859uq05wjc8uaw1818i5l1560";
		String fchecklogname="_fcheck_" + System.currentTimeMillis();
		String[] fPaths=ffmpeglogpath.split("\\/");
		String fPath="";
		for(int l=0;l<fPaths.length-1;l++){
			fPath+=fPaths[l]+"/";
		}
		
		String fcheckPath = ffmpeglogpath + "_fcheck_" + System.currentTimeMillis();
		
		System.out.println(fPath);
		System.out.println(fPaths[fPaths.length-1]+fchecklogname);
	}

}
