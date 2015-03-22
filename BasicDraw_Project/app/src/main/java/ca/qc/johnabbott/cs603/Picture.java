package ca.qc.johnabbott.cs603;

import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs603.Shapes.Shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void convertToJSON(){
        for(Shape s : this.shapes){
            JSONObject shapeObject= new JSONObject();
            try {
                shapeObject.put("fillColor", s.getFillColor());
                shapeObject.put("strokeColor", s.getStrokeColor());
                shapeObject.put("strokeWidth", s.getStrokeWidth());
                JSONObject fullJSON = s.toJSON(shapeObject);
                Log.e("JSON", fullJSON.toString());
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
