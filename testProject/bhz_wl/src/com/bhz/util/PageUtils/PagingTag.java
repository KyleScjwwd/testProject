package com.bhz.util.PageUtils;

import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PagingTag extends TagSupport implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否显示改变页数下拉框
	 */
	private boolean skipCount = true;

	/**
	 * 是否显示跳页输入框
	 */
	private boolean jumpText = true;

	/**
	 * 是否显示上/下一页按钮
	 */
	private boolean prevPage = true;

	/**
	 * 是否显示首/尾页按钮
	 */
	private boolean startPage = true;

	/**
	 * 是否显示遍历页
	 */
	private boolean forPage = true;

	/**
	 * 是否显示当前页和总页数
	 */
	private boolean showCurAndSum = true;

	/**
	 * 是否启用刷新
	 */
	private boolean refresh = true;

	/**
	 * 分页实体对象
	 */
	private Page page;

	/**
	 * 请求URL
	 */
	private String url;

	public boolean isSkipCount() {
		return skipCount;
	}

	public void setSkipCount(boolean skipCount) {
		this.skipCount = skipCount;
	}

	public boolean isJumpText() {
		return jumpText;
	}

	public void setJumpText(boolean jumpText) {
		this.jumpText = jumpText;
	}

	public boolean isPrevPage() {
		return prevPage;
	}

	public void setPrevPage(boolean prevPage) {
		this.prevPage = prevPage;
	}

	public boolean isStartPage() {
		return startPage;
	}

	public void setStartPage(boolean startPage) {
		this.startPage = startPage;
	}

	public boolean isForPage() {
		return forPage;
	}

	public void setForPage(boolean forPage) {
		this.forPage = forPage;
	}

	public boolean isShowCurAndSum() {
		return showCurAndSum;
	}

	public void setShowCurAndSum(boolean showCurAndSum) {
		this.showCurAndSum = showCurAndSum;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 输出分页标签
	 */
	public int doStartTag() throws JspException {
		try {
			if (page != null) {
				String pix = "?";
				if(url.contains("?"))
					pix = "&";
				long currentPage = page.getCurrentPage(); // 获得当前页面
				long totalPage = page.getTotalPage(); // 获得总页数
				StringBuilder builder = new StringBuilder("<script type='text/javascript'>");
				builder.append("function loadPage(pagenum){var url='" + url + "" + pix + "page.currentPage='+pagenum;self.location.href=url;"+
				"if(pagenum==1) alert('已经是第一页');if(pagenum>="+totalPage+") alert('已经是最后一页');}"
				);
				builder.append("function jumpPage(){var jumpPageNum=document.getElementById('jump_page_num').value;");
				builder.append("if(jumpPageNum.length<=0){alert('请填写要跳转的页面值!');return false;}");
				builder.append("if(!(/^[1-9]\\d*$/).test(jumpPageNum)){alert('跳转页面值只能填写正整数');return false;}");
				builder.append("if(jumpPageNum>" + page.getTotalPage() + "){alert('跳转的页数大于总页数!');return false;}");
				builder.append("var url='" + url + ""+pix+"page.currentPage='+jumpPageNum;self.location.href=url }");
				builder.append("function refreshPage(){self.location.href='" + url + "';}");
				builder.append("</script>");

				builder.append("<div class=\"pagin\">");
				builder.append(" <div class=\"message\">共<i class=\"blue\">"+page.getTotalCount()+"</i>条记录，当前显示第&nbsp;<i class=\"blue\">"+page.getCurrentPage()+"&nbsp;</i>页,共"+page.getTotalPage()+"页,每页"+page.getPageSize()+"条数据</div>");
				builder.append("<ul class=\"paginList\">");
				builder.append("<li class=\"paginItem \"><a title='首页' href=\"javascript:;\" onclick='javascript:loadPage(1)'><span class=\"pagesy\"></span></a></li>");
				if(currentPage==1){
					if(startPage){
						builder.append("<li class=\"paginItem\"><a title='上一页' href=\"javascript:;\" onclick='javascript:loadPage(1)'><span class=\"pagepre\"></span></a></li>");
					}
				} else {
					builder.append("<li class=\"paginItem\"><a title='上一页' href=\"javascript:;\" onclick='javascript:loadPage("+(page.getCurrentPage()-1)+")'><span class=\"pagepre\"></span></a></li>");
				}
				if(forPage && totalPage>0){
					for(long pagenum=1;pagenum<=page.getTotalPage();pagenum++){
						if(page.getCurrentPage() < 5 && pagenum < 10){
							if(pagenum==page.getCurrentPage()){
								builder.append("<li class=\"paginItem current\"><a title='第"+pagenum+"页' href=\"javascript:;\">"+pagenum+"</a></li>");
							}else{
								builder.append("<li class=\"paginItem\"><a title='第"+pagenum+"页' href='"+url+pix+"page.currentPage="+pagenum+"'>"+pagenum+"</a></li>");
							}
						}else{
							if (pagenum > page.getCurrentPage() - 5 && pagenum < page.getCurrentPage() + 5) {
								if (pagenum == page.getCurrentPage())
									builder.append("<li class=\"paginItem current\"><a title='第"+pagenum+"页' href=\"javascript:;\">"+pagenum+"</a></li>");
								else{
									builder.append("<li class=\"paginItem\"><a title='第"+pagenum+"页' href='"+url+pix+"page.currentPage="+pagenum+"'>"+pagenum+"</a></li>");
								}
							}
						}
					}
				}

				if (currentPage == totalPage || totalPage <= 0) {
					if (startPage)
						builder.append("<li class=\"paginItem\"><a title='下一页' href=\"javascript:;\"><span class=\"pagenxt\"></span></a></li>");
				}
				else {
					if (startPage)
						builder.append("<li class=\"paginItem\"><a title='下一页' href=\"javascript:;\" onclick='javascript:loadPage("+(page.getCurrentPage()+1)+");' ><span class=\"pagenxt\"></span></a></li>");
				}
				builder.append("<li class=\"paginItem\"><a title='尾页' href=\"javascript:;\" onclick='javascript:loadPage("+totalPage+");' ><span class=\"pagewy\"></span></a></li>");
				builder.append("</ul>");
				builder.append("</div>");
				super.pageContext.getOut().print(builder.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
