package ca.qc.johnabbott.cs603.Tools;

import android.graphics.Canvas;
import android.graphics.RectF;

import ca.qc.johnabbott.cs603.Shapes.Ellipse;
import ca.qc.johnabbott.cs603.ToolBox;

/**
 * Created by AlexGenio on 15-03-04.
 */
public class EllipseTool extends RectangleBasedTool {

    public EllipseTool(ToolBox tbox) {
        super(tbox, ToolName.ELLIPSE);
    }

    @Override
    public void addToDrawing() {
        Ellipse oval = new Ellipse(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(oval);
        toolbox.getDrawingView().invalidate();
    }

    public void drawPreview(Canvas canvas){
        if(x1 > x2 && y1 > y2)
            canvas.drawOval(new RectF(x2, y2, x1, y1), getPreviewPaint());
        else if(x1 > x2)
            canvas.drawOval(new RectF(x2, y1, x1, y2), getPreviewPaint());
        else if(y1 > y2)
            canvas.drawOval(new RectF(x1, y2, x2, y1), getPreviewPaint());
        else
            canvas.drawOval(new RectF(x1, y1, x2, y2), getPreviewPaint());
    }
}
