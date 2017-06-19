package com.bhz.util.jfreechat;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import com.bhz.util.Util;

public class BarChart {
	
	//生成柱状图
	public static void makeBarChart(List list,String titleName,String x,String y,
			String chartPath,String chartName)throws Exception{
		/*dataset.addValue(510, "深圳", "苹果");  
		dataset.addValue(320, "北京", "香蕉");  
		dataset.addValue(580, "上海", "橘子");  
		dataset.addValue(390, "广州", "梨子");*/
		CategoryDataset dataset = getDataSet(list);
		createBarChart(dataset,titleName,x,y,chartPath,chartName);
	}
	
	/**
	 * 
	 * 获取一个演示用的组合数据集对象
	 * 
	 * @return
	 * 
	 */

	private static CategoryDataset getDataSet(List list) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			Double val = null;
			if(obj[1]!=null){
				val = Double.valueOf(obj[1].toString());
				if(val>0)
					dataset.addValue(val, obj[0].toString(), obj[0].toString()+"("+obj[2].toString()+"盘)");
			}
		}
		return dataset;
	}

	public static void createBarChart(CategoryDataset dataset,String titleName,String x,String y,
			String chartPath,String chartName)throws Exception {
		JFreeChart chart = ChartFactory.createStackedBarChart3D(
				titleName, // 图表标题
				x, // 目录轴的显示标签
				y, // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				false, // 是否显示图例(对于简单的柱状图必须是false)
				true, // 是否生成工具
				true // 是否生成URL链接
				);

		// 设置Title和Legend的字体
		chart.getTitle().setFont((new Font("宋体", Font.CENTER_BASELINE, 20)));
		CategoryPlot plot = chart.getCategoryPlot();// 获得图表区域对象
		//无数据
		plot.setNoDataMessage("无对应的数据，请重新查询。");
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		// 设置柱图背景色
		// plot.setBackgroundPaint(new Color(160, 160, 255));

		// 设置纵横坐标的显示位置
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		vn.setAutoRangeIncludesZero(true);
		DecimalFormat df = new DecimalFormat("#0.00");
		vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式
		vn.setTickUnit(new NumberTickUnit(140));	//纵坐标间距

		// 设置图表的横轴（X轴）
		CategoryAxis domainAxis = plot.getDomainAxis();
		setXDomainAxis(domainAxis);

		// 设置图表的纵轴（Y轴）
		ValueAxis valueAxis = plot.getRangeAxis();
		setYDomainAxis(valueAxis);

		// 设置图表的颜色
		setRender(plot);
		FileOutputStream fos_jpg = null;
		try {
			Util.isChartPathExist(chartPath);
			chartName = chartPath + chartName;
			fos_jpg = new FileOutputStream(chartName);
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 1000, 500);// 把报表保存为文件
		} catch (Exception e) {
			String s = e.getLocalizedMessage();
			s = e.getMessage();
			s = e.toString();
			System.out.println(s);
		}
		/*// 将生成的报表放到预览窗口中
		ChartFrame preview = new ChartFrame("2010中国各大城市水果销量统计", chart);
		preview.pack();
		// 显示报表预览窗口
		preview.setVisible(true);*/
	}

	/**
	 * 对X轴的设置
	 * 
	 * @param categoryAxis
	 */
	public static void setXDomainAxis(CategoryAxis domainAxis) {
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		
		domainAxis.setLowerMargin(0.1);// 设置距离图片左端距离此时为10%
		domainAxis.setUpperMargin(0.1);// 设置距离图片右端距离此时为百分之10

		domainAxis.setLabelFont(labelFont);// X轴标题
		domainAxis.setTickLabelFont(labelFont);// X轴数值

		domainAxis.setCategoryLabelPositionOffset(10);// 图表横轴与标签的距离(10像素)
		domainAxis.setCategoryMargin(0.2);// 横轴标签之间的距离20%

		// domainAxis.setMaximumCategoryLabelLines(1);
		// domainAxis.setMaximumCategoryLabelWidthRatio(0);

		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		
		// Lable（Math.PI/3.0）度倾斜
		//domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 3.0));
		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示

	}

	/**
	 * 对Y轴的设置
	 * 
	 * @param categoryAxis
	 */
	public static void setYDomainAxis(ValueAxis valueAxis) {
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

		valueAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)
		valueAxis.setLowerMargin(0.15);// 设置最低的一个 Item 与图片底端的距离

		valueAxis.setLabelFont(labelFont);// Y轴标题
		valueAxis.setTickLabelFont(labelFont);// Y轴数值
		valueAxis.setUpperBound(700);

		// Lable水平显示
		//valueAxis.setLabelAngle(1.5);
	}

	/**
	 * 设置图表的颜色(渲染图表)
	 * 
	 * @param categoryPlot
	 */
	public static void setRender(CategoryPlot plot) {
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseOutlinePaint(Color.red);
		renderer.setSeriesPaint(0, new Color(0, 255, 255));// 设置柱的颜色为青色

		renderer.setSeriesOutlinePaint(0, Color.BLACK);// 边框为黑色
		renderer.setSeriesPaint(1, new Color(0, 255, 0));// 设置柱的颜色为绿色

		renderer.setSeriesOutlinePaint(1, Color.red);// 边框为红色
		renderer.setItemMargin(-1);// 组内柱子间隔为组宽的10%

		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题(3D的才需啊这句，2D的上面那句足矣)
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));

		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelFont((new Font("黑体", Font.BOLD, 12)));// 12号黑体加粗
		renderer.setBaseItemLabelPaint(Color.black);// 字体为黑色
		renderer.setBaseItemLabelsVisible(true);
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.3);
		// 设置柱子高度
		renderer.setMinimumBarLength(0.2);
		// 设置柱子边框可见
		// renderer.setDrawBarOutline(true);
		plot.setRenderer(renderer);// 使用我们设计的效果
	}
}
