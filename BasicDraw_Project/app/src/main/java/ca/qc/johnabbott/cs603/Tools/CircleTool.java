package ca.qc.johnabbott.cs603.Tools;

import android.graphics.Canvas;
import android.graphics.RectF;

import ca.qc.johnabbott.cs603.ToolBox;
import ca.qc.johnabbott.cs603.Shapes.Circle;

/**
 * Created by AlexGenio on 15-03-04.
 */
public class CircleTool extends RectangleBasedTool {

    public CircleTool(ToolBox tbox) {
        super(tbox, ToolName.CIRCLE);
    }

    private void buildCircle(){

        //check for a rectangle
        if(Math.abs(x1-x2) != Math.abs(y2-y1)){
            //take the width and make a square
            float distX = Math.abs(x2-x1);
            float distY = Math.abs(y2-y1);
            //is the width the shortest?
            if(distX < distY) {
                y2 = y1 + distX;
            }else{
                x2 = x1 + distY;
            }
        }

    }

    @Override
    public void addToDrawing() {
        buildCircle();
        Circle oval = new Circle(x1, y1, x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(oval);
        toolbox.getDrawingView().invalidate();
    }

    public void drawPreview(Canvas canvas){
        buildCircle();
        canvas.drawOval(new RectF(x1, y1, x2, y2), getPreviewPaint());
    }
}
