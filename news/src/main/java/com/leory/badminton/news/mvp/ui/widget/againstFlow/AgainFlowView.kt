package com.leory.badminton.news.mvp.ui.widget.againstFlow

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.leory.badminton.news.R
import com.leory.commonlib.utils.ScreenUtils
import java.util.*

/**
 * Describe : 对阵图
 * Author : leory
 * Date : 2019-05-24
 */
class AgainFlowView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var linePaint: Paint? = null
    private var textPaint: Paint? = null
    private var dividerPaint: Paint? = null
    private var lineColor: Int = 0//线的颜色
    private var textColor: Int = 0//文本的颜色
    private var dividerTextColor: Int = 0//分隔文字颜色
    private var textSize: Int = 0//文字大小
    private var dividerTextSize: Int = 0//分隔文字大小
    private var lineWidth: Int = 0//线宽
    private var itemHeight: Int = 0//一项高度
    private var dividerHeight: Int = 0//分割线高度
    private var itemWidth: Int = 0//一项宽度
    private var imgWidth: Int = 0//图片的宽度
    private var imgHeight: Int = 0//图片的高度

    private var textPadding: Float = 0.toFloat()//文字的间距
    private var itemCount = 0//选手数量
    private val colCount = 3//列的数量
    private var againstData: List<List<AgainstFlowBean>>? = null//对阵数据

    lateinit var mainHandler: Handler

    /**
     * 测试数据
     *
     * @return
     */
    private val data: List<List<AgainstFlowBean>>
        get() {
            val count = 32
            val data = ArrayList<List<AgainstFlowBean>>()
            val totalCol = (Math.log(count.toDouble()) / Math.log(2.0)).toInt()
            for (col in 0 until totalCol) {
                val rowData = ArrayList<AgainstFlowBean>()
                var i = 0
                while (i < count / Math.pow(2.0, col.toDouble())) {
                    val bean = AgainstFlowBean()
                    bean.isDouble = true
                    bean.name1 = "col:" + (col + 1) + "  row:" + (i + 1)
                    bean.name2 = "col:" + (col + 1) + "  row:" + (i + 1) + "2"
                    if (col != 0) {
                        bean.score = "21:4 21:15"
                    }
                    rowData.add(bean)
                    i++
                }
                data.add(rowData)
            }
            return data
        }

    init {
        init()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val measureWidth = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val measureHeight = itemHeight * itemCount * 2 + dividerHeight * 2
        setMeasuredDimension(measureWidth, measureHeight)


    }


    override fun onDraw(canvas: Canvas) {
        //        drawCol1(canvas);
        //        drawCol2(canvas);
        //        drawCol3(canvas);
        drawColLines(canvas)//画线
        drawText(canvas)//画文字
        drawDivider(canvas)//画分隔线

    }

    private fun init() {
        lineColor = resources.getColor(R.color.deep_gray)
        textColor = resources.getColor(R.color.txt_gray)
        dividerTextColor = resources.getColor(R.color.black)
        textSize = ScreenUtils.dp2px(context, 11f)
        dividerTextSize = ScreenUtils.dp2px(context, 14f)
        imgWidth = ScreenUtils.dp2px(context, 16f)
        imgHeight = ScreenUtils.dp2px(context, 8f)
        lineWidth = ScreenUtils.dp2px(context, 2f)
        textPadding = ScreenUtils.dp2px(context, 4f).toFloat()
        itemHeight = ScreenUtils.dp2px(context, 28f)
        dividerHeight = ScreenUtils.dp2px(context, 32f)

        itemWidth = ScreenUtils.getScreenWidth(context) / (colCount - 1) - ScreenUtils.dp2px(context, 30f)
        linePaint = Paint()
        linePaint!!.color = lineColor
        linePaint!!.strokeWidth = lineWidth.toFloat()
        textPaint = Paint()
        textPaint!!.isAntiAlias = true
        textPaint!!.color = textColor
        textPaint!!.textSize = textSize.toFloat()
        dividerPaint = Paint()
        dividerPaint!!.isAntiAlias = true
        dividerPaint!!.color = dividerTextColor
        dividerPaint!!.textSize = dividerTextSize.toFloat()

        mainHandler = Handler(context.mainLooper)
    }

    /**
     * 设置数据
     *
     * @param data
     */
    fun setAgainstData(data: List<List<AgainstFlowBean>>) {
        this.againstData = data
        if (againstData != null && againstData!!.size > 0) {
            val rowData = againstData!![0]
            if (rowData.size > 0) {
                itemCount = rowData.size
            }

        }
        requestLayout()
        invalidate()
        requestImage()
    }

    private fun requestImage() {
        if (againstData != null) {
            for (col in 0 until colCount) {
                if (col < againstData!!.size) {
                    val rowData = againstData!![col]
                    for (i in 0 until itemCount / Math.pow(2.0, col.toDouble()).toInt()) {
                        if (rowData.size > i) {
                            val bean = rowData[i]
                            if (!bean.isDouble) {

                                if (bean.name1 != null) {


                                    if (!TextUtils.isEmpty(bean.icon1) && context != null) {

                                        Glide.with(context).asBitmap().load(bean.icon1).into(object : SimpleTarget<Bitmap>() {
                                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                                bean.bitmap1 = resource
                                                invalidate()
                                                //                                                if (finalI == itemCount / (int) (Math.pow(2, finalCol)) - 1) {
                                                //                                                    invalidate();
                                                //                                                }
                                            }
                                        })
                                    }
                                }
                            } else {
                                if (bean.name1 != null) {
                                    if (!TextUtils.isEmpty(bean.icon1) && context != null) {
                                        Glide.with(context).asBitmap().load(bean.icon1).into(object : SimpleTarget<Bitmap>() {
                                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                                bean.bitmap1 = resource
                                                invalidate()
                                            }
                                        })
                                    }

                                }
                                if (bean.name2 != null) {
                                    if (!TextUtils.isEmpty(bean.icon2) && context != null) {
                                        Glide.with(context)
                                                .asBitmap()
                                                .load(bean.icon2)
                                                .into(object : SimpleTarget<Bitmap>() {
                                                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                                        bean.bitmap2 = resource
                                                        invalidate()
                                                    }
                                                })
                                    }
                                }
                            }
                            if (col != 0 && !TextUtils.isEmpty(bean.score)) {
                                if (bean.score != null) {
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
    private fun drawDivider(canvas: Canvas) {
        if (itemCount > 4) {
            val textWidth = getTextWidth(dividerPaint, "上半区")
            canvas.drawText("上半区", ((ScreenUtils.getScreenWidth(context) - textWidth) / 2).toFloat(), (dividerHeight / 2 + dividerTextSize).toFloat(), dividerPaint!!)
            canvas.drawText("下半区", ((ScreenUtils.getScreenWidth(context) - textWidth) / 2).toFloat(), (measuredHeight / 2 + dividerTextSize).toFloat(), dividerPaint!!)
        } else if (itemCount == 4) {
            val textWidth = getTextWidth(dividerPaint, "半决赛")
            canvas.drawText("半决赛", ((ScreenUtils.getScreenWidth(context) - textWidth) / 2).toFloat(), (dividerHeight / 2 + dividerTextSize).toFloat(), dividerPaint!!)
        } else if (itemCount == 2) {
            val textWidth = getTextWidth(dividerPaint, "决赛")
            canvas.drawText("决赛", ((ScreenUtils.getScreenWidth(context) - textWidth) / 2).toFloat(), (dividerHeight / 2 + dividerTextSize).toFloat(), dividerPaint!!)
        }
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private fun drawText(canvas: Canvas) {
        if (againstData != null) {
            for (col in 0 until colCount) {
                if (col < againstData!!.size) {
                    val rowData = againstData!![col]
                    val currentColNum = itemCount / Math.pow(2.0, col.toDouble()).toInt()
                    for (i in 0 until currentColNum) {
                        if (rowData.size > i) {
                            val bean = rowData[i]
                            if (!bean.isDouble) {
                                if (bean.name1 != null) {
                                    val x = (itemWidth * col).toFloat() + textPadding + imgWidth.toFloat()
                                    var y = itemHeight * Math.pow(2.0, col.toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt() - textPadding
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += (dividerHeight * 2).toFloat()
                                    } else {
                                        y += dividerHeight.toFloat()
                                    }
                                    canvas.drawText(bean.name1, x, y, textPaint!!)

                                    if (bean.bitmap1 != null) {
                                        val left = itemWidth * col + textPadding
                                        val right = left + imgWidth
                                        var top = (itemHeight * Math.pow(2.0, col.toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt()).toFloat() - textPadding - imgHeight.toFloat()
                                        var bottom = top + imgHeight
                                        if (i >= currentColNum / 2 && itemCount > 4) {
                                            top += (dividerHeight * 2).toFloat()
                                            bottom += (dividerHeight * 2).toFloat()
                                        } else {
                                            top += dividerHeight.toFloat()
                                            bottom += dividerHeight.toFloat()
                                        }
                                        val dst = RectF(left, top, right, bottom)
                                        canvas.drawBitmap(bean.bitmap1, null, dst, textPaint)
                                    }
                                }
                            } else {
                                if (bean.name1 != null) {
                                    val x = (itemWidth * col).toFloat() + textPadding + imgWidth.toFloat()
                                    var y = (itemHeight * Math.pow(2.0, col.toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt()).toFloat() - textPadding - textSize.toFloat()
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += (dividerHeight * 2).toFloat()
                                    } else {
                                        y += dividerHeight.toFloat()
                                    }
                                    canvas.drawText(bean.name1, x, y, textPaint!!)
                                    if (bean.bitmap1 != null) {
                                        val left = itemWidth * col + textPadding
                                        val right = left + imgWidth
                                        var top = (itemHeight * Math.pow(2.0, col.toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt()).toFloat() - textPadding - textSize.toFloat() - imgHeight.toFloat()
                                        var bottom = top + imgHeight
                                        if (i >= currentColNum / 2 && itemCount > 4) {
                                            top += (dividerHeight * 2).toFloat()
                                            bottom += (dividerHeight * 2).toFloat()
                                        } else {
                                            top += dividerHeight.toFloat()
                                            bottom += dividerHeight.toFloat()
                                        }
                                        val dst = RectF(left, top, right, bottom)
                                        canvas.drawBitmap(bean.bitmap1, null, dst, textPaint)
                                    }
                                }
                                if (bean.name2 != null) {
                                    val x = (itemWidth * col).toFloat() + textPadding + imgWidth.toFloat()
                                    var y = itemHeight * Math.pow(2.0, col.toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt() - textPadding
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += (dividerHeight * 2).toFloat()
                                    } else {
                                        y += dividerHeight.toFloat()
                                    }
                                    canvas.drawText(bean.name2, x, y, textPaint!!)
                                    if (bean.bitmap2 != null) {
                                        val left = itemWidth * col + textPadding
                                        val right = left + imgWidth
                                        var top = (itemHeight * Math.pow(2.0, col.toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt()).toFloat() - textPadding - imgHeight.toFloat()
                                        var bottom = top + imgHeight
                                        if (i >= currentColNum / 2 && itemCount > 4) {
                                            top += (dividerHeight * 2).toFloat()
                                            bottom += (dividerHeight * 2).toFloat()
                                        } else {
                                            top += dividerHeight.toFloat()
                                            bottom += dividerHeight.toFloat()
                                        }
                                        val dst = RectF(left, top, right, bottom)
                                        canvas.drawBitmap(bean.bitmap2, null, dst, textPaint)
                                    }
                                }
                            }
                            if (col != 0 && !TextUtils.isEmpty(bean.score)) {
                                if (bean.score != null) {
                                    val x = itemWidth * col + textPadding
                                    var y = (itemHeight * Math.pow(2.0, col.toDouble()).toInt()).toFloat() + (itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt()).toFloat() + textPadding + textSize.toFloat()
                                    if (i >= currentColNum / 2 && itemCount > 4) {
                                        y += (dividerHeight * 2).toFloat()
                                    } else {
                                        y += dividerHeight.toFloat()
                                    }
                                    canvas.drawText(bean.score, x, y, textPaint!!)

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
    private fun drawColLines(canvas: Canvas) {
        for (col in 0 until colCount) {//画列数
            val currentColNum = itemCount / Math.pow(2.0, col.toDouble()).toInt()
            for (i in 0 until currentColNum) {
                if (col != 0) {//画竖线
                    val startX = (itemWidth * col).toFloat()
                    var startY = (itemHeight * Math.pow(2.0, (col - 1).toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt()).toFloat()
                    var stopY = (itemHeight * Math.pow(2.0, (col - 1).toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt() + itemHeight * Math.pow(2.0, col.toDouble()).toInt()).toFloat()

                    if (i >= currentColNum / 2 && itemCount > 4) {
                        startY += (dividerHeight * 2).toFloat()
                        stopY += (dividerHeight * 2).toFloat()
                    } else {
                        startY += dividerHeight.toFloat()
                        stopY += dividerHeight.toFloat()
                    }
                    canvas.drawLine(startX, startY, startX, stopY, linePaint!!)

                }
                //画横线
                val startX = (itemWidth * col).toFloat()
                val stopX = (itemWidth * col + itemWidth).toFloat()
                var startY = (itemHeight * Math.pow(2.0, col.toDouble()).toInt() + itemHeight * i * Math.pow(2.0, (col + 1).toDouble()).toInt()).toFloat()
                var stopY = startY
                if (i >= currentColNum / 2 && itemCount > 4) {
                    startY += (dividerHeight * 2).toFloat()
                    stopY += (dividerHeight * 2).toFloat()
                } else {
                    startY += dividerHeight.toFloat()
                    stopY += dividerHeight.toFloat()
                }
                canvas.drawLine(startX, startY, stopX, stopY, linePaint!!)
            }
        }
    }

    private fun drawCol1(canvas: Canvas) {
        for (i in 0 until itemCount) {
            canvas.drawLine(0f, (itemHeight + itemHeight * i * 2).toFloat(), itemWidth.toFloat(), (itemHeight + itemHeight * i * 2).toFloat(), linePaint!!)
        }
    }

    private fun drawCol2(canvas: Canvas) {
        for (i in 0 until itemCount / 2) {
            canvas.drawLine(itemWidth.toFloat(), (itemHeight + itemHeight * i * 4).toFloat(), itemWidth.toFloat(), (itemHeight + itemHeight * i * 4 + itemHeight * 2).toFloat(), linePaint!!)
            canvas.drawLine(itemWidth.toFloat(), (itemHeight * 2 + itemHeight * i * 4).toFloat(), (itemWidth * 2).toFloat(), (itemHeight * 2 + itemHeight * i * 4).toFloat(), linePaint!!)
        }
    }

    private fun drawCol3(canvas: Canvas) {
        for (i in 0 until itemCount / 4) {
            canvas.drawLine((itemWidth * 2).toFloat(), (itemHeight * 2 + itemHeight * i * 8).toFloat(), (itemWidth * 2).toFloat(), (itemHeight * 2 + itemHeight * i * 8 + itemHeight * 4).toFloat(), linePaint!!)
            canvas.drawLine((itemWidth * 2).toFloat(), (itemHeight * 4 + itemHeight * i * 8).toFloat(), (itemWidth * 3).toFloat(), (itemHeight * 4 + itemHeight * i * 8).toFloat(), linePaint!!)
        }
    }

    //3. 精确计算文字宽度
    private fun getTextWidth(paint: Paint?, str: String?): Int {
        var iRet = 0
        if (str != null && str.length > 0) {
            val len = str.length
            val widths = FloatArray(len)
            paint!!.getTextWidths(str, widths)
            for (j in 0 until len) {
                iRet += Math.ceil(widths[j].toDouble()).toInt()
            }
        }
        return iRet
    }
}
