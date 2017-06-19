package com.bhz.util.PageUtils;

import com.bhz.init.InitDataListener;

public interface PagingFactory {
	/**  
     * @project paging  
     * @desc 查询对象条数  
     * @param page 分页对象  
     * @return  
     */
    public Integer queryObjCount(Page page);
    /**  
     * @project paging  
     * @desc 查询对象  
     * @param page 分页对象  
     * @return  
     */
    public Page queryObjList(Page page);
    /**  
     * @project paging  
     * @desc SQL查询对象条数  
     * @param page 分页对象  
     * @return  
     */
    public Integer queryObjCountBySql(Page page);
    /**  
     * @project paging  
     * @desc SQL查询对象  
     * @param page 分页对象  
     * @return  
     */
    public Page queryObjListBySql(Page page);

    /**
     * 查询车载累积数据
     * @param page
     * @return
     */
    public Page queryObjCzListBySql(Page page);

    /**
     * 查询车载累积数据条数
     * @param page
     * @return
     */
    public Integer queryObjCzCountBySql(Page page);
}
