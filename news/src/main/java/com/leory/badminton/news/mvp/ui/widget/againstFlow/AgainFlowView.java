package com.leory.badminton.news.mvp.ui.widget.againstFlow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.leory.badminton.news.R;
import com.leory.commonlib.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe : 对阵图
 * Author : leory
 * Date : 2019-05-24
 */
public class AgainFlowView extends View {

    private Paint linePaint;
    private Paint textPaint;
    private Paint dividerPaint;
    private int lineColor;//线的颜色
    private int textColor;//文本的颜色
    private int dividerTextColor;//分隔文字颜色
    private int textSize;//文字大小
    private int dividerTextSize;//分隔文字大小
    private int lineWidth;//线宽
    private int itemHeight;//一项高度
    private int dividerHeight;//分割线高度
    private int itemWidth;//一项宽度
    private int imgWidth;//图片的宽度
    private int imgHeight;//图片的高度

    private float textPadding;//文字的间距
    private int itemCount = 0;//选手数量
    private int colCount = 3;//列的数量
    List<List<AgainstFlowBean>> againstData;//对阵数据

    Handler mainHandler;

    public AgainFlowView(Context context) {
        this(context, null);
    }

    public AgainFlowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgainFlowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measureWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int measureHeight = itemHeight * itemCount * 2 + dividerHeight * 2;
        setMeasuredDimension(measureWidth, measureHeight);


    }


    @Override
    protected void onDraw(Canvas canvas) {
//        drawCol1(canvas);
//        drawCol2(canvas);
//        drawCol3(canvas);
        drawColLines(canvas);//画线
        drawText(canvas);//画文字
        drawDivider(canvas);//画分隔线

    }

    private void init() {
        lineColor = getResources().getColor(R.color.deep_gray);
        textColor = getResources().getColor(R.color.txt_gray);
        dividerTextColor = getResources().getColor(R.color.black);
        textSize = ScreenUtils.dp2px(getContext(), 11);
        dividerTextSize = ScreenUtils.dp2px(getContext(), 14);
        imgWidth = ScreenUtils.dp2px(getContext(), 16);
        imgHeight = ScreenUtils.dp2px(getContext(), 8);
        lineWidth = ScreenUtils.dp2px(getContext(), 2);
        textPadding = ScreenUtils.dp2px(getContext(), 4);
        itemHeight = ScreenUtils.dp2px(getContext(), 28);
        dividerHeight = ScreenUtils.dp2px(getContext(), 32);

        itemWidth = ScreenUtils.getScreenWidth(getContext()) / (colCount - 1) - ScreenUtils.dp2px(getContext(), 30);
        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setColor(dividerTextColor);
        dividerPaint.setTextSize(dividerTextSize);

        mainHandler = new Handler(getContext().getMainLooper());
    }

    /**
     * 测试数据
     *
     * @return
     */
    private List<List<AgainstFlowBean>> getData() {
        int count = 32;
        List<List<AgainstFlowBean>> data = new ArrayList<>();
        int totalCol = (int) (Math.log(count) / Math.log(2));
        for (int col = 0; col < totalCol; col++) {
            List<AgainstFlowBean> rowData = new ArrayList<>();
            for (int i = 0; i < count / Math.pow(2, col); i++) {
                AgainstFlowBean bean = new AgainstFlowBean();
                bean.setDouble(true);
                bean.setName1("col:" + (col + 1) + "  row:" + (i + 1));
                bean.setName2("col:" + (col + 1) + "  row:" + (i + 1) + "2");
                if (col != 0) {
                    bean.setScore("21:4 21:15");
                }
                rowData.add(bean);
            }
            data.add(rowData);
        }
        return data;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setAgainstData(List<List<AgainstFlowBean>> data) {
        this.againstData = data;
        if (againstData != null && againstData.size() > 0) {
            List<AgainstFlowBean> rowData = againstData.get(0);
            if (rowData.size() > 0) {
                itemCount = rowData.size();
            }

        }
        requestLayout();
        invalidate();
        requestImage();
    }

    private void requestImage() {
        if (againstData != null) {
            for (int col = 0; col < colCount; col++) {
                if (col < againstData.size()) {
                    List<AgainstFlowBean> rowData = againstData.get(col);
                    for (int i = 0; i < itemCount / (int) (Math.pow(2, col)); i++) {
                        if (rowData.size() > i) {
                            AgainstFlowBean bean = rowData.get(i);
                            if (!bean.isDouble()) {

                                if (bean.getName1() != null) {


                                    if (!TextUtils.isEmpty(bean.getIcon1()) && getContext() != null) {
                                        int finalCol = col;
                                        int finalI = i;
                                        Glide.with(getContext()).asBitmap().load(bean.getIcon1()).into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                bean.setBitmap1(resource);
                                                invalidate();
//                                                if (finalI == itemCount / (int) (Math.pow(2, finalCol)) - 1) {
//                                                    invalidate();
//                                                }
                                            }
                                        });
                                    }
                                }
                            } else {
                                if (bean.getName1() != null) {
                                    if (!TextUtils.isEmpty(bean.getIcon1()) && getContext() != null) {
                                        Glide.with(getContext()).asBitmap().load(bean.getIcon1()).into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                bean.setBitmap1(resource);
                                                invalidate();
                                            }
                                        });
                                    }

                                }
                                if (bean.getName2() != null) {
                                    if (!TextUtils.isEmpty(bean.getIcon2()) && getContext() != null) {
                                        Glide.with(getContext()).asBitmap().load(bean.getIcon2()).into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                bean.setBitmap2(resource);
                                                invalidate();
                                            }
                                        });
                                    }
                                }
                            }
                            if (col != 0 && !TextUtils.isEmpty(bean.getScore())) {
                                if (bean.getScore() != null) {
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    /**
     * 画分隔线
     *
     * @param canvas
     */
    private void drawDivider(Canvas canvas) {
        if (itemCount > 4) {
            int textWidth = getTextWidth(dividerPaint, "上半区");
            canvas.drawText("上半区", (ScreenUtils.getScreenWidth(getContext()) - textWidth) / 2, dividerHeight / 2 + dividerTextSize, dividerPaint);
            canvas.drawText("下半区", (ScreenUtils.getScreenWidth(getContext()) - textWidth) / 2, getMeasuredHeight() / 2 + dividerTextSize, dividerPaint);
        }else if(itemCount==4){
            int textWidth = getTextWidth(dividerPaint, "半决赛");
            canvas.drawText("半决赛", (ScreenUtils.getScreenWidth(getContext()) - textWidth) / 2, dividerHeight / 2 + dividerTextSize, dividerPaint);
        }else if(itemCount==2){
            int textWidth = getTextWidth(dividerPaint, "决赛");
            canvas.drawText("决赛", (ScreenUtils.getScreenWidth(getContext()) - textWidth) / 2, dividerHeight / 2 + dividerTextSize, dividerPaint);
        }
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        if (againstData != null) {
            for (int col = 0; col < colCount; col++) {
                if (col < againstData.size()) {
                    List<AgainstFlowBean> rowData = againstData.get(col);
                    int currentColNum = itemCount / (int) (Math.pow(2, col));
                    for (int i = 0; i < currentColNum; i++) {
                        if (rowData.size() > i) {
                            AgainstFlowBean bean = rowData.get(i);
                            if (!bean.isDouble()) {
                                if (bean.getName1() != null) {
                                    float x = itemWidth * col + textPadding + imgWidth;
                                    float y = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1) - textPadding;
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += dividerHeight * 2;
                                    } else {
                                        y += dividerHeight;
                                    }
                                    canvas.drawText(bean.getName1(), x, y, textPaint);

                                    if (bean.getBitmap1() != null) {
                                        float left = itemWidth * col + textPadding;
                                        float right = left + imgWidth;
                                        float top = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1) - textPadding - imgHeight;
                                        float bottom = top + imgHeight;
                                        if (i >= currentColNum / 2 && itemCount > 4) {
                                            top += dividerHeight * 2;
                                            bottom += dividerHeight * 2;
                                        } else {
                                            top += dividerHeight;
                                            bottom += dividerHeight;
                                        }
                                        RectF dst = new RectF(left, top, right, bottom);
                                        canvas.drawBitmap(bean.getBitmap1(), null, dst, textPaint);
                                    }
                                }
                            } else {
                                if (bean.getName1() != null) {
                                    float x = itemWidth * col + textPadding + imgWidth;
                                    float y = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1) - textPadding - textSize;
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += dividerHeight * 2;
                                    } else {
                                        y += dividerHeight;
                                    }
                                    canvas.drawText(bean.getName1(), x, y, textPaint);
                                    if (bean.getBitmap1() != null) {
                                        float left = itemWidth * col + textPadding;
                                        float right = left + imgWidth;
                                        float top = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1) - textPadding - textSize - imgHeight;
                                        float bottom = top + imgHeight;
                                        if (i >= currentColNum / 2 && itemCount > 4) {
                                            top += dividerHeight * 2;
                                            bottom += dividerHeight * 2;
                                        } else {
                                            top += dividerHeight;
                                            bottom += dividerHeight;
                                        }
                                        RectF dst = new RectF(left, top, right, bottom);
                                        canvas.drawBitmap(bean.getBitmap1(), null, dst, textPaint);
                                    }
                                }
                                if (bean.getName2() != null) {
                                    float x = itemWidth * col + textPadding + imgWidth;
                                    float y = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1) - textPadding;
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += dividerHeight * 2;
                                    } else {
                                        y += dividerHeight;
                                    }
                                    canvas.drawText(bean.getName2(), x, y, textPaint);
                                    if (bean.getBitmap2() != null) {
                                        float left = itemWidth * col + textPadding;
                                        float right = left + imgWidth;
                                        float top = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1) - textPadding - imgHeight;
                                        float bottom = top + imgHeight;
                                        if (i >= currentColNum / 2 && itemCount > 4) {
                                            top += dividerHeight * 2;
                                            bottom += dividerHeight * 2;
                                        } else {
                                            top += dividerHeight;
                                            bottom += dividerHeight;
                                        }
                                        RectF dst = new RectF(left, top, right, bottom);
                                        canvas.drawBitmap(bean.getBitmap2(), null, dst, textPaint);
                                    }
                                }
                            }
                            if (col != 0 && !TextUtils.isEmpty(bean.getScore())) {
                                if (bean.getScore() != null) {
                                    float x = itemWidth * col + textPadding;
                                    float y = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1) + textPadding + textSize;
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += dividerHeight * 2;
                                    } else {
                                        y += dividerHeight;
                                    }
                                    canvas.drawText(bean.getScore(), x, y, textPaint);

                                }
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * 画线
     *
     * @param canvas
     */
    private void drawColLines(Canvas canvas) {
        for (int col = 0; col < colCount; col++) {//画列数
            int currentColNum = itemCount / (int) (Math.pow(2, col));
            for (int i = 0; i < currentColNum; i++) {
                if (col != 0) {//画竖线
                    float startX = itemWidth * col;
                    float stopX = startX;
                    float startY = itemHeight * (int) Math.pow(2, col - 1) + itemHeight * i * (int) Math.pow(2, col + 1);
                    float stopY = itemHeight * (int) Math.pow(2, col - 1) + itemHeight * i * (int) Math.pow(2, col + 1) + itemHeight * (int) Math.pow(2, col);

                    if (i >= currentColNum / 2 && itemCount > 4) {
                        startY += dividerHeight * 2;
                        stopY += dividerHeight * 2;
                    } else {
                        startY += dividerHeight;
                        stopY += dividerHeight;
                    }
                    canvas.drawLine(startX, startY, stopX, stopY, linePaint);

                }
                //画横线
                float startX = itemWidth * col;
                float stopX = itemWidth * col + itemWidth;
                float startY = itemHeight * (int) Math.pow(2, col) + itemHeight * i * (int) Math.pow(2, col + 1);
                float stopY = startY;
                if (i >= currentColNum / 2 && itemCount > 4) {
                    startY += dividerHeight * 2;
                    stopY += dividerHeight * 2;
                } else {
                    startY += dividerHeight;
                    stopY += dividerHeight;
                }
                canvas.drawLine(startX, startY, stopX, stopY, linePaint);
            }
        }
    }

    private void drawCol1(Canvas canvas) {
        for (int i = 0; i < itemCount; i++) {
            canvas.drawLine(0, itemHeight + itemHeight * i * 2, itemWidth, itemHeight + itemHeight * i * 2, linePaint);
        }
    }

    private void drawCol2(Canvas canvas) {
        for (int i = 0; i < itemCount / 2; i++) {
            canvas.drawLine(itemWidth, itemHeight + itemHeight * i * 4, itemWidth, itemHeight + itemHeight * i * 4 + itemHeight * 2, linePaint);
            canvas.drawLine(itemWidth, itemHeight * 2 + itemHeight * i * 4, itemWidth * 2, itemHeight * 2 + itemHeight * i * 4, linePaint);
        }
    }

    private void drawCol3(Canvas canvas) {
        for (int i = 0; i < itemCount / 4; i++) {
            canvas.drawLine(itemWidth * 2, itemHeight * 2 + itemHeight * i * 8, itemWidth * 2, itemHeight * 2 + itemHeight * i * 8 + itemHeight * 4, linePaint);
            canvas.drawLine(itemWidth * 2, itemHeight * 4 + itemHeight * i * 8, itemWidth * 3, itemHeight * 4 + itemHeight * i * 8, linePaint);
        }
    }

    //3. 精确计算文字宽度
    private int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
}
