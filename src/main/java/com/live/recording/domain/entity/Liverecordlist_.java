package com.live.recording.domain.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Liverecordlist.class)
public class Liverecordlist_ {// 写元数据模型 查询sql条件

	public static volatile SingularAttribute<Liverecordlist, String> channelId;
	public static volatile SingularAttribute<Liverecordlist, String> recordId;
	public static volatile SingularAttribute<Liverecordlist, Integer> isDelete;
	public static volatile SingularAttribute<Liverecordlist, Integer> fileStatus;
}
