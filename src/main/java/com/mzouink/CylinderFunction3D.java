package com.mzouink;

import org.jzy3d.plot3d.builder.Func3D;

import java.util.function.BiFunction;

public class CylinderFunction3D extends Func3D {
    public CylinderFunction3D(BiFunction<Double, Double, Double> function) {
        super(function);
    }


}
