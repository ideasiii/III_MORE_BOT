package org.iii.bot;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * Created by jugo on 2017/11/1
 */

public class RobotHead extends RelativeLayout
{
    private static int mCurX = 0;
    private static int mCurY = 0;
    
    public RobotHead(Context context)
    {
        super(context);
        init(context);
    }
    
    public RobotHead(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }
    
    public RobotHead(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    public RobotHead(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    
    
    private void init(Context context)
    {
        LayoutParams layoutParamsP = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParamsP);
        setBackgroundColor(Color.rgb(108, 147, 213));
    }
    
    public void setBackground(int nColor)
    {
        setBackgroundResource(nColor);
    }
}
