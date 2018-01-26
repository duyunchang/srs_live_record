package com.live.recording.manager.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.live.recording.domain.entity.Livecutlist_;
import com.live.recording.domain.entity.Livecutlist;

public class LiveCutListDao {

	// 动态查询 Liverecordlist表 sql
	public static Specification<Livecutlist> getLiverecordlist(final String cutid, final String[] recordid, final Integer isDelete) {
		return new Specification<Livecutlist>() {

			@SuppressWarnings("unused")
			@Override
			public Predicate toPredicate(Root<Livecutlist> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = null;
				if (!StringUtils.isEmpty(cutid)) {

					Predicate p2 = cb.equal(root.get(Livecutlist_.cutId), cutid);
					if (p1 != null) {
						p1 = cb.and(p1, p2);
					} else {
						p1 = p2;
					}
				}
				if (recordid != null && recordid.length > 0) {

					Predicate p2 = cb.and(root.get(Livecutlist_.recordId).in(recordid));
					if (p1 != null) {
						p1 = cb.and(p1, p2);
					} else {
						p1 = p2;
					}
				}

				Predicate p2 = cb.equal(root.get(Livecutlist_.isDelete), isDelete);
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
