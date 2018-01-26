package com.live.recording.manager;



import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.live.recording.domain.entity.Livereport;


public interface LiveReportManager extends JpaRepository<Livereport, Integer> {
	
	@Transactional
	@Modifying
	@Query("update Livereport l set l.callBackStatus=?1,l.reportFailCount=?2,l.remark=?3 where l.videoId=?4")
	Integer updateByReportStatus(Integer reportStatus,Integer reportFailCount,String reportfailMsg,String recordId);
	
	@Transactional
	@Modifying
	@Query("update Livereport l set l.isDelete=?1,l.remark=?2 ,l.lastUpdater=?4,l.lastUpdateTime=?5  where l.videoId in ?3")
	Integer updateByVideoIds(Integer isDelete,String remark,String[] recordId,String lastUpdater,Date lastUpdateTime);
	
	
	@Query("FROM Livereport l WHERE  l.videoId=?1 and isDelete=?2 ")
    Livereport findReportByVideoId(String videoId,Integer isDelete);

}
