package com.live.recording.service.thread;

import java.io.File;

import org.apache.log4j.Logger;

import com.live.recording.config.MyConfig;
import com.live.recording.config.CacheMap;
import com.live.recording.constants.Constants;
import com.live.recording.domain.entity.Livecutlist;
import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.manager.LiveCutListManager;
import com.live.recording.util.ExecRuntime;
import com.live.recording.util.WriterFile;

public class ExectuerRunnableCut implements Runnable {
	private Logger logger = Logger.getLogger(ExectuerRunnableCut.class);
	private Livecutlist saveBean;
	private MyConfig myConfig;

	private String cmd;// ffmpeg指令
	private String flvPathReal;// 生成视频地址
	private String ffmpeglogpath;// 生成ffmpeg日志地址
	private LiveCutListManager liveCutListManager;

	private Liverecordlist findByChannelIAll;
	private String concat_path;
	private long allsize;

	public ExectuerRunnableCut() {
	}

	public ExectuerRunnableCut(Livecutlist saveBean, MyConfig myConfig, String cmd, String flvPathReal, LiveCutListManager liveCutListManager, String ffmpeglogpath, Liverecordlist findByChannelIAll, String concat_path, long allsize) {
		super();
		this.saveBean = saveBean;
		this.myConfig = myConfig;
		this.cmd = cmd;
		this.flvPathReal = flvPathReal;
		this.ffmpeglogpath = ffmpeglogpath;
		this.liveCutListManager = liveCutListManager;
		this.findByChannelIAll = findByChannelIAll;
		this.concat_path = concat_path;
		this.allsize = allsize;
	}

	@Override
	// 执行ffmpeg指令
	public void run() {
		boolean executeCmd = false;
		CacheMap cahcheMap = new CacheMap();
		try {
			// 设置缓存
			
			cahcheMap.setCahche(saveBean.getCutId(), saveBean.getId() + "", "0");
			
			
			saveBean = liveCutListManager.findOne(saveBean.getId());
			if (saveBean.getIsDelete()==Constants.ISDELETE_YES) {
				//cahcheMap.setCahche(saveBean.getCutId(), saveBean.getId() + "", "1");
				
				logger.info("这条数据已经被删除;livecutlist表;id=" + saveBean.getId());
				return;
			}

			// 写concat文件// 创建视频路径和concat文件路径
			String createCmdCutFlv = WriterFile.createCmdCutFlv(findByChannelIAll, concat_path);
			if (createCmdCutFlv == null) {
				logger.info("创建视频存储路径失败;初始化失败");
							
				liveCutListManager.updateById(Constants.TRANS_STATUS_4, "视频存储路径无法;创建视频路径失败;初始化失败", saveBean.getId());
				return;
			}

			// 创建log路径
			if (!WriterFile.createDir(ffmpeglogpath)) {
				
				logger.info("日志路径无法创建;录制失败");				
				liveCutListManager.updateById(Constants.TRANS_STATUS_4, "日志路径无法创建;录制失败;初始化失败", saveBean.getId());
				return;
			}
			;
			ffmpeglogpath = ffmpeglogpath + File.separator + saveBean.getCutId() + saveBean.getId();
			// 更新数据库状态
			
			liveCutListManager.updateById(Constants.TRANS_STATUS_2, "开始录制", saveBean.getId());
			
			executeCmd = new ExecRuntime().executeCmd(cmd.toString(), myConfig.getTimeouts(), ffmpeglogpath, allsize, cahcheMap, saveBean.getCutId());
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
					
					liveCutListManager.updateById(Constants.TRANS_STATUS_2, "fcheck成功;"+readfileALl, saveBean.getId());
					
				}else{
					logger.info("fcheck失败;"+readfileALl);
					
					liveCutListManager.updateById(Constants.TRANS_STATUS_4, "fcheck失败;"+readfileALl, saveBean.getId());
					return;
				}
			
			} else {
				logger.info("cmd执行失败!!");
				
				liveCutListManager.updateById(Constants.TRANS_STATUS_4, "录制失败;"+readfileALl, saveBean.getId());
				return;
			}
			
			
			if (executeCmd) {

				logger.info("拆条成功");
				// duration指令
				String durationCmd = myConfig.getFfmpegPath() + "ffprobe -v 0 -print_format compact -show_format -show_streams " + flvPathReal;
				// double duration = ReadVideo.getDuration(flvPathReal);
				String durationpath = ffmpeglogpath + "_duration_" + System.currentTimeMillis();

				durationCmd = durationCmd + " > " + durationpath;

				executeCmd = new ExecRuntime().executeCmd(durationCmd.toString(), myConfig.getTimeouts(), durationpath, 1, null, null);
				if (executeCmd) {
					logger.info("执行视频时间成功");
					// 生成文件慢 需要可能等待1秒

					//WriterFile writerFile = new WriterFile();
					writerFile.existsFile(durationpath);
					
					readfileALl = writerFile.readfileALl(durationpath);
					String duration = writerFile.prasefile(readfileALl);
					
					
					if (duration != null) {
						saveBean.setCutDuration(duration );
						saveBean.setFileStatus(Constants.TRANS_STATUS_3);
						saveBean.setRemark("录制成功;"+readfileALl);
						saveBean.setCutPath(flvPathReal);
					} else {
						// 第二次获取duration
						writerFile.existsFile(durationpath);
						duration = writerFile.readfile(durationpath);
						if (duration != null) {
							saveBean.setCutDuration(duration);
							saveBean.setFileStatus(Constants.TRANS_STATUS_3);
							saveBean.setRemark("录制成功;"+readfileALl);
							saveBean.setCutPath(flvPathReal);
						} else {
							saveBean.setFileStatus(Constants.TRANS_STATUS_4);
							saveBean.setCutDuration(duration );
							saveBean.setRemark("读取生成的Duration失败;"+readfileALl);
							saveBean.setCutPath(flvPathReal);
						}

					}
				} else {
					logger.info("获取视频时间失败");
					saveBean.setCutDuration(null);
					saveBean.setFileStatus(Constants.TRANS_STATUS_4);
					saveBean.setRemark("获取视频时间失败;"+readfileALl);
					saveBean.setCutPath(flvPathReal);

				}

			} else {
				logger.info("cmd执行失败!!");
				saveBean.setCutDuration(null);
				saveBean.setFileStatus(Constants.TRANS_STATUS_4);
				saveBean.setRemark("录制失败;"+readfileALl);
			}

			//liveCutListManager.saveAndFlush(saveBean);
			liveCutListManager.updateByIdrd(saveBean.getFileStatus(),saveBean.getRemark(),saveBean.getCutDuration(),saveBean.getCutPath(),saveBean.getId());

		} catch (Exception e) {
			//cahcheMap.setCahche(saveBean.getCutId(), saveBean.getId() + "", "1");
			e.printStackTrace();
		}finally{
			cahcheMap.setCahche(saveBean.getCutId(), saveBean.getId() + "", "1");
		}

	}

}
