package com.live.recording.manager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.manager.dao.JpaSpecificationExecutor;

public interface LiverecordlistManager extends JpaRepository<Liverecordlist, Integer>, JpaSpecificationExecutor<Liverecordlist> {

	//@Query("FROM Liverecordlist l WHERE  l.isDelete=?1 or l.recordStartTime>=?2")
	@Query("FROM Liverecordlist l WHERE  l.isDelete=?1 or l.recordStartTime<=?2")
	List<Liverecordlist> findByAll(Integer isDelete,String time);
	
	@Query("FROM Liverecordlist l WHERE  l.isDelete=?1 and fileStatus=?3  and l.id=?2")
	Liverecordlist findById(Integer isDelete,Integer id,Integer fileStatus);

	@Query("FROM Liverecordlist l WHERE  l.isDelete=?1 and l.fileStatus=?2 and l.remark !=?3")
	List<Liverecordlist> findByFileStatus(Integer noDelete,Integer fileStatus,String locking);
	
	
	@Transactional
	@Modifying
	@Query("update Liverecordlist l set l.isDelete=?1 WHERE  l.isDelete=?2 and l.recordStartTime<=?3")
	Integer updateByStartTime(Integer isDelete,Integer notDelete,String nowDate);
	
	@Transactional
	@Modifying
	@Query("update Liverecordlist l set l.fileStatus=?1,l.remark=?2 WHERE  l.id=?3")
	Integer updateById(Integer fileStatus,String remark,Integer id);
	
	@Transactional
	@Modifying
	@Query("update Liverecordlist l set l.fileStatus=?1 WHERE l.id=?2")
	Integer updateFileStatusById(Integer fileStatus,Integer id);
	
	
	@Transactional
	@Modifying
	@Query("update Liverecordlist l set l.fileStatus=?1,l.remark=?2 ,l.recordDuration=?3,l.recordPath=?4 WHERE l.id=?5")
	Integer updateByIdrd(Integer fileStatus,String remark,String recordDuration,String recordPath,Integer id);
	
	@Query("FROM Liverecordlist l WHERE  l.recordId=?1 ")
	List<Liverecordlist> findReport(String recordId);
	
	
	
}
