package com.live.recording.manager;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.live.recording.domain.entity.Livefilelist;
import com.live.recording.manager.dao.JpaSpecificationExecutor;

public interface LivefilelistManager extends JpaRepository<Livefilelist, Integer>, JpaSpecificationExecutor<Livefilelist> {



	@Query("from Livefilelist l where l.endTime>=?3 and l.startTime<=?4 and l.isDelete=?2 and l.channelId like ?1% group by l.channelId order by l.startTime asc ")
	List<Livefilelist> findByChannelIdLike(String channelId, Integer isDelete, String startTime, String endTime);
	
	@Query("from Livefilelist l where  l.isDelete=?2 and l.channelId like ?1% group by l.channelId order by l.startTime asc ")
	List<Livefilelist> findByChannelIdLike1(String channelId, Integer isDelete);
	
	@Query("from Livefilelist l where l.channelId=?1 and l.isDelete=?2 and l.endTime>=?3 and l.startTime<=?4  order by l.startTime asc")
	List<Livefilelist> findByChannelIAll(String channelId, Integer isDelete, String startTime, String endTime);
		
	@Transactional
	@Modifying
	@Query("update Livefilelist l set l.isDelete=?1 WHERE  l.isDelete=?2 and l.startTime<=?3")
	Integer updateByStartTime(Integer isDelete,Integer notDelete,String nowDate);
	
	
	
}
