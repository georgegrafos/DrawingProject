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

    public void buildEllipse(){
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
        buildEllipse();
        Ellipse oval = new Ellipse(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(oval);
        toolbox.getDrawingView().invalidate();
    }

    public void drawPreview(Canvas canvas){
        buildEllipse();
        canvas.drawOval(new RectF(x1, y1, x2, y2), getPreviewPaint());
    }
}
