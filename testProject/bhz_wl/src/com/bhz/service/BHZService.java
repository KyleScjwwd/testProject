package com.bhz.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.bhz.dao.TbBaseDao;
import com.bhz.dao.TbDesignPhbDao;
import com.bhz.dao.TdMergeColsOrderDao;
import com.bhz.pojo.TbBase;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;

public class BHZService extends BaseService{
	private TbBaseDao tbBaseDao;
	private TbDesignPhbDao tbDesignPhbDao;
	private TdMergeColsOrderDao tdMergeColsOrderDao;
	private PagingFactory pageDao;
	
	public void setTbBaseDao(TbBaseDao tbBaseDao) {
		this.tbBaseDao = tbBaseDao;
	}
	public void setTbDesignPhbDao(TbDesignPhbDao tbDesignPhbDao) {
		this.tbDesignPhbDao = tbDesignPhbDao;
	}
	public void setTdMergeColsOrderDao(TdMergeColsOrderDao tdMergeColsOrderDao) {
		this.tdMergeColsOrderDao = tdMergeColsOrderDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	
	public Page getBhzByPage(HttpServletRequest request,Page page) throws Exception {
		if(page==null)
			page = new Page();
		String hql = "TbBhz where 1=1";
		String username = Util.getUserName(request);
		if(!"admin".equals(username)){
			Map<String,String> filterData = getBdBhzFilterAuth(request);
			String bdIn = filterData.get("bdIn");
			if(!Util.isEmpty(bdIn)){
				hql+=" and bdCode in ("+bdIn+")";
			}else{
				hql+=" and 1=2";
			}
			String bhzIn = filterData.get("bhzIn");
			if(!Util.isEmpty(bhzIn)){
				hql+=" and bhzCode in ("+bhzIn+")";
			}else{
				hql+=" and 1=2";
			}
		}
		page.setObjName(hql);
		page.setOrderBy("bhzCode");
		return pageDao.queryObjList(page);
	}

	public void addBHZ(TbBhz model) throws Exception {
		if(Util.isEmpty(model.getBhzCode()))
			return;
		boolean flag = isBhzCode(model.getBhzCode());
		if(flag)
			return;
		this.tbBhzDao.insert(model);
		setBhzCache();
	}

	public TbBhz getBhzInfo(String bdCode,String bhzCode,String pwd) throws Exception {
		String hql="from TbBhz where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and ext1='"+pwd+"' ";
		List<TbBhz> tbBhzs=tbBhzDao.queryByHql(hql);
		return tbBhzs!=null&&tbBhzs.size()>0?tbBhzs.get(0):null;
	}

	public boolean isBhzCode(String bhzCode)throws Exception{
		if(Util.isEmpty(bhzCode))
			return true;
		String hql = "from TbBhz where bhzCode='"+bhzCode+"'";
		List<TbBhz> list = tbBhzDao.queryByHql(hql);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	//查出目前数据库里最大的bhzCode
	private String getMaxBHZCode() throws Exception {
		String hql = "select max(convert(int,b.bhzCode)) from TbBhz b";
		List queryByHql = this.tbBhzDao.queryBySql(hql);
		//当数据库还没有一条拌合站记录时，queryByHql.get(0)等于null
		if (queryByHql.get(0) == null) {
			return "0";
		} else {
			return queryByHql.get(0).toString();
		}
	}

	public TbBhz getBHZById(Integer id) throws Exception {
		return this.tbBhzDao.queryById(id);
	}

	public void updateBHZ(TbBhz model) throws Exception {
		if(model.getId()==null)
			return;
		TbBhz bhz = getBHZById(model.getId());
		if(bhz==null)
			return;
		bhz.setBhzName(model.getBhzName());
		bhz.setUpNum(model.getUpNum());
		bhz.setDownNum(model.getDownNum());
		bhz.setExt1(model.getExt1());
		bhz.setCjParam(model.getCjParam());
		bhz.setCjStartTime(model.getCjStartTime());
		this.tbBhzDao.update(bhz);
		setBhzCache();
	}

	public void deleteBhz(TbBhz bhz) throws Exception {
		if(bhz.getId()==null)
			return;
		TbBhz obj = tbBhzDao.queryById(bhz.getId());
		String bdCode = obj.getBdCode();
		String bhzCode = obj.getBhzCode();
		tbBhzDao.delete(obj);
		tbBaseDao.deleteBySql("delete from TbBase where m2 = '" + bdCode + "' and m6 = '" + bhzCode + "'");
		tbDesignPhbDao.deleteBySql("delete from TbDesignPhb where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'");
		tbBaoJingMainDao.deleteBySql("delete from TbBaoJingMain where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'");
		tbBaoJingMainMergeDao.deleteBySql("delete from TbBaoJingMainMerge where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'");
		tbBaoJingSubDao.deleteBySql("delete from TbBaoJingSub where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'");
		tbBaoJingSubMergeDao.deleteBySql("delete from TbBaoJingSubMerge where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'");
		tbBaseDao.deleteBySql("delete from tbBaoJingPersonBase where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'");	//删除报警基本人员信息配置表
		//更新报警设置缓存
		setBaoJingCache();
		setBaoJingCacheMerge();
		setBhzCache();
	}

	public Page getBHZsByMultiFields(HttpServletRequest request,Page page,TbBhz model) throws Exception {
		if(page==null)
			page = new Page();
		String bdCode = model.getBdCode();
		String bhzName = model.getBhzName();
		String upNum = model.getUpNum();
		String downNum = model.getDownNum();
		
		String hql = "TbBhz b where 1=1";
		if (!Util.isEmpty(bdCode)) {
			hql = hql + " and b.bdCode = '" + bdCode + "'";
		}
		if (!Util.isEmpty(bhzName)) {
			hql = hql + " and b.bhzName like '%" + bhzName + "%'";
		}
		if (!Util.isEmpty(upNum)) {
			hql = hql + " and b.upNum = '" + upNum + "'";
		}
		if (!Util.isEmpty(downNum)) {
			hql = hql + "b.downNum = '" + downNum + "'";
		}
		String username = Util.getUserName(request);
		if(!"admin".equals(username)){
			Map<String,String> filterData = getBdBhzFilterAuth(request);
			String bdIn = filterData.get("bdIn");
			if(!Util.isEmpty(bdIn)){
				hql+=" and b.bdCode in ("+bdIn+")";
			}else{
				hql+=" and 1=2";
			}
			String bhzIn = filterData.get("bhzIn");
			if(!Util.isEmpty(bhzIn)){
				hql+=" and b.bhzCode in ("+bhzIn+")";
			}else{
				hql+=" and 1=2";
			}
		}
		page.setObjName(hql);
		page.setOrderBy("bhzCode");
		return pageDao.queryObjList(page);
	}

	public void deleteBhzByBdCode(String bdCode) throws Exception {
		String hql = "delete from TbBhz b where b.bdCode = '" + bdCode + "'";
		this.tbBhzDao.deleteByHql(hql);
		setBhzCache();
	}
	
	public void updateClientVersionByCode(String bdCode, String bhzCode, String clientVersion,String cjParam,String lastCollTime,String lastOnlineTime) throws Exception {
		String hql = "update TbBhz set clientVersion = '" + clientVersion + "',cjParam='"+cjParam+"',lastOnlineTime='"+lastOnlineTime+"',lastCollTime='"+lastCollTime+"' where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'";
		this.tbBhzDao.updateClientVersion(hql);
	}

	public String getClDateByGroupId(String bdCode,String bhzCode,String groupId)
		throws Exception{
		String result = "无记录";
		String hql = "from TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"' and groupId='"+groupId+"'";
		List<TbBase> list = tbBaseDao.queryByHql(hql);
		for(TbBase t:list){
			if(t.getM4()!=null)
				//result = Util.getPatternDate(t.getM4(), "yyyy-MM-dd HH:mm:ss");
			result=t.getM4();
		}
		return result;
	}
	
	public String getMaxGroupId(String bdCode,String bhzCode)throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return null;
		String sql = "select max(convert(decimal,groupId)) from TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"'";
		List list = tbBaseDao.queryBySql(sql);
		if(list!=null && list.size()>0 && list.get(0)!=null)
			return list.get(0).toString();
		return null;
	}

	public void deleteDataByGroupId(String bdCode,String bhzCode,
									String maxCode,String custCode)throws Exception{
		String hql = "delete from TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"' and " +
				"convert(bigint,groupId)>"+custCode;
		tbBaseDao.deleteByHql(hql);
	}

	public void deleteDataBycjTime(String bdCode,String bhzCode,String custCjStartTime) throws Exception {
		String hql = "delete from TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"' and " +
				"m4>='"+custCjStartTime+"'";
		tbBaseDao.deleteByHql(hql);
	}

	public void setCjTime(String bdCode,String bhzCode,String custCjStartTime) throws Exception {
		String hql="update TbBhz set cjStartTime='"+custCjStartTime+"' where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		tbBhzDao.updateByHql(hql);
	}

	public String updateBhzIsMerge(String id,String flag)throws Exception{
		if(Util.isEmpty(id) || Util.isEmpty(flag))
			return "0";
		TbBhz t = tbBhzDao.queryById(Integer.valueOf(id));
		if(t==null)
			return "0";
		String hql = "from TdMergeColsOrder where bdCode='"+t.getBdCode()+"' and bhzCode='"+t.getBhzCode()+"'";
		List<TdMergeColsOrder> list = tdMergeColsOrderDao.queryByHql(hql);
		if(list.size()>0){
			if(flag.equals("false"))
				t.setMergeClos("1");
			else
				t.setMergeClos(null);
			tbBhzDao.update(t);
			setBhzCache();	//同步更新缓存
			return "2";
		}else{
			return "1";
		}
	}
	
	public String updateBhzIsTestSgpb(String id,String flag)throws Exception{
		if(Util.isEmpty(id) || Util.isEmpty(flag))
			return "0";
		TbBhz t = tbBhzDao.queryById(Integer.valueOf(id));
		if(t==null)
			return "0";
		if(flag.equals("false"))
			t.setSgpbTest("1");
		else
			t.setSgpbTest(null);
		tbBhzDao.update(t);
		setBhzCache();	//同步更新缓存
		return "1";
	}

	public TbBhz getBhzByCode(String bdCode,String bhzCode) throws Exception {
		String hql="from TbBhz where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		List<TbBhz> tbBhzs=tbBhzDao.queryByHql(hql);
		if(null!=tbBhzs && tbBhzs.size()>0){
			return tbBhzs.get(0);
		}else {
			return null;
		}
	}
}
