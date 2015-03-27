package ca.qc.johnabbott.cs603.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

public class Line extends Shape {

    private float x1, x2, y1, y2;


    public Line(float x1, float y1, float x2, float y2, int strokeColor, int strokeWidth) {
        super(strokeColor, Color.TRANSPARENT, strokeWidth);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public void draw(Paint paint, Canvas canvas) {
        // reset any path effect
        paint.setPathEffect(null);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(strokeColor);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "line");
            jsonObject.put("startX", this.x1);
            jsonObject.put("startY", this.y1);
            jsonObject.put("endX", this.x2);
            jsonObject.put("endY", this.y2);
            jsonObject.put("fillColor", getFillColor());
            jsonObject.put("strokeColor", getStrokeColor());
            jsonObject.put("strokeWidth", getStrokeWidth());
            return jsonObject;
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
