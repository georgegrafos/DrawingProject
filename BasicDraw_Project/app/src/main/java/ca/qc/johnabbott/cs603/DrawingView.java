package ca.qc.johnabbott.cs603;

import ca.qc.johnabbott.cs603.Shapes.Shape;
import ca.qc.johnabbott.cs603.Tools.ToolName;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    private Picture picture;
    private ToolBox toolbox;
    private Paint paint;
    private Boolean viewMode;
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.picture = new Picture();
        this.toolbox = new ToolBox(this);
        toolbox.changeTool(ToolName.LINE);
        paint = new Paint();
        paint.setAntiAlias(true);
        this.viewMode = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        picture.draw(paint, canvas);
        if(toolbox.getCurrentTool().hasPreview()){
            toolbox.getCurrentTool().drawPreview(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!this.viewMode) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    toolbox.getCurrentTool().setPreview(true);
                    toolbox.getCurrentTool().touchStart(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    toolbox.getCurrentTool().touchMove(event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    toolbox.getCurrentTool().setPreview(false);
                    toolbox.getCurrentTool().touchEnd(event);
                    break;

                default:
                    toolbox.getCurrentTool().touchMove(event);
            }
        }
        return true;
    }

    public void addShape(Shape s) {
        picture.add(s);
    }

    public ToolBox getToolBox() {
        return toolbox;
    }

    public void erase() {
        picture.clear();
    }

    public Picture getPicture() {
        return picture;
    }

    public void setViewMode(Boolean val){
        this.viewMode = val;
    }
}
