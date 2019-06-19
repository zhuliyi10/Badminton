package com.leory.commonlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.leory.commonlib.R;
import com.leory.commonlib.utils.ScreenUtils;


/**
 * Describe : 万能自定义头像
 * Author : leory
 * Date : 2019-6-19
 */
public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Context mContext;

    private Bitmap mSrcBitmap;
    private Bitmap mDstBitmap;
    private Bitmap mBordBitmap;

    private ColorFilter mColorFilter;

    private boolean mReady;
    private boolean mSetupPending;

    private boolean isBord;
    private int dscRes;
    private int bordRes;
    private int mode = 1;
    private RectF mRectF;
    private Rect mRect;
    private Bitmap mBitmap;
    private PorterDuffXfermode mXfermode;
    private PorterDuffXfermode mXfermode1;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyle, 0);
        isBord = typedArray.getBoolean(R.styleable.CustomImageView_hasBord, false);
        dscRes = typedArray.getResourceId(R.styleable.CustomImageView_dsc, R.mipmap.icon_white_circle);
        bordRes = typedArray.getResourceId(R.styleable.CustomImageView_bord, R.mipmap.icon_circle_bord);
        typedArray.recycle();
        init();
    }

    public void setMode(int mode) {
        if (this.mode == mode) {
            return;
        }
        this.mode = mode;
        invalidate();
    }

    private void init() {
        mReady = true;
        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mSrcBitmap == null) {
            return;
        }

        //背景色设为白色，方便比较效果
        canvas.drawColor(Color.TRANSPARENT);
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        int saveCount = canvas.saveLayer(mDrawableRect, mBitmapPaint, Canvas.ALL_SAVE_FLAG);
        //绘制目标图
        canvas.drawBitmap(mDstBitmap, null, mDrawableRect, mBitmapPaint);
        //设置混合模式
        if (mXfermode1 == null)
            mXfermode1 = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        mBitmapPaint.setXfermode(mXfermode1);
        //绘制源图
        canvas.drawBitmap(mSrcBitmap, null, mDrawableRect, mBitmapPaint);
        //清除混合模式
        mBitmapPaint.setXfermode(null);
        if (isBord) {
            canvas.drawBitmap(mBordBitmap, null, mDrawableRect, mBitmapPaint);
        }
        // 还原画布
        if (mode == 2) {
            if (mRectF == null) {
                mRectF = new RectF(0, getHeight() - ScreenUtils.dp2px(mContext, 20), getWidth(), getHeight());
            }
            RectF rectF = mRectF;
            if (mRect == null) {
                mRect = new Rect(0, getHeight() - ScreenUtils.dp2px(mContext, 20), getWidth(), getHeight());
            }
            if (mXfermode == null) {
                mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
            }
            mBitmapPaint.setXfermode(mXfermode);
            if (mBitmap == null) {
                mBitmap = Bitmap.createBitmap(getWidth(), ScreenUtils.dp2px(mContext, 20),
                        Bitmap.Config.ARGB_8888);
                mBitmap.eraseColor(Color.parseColor("#b3005073"));
            }
            canvas.drawBitmap(mBitmap, null, rectF, mBitmapPaint);
            mBitmapPaint.setXfermode(null);
            mBitmapPaint.setAlpha(255);
        }
        canvas.restoreToCount(saveCount);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    public void isBord(boolean isBord) {
        this.isBord = isBord;
        setup();
    }

    public void setImageBordRes(@DrawableRes int bordRes) {
        isBord = true;
        this.bordRes = bordRes;
        setup();
    }

    public void setImageDscRes(@DrawableRes int dscRes) {
        this.dscRes = dscRes;
        setup();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        setup();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }

        mColorFilter = cf;
        applyColorFilter();
        invalidate();
    }

    @Override
    public ColorFilter getColorFilter() {
        return mColorFilter;
    }

    private void applyColorFilter() {
        if (mBitmapPaint != null) {
            mBitmapPaint.setColorFilter(mColorFilter);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initializeBitmap() {
        mSrcBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        if (mSrcBitmap == null) {
            invalidate();
            return;
        }

        mBorderRect.set(calculateBounds());

        mDrawableRect.set(mBorderRect);

        Shader srcShader = new BitmapShader(mSrcBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        if (mDstBitmap == null) {
            mDstBitmap = getBitmapFromResId(dscRes);
        }
        Shader dstShader = new BitmapShader(mDstBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        if (isBord) {
            if (mBordBitmap == null) {
                mBordBitmap = getBitmapFromResId(bordRes);
            }
            Shader bordShader = new BitmapShader(mBordBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            updateShaderMatrix(mBordBitmap, bordShader);
        }

        applyColorFilter();
        updateShaderMatrix(mSrcBitmap, srcShader);
        updateShaderMatrix(mDstBitmap, dstShader);
        invalidate();
    }

    private Bitmap getBitmapFromResId(int resId) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = getContext().getDrawable(resId);
            bitmap = Bitmap.createBitmap((int) mDrawableRect.width(),
                    (int) mDrawableRect.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
        }
        return bitmap;
    }

    private RectF calculateBounds() {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }

    private void updateShaderMatrix(Bitmap bitmap, Shader shader) {
        mShaderMatrix.set(null);
        float scaleX = ((float) bitmap.getWidth()) / mDrawableRect.width();
        float scaleY = ((float) bitmap.getHeight()) / mDrawableRect.height();

        mShaderMatrix.setScale(scaleX, scaleY);
        shader.setLocalMatrix(mShaderMatrix);
    }

}