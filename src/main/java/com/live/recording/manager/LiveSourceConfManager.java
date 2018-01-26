package com.live.recording.manager;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.live.recording.domain.entity.LiveSourceConf;


public interface LiveSourceConfManager extends JpaRepository<LiveSourceConf, Integer> {
	
	@Query("FROM LiveSourceConf l WHERE  l.sourceCode=?1 and l.isDelete=?2 and l.status=?3")
	LiveSourceConf findBySourceCode(String sourceCode ,Integer isDelete,Integer status);
	
	@Query("FROM LiveSourceConf l WHERE  l.id=?1 and l.isDelete=?2 and l.status=?3")
	LiveSourceConf findById(Integer sourceCode ,Integer isDelete,Integer status);
}
