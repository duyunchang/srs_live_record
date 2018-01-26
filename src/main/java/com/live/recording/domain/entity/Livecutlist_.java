package com.live.recording.domain.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Livecutlist.class)
public class Livecutlist_ {// 写元数据模型 查询sql条件

	public static volatile SingularAttribute<Livecutlist, String> cutId;
	public static volatile SingularAttribute<Livecutlist, String> recordId;
	public static volatile SingularAttribute<Livecutlist, Integer> isDelete;

}
