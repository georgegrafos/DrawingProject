package ca.qc.johnabbott.cs603;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import ca.qc.johnabbott.cs603.Shapes.Circle;
import ca.qc.johnabbott.cs603.Shapes.Ellipse;
import ca.qc.johnabbott.cs603.Shapes.Line;
import ca.qc.johnabbott.cs603.Shapes.Rectangle;
import ca.qc.johnabbott.cs603.Shapes.Shape;
import ca.qc.johnabbott.cs603.Shapes.Square;
import ca.qc.johnabbott.cs603.Tools.ToolName;

/**
 * Created by AlexGenio on 15-05-01.
 */
public class PictureView extends Activity {

    private DrawingView drawing;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = this.getApplicationContext();
        drawing = (DrawingView)this.findViewById(R.id.drawing_view);

        // get the json of picture to view
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");

        // disable touch events in drawing view
        drawing.setViewMode(true);

        // parse json and draw
        parseJSON(json);
    }
    private void parseJSON(String response){
        try {
            JSONObject json = new JSONObject(response);
            JSONArray json_array = json.getJSONArray("list");;
            for(int i = 0; i < json_array.length(); i++){
                JSONObject oneShape = json_array.getJSONObject(i);
                String type = oneShape.getString("type");
                float x1 = (float) oneShape.getDouble("startX");
                float x2 = (float) oneShape.getDouble("endX");
                float y1 = (float) oneShape.getDouble("startY");
                float y2 = (float) oneShape.getDouble("endY");
                int fillColor = oneShape.getInt("fillColor");
                int strokeColor = oneShape.getInt("strokeColor");
                int strokeWidth = oneShape.getInt("strokeWidth");

                if(type.equals("rectangle")){
                    drawing.getPicture().add(new Rectangle(x1, y1, x2, y2, strokeColor, strokeWidth, fillColor));
                }else if(type.equals("circle")){
                    drawing.getPicture().add(new Circle(x1, y1, x2, y2, strokeColor, strokeWidth, fillColor));
                }else if(type.equals("square")){
                    drawing.getPicture().add(new Square(x1, y1, x2, y2, strokeColor, strokeWidth, fillColor));
                }else if(type.equals("ellipse")){
                    drawing.getPicture().add(new Ellipse(x1, y1, x2, y2, strokeColor, strokeWidth, fillColor));
                }else{
                    drawing.getPicture().add(new Line(x1, y1, x2, y2, strokeColor, strokeWidth));
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
