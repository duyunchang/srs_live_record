package com.live.recording.manager.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.domain.entity.Liverecordlist_;

public class LiverecordlistDao {

	// 动态查询 Liverecordlist表 sql
	public static Specification<Liverecordlist> getLiverecordlist(final String channelid, final String recordid, final Integer isDelete) {
		return new Specification<Liverecordlist>() {

			@SuppressWarnings("unused")
			@Override
			public Predicate toPredicate(Root<Liverecordlist> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = null;
				if (!StringUtils.isEmpty(channelid)) {

					Predicate p2 = cb.equal(root.get(Liverecordlist_.channelId), channelid);
					if (p1 != null) {
						p1 = cb.and(p1, p2);
					} else {
						p1 = p2;
					}
				}
				if (!StringUtils.isEmpty(recordid)) {

					Predicate p2 = cb.equal(root.get(Liverecordlist_.recordId), recordid);
					if (p1 != null) {
						p1 = cb.and(p1, p2);
					} else {
						p1 = p2;
					}
				}

				Predicate p2 = cb.equal(root.get(Liverecordlist_.isDelete), isDelete);
				if (p1 != null) {
					p1 = cb.and(p1, p2);
				} else {
					p1 = p2;
				}

				//query.groupBy("");
				return p1;
			}
		};
	}

	// 动态查询 Liverecordlist表 sql
	public static Specification<Liverecordlist> getLiverecordlistByFileStatus(final Integer fileStatus, final Integer isDelete) {
		return new Specification<Liverecordlist>() {

			@SuppressWarnings("unused")
			@Override
			public Predicate toPredicate(Root<Liverecordlist> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = null;
				if (fileStatus != 0) {

					Predicate p2 = cb.equal(root.get(Liverecordlist_.fileStatus), fileStatus);
					if (p1 != null) {
						p1 = cb.and(p1, p2);
					} else {
						p1 = p2;
					}
				}

				Predicate p2 = cb.equal(root.get(Liverecordlist_.isDelete), isDelete);
				if (p1 != null) {
					p1 = cb.and(p1, p2);
				} else {
					p1 = p2;
				}

				return p1;
			}
		};
	}

}
