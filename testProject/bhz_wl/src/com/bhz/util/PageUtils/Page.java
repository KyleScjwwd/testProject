package com.bhz.util.PageUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bhz.pojo.TbBase;

public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 每页显示条数 默认显示10条
	 */
	private Integer pageSize = 10;

	/**
	 * 总条数
	 */
	private Integer totalCount = 0;

	/**
	 * 总页数
	 */
	private Integer totalPage = 0;

	/**
	 * 当前页
	 */
	private Integer currentPage = 1;

	/**
	 * 起始索引
	 */
	private Integer startIndex = 0;

	/**
	 * 分页条件
	 */
	private String condition;

	/**
	 * 查询的返回值
	 */
	private List<?> objList;
	
	/**
	 * 查询的返回值
	 */
	private Map<String,List<TbBase>> baseMap;
	
	/**
	 * 查询的返回值
	 */
	private Map<String,List<Object[]>> baseObj;
	
	/**
	 * 排序
	 */
	private String orderBy;

	/**
	 * 查询对象名称或表名/或查询语句
	 */
	private String objName;

	/**
	 * 需要查询的字段
	 */
	private String queryField;

	public Page(Integer totalCount, List<?> objList, Integer currentPage) {
		super();
		this.totalCount = totalCount;
		this.objList = objList;
		this.currentPage = currentPage;
	}

	public Page(Integer pageSize, String condition, List<?> objList,
			String orderBy, String objName) {
		super();
		this.pageSize = pageSize;
		this.condition = condition;
		this.objList = objList;
		this.orderBy = orderBy;
		this.objName = objName;
	}

	public Page(Integer pageSize, String objName) {
		super();
		this.pageSize = pageSize;
		this.objName = objName;
	}

	public Page() {
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		totalPage = totalCount % pageSize == 0 ? (totalCount / pageSize)
				: (totalCount / pageSize + 1);
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		if (currentPage <= 0)
			currentPage = 1;
		this.currentPage = currentPage;
	}

	public Integer getStartIndex() {
		startIndex = this.pageSize * (this.currentPage - 1);
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public List<?> getObjList() {
		return objList;
	}

	public void setObjList(List<?> objList) {
		this.objList = objList;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getQueryField() {
		return queryField;
	}

	public void setQueryField(String queryField) {
		this.queryField = queryField;
	}

	public Map<String, List<TbBase>> getBaseMap() {
		return baseMap;
	}

	public void setBaseMap(Map<String, List<TbBase>> baseMap) {
		this.baseMap = baseMap;
	}

	public Map<String, List<Object[]>> getBaseObj() {
		return baseObj;
	}

	public void setBaseObj(Map<String, List<Object[]>> baseObj) {
		this.baseObj = baseObj;
	}
}
