package com.bhz.util.jfreechat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.io.FileOutputStream;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import com.bhz.util.Util;

public class PieLeaveChart {

	private static final Paint[] COLORS = { new Color(177,74,74), new Color(83,83,173),   
		new Color(202,123,46), new Color(83,193,83), new Color(55,55,85), 
		new Color(134,23,134), new Color(128,128,128), new Color(192,113,113),
		new Color(22,22,150), new Color(24,130,169)};
	
	public static void makePieChart(List<Object> list, String titleName,
			String chartPath, String chartName,Integer width,Integer height) 
		throws Exception{
		DefaultPieDataset dataSet = createDataset(list);
		createPieChart(dataSet, titleName, chartPath, chartName, width, height);
	}

	public static DefaultPieDataset createDataset(List<Object> list) 
		throws Exception{
		if(list.size()==0)
			return null;
		// 设置数据
		String[] keys = (String[])list.get(0);
		Double[] data = (Double[])list.get(1);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < keys.length; i++) {
			dataset.setValue(keys[i], data[i]);
		}
		return dataset;
	}
	
	public static void makePieChart(double[] data,String[] keys,String chartName,
			String chartTitle,String chartPath,Integer width,Integer height) 
		throws Exception{
		DefaultPieDataset dataSet = createDateset(data, keys);
		createPieChart(dataSet, chartTitle, chartPath, chartName, width, height);
	}
	
	public static DefaultPieDataset createDateset(double[] data,String[] keys)
		throws Exception{
		if (data != null && keys != null) {
			if (data.length == keys.length) {
				DefaultPieDataset dataset = new DefaultPieDataset();
				for (int i = 0; i < data.length; i++) {
					dataset.setValue(keys[i], data[i]);
				}
				return dataset;
			}
		}
		return null;
	}

	public static void createPieChart(DefaultPieDataset dataSet, String titleName,
			String chartPath, String chartName, Integer width, Integer height) {
		// 用工厂类创建饼图
		JFreeChart pieChart = ChartFactory.createPieChart(titleName,dataSet, true, true, false);
		// 设置Title和Legend的字体
		pieChart.getTitle().setFont((new Font("宋体", Font.CENTER_BASELINE, 20)));
		pieChart.getLegend().setItemFont(
				new Font("宋体", Font.CENTER_BASELINE, 15));
		// RenderingHints做文字渲染参数的修改
		// VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭.
		pieChart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// 得到饼图的Plot对象
		PiePlot piePlot = (PiePlot) pieChart.getPlot();
		setSection(piePlot);
		setLabel(piePlot);
		setNoDataMessage(piePlot);
		setNullAndZeroValue(piePlot);
		
		//自定义颜色设置
		setColor(piePlot, dataSet);
		
		piePlot.setOutlinePaint(Color.BLACK);
		piePlot.setShadowPaint(Color.WHITE);
		piePlot.setBackgroundPaint(null);	//去除背景
		piePlot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);	//直线引导
		FileOutputStream fos_jpg = null;
		try {
			Util.isChartPathExist(chartPath);
			chartName = chartPath + chartName;
			fos_jpg = new FileOutputStream(chartName);
			ChartUtilities.writeChartAsPNG(fos_jpg, pieChart, width, height);// 把报表保存为文件
		} catch (Exception e) {
			String s = e.getLocalizedMessage();
			s = e.getMessage();
			s = e.toString();
			System.out.println(s);
		}
	}

	public static void setSection(PiePlot pieplot) {
		// 设置扇区分离显示
		//pieplot.setExplodePercent(2, 0.2D);
		// 设置扇区边框不可见
		pieplot.setSectionOutlinesVisible(true);
	}

	public static void setLabel(PiePlot pieplot) {
		// 设置扇区标签显示格式：关键字：值(百分比)
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}：{1}({2})"));
		// 设置图例百分比
		pieplot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}：{1}({2})"));
		// 设置扇区标签颜色
		pieplot.setLabelBackgroundPaint(new Color(220, 220, 220));
		pieplot.setLabelFont((new Font("宋体", Font.PLAIN, 12)));
	}

	public static void setNoDataMessage(PiePlot pieplot) {
		// 设置没有数据时显示的信息
		pieplot.setNoDataMessage("无对应的数据，请重新查询。");
		// 设置没有数据时显示的信息的颜色
		pieplot.setNoDataMessagePaint(Color.red);
		// 设置没有数据时显示的信息的字体
		//pieplot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 14)); 
	}

	public static void setNullAndZeroValue(PiePlot piePlot) {
		// 设置是否忽略0和null值
		piePlot.setIgnoreNullValues(true);
		piePlot.setIgnoreZeroValues(true);
	}
	
	public static void setColor(PiePlot plot, DefaultPieDataset dataset) {
        List keys = dataset.getKeys();
        for (int i = 0; i < keys.size(); i++) {
            plot.setSectionPaint(keys.get(i).toString(), COLORS[i]);   
        }
    }
}
