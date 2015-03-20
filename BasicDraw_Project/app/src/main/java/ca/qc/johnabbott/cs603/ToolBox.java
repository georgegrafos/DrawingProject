package ca.qc.johnabbott.cs603;

import ca.qc.johnabbott.cs603.Tools.*;

import android.graphics.Color;

public class ToolBox {

    private DrawingView drawing;
    private Tool currentTool;
    private int strokeWidth;
    private int strokeColor;
    private int fillColor;

    public ToolBox(DrawingView drawing) {
        super();
        this.drawing = drawing;
        this.strokeWidth = 2;
        this.strokeColor = Color.BLACK;
        this.fillColor = Color.TRANSPARENT;
    }

    public void changeTool(ToolName name) {
        if (currentTool == null || currentTool.getName() != name) {
            switch (name) {
                case LINE:
                    currentTool = new LineTool(this);
                    break;
                case RECTANGLE:
                    currentTool = new RectangleTool(this);
                    break;
                case SQUARE:
                    currentTool = new SquareTool(this);
                    break;
                case ELLIPSE:
                    currentTool = new EllipseTool(this);
                    break;
                case CIRCLE:
                    currentTool = new CircleTool(this);
                    break;
                default:
                    currentTool = new LineTool(this);
                    break;
            }
        }
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public ToolName getCurrentToolName() {
        return currentTool.getName();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int color) {
        this.strokeColor = color;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int color) {
        this.fillColor = color;
    }

    public DrawingView getDrawingView() {
        return drawing;
    }

}
