package ca.qc.johnabbott.cs603;

import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs603.Shapes.Shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Picture {

    private List<Shape> shapes;

    public Picture() {
        super();
        shapes = new LinkedList<Shape>();
    }

    public void draw(Paint paint, Canvas canvas) {
        for(Shape shape : shapes)
            shape.draw(paint, canvas);
    }

    public void add(Shape s) {
        shapes.add(s);
    }

    public void undo(){
        shapes.remove(shapes.size()-1);
    }

    public int numShapes(){
        return shapes.size();
    }

    public void clear() {
        shapes.clear();
    }

}
