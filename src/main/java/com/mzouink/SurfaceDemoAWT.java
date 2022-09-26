package com.mzouink;


import org.jzy3d.analysis.AWTAbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.chart.factories.IChartFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Func3D;
import org.jzy3d.plot3d.builder.SurfaceBuilder;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import com.jogamp.opengl.awt.GLCanvas;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Demo an AWT chart using JOGL {@link GLCanvas}.
 *
 * @author martin
 */
public class SurfaceDemoAWT extends AWTAbstractAnalysis {
    double factor = 0.5;
    double sur = 0.5;
    private Shape surface;

    public static void main(String[] args) throws Exception {
        SurfaceDemoAWT d = new SurfaceDemoAWT();

        AnalysisLauncher.open(d);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run () {
                System.out.println("init: ");
              d.init();
            }
        },5000);
    }

//    Half circle and half oval

    @Override
    public void init() {
        if (surface!=null)
            chart.getScene().getGraph().remove(surface);
        // Define a function to plot
        Func3D func = new Func3D((x, y) -> {
            if (y>0){
            if ((Math.pow((x-0),2) + Math.pow((y-0),2)) > Math.pow(1,2))
                return Double.valueOf(1);
            else return Double.valueOf(0);}
            else {if ((Math.pow((x-0),2)/Math.pow(sur,2)  + Math.pow((y-0),2))> Math.pow(1/sur,2))
                return Double.valueOf(1);
            else return Double.valueOf(0);}
        });
        Range range = new Range(-4, 4);
        int steps = 80;

        // Create the object to represent the function over the given range.
        surface =
                new SurfaceBuilder().orthonormal(new OrthonormalGrid(range, steps), func);
        surface
                .setColorMapper(new ColorMapper(new ColorMapRainbow(), surface, new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(Color.BLACK);

        // Create a chart
        //GLCapabilities c = new GLCapabilities(GLProfile.get(GLProfile.GL3));
        //IPainterFactory p = new AWTPainterFactory(c);
        IChartFactory f = new AWTChartFactory();

        if (chart ==null)
            chart = f.newChart(Quality.Advanced().setHiDPIEnabled(true));
        chart.getScene().getGraph().add(surface);

        sur=sur*factor;
        chart.render();
    }
}