package com.live.recording.service;

import java.util.List;

import com.live.recording.domain.entity.Livecutlist;
import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.domain.entity.V_livefilelist;
import com.live.recording.domain.vo.input.LiveRecordListReq;
import com.live.recording.domain.vo.input.LiveFileListDataReq;
import com.live.recording.domain.vo.input.SysReq;
import com.live.recording.domain.vo.input.V_Livecutlist;
import com.live.recording.domain.vo.input.V_MultiplePeriodsRecordsReq;
import com.live.recording.domain.vo.output.LivecutlistResp;
import com.live.recording.domain.vo.output.ProgressResp;

public interface ILiveRecordService {

	
	  
	List<V_livefilelist> findByChannelId(LiveFileListDataReq req);

	List<LivecutlistResp> findLiveRecordByChannelId(LiveRecordListReq req);

	List<Livecutlist> findLiveCutList(LiveFileListDataReq req);
	
	List<V_Livecutlist> findLiveCutList_V(LiveFileListDataReq req);

	List<Liverecordlist> findLiveRecords(LiveRecordListReq req);

	List<Liverecordlist> findLiverecordlistByFileStatus(Integer fileStatus);

	ProgressResp selectProgress(String keyId);
	
	int execCreatVideos(LiveRecordListReq req) throws Exception;
	
	int creatMultiplePeriodsRecordsUP(V_MultiplePeriodsRecordsReq req) throws Exception;
	
	Integer ScheduledSqFlv ();
	
	void ScheduledFlv();

	int creatRecord(LiveRecordListReq req) throws Exception;

	int creatRecordCut(LiveRecordListReq req) throws Exception;

	int saveRecord(SysReq req);

	int deleteLiveCutList(LiveFileListDataReq req);

	int deleteLiveRecordAndLiveCut(LiveRecordListReq req);

	int ScheduledDelFlv();
	
	int ScheduledReportDatas();

}
