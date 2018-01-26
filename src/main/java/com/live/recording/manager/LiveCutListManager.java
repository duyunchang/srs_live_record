package com.live.recording.manager;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.live.recording.domain.entity.Livecutlist;
import com.live.recording.manager.dao.JpaSpecificationExecutor;

public interface LiveCutListManager extends JpaRepository<Livecutlist, Integer>, JpaSpecificationExecutor<Livecutlist> {
	@Query("FROM Livecutlist l WHERE  l.recordId in ?1")
	List<Livecutlist> findByRecordIdIn(List<String> recordIds);
	
	@Query("FROM Livecutlist l WHERE  l.isDelete = ?1 and id=?2")
	Livecutlist findById(Integer isDelete,Integer id);
	
	@Query("FROM Livecutlist l WHERE  l.cutId = ?1")
	List<Livecutlist> findBycutId(String cutId);
	
	
	@Transactional
	@Modifying
	@Query("update Livecutlist l set l.isDelete=?1,l.remark=?2,l.updater=?4 ,l.updateTime=?5 where l.recordId in ?3")
	Integer updateByRecordId(Integer isDelete,String remark,String[] recordId,String lastUpdater,Date lastUpdateTime);
	
	
	@Transactional
	@Modifying
	@Query("update Livecutlist l set l.fileStatus=?1,l.remark=?2 WHERE  l.id=?3")
	Integer updateById(Integer fileStatus,String remark,Integer id);
	
	
	
	@Transactional
	@Modifying
	@Query("update Livecutlist l set l.fileStatus=?1,l.remark=?2 ,l.cutDuration=?3,l.cutPath=?4 WHERE l.id=?5")
	Integer updateByIdrd(Integer fileStatus,String remark,String cutDuration,String cutPath,Integer id);
	
	
}
