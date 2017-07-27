package moocollege.cn.qqstepview03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zsd on 2017/7/26 10:21
 * desc:仿照QQ计步器
 */

public class QQStepView extends View {

    //外圈圆的颜色
    private int mOuterColor = Color.BLUE;
    //覆盖在外圈上面的圆的默认颜色
    private int mInnerColor = Color.RED;
    //里面文字的颜色
    private int mInnerTextColor;
    //文字的大小
    private int mInnerTextSize;
    //圆的宽度
    private int mBorderWidth = 20; //圆弧宽度这里是px
    //当前部署
    private int mCurrentStepCount = 0;
    //总的步数
    private int mMaxStepCount = 0;
    private Paint mOuterPain, mInnerPaint, mTextPaint;


    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mInnerTextColor = array.getColor(R.styleable.QQStepView_innerTextColor, mInnerTextColor);
        mInnerTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_innerTextSize, mInnerTextSize);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        array.recycle();

        //外圈画笔
        mOuterPain = new Paint();
        //设置抗锯齿
        mOuterPain.setAntiAlias(true);
        //设置外圆画笔颜色
        mOuterPain.setColor(mOuterColor);
        //设置画笔宽度
        mOuterPain.setStrokeWidth(mBorderWidth);
        //设置画笔开始画的地方为圆形
        mOuterPain.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔空心
        mOuterPain.setStyle(Paint.Style.STROKE);


        //覆盖圆的画笔
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mInnerTextColor);
        mTextPaint.setTextSize(mInnerTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得控件宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设置宽高，这里把控件设置为正方形，所以宽高的最小值为控件设置宽高
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBorderWidth / 2;
        //绘制的区域
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        //画最外面的圆弧
        canvas.drawArc(rectF, 135, 270, false, mOuterPain);
        //画内圆
        if (mMaxStepCount == 0) return;
        //扫过的角度
        float sweepAngle = (float) mCurrentStepCount / mMaxStepCount;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnerPaint);
        //画文字
        String stepText = mCurrentStepCount + "";
        Rect textBouunds = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), textBouunds);
        int x = getWidth() / 2 - textBouunds.width() / 2;
        //文字基线
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        //这里注意一下文字的top为负值
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, x, baseLine, mTextPaint);
    }

    public synchronized void setmCurrentStepCount(int mCurrentStepCount) {
        this.mCurrentStepCount = mCurrentStepCount;
        //不断地去绘制
        invalidate();
    }

    public synchronized void setmMaxStepCount(int mMaxStepCount) {
        this.mMaxStepCount = mMaxStepCount;
    }
}
