package com.leory.badminton.news.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Describe : 对阵图
 * 参考 https://github.com/fgnna/championroadview
 * Author : zhuly
 * Date : 2019-05-23
 */
public class AgainstView extends View {

    private int defaultPaintStrokeWidth = 4;
    private int lightPaintStrokeWidth = 2;
    private Paint mDefaultPaint;
    private Paint mLostPaint;
    private Paint mWinPaint;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mDefaultVerticalSpacingSupplement;
    private int mLightVerticalSpacingSupplement;
    private float[][] mVerticalSpacingArray;
    private float[] mHorizontalSpacingArray;
    private Position[] mPositions;

    public AgainstView(Context context) {
        this(context, null);
    }

    public AgainstView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgainstView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        defaultPaintStrokeWidth = dip2px(defaultPaintStrokeWidth);
        lightPaintStrokeWidth = dip2px(lightPaintStrokeWidth);
        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(Color.parseColor("#2c323e"));
        mDefaultPaint.setStrokeWidth(defaultPaintStrokeWidth);

        mLostPaint = new Paint();
        mLostPaint.setColor(Color.parseColor("#574832"));
        mLostPaint.setStrokeWidth(lightPaintStrokeWidth);

        mWinPaint = new Paint();
        mWinPaint.setColor(Color.parseColor("#ff9c00"));
        mWinPaint.setStrokeWidth(lightPaintStrokeWidth);
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (0 == mWidth) {
            initializeSpacing();
        }
        initDefaultLine(canvas);
        initChampionroadLine(canvas);
    }

    private void initializeSpacing() {

        mWidth = getWidth();
        mHeight = getHeight();

        float mHorizontalSpacing = mWidth / 4;
        float mVerticalSpacing = (mHeight - defaultPaintStrokeWidth) / 14f;
        Log.d("ChampionRoadView", String.valueOf(mVerticalSpacing));
        Log.d("ChampionRoadView", String.valueOf(mHeight) + "-" + String.valueOf(defaultPaintStrokeWidth));
        /**
         * 用于填补转角位接驳时由于线粗的半个差值做成的缺角情况,例如下面这种情况
         * **********************
         * ************************
         * ************************
         *                      ***
         *                      ***
         *                      ***
         */
        mDefaultVerticalSpacingSupplement = defaultPaintStrokeWidth / 2;
        mLightVerticalSpacingSupplement = lightPaintStrokeWidth / 2;


        float mVerticalSpacing2Times = mVerticalSpacing * 2;
        float mVerticalSpacing3Times = mVerticalSpacing * 3;
        float mVerticalSpacing4Times = mVerticalSpacing * 4;
        float mVerticalSpacing7Times = mVerticalSpacing * 7;
        float mVerticalSpacing8Times = mVerticalSpacing * 8;

        mHorizontalSpacingArray = new float[]{0, mHorizontalSpacing, mHorizontalSpacing * 2, mHorizontalSpacing * 3, mWidth};

        float mPosition_x_1_1 = defaultPaintStrokeWidth / 2;
        float mPosition_x_1_2 = mPosition_x_1_1 + mVerticalSpacing2Times;
        float mPosition_x_1_3 = mPosition_x_1_2 + mVerticalSpacing2Times;
        float mPosition_x_1_4 = mPosition_x_1_3 + mVerticalSpacing2Times;
        float mPosition_x_1_5 = mPosition_x_1_4 + mVerticalSpacing2Times;
        float mPosition_x_1_6 = mPosition_x_1_5 + mVerticalSpacing2Times;
        float mPosition_x_1_7 = mPosition_x_1_6 + mVerticalSpacing2Times;
        float mPosition_x_1_8 = mPosition_x_1_7 + mVerticalSpacing2Times;
        float[] mLinePosition_x_1 = new float[]{mPosition_x_1_1, mPosition_x_1_2, mPosition_x_1_3, mPosition_x_1_4, mPosition_x_1_5, mPosition_x_1_6, mPosition_x_1_7, mPosition_x_1_8};

        float mPosition_x_2_1 = mVerticalSpacing + mDefaultVerticalSpacingSupplement;
        float mPosition_x_2_2 = mPosition_x_2_1 + mVerticalSpacing4Times;
        float mPosition_x_2_3 = mPosition_x_2_2 + mVerticalSpacing4Times;
        float mPosition_x_2_4 = mPosition_x_2_3 + mVerticalSpacing4Times;
        float[] mLinePosition_x_2 = new float[]{mPosition_x_2_1, mPosition_x_2_1, mPosition_x_2_2, mPosition_x_2_2, mPosition_x_2_3, mPosition_x_2_3, mPosition_x_2_4, mPosition_x_2_4};

        float mPosition_x_3_1 = mVerticalSpacing3Times;
        float mPosition_x_3_2 = mPosition_x_3_1 + mVerticalSpacing8Times;
        float[] mLinePosition_x_3 = new float[]{mPosition_x_3_1, mPosition_x_3_1, mPosition_x_3_1, mPosition_x_3_1, mPosition_x_3_2, mPosition_x_3_2, mPosition_x_3_2, mPosition_x_3_2};
        float mPosition_x_4_1 = mVerticalSpacing7Times;

        mVerticalSpacingArray = new float[][]{mLinePosition_x_1, mLinePosition_x_2, mLinePosition_x_3, new float[]{mPosition_x_4_1, mPosition_x_4_1, mPosition_x_4_1, mPosition_x_4_1, mPosition_x_4_1, mPosition_x_4_1, mPosition_x_4_1, mPosition_x_4_1}, new float[]{mWidth, mWidth, mWidth, mWidth, mWidth, mWidth, mWidth, mWidth}};

    }


    /**
     * 初始化最底部的基线
     *
     * @param canvas
     */
    private void initDefaultLine(Canvas canvas) {
        drawLine(Position.LEVEL_4, 0, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
        drawLine(Position.LEVEL_1, 1, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
        drawLine(Position.LEVEL_2, 2, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
        drawLine(Position.LEVEL_1, 3, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
        drawLine(Position.LEVEL_3, 4, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
        drawLine(Position.LEVEL_1, 5, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
        drawLine(Position.LEVEL_2, 6, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
        drawLine(Position.LEVEL_1, 7, canvas, mDefaultPaint, mDefaultVerticalSpacingSupplement, Position.Promotion_Status_Standby);
    }

    /**
     * 按照层级level绘制线路
     *
     * @param level
     * @param position
     * @param canvas
     * @param paint
     * @param verticalSpacingSupplement 转角空缺填充宽度
     */
    private void drawLine(int level, int position, Canvas canvas, Paint paint, float verticalSpacingSupplement, int status) {


        for (int i = 0; i <= level; i++) {
            int tempNextIndex = i + 1;

            if (!(status == Position.Promotion_Status_Lost && i == level)) {
                if ((Position.LEVEL_4 + 1) > (tempNextIndex))
                    canvas.drawLine(mHorizontalSpacingArray[tempNextIndex], mVerticalSpacingArray[i][position], mHorizontalSpacingArray[tempNextIndex], mVerticalSpacingArray[tempNextIndex][position], paint);

            }
            canvas.drawLine(mHorizontalSpacingArray[i] + (i != Position.LEVEL_1 ? -verticalSpacingSupplement : 0),
                    mVerticalSpacingArray[i][position], mHorizontalSpacingArray[tempNextIndex] + verticalSpacingSupplement,
                    mVerticalSpacingArray[i][position], paint);

        }

    }


    private void initChampionroadLine(Canvas canvas) {
        if (null != mPositions) {
            for (Position position : mPositions) {
                if (null != position)
                    drawLineByPosition(position, canvas);
            }
        }

    }

    private void drawLineByPosition(Position position, Canvas canvas) {


        Paint paint;
        if (position.Promotion_Status_Lost == position.promotionStatus && position.level == Position.LEVEL_1)
            paint = mDefaultPaint;
        else
            paint = position.promotionStatus != Position.Promotion_Status_Lost ? mWinPaint : mLostPaint;
        drawLine(position.level, position.position, canvas, paint, mLightVerticalSpacingSupplement, position.promotionStatus);
    }
}
