package com.kimmin.jfreechart;

import org.apache.tools.ant.types.resources.First;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by kimmin on 3/9/16.
 */


public class PlotTest {

    @Test
    public void doPieTest() {
        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("One", new Double(43.2));
        data.setValue("Two", new Double(10));
        data.setValue("Three", new Double(32.2));
        data.setValue("Four", new Double(12));

        JFreeChart jFreeChart = ChartFactory.createPieChart("Demo",
                data, true, true, false);
        try {
            ChartUtilities.saveChartAsPNG(new File("/home/kimmin/chart.png"),jFreeChart,500,500);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


    @Test
    public void doCategoryTest() throws IOException{
        JFreeChart jFreeChart = ChartFactory.createBarChart("Demo2","Cat","Val",createCatDataset(), PlotOrientation.VERTICAL
        ,true,true,false);
        ChartUtilities.saveChartAsPNG(new File("/home/kimmin/chart.png"),jFreeChart,500,500);
    }

    @Test
    public void doLineTest() throws IOException{
        JFreeChart jFreeChart = ChartFactory.createLineChart("Demo3","Cat","Val",createLineDataset(),
                PlotOrientation.VERTICAL,true,true,false);
        ChartUtilities.saveChartAsPNG(new File("/home/kimmin/chart.png"),jFreeChart,500,500);
    }


    private static CategoryDataset createCatDataset()
    {
        String series1 = "First";
        String series2 = "Second";
        String series3 = "Third";
        String category1 = "Category 1";
        String category2 = "Category 2";
        String category3 = "Category 3";
        String category4 = "Category 4";
        String category5 = "Category 5";
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        defaultcategorydataset.addValue(1.0D, series1, category1);
        defaultcategorydataset.addValue(4D, series1, category2);
        defaultcategorydataset.addValue(3D, series1, category3);
        defaultcategorydataset.addValue(5D, series1, category4);
        defaultcategorydataset.addValue(5D, series1, category5);

        defaultcategorydataset.addValue(5D, series2, category1);
        defaultcategorydataset.addValue(7D, series2, category2);
        defaultcategorydataset.addValue(6D, series2, category3);
        defaultcategorydataset.addValue(8D, series2, category4);
        defaultcategorydataset.addValue(4D, series2, category5);

        defaultcategorydataset.addValue(4D, series3, category1);
        defaultcategorydataset.addValue(3D, series3, category2);
        defaultcategorydataset.addValue(2D, series3, category3);
        defaultcategorydataset.addValue(3D, series3, category4);
        defaultcategorydataset.addValue(6D, series3, category5);
        return defaultcategorydataset;
    }

    private static CategoryDataset createLineDataset()
    {
        String series1 = "First";
        String series2 = "Second";
        String series3 = "Third";
        String type1 = "Type 1";
        String type2 = "Type 2";
        String type3 = "Type 3";
        String type4 = "Type 4";
        String type5 = "Type 5";
        String type6 = "Type 6";
        String type7 = "Type 7";
        String type8 = "Type 8";
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        defaultcategorydataset.addValue(1.0D, series1, type1);
        defaultcategorydataset.addValue(4D, series1, type2);
        defaultcategorydataset.addValue(3D, series1, type3);
        defaultcategorydataset.addValue(5D, series1, type4);
        defaultcategorydataset.addValue(5D, series1, type5);
        defaultcategorydataset.addValue(7D, series1, type6);
        defaultcategorydataset.addValue(7D, series1, type7);
        defaultcategorydataset.addValue(8D, series1, type8);

        defaultcategorydataset.addValue(5D, series2, type1);
        defaultcategorydataset.addValue(7D, series2, type2);
        defaultcategorydataset.addValue(6D, series2, type3);
        defaultcategorydataset.addValue(8D, series2, type4);
        defaultcategorydataset.addValue(4D, series2, type5);
        defaultcategorydataset.addValue(4D, series2, type6);
        defaultcategorydataset.addValue(2D, series2, type7);
        defaultcategorydataset.addValue(1.0D, series2, type8);

        defaultcategorydataset.addValue(4D, series3, type1);
        defaultcategorydataset.addValue(3D, series3, type2);
        defaultcategorydataset.addValue(2D, series3, type3);
        defaultcategorydataset.addValue(3D, series3, type4);
        defaultcategorydataset.addValue(6D, series3, type5);
        defaultcategorydataset.addValue(3D, series3, type6);
        defaultcategorydataset.addValue(4D, series3, type7);
        defaultcategorydataset.addValue(3D, series3, type8);
        return defaultcategorydataset;
    }

}
