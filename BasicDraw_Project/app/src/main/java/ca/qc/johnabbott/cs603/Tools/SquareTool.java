package ca.qc.johnabbott.cs603.Tools;

/**
 * Created by AlexGenio on 15-03-03.
 */
import android.graphics.Canvas;

import ca.qc.johnabbott.cs603.ToolBox;
import ca.qc.johnabbott.cs603.Shapes.Square;

public class SquareTool extends RectangleBasedTool {

    public SquareTool(ToolBox tbox) {
        super(tbox, ToolName.SQUARE);
    }

    private void buildSquare(){

        //check for a rectangle
        if(Math.abs(x1-x2) != Math.abs(y2-y1)){
            //take the width and make a square
            float distX = Math.abs(x2-x1);
            float distY = Math.abs(y2-y1);
            //account for direction of drag
            //is the width the shortest?
            if(distX < distY) {
                //bottom right to top left
                if(x1 > x2 && y1 > y2)
                    y2 = y1 - distX;
                //bottom left to top right
                else if(x2 > x1 && y2 < y1)
                    y2 = y1 - distX;
                else
                    y2 = y1 + distX;
            }else{
                if(x1 > x2 && y1 > y2)
                    x2 = x1 - distY;
                else if(x2 < x1 && y2 > y1)
                    x2 = x1 - distY;
                else
                    x2 = x1 + distY;
            }
        }
    }

    @Override
    public void addToDrawing() {
        buildSquare();
        Square square = new Square(x1, y1, x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(square);
        toolbox.getDrawingView().invalidate();
    }

    public void drawPreview(Canvas canvas){
        buildSquare();
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
