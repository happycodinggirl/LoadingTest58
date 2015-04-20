package com.lily.huangxingli.loadingtest58;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by huangxingli on 2015/4/20.
 */
public class LoadingView extends View {

    float jumpHeight;
    int heigh;
    int width;

    boolean animating;
     enum SHAPE{
        CIRCLE,
        RECT,
        TRIANGLE
    };
    SHAPE shapeType=SHAPE.CIRCLE;


    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.LoadingView);
        jumpHeight=typedArray.getDimension(R.styleable.LoadingView_jumphight,20);
        typedArray.recycle();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        heigh=getHeight();
        width=getWidth();
        Log.v("TAG", "====WIDTH IS ---" + width + "====height is ---" + heigh);
        canvas.save();
        canvas.translate(width / 2, heigh / 2);
        switch (shapeType){
            case CIRCLE:
                Log.v("TAG","-----CIRCLE----");
                drawCircle(canvas);
                break;
            case RECT:
               Log.v("TAG","---RECT 00000");
                drawRect(canvas);

                break;
            case TRIANGLE:
                Log.v("TAG","-----TRIANGLE----");
                drawTriangle(canvas);

                break;
        }
        startAnimation();


        super.onDraw(canvas);

    }

    private void drawCircle(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, 100, paint);
        canvas.restore();
    }
    private void drawRect(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(-50,-50,50, 50, paint);

    }
    private void drawTriangle(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        Path path=new Path();
        path.moveTo(-50,50);
        path.lineTo(50, 50);
        path.lineTo(0, -50);
        path.close();
        canvas.drawPath(path, paint);
        canvas.restore();


    }
    public void startAnimation(){
       // if (!animating) {
            final float start=0;
            float end=-jumpHeight;
            Log.v("TAG","START IS ---"+start+"----end is ---"+end);
            final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "y",start,end,start);
            final ObjectAnimator rotationAnimator=ObjectAnimator.ofFloat(this,"rotation",0,90,180,90,0);
            rotationAnimator.setDuration(2000);
            //objectAnimator.reverse();
            objectAnimator.setDuration(2000);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (shapeType == SHAPE.CIRCLE) {
                        shapeType = SHAPE.RECT;
                    } else if (shapeType == SHAPE.RECT) {
                        shapeType = SHAPE.TRIANGLE;
                    } else if (shapeType == SHAPE.TRIANGLE) {
                        shapeType = SHAPE.CIRCLE;
                    }
                    //  shapeType = shapeType == SHAPE.CIRCLE ? SHAPE.RECT : shapeType == SHAPE.RECT ? SHAPE.TRIANGLE : shapeType == SHAPE.TRIANGLE ? SHAPE.CIRCLE : SHAPE.RECT;
                    //  objectAnimator.reverse();
                    objectAnimator.start();
                    rotationAnimator.start();
                    invalidate();
                    Log.v("TAG", "===ONANIMATION 3END");
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
           //objectAnimator.start();
        rotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                    rotationAnimator.start();
                Log.v("TAG","====ROTATION ANIMATION END ----");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet animationSet=new AnimatorSet();
        animationSet.play(rotationAnimator).after(objectAnimator);
        animationSet.start();

    }




}
