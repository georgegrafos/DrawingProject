package ca.qc.johnabbott.cs603.Tools;

import ca.qc.johnabbott.cs603.ToolBox;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Tool {

    protected ToolBox toolbox;
    protected ToolName name;
    protected Boolean preview;

    public Tool(ToolBox toolbox, ToolName name) {
        super();
        this.toolbox = toolbox;
        this.name = name;
        this.preview = false;
    }

    public ToolName getName() {
        return name;
    }
    public Boolean hasPreview(){ return this.preview; }
    public void setPreview(Boolean val){ this.preview = val;}

    public abstract void touchStart(MotionEvent event);
    public abstract void touchEnd(MotionEvent event);
    public abstract void touchMove(MotionEvent event);
    public abstract void addToDrawing();
    public abstract void drawPreview(Canvas canvas);
}
