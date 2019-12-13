package org.fitz.bubbledrawable

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * author       : Fitz Lu
 * created on   : 28/12/2018 10:26
 * description  :
 */
class BubbleDrawable: Drawable() {

    companion object TriangleLocation{
        /**
         * On the top of content bounds
         * */
        const val locTop = 0
        /**
         * On the right of content bounds
         * */
        const val locRight = 1
        /**
         * On the bottom of content bounds
         * */
        const val locBottom = 2
        /**
         * On the left of content bounds
         * */
        const val locLeft = 3
    }

    /**
     * Paint to draw path
     * */
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * Bubble path
     * */
    private val mPath  = Path()
    /**
     * Width of the triangle
     * */
    private var triangleWidth = 0f
    /**
     * Height of the triangle
     * */
    private var triangleHeight = 0f
    /**
     * Should in range 0...1
     * */
    private var triangleBias = 0f
    /**
     * Determine where the triangle is placed,
     * default is [TriangleLocation.locTop]
     * @see TriangleLocation
     * */
    private var triangleLoc  = TriangleLocation.locTop
    /**
     * Index for [cornersRadius]
     * */
    private var leftTop = 0
    private var rightTop = 1
    private var rightBottom = 2
    private var leftBottom = 3
    /**
     * left-top, right-top, right-bottom, left-bottom
     * */
    private var cornersRadius = floatArrayOf(0f, 0f, 0f, 0f)
    /**
     * Left top corner rectF
     * */
    private val leftTopRectF = RectF()
    /**
     * Right top corner rectF
     * */
    private val rightTopRectF = RectF()
    /**
     * Right bottom corner rectF
     * */
    private val rightBottomRectF = RectF()
    /**
     * Left bottom corner rectF
     * */
    private val leftBottomRectF = RectF()

    /**
     * Stroke color
     * */
    private var strokeColor = Color.TRANSPARENT
    /**
     * Stroke width
     * */
    private var strokeWidth = 0f
    /**
     * Solid color
     * */
    private var solidColor = Color.TRANSPARENT

    override fun draw(canvas: Canvas) {
        measure()

        mPath.reset()
        mPath.moveTo(leftTopRectF.left, leftTopRectF.bottom - cornersRadius[leftTop])
        mPath.arcTo(leftTopRectF, 180f, 90f)

        if (triangleLoc == TriangleLocation.locTop) {
            mPath.lineTo(bounds.left + bounds.width() * triangleBias - triangleWidth / 2, bounds.top + triangleHeight)
            mPath.lineTo(bounds.left + bounds.width() * triangleBias, bounds.top.toFloat())
            mPath.lineTo(bounds.left + bounds.width() * triangleBias + triangleWidth / 2, bounds.top + triangleHeight)
        }
        mPath.lineTo(rightTopRectF.left + cornersRadius[rightTop], rightTopRectF.top)

        mPath.arcTo(rightTopRectF, 270f, 90f)

        if (triangleLoc == TriangleLocation.locRight) {
            mPath.lineTo(rightTopRectF.right, bounds.top + bounds.height() * triangleBias - triangleHeight / 2)
            mPath.lineTo(bounds.right.toFloat() - strokeWidth, bounds.top + bounds.height() * triangleBias)
            mPath.lineTo(rightBottomRectF.right, bounds.top + bounds.height() * triangleBias + triangleHeight / 2)
        }
        mPath.lineTo(
            rightBottomRectF.right,
            rightBottomRectF.top + cornersRadius[rightBottom])

        mPath.arcTo(rightBottomRectF, 0f, 90f)

        if (triangleLoc == TriangleLocation.locBottom){
            mPath.lineTo(bounds.right - bounds.width() * triangleBias + triangleWidth / 2, rightBottomRectF.bottom)
            mPath.lineTo(bounds.right - bounds.width() * triangleBias, bounds.bottom - strokeWidth)
            mPath.lineTo(bounds.right - bounds.width() * triangleBias - triangleWidth / 2, rightBottomRectF.bottom)
        }
        mPath.lineTo(
            leftBottomRectF.right - cornersRadius[leftBottom],
            leftBottomRectF.bottom)

        mPath.arcTo(leftBottomRectF, 90f, 90f)

        if (triangleLoc == TriangleLocation.locLeft){
            mPath.lineTo(
                leftBottomRectF.left,
                leftBottomRectF.bottom - bounds.height() * triangleBias + triangleHeight / 2)
            mPath.lineTo(bounds.left.toFloat(), leftBottomRectF.bottom - bounds.height() * triangleBias)
            mPath.lineTo(
                leftTopRectF.left, leftBottomRectF.bottom - bounds.height() * triangleBias - triangleHeight / 2)
        }

        mPath.close()

        mPaint.color = solidColor
        mPaint.style = Paint.Style.FILL
        canvas.drawPath(mPath, mPaint)

        mPaint.color = strokeColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = strokeWidth / 2f
        canvas.drawPath(mPath, mPaint)
    }

    private fun measure(){
        measureLeftTopCorner()
        measureRightTopCorner()
        measureRightBottomCorner()
        measureLeftBottomCorner()
    }

    private fun measureLeftTopCorner(){
        bounds.apply {
            leftTopRectF.left = if (triangleLoc == TriangleLocation.locLeft) left.toFloat() + triangleWidth else left.toFloat()
            leftTopRectF.top = if (triangleLoc == TriangleLocation.locTop) top.toFloat() + triangleHeight else top.toFloat() + strokeWidth
            leftTopRectF.right = leftTopRectF.left + cornersRadius[leftTop] * 2
            leftTopRectF.bottom = leftTopRectF.top + cornersRadius[leftTop] * 2

            leftTopRectF.left += strokeWidth
            leftTopRectF.right += strokeWidth
        }
    }

    private fun measureRightTopCorner(){
        bounds.apply {
            rightTopRectF.right = if (triangleLoc == TriangleLocation.locRight) right.toFloat() - triangleWidth else right.toFloat()
            rightTopRectF.top = if (triangleLoc == TriangleLocation.locTop) top.toFloat() + triangleHeight else top.toFloat() + strokeWidth
            rightTopRectF.left = rightTopRectF.right - cornersRadius[rightTop] * 2
            rightTopRectF.bottom = rightTopRectF.top + cornersRadius[rightTop] * 2

            rightTopRectF.left -= strokeWidth
            rightTopRectF.right -= strokeWidth
        }
    }

    private fun measureRightBottomCorner(){
        bounds.apply {
            rightBottomRectF.right = if (triangleLoc == TriangleLocation.locRight) right.toFloat() - triangleWidth else right.toFloat()
            rightBottomRectF.bottom = if (triangleLoc == TriangleLocation.locBottom) bottom.toFloat() - triangleHeight else bottom.toFloat()
            rightBottomRectF.left = rightBottomRectF.right - cornersRadius[rightBottom] * 2
            rightBottomRectF.top = rightBottomRectF.bottom - cornersRadius[rightBottom] * 2

            rightBottomRectF.left -= strokeWidth
            rightBottomRectF.right -= strokeWidth
            rightBottomRectF.top -= strokeWidth
            rightBottomRectF.bottom -= strokeWidth
        }
    }

    private fun measureLeftBottomCorner(){
        bounds.apply {
            leftBottomRectF.left = if (triangleLoc == TriangleLocation.locLeft) left.toFloat() + triangleWidth else left.toFloat()
            leftBottomRectF.bottom = if (triangleLoc == TriangleLocation.locBottom) bottom.toFloat() - triangleHeight else bottom.toFloat()
            leftBottomRectF.top = leftBottomRectF.bottom - cornersRadius[leftBottom] * 2
            leftBottomRectF.right = leftBottomRectF.left + cornersRadius[leftBottom] * 2

            leftBottomRectF.left += strokeWidth
            leftBottomRectF.right += strokeWidth
            leftBottomRectF.top -= strokeWidth
            leftBottomRectF.bottom -= strokeWidth
        }
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    /**
     * @param width
     * */
    fun setTriangleWidth(width: Float){
        triangleWidth = width
    }

    /**
     * @param height
     * */
    fun setTriangleHeight(height: Float){
        triangleHeight = height
    }

    /**
     * @param bias
     * */
    fun setTriangleBias(bias: Float){
        triangleBias = bias
    }

    /**
     * @param corners float array {left-top, right-top, right-bottom, left-bottom]
     * */
    fun setCorners(corners: FloatArray){
        if (corners.size != 4){
            return
        }
        cornersRadius = corners
    }

    /**
     * @param radius
     * */
    fun setLeftTopRadius(radius: Float){
        cornersRadius[leftTop] = radius
    }

    /**
     * @param radius
     * */
    fun setLeftBottomRadius(radius: Float){
        cornersRadius[leftBottom] = radius
    }

    /**
     * @param radius
     * */
    fun setRightTopRadius(radius: Float){
        cornersRadius[rightTop] = radius
    }

    /**
     * @param radius
     * */
    fun setRightBottomRadius(radius: Float){
        cornersRadius[rightBottom] = radius
    }

    /**
     * @param color
     * */
    fun setStrokeColor(color: Int){
        strokeColor = color
    }

    /**
     * @param width
     * */
    fun setStrokeWidth(width: Float){
        strokeWidth = width
    }

    /**
     * @param color
     * */
    fun setSolidColor(color: Int){
        solidColor = color
    }

    /**
     * Triangle loc
     * @see TriangleLocation
     * */
    fun setTriangleLocation(loc: Int){
        triangleLoc = loc
    }

}