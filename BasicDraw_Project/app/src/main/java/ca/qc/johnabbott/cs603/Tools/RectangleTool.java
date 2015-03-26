package ca.qc.johnabbott.cs603.Tools;

import android.graphics.Canvas;

import ca.qc.johnabbott.cs603.ToolBox;
import ca.qc.johnabbott.cs603.Shapes.Rectangle;

public class RectangleTool extends RectangleBasedTool {

    public RectangleTool(ToolBox tbox) {
        super(tbox, ToolName.RECTANGLE);
    }

    @Override
    public void addToDrawing() {
        Rectangle rect = new Rectangle(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(rect);
        toolbox.getDrawingView().invalidate();
    }

    public void drawPreview(Canvas canvas){
        if(x1 > x2 && y1 > y2)
            canvas.drawRect(x2, y2, x1, y1, getPreviewPaint());
        else if(x1 > x2)
            canvas.drawRect(x2, y1, x1, y2, getPreviewPaint());
        else if(y1 > y2)
            canvas.drawRect(x1, y2, x2, y1, getPreviewPaint());
        else
            canvas.drawRect(x1, y1, x2, y2, getPreviewPaint());
    }

}
