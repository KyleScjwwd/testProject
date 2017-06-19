package com.bhz.util.jfreechat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import com.bhz.util.Util;

public class BarGroupChart {
	
	private static final Paint[] COLORS = { new Color(177,74,74), new Color(83,83,173),   
		new Color(202,123,46), new Color(83,193,83), new Color(55,55,85), 
		new Color(134,23,134), new Color(128,128,128), new Color(192,113,113),
		new Color(22,22,150), new Color(24,130,169), Color.red, Color.black,
		Color.blue, Color.gray, Color.red, Color.black,Color.blue, Color.gray, Color.red, 
		Color.black,new Color(154,25,155), new Color(65,65,65), new Color(88,66,77),
		new Color(202,123,46), new Color(83,193,83), new Color(55,55,85)};
	
	//生成分组的柱状图
	public static void makeBarGroupChart(String chartName,String chartPath,
			String[] rowKeys,String[] columnKeys,double[][] data) throws Exception{
		/*double[][] data2 = new double[][] { { 672, 766},
				{ 325, 521}, { 332, 256},{ 172, 166},
				{ 125, 121}, { 132, 156} };
		String[] rowKeys2 = { "苹果", "苹果2", "梨子","梨子2", "葡萄","葡萄2" };
		String[] columnKeys2 = { "北京2", "上海2"};*/
		CategoryDataset dataset = getBarData(rowKeys, columnKeys, data);
		createBarChart(dataset, "", "单位(kg)", chartName, "countclyl.jpg", chartPath);
	}
	
	public static void makeBarChart(List list,String titleName,String x,String y,
			String chartPath,String chartName)throws Exception{
		CategoryDataset dataset = getBarData(list);
		createBarChart(dataset, x, y, titleName, chartName, chartPath);
	}

	// 柱状图,折线图 数据集
	public static CategoryDataset getBarData(String[] rowKeys,
			String[] columnKeys,double[][] data) {
		return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
	}
	
	public static CategoryDataset getBarData(List list)throws Exception{
		DefaultCategoryDataset  dataset = new DefaultCategoryDataset();
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

	/**
	 * 柱状图
	 * 
	 * @param dataset 数据集
	 * @param xName x轴的说明（如种类，时间等）
	 * @param yName y轴的说明（如速度，时间等）
	 * @param chartTitle 图标题
	 * @param charName 生成图片的名字
	 * @return
	 */
	public static String createBarChart(CategoryDataset dataset, String xName,
			String yName, String chartTitle, String charName, String chartPath) throws Exception{
		JFreeChart chart = ChartFactory.createBarChart(chartTitle, // 图表标题
				xName, // 目录轴的显示标签
				yName, // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				false, // 是否生成工具
				false // 是否生成URL链接
				);
		//设置图标题的字体重新设置title
		Font font = new Font("隶书", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		chart.getLegend().setItemFont(new Font("宋体",Font.TRUETYPE_FONT, 12));
		/*
		 * VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭,
		 * 使用的关闭抗锯齿后，字体尽量选择12到14号的宋体字,这样文字最清晰好看
		 */
		// chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.white);
		// create plot
		CategoryPlot plot = chart.getCategoryPlot();
		//无数据时
		plot.setNoDataMessage("无对应的数据，请重新查询。");
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		// vn.setAutoRangeIncludesZero(true);
		DecimalFormat df = new DecimalFormat("#0.00");
		vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式
		// x轴设置
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		domainAxis.setMaximumCategoryLabelLines(10);	//设置X轴最大字符数
		// Lable（Math.PI/3.0）度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions
		// .createUpRotationLabelPositions(Math.PI / 3.0));

		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示

		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.02);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.02);
		// 设置 columnKey 是否间隔显示
		// domainAxis.setSkipCategoryLabelsToFit(true);

		plot.setDomainAxis(domainAxis);
		// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
		//plot.setBackgroundPaint(new Color(255, 255, 204));

		// y轴设置
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		// 设置最高的一个 Item 与图片顶端的距离
		rangeAxis.setUpperMargin(0.15);
		// 设置最低的一个 Item 与图片底端的距离
		rangeAxis.setLowerMargin(0.15);
		//设置Y轴最大值
		rangeAxis.setUpperBound(2500);
		plot.setRangeAxis(rangeAxis);
		
		//数字轴设置
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		numberaxis.setTickUnit(new NumberTickUnit(245));	//纵坐标间距

		BarRenderer renderer = new BarRenderer();
		//显示柱子数值
		//renderer.setItemLabelsVisible(true);
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.2);
		// 设置柱子高度
		renderer.setMinimumBarLength(0.2);
		// 设置柱子边框颜色
		renderer.setBaseOutlinePaint(Color.BLACK);
		// 设置柱子边框可见
		renderer.setDrawBarOutline(false);
		// // 设置柱的颜色
		//renderer.setSeriesPaint(0, new Color(204, 255, 255));
		setColor(renderer, dataset);

		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0);
		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		plot.setRenderer(renderer);
		// 设置柱的透明度
		plot.setForegroundAlpha(1f);

		FileOutputStream fos_jpg = null;
		try {
			Util.isChartPathExist(chartPath);
			String chartName = chartPath + charName;
			fos_jpg = new FileOutputStream(chartName);
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 1000, 500, true, 10);
			return chartName;
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
	}
	
	public static void setColor(BarRenderer renderer, CategoryDataset dataset)throws Exception{
        List keys = dataset.getRowKeys();
        for (int i = 0; i < keys.size(); i++) {
        	renderer.setSeriesPaint(i, COLORS[i]);
            //plot.setSectionPaint(keys.get(i).toString(), COLORS[i]);
        }
    }
}
