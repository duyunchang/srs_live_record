package com.live.recording.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.live.recording.domain.entity.Livequlityconf;
import com.live.recording.domain.vo.input.V_Livecutlist;
import com.live.recording.domain.vo.input.V_Livereport;

/**
 * 使用原生sql语句 用法
 */
@Service
public class BaseNativeSqlRepository {

	@PersistenceUnit(unitName = "default")
	private EntityManagerFactory emf;
	
	public List<String> findLivefilelist(String channelId, Integer isDelete,String startTime, String endTime) {

		EntityManager em = emf.createEntityManager();
		String sql = "";// l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutPath,l.remark,l.fileStatus,l.cutDuration
		// 定义SQL
		sql = "select l.channelId "			
				+ "from livefilelist l where  l.endTime>="+startTime+" and l.startTime<="+endTime+" and l.isDelete="+isDelete+" and l.channelId like '"+channelId+"%' group by l.channelId order by l.startTime asc";

		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> resultList = createNativeQuery.getResultList();

		em.close();

		return resultList;
	}

	public List<String> findLivefilelistGroupBychannelId(String channelId, Integer isDelete) {

		EntityManager em = emf.createEntityManager();
		String sql = "";// l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutPath,l.remark,l.fileStatus,l.cutDuration
		// 定义SQL
		sql = "SELECT channelId "			
				+ "from livefilelist l where  l.isDelete="+isDelete+" and l.channelId like '"+channelId+"%' group by l.channelId";

		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> resultList = createNativeQuery.getResultList();

		em.close();

		return resultList;
	}
	
	
	public List<Object[]> findV_livefilelist(String channelId) {

		EntityManager em = emf.createEntityManager();
		String sql = "";
		// 定义SQL
		if (StringUtils.isEmpty(channelId)) {
			sql = "select l.channelId,l.startTime,l.endTime,l.contentName from v_livefilelist l";
		} else {
			sql = "select l.channelId,l.startTime,l.endTime,l.contentName from v_livefilelist l where l.channelId like '%" + channelId + "%'";
		}
		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = createNativeQuery.getResultList();

		em.close();
		return resultList;
	}

	public List<Object[]> findV_liverecordlist(String channelId) {

		EntityManager em = emf.createEntityManager();
		String sql = "";
		// 定义SQL
		if (StringUtils.isEmpty(channelId)) {
			sql = "select r.channelId,r.recordId,r.recordName,r.recordStartTime,r.recordEndTime,r.recordPath,r.fileStatus from v_liverecordlist r";
		} else {
			sql = "select r.channelId,r.recordId,r.recordName,r.recordStartTime,r.recordEndTime,r.recordPath,r.fileStatus from v_liverecordlist r where  r.channelId='" + channelId + "'";

		}
		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = createNativeQuery.getResultList();

		em.close();
		return resultList;
	}

	public List<V_Livecutlist> findV_livecutlist(String recordId) {

		EntityManager em = emf.createEntityManager();
		String sql = "";// l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutPath,l.remark,l.fileStatus,l.cutDuration
		// 定义SQL
		sql = "select l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutInfo,l.fileStatus from v_livecutlist l where l.recordId='" + recordId + "'";

		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = createNativeQuery.getResultList();

		em.close();

		return livefilelists(resultList);
	}

	public List<V_Livecutlist> findV_livecutlistByCutId(String cutId) {

		EntityManager em = emf.createEntityManager();
		String sql = "";// l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutPath,l.remark,l.fileStatus,l.cutDuration
		// 定义SQL
		// sql="select * from v_livecutlist l where l.cutId in ('"+cutId+"')";
		sql = "select l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutInfo,l.fileStatus from v_livecutlist l where l.cutId ='" + cutId + "'";
		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = createNativeQuery.getResultList();

		em.close();

		return livefilelists(resultList);
	}

	private List<V_Livecutlist> livefilelists(List<Object[]> resultList) {

		List<V_Livecutlist> livefilelists = new ArrayList<>();
		for (Object[] obj : resultList) {
			V_Livecutlist livefilelist = new V_Livecutlist();
			livefilelist.setId(Integer.parseInt(String.valueOf(obj[0])));
			livefilelist.setRecordId(String.valueOf(obj[1]));
			livefilelist.setCutId(String.valueOf(obj[2]));
			livefilelist.setCutName(String.valueOf(obj[3]));

			livefilelist.setCutStartTime(String.valueOf(obj[4]));
			livefilelist.setCutEndTime(String.valueOf(obj[5]));
			livefilelist.setCutInfo(String.valueOf(obj[6]));
			livefilelist.setFileStatus(Integer.parseInt(String.valueOf(obj[7])));

			livefilelists.add(livefilelist);
		}

		return livefilelists;

	}

	public List<V_Livereport> findV_livereport() {

		EntityManager em = emf.createEntityManager();
		String sql = "";// l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutPath,l.remark,l.fileStatus,l.cutDuration
		// 定义SQL
		sql = "select l.id,l.videoId,l.minfileStatus,l.maxfileStatus,l.sourceId,l.sourceName,l.callbackUrl,l.reportFailCount from v_livereport l";
		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = createNativeQuery.getResultList();

		em.close();

		return V_Livereports(resultList);
	}

	private List<V_Livereport> V_Livereports(List<Object[]> resultList) {

		List<V_Livereport> livefilelists = new ArrayList<>();
		for (Object[] obj : resultList) {
			V_Livereport livereport = new V_Livereport();
			livereport.setId(Integer.parseInt(String.valueOf(obj[0])));
			livereport.setVideoId(String.valueOf(obj[1]));
			livereport.setMinfileStatus(Integer.parseInt(String.valueOf(obj[2]==null?"1":obj[2])));
			livereport.setMaxfileStatus(Integer.parseInt(String.valueOf(obj[3]==null?"1":obj[3])));

			livereport.setSourceId(String.valueOf(obj[4]));
			livereport.setSourceName(String.valueOf(obj[5]==null?"":obj[5]));
			livereport.setCallbackUrl(String.valueOf(obj[6]==null?"":obj[6]));
			livereport.setReportFailCount(Integer.parseInt(String.valueOf(obj[7]==null?"0":obj[7])));
			livefilelists.add(livereport);
		}

		return livefilelists;

	}
	
	public List<Livequlityconf> findLivequlityconf(Integer status,Integer isDelete) {

		EntityManager em = emf.createEntityManager();
		String sql = "";// l.id,l.recordId,l.cutId,l.cutName,l.cutStartTime,l.cutEndTime,l.cutPath,l.remark,l.fileStatus,l.cutDuration
		// 定义SQL
		sql = "select *  from livequlityconf l where l.status="+status+" and l.isDelete="+isDelete;

		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql,Livequlityconf.class);
		@SuppressWarnings("unchecked")
		List<Livequlityconf> resultList = createNativeQuery.getResultList();

		em.close();

		return resultList;
	}
	//qq
	public List<String> findV_livefilelistBychannelId(String channelId) {

		EntityManager em = emf.createEntityManager();
		String sql = "";
		// 定义SQL
		
		sql = "select l.channelId from v_livefilelist l where l.channelId = " + channelId ;
		
		// 创建原生SQL查询QUERY实例
		Query createNativeQuery = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> resultList = createNativeQuery.getResultList();

		em.close();
		return resultList;
	}
	
	
	public static void main(String[] args) {
//		Liverecordlist l = new Liverecordlist();
//		System.out.println(l);
		String tmp = null+"";
		System.out.println("null".equals(tmp));
		//System.out.println("null" == tmp);
		//		String s="null";
//		System.out.println(s);
		
		
//		String value = "duration=10";
//		Pattern p = Pattern.compile("duration=(\\d+\\.\\d+)");
//		Matcher m = p.matcher(value);
//		while (m.find()) {
//			tmp = m.group(1);
//		}
//		System.out.println(tmp);
//		if("null".equals(tmp)){
//			System.out.println("in");
//		}
	}
}
