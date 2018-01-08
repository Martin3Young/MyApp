package yunfucloud.com.myapp.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import yunfucloud.com.myapp.R;

/**
 * Created by jingjing on 2017/9/11.
 */

public class GranzortThreeView extends View {

    private Paint paint;
    private int mColor = Color.WHITE;

    private Path innerCircle;//内圆 path
    private Path innerCircle2;//内圆 path
    private Path outerCircle;//外圆 path
    private Path trangle1;//第一个三角形的 Path
    private Path trangle2;//第二个三角形的 Path
    private Path drawPath;//用于截取路径的 Path

    private PathMeasure pathMeasure;
    private PathMeasure pathMeasure2;

    private float mViewWidth;
    private float mViewHeight;

    private long duration = 3000;
    private ValueAnimator valueAnimator;

    private Handler mHanlder;

    private float distance;//当前动画执行的百分比取值为0-1
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private Animator.AnimatorListener animatorListener;

    private State mCurrentState = State.CIRCLE_STATE;

    //三个阶段的枚举
    private enum State {
        CIRCLE_STATE,
        TRANGLE_STATE,
        FINISH_STATE
    }

    public GranzortThreeView(Context context) {
        this(context, null);
    }

    public GranzortThreeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GranzortThreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GranzortThreeView);
        mColor = a.getColor(R.styleable.GranzortThreeView_three_color,Color.WHITE);
        a.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));
        canvas.save();
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        switch (mCurrentState) {
            case CIRCLE_STATE:
                drawPath.reset();
                pathMeasure.setPath(innerCircle, false);
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);
                pathMeasure.setPath(outerCircle, false);
                drawPath.reset();
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);
                break;
            case TRANGLE_STATE:
                canvas.drawPath(innerCircle, paint);
                canvas.drawPath(outerCircle, paint);
//                canvas.drawPath(innerCircle2, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle1, false);
                float stopD = distance * pathMeasure.getLength();
                float startD = stopD - (0.5f - Math.abs(0.5f - distance)) * 200;
                pathMeasure.getSegment(startD, stopD, drawPath, true);
                canvas.drawPath(drawPath, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle2, false);
                pathMeasure.getSegment(startD, stopD, drawPath, true);
                canvas.drawPath(drawPath, paint);
                break;
            case FINISH_STATE:
                canvas.drawPath(innerCircle, paint);
//                canvas.drawPath(innerCircle2, paint);
                canvas.drawPath(outerCircle, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle1, false);
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle2, false);
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);

//                drawPath.reset();
//                pathMeasure.setPath(innerCircle2, false);
//                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
//                canvas.drawPath(drawPath, paint);
                break;

        }

        canvas.restore();

    }

    private void init() {

        initPaint();    //初始化画笔

        initPath();    //初始化路径

        initHandler();

        initAnimatorListener();

        initAnimator();

        mCurrentState = State.CIRCLE_STATE;
        valueAnimator.start();

    }

    private void initHandler() {
        mHanlder = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (mCurrentState) {
                    case CIRCLE_STATE:
                        mCurrentState = State.TRANGLE_STATE;
                        valueAnimator.start();
                        break;
                    case TRANGLE_STATE:
                        mCurrentState = State.FINISH_STATE;
                        valueAnimator.start();
                        break;
                }
            }
        };
    }

    private void initAnimatorListener() {
        animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                distance = (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("---star:",mCurrentState+"_");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("---end:",mCurrentState+"_");
                mHanlder.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);

        valueAnimator.addUpdateListener(animatorUpdateListener);

        valueAnimator.addListener(animatorListener);
    }

    private void initPath() {
        innerCircle = new Path();    //内圈圆
        innerCircle2 = new Path();    //内圈圆
        outerCircle = new Path();    //外圈圆
        trangle1 = new Path();
        trangle2 = new Path();
        drawPath = new Path();

        pathMeasure = new PathMeasure();
        pathMeasure2 = new PathMeasure();

        RectF innerRect = new RectF(-220, -220, 220, 220);
        RectF outerRect = new RectF(-280, -280, 280, 280);
        innerCircle.addArc(innerRect, 150, -359.9F);     // 不能取360f，否则可能造成测量到的值不准确
        outerCircle.addArc(outerRect, 60, -359.9F);

        RectF innerRect2 = new RectF(-220f/(float) Math.sqrt(3), -220/(float) Math.sqrt(3), 220/(float) Math.sqrt(3), 220/(float) Math.sqrt(3));
        innerCircle2.addArc(innerRect2, 120, -359.9F);     // 不能取360f，否则可能造成测量到的值不准确
        pathMeasure.setPath(innerCircle, false);
        pathMeasure2.setPath(innerCircle2,false);

        float[] pos = new float[2];
        pathMeasure.getPosTan((5f / 6f) * pathMeasure.getLength(), pos, null);        // 获取开始位置的坐标
        trangle1.moveTo(pos[0], pos[1]);                                               //移动到该点

        pathMeasure.getPosTan((1f / 2f) * pathMeasure.getLength(), pos, null);
        trangle1.lineTo(pos[0], pos[1]);

//        pathMeasure.getPosTan((1f / 6f) * pathMeasure.getLength(), pos, null);
//        trangle1.lineTo(pos[0], pos[1]);

        pathMeasure2.getPosTan((1f / 6f) * pathMeasure2.getLength(), pos, null);
        trangle1.lineTo(pos[0], pos[1]);

        pathMeasure2.getPosTan((2f / 3f) * pathMeasure2.getLength(), pos, null);
        trangle1.lineTo(pos[0], pos[1]);

        pathMeasure.getPosTan(0, pos, null);
        trangle1.lineTo(pos[0], pos[1]);
//        trangle1.close();

        pathMeasure.getPosTan((2f / 3f) * pathMeasure.getLength(), pos, null);
        Matrix matrix = new Matrix();
        matrix.preScale(1,-1);
        trangle1.transform(matrix, trangle2);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        paint.setShadowLayer(15, 0, 0, Color.WHITE);//白色光影效果
    }

    public void resetView(){
        mCurrentState = State.CIRCLE_STATE;
        valueAnimator.start();
    }

}

