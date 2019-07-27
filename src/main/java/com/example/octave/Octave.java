package com.example.octave;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import dk.ange.octave.type.OctaveDouble;

import java.io.File;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/7/6 14:22
 */
public class Octave {
    public static void main(String[] argv){
        OctaveEngineFactory factory = new OctaveEngineFactory();
        factory.setOctaveProgram(new File("D:/Octave/Octave-4.0.3/bin/octave.exe"));
        OctaveEngine octave = factory.getScriptEngine();
//        OctaveEngine octave = new OctaveEngineFactory().getScriptEngine();
        OctaveDouble matA = new OctaveDouble(new double[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, 3, 5);
        octave.put("a", matA);
        octave.eval("a");

        String matB = "b = [1,2,3; 4,5,6; 7,8,9]";
        octave.eval(matB);

        octave.eval("x = b(1,:)");
        OctaveDouble varX = (OctaveDouble) octave.get("x");
        System.out.println("Result: "+varX.get(1)+" "+varX.get(2)+" "+varX.get(3));
        octave.close();
    }
}
