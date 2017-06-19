package com.bhz.util.jfreechat;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import com.bhz.util.Util;

public class LineChart {

	/**
	 * 生成折线图
	 */
	public static void makeLineChart(List list,Map<String,String> xianMap,
			String chartTitle,String x,String y,String charName,String chartPath)
		throws Exception {
		CategoryDataset cd = getBarData(list);
		createTimeXYChar(xianMap, chartTitle, x,  y, cd, charName, chartPath);
	}

	/**
	 * 折线图 数据集 方法
	 */
	public static CategoryDataset getBarData(List list)throws Exception{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if(list!=null && list.size()>0){
			List<String[]> datas = (List<String[]>)list;
			for(String[] ss : datas){
				if(!Util.isEmpty(ss[0]))
					dataset.addValue(Integer.valueOf(ss[0]), ss[1], ss[2]);
			}
			return dataset;
		}
		return dataset;
	}

	/**
	 * 折线图样式
	 * 
	 * @param chartTitle
	 * @param x
	 * @param y
	 * @param xyDataset
	 * @param charName
	 * @return
	 */
	public static JFreeChart createTimeXYChar(Map<String,String> xianMap,
			String chartTitle, String x, String y,CategoryDataset xyDataset, 
			String charName, String chartPath)throws Exception{
		Double upperBound = 200.0;
		if(!Util.isEmpty(xianMap.get("up"))){
			upperBound = Double.valueOf(xianMap.get("up"))+40;
		}
		JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y,
				xyDataset, PlotOrientation.VERTICAL, false, true, false);
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.RED);
		// 设置图标题的字体重新设置title
		Font font = new Font("隶书", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		// 设置面板字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		chart.setBackgroundPaint(Color.white);
		// 获取categoryplot 对象
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		//无数据时
		categoryplot.setNoDataMessage("无对应的数据，请重新查询。");
		// x轴 // 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴 //数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);
		categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setDomainGridlineStroke(new BasicStroke());
		categoryplot.setRangeGridlineStroke(new BasicStroke());
		categoryplot.setBackgroundPaint(Color.lightGray);
		// 设置轴和面板之间的距离
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		//获得X轴
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // 横轴上的
		domainAxis.setMaximumCategoryLabelLines(10);
		// Lable
		// 45度倾斜
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);
		//获得Y轴
        ValueAxis rangeAxis = categoryplot.getRangeAxis();
        rangeAxis.setLabelFont(labelFont);
        rangeAxis.setUpperBound(upperBound);
        //数字轴设置
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		numberaxis.setTickUnit(new NumberTickUnit(22.2));	//纵坐标间距
		//设置基线
		for(String s : xianMap.keySet()){
			if(s.equals("up")){
				setValueMarker(categoryplot, xianMap.get(s), "上限");
			}else if(s.equals("down")){
				setValueMarker(categoryplot, xianMap.get(s), "下限");
			}
		}
		
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true); // series
		// 点（即数据点）可见
		lineandshaperenderer.setBaseLinesVisible(true); // series
		// 显示折点数据
		/*lineandshaperenderer.setBaseItemLabelGenerator(new
		StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);*/
		// 图片路径
		FileOutputStream fos_jpg = null;
		try {
			Util.isChartPathExist(chartPath);
			String chartName = chartPath + charName;
			fos_jpg = new FileOutputStream(chartName);
			// 将报表保存为JPG文件
			ChartUtilities.writeChartAsJPEG(fos_jpg, chart, 1000, 400);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fos_jpg.close();
				System.out.println("create time-createTimeXYChar.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chart;
	}
	
	//设置基线
	public static void setValueMarker(CategoryPlot categoryplot,String val,String name)throws Exception{
		if(Util.isEmpty(val))
			return;
		//开始设置基准温度曲线
		ValueMarker valuemarker = new ValueMarker(Double.parseDouble(val));
		valuemarker.setLabelOffsetType(LengthAdjustmentType.EXPAND);
		valuemarker.setPaint(Color.YELLOW);
		valuemarker.setStroke(new BasicStroke(1.0F));
		valuemarker.setLabelFont(new Font("SansSerif", 0, 11));
		valuemarker.setLabelPaint(Color.blue);
		valuemarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
		valuemarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
		valuemarker.setLabelOffset(new RectangleInsets(5, 5, 5, 5));
		float[] f = { 2, 4, 2, 4 }; // setStroke将基准线设置为虚线，float[]数组实现
		valuemarker.setStroke(new BasicStroke(2.0f, 1, 1, 0, f, 1.0f));
		valuemarker.setLabel(name);
		categoryplot.addRangeMarker(valuemarker, Layer.BACKGROUND);
	}
}