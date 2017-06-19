package com.bhz.util.jfreechat;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import com.bhz.util.Util;

public class PieChart {
	/**
	 * 生成饼状图
	 */
	public static void makePieChart(double[] data,String[] keys,String chartName,String chartTitle,String chartPath) 
		throws Exception{
		//double[] data = { 30, 60, 10 };
		//String[] keys = { "合格", "不合格", "未评定" };
		createValidityComparePimChar(getDataPieSetByUtil(data, keys), chartTitle,
				chartName, keys, chartPath);
	}

	// 饼状图 数据集
	public static PieDataset getDataPieSetByUtil(double[] data,
			String[] datadescription) {
		if (data != null && datadescription != null) {
			if (data.length == datadescription.length) {
				DefaultPieDataset dataset = new DefaultPieDataset();
				for (int i = 0; i < data.length; i++) {
					dataset.setValue(datadescription[i], data[i]);
				}
				return dataset;
			}
		}
		return null;
	}

	/**
	 * 饼状图
	 * 
	 * @param dataset
	 *            数据集
	 * @param chartTitle
	 *            图标题
	 * @param charName
	 *            生成图的名字
	 * @param pieKeys
	 *            分饼的名字集
	 * @return
	 */
	public static String createValidityComparePimChar(PieDataset dataset,
			String chartTitle, String charName, String[] pieKeys, String chartPath) {
		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, // chart
				// title
				dataset,// data
				true,// include legend
				true, false);
		
		// 使下说明标签字体清晰,去锯齿类似于
		// chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);的效果
		chart.setTextAntiAlias(false);
		// 图片背景色
		chart.setBackgroundPaint(Color.white);
		// 设置图标题的字体重新设置title
		Font font = new Font("隶书", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		// 设置图例(Legend)上的文字(//底部的字体)
		chart.getLegend().setItemFont(new Font("宋体", Font.CENTER_BASELINE, 15));
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图片中显示百分比:默认方式
		
		// 指定饼图轮廓线的颜色
		// plot.setBaseSectionOutlinePaint(Color.BLACK);
		// plot.setBaseSectionPaint(Color.BLACK);

		// 设置无数据时的信息
		plot.setNoDataMessage("无对应的数据，请重新查询。");
		// 设置无数据时的信息显示颜色
		plot.setNoDataMessagePaint(Color.red);

		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}：{1}({2})"));//, NumberFormat.getNumberInstance(),new DecimalFormat("0%")
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1}({2})"));

		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));

		// 指定图片的透明度(0.0-1.0)
		plot.setForegroundAlpha(0.65f);
		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(false, true);

		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
		plot.setStartAngle(90);

		// // 设置分饼颜色
		plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
		plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));

		FileOutputStream fos_jpg = null;
		try {
			// 文件夹不存在则创建
			Util.isChartPathExist(chartPath);
			String chartName = chartPath + charName;
			fos_jpg = new FileOutputStream(chartName);
			// 高宽的设置影响椭圆饼图的形状
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 650, 420);
			return chartName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fos_jpg.close();
				System.out.println("create pie-chart.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
