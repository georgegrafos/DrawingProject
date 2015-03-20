package ca.qc.johnabbott.cs603.Tools;

import ca.qc.johnabbott.cs603.ToolBox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.MotionEvent;

public abstract class RectangleBasedTool extends Tool {

    protected float x1;
    protected float y1;
    protected float x2;
    protected float y2;
    private Paint previewPaint;

    public RectangleBasedTool(ToolBox tbox, ToolName name) {
        super(tbox, name);
        this.previewPaint = new Paint();
        this.previewPaint.setStyle(Paint.Style.STROKE);
        this.previewPaint.setColor(Color.GRAY);
        this.previewPaint.setStrokeWidth(1);
        this.previewPaint.setStrokeCap(Paint.Cap.ROUND);
        this.previewPaint.setPathEffect(new DashPathEffect(new float[]{4.0f, 4.0f}, 1.0f));
    }

    public Paint getPreviewPaint(){
        return this.previewPaint;
    }
    @Override
    public void touchStart(MotionEvent event) {
        x1 = x2 = event.getX();
        y1 = y2 = event.getY();
    }

    @Override
    public void touchEnd(MotionEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        addToDrawing();
    }

    @Override
    public void touchMove(MotionEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        toolbox.getDrawingView().invalidate();
    }
}
