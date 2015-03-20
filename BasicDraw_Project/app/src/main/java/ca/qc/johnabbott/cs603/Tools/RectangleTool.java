package ca.qc.johnabbott.cs603.Tools;

import android.graphics.Canvas;

import ca.qc.johnabbott.cs603.ToolBox;
import ca.qc.johnabbott.cs603.Shapes.Rectangle;

public class RectangleTool extends RectangleBasedTool {

    public RectangleTool(ToolBox tbox) {
        super(tbox, ToolName.RECTANGLE);
    }

    private void buildRectangle(){
        float tmpX, tmpY;
        //get the leftmost x as starting coordinate
        if(x1 > x2){
            tmpX = x2;
            x2 = x1;
            x1 = tmpX;
        }
        //get the topmost y as starting coordinate
        if(y1 > y2){
            tmpY = y2;
            y2 = y1;
            y1 = tmpY;
        }
    }

    @Override
    public void addToDrawing() {
        buildRectangle();
        Rectangle rect = new Rectangle(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(rect);
        toolbox.getDrawingView().invalidate();
    }

    public void drawPreview(Canvas canvas){
        buildRectangle();
        canvas.drawRect(x1, y1, x2, y2, getPreviewPaint());
    }

}
