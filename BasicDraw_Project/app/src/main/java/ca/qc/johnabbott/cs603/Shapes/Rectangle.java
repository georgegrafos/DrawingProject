package ca.qc.johnabbott.cs603.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import org.json.JSONException;
import org.json.JSONObject;

public class Rectangle extends Shape {

    private float x1, y1, x2, y2, tmpX, tmpY;

    public Rectangle(float x1, float y1, float x2, float y2, int strokeColor, int width) {
        this(x1, y1, x2, y2, strokeColor, width, Color.TRANSPARENT);
    }

    public Rectangle(float x1, float y1, float x2, float y2, int strokeColor, int strokeWidth, int fillColor) {
        super(strokeColor, fillColor, strokeWidth);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }



    @Override
    public void draw(Paint paint, Canvas canvas) {

        // reset any path effect
        paint.setPathEffect(null);

        if(fillColor != Color.TRANSPARENT) {
            paint.setColor(fillColor);
            paint.setStyle(Style.FILL);
            canvas.drawRect(x1, y1, x2, y2, paint);
        }
        if(strokeColor != Color.TRANSPARENT && strokeWidth > 0) {
            paint.setStyle(Style.STROKE);
            paint.setColor(strokeColor);
            paint.setStrokeWidth(strokeWidth);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawRect(x1, y1, x2, y2, paint);
        }

    }

    public JSONObject toJSON(JSONObject jsonObject){
        try {
            jsonObject.put("startX", this.x1);
            jsonObject.put("startY", this.y1);
            jsonObject.put("endX", this.x2);
            jsonObject.put("endY", this.y2);
            return jsonObject;
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
