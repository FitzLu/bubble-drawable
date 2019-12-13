package org.bubbledrawable.example

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import org.fitz.bubbledrawable.BubbleDrawable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bubbleContent?.background = createBubbleDrawable(BubbleDrawable.TriangleLocation.locTop, 0.618f)
        seekBar?.max = 400
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when{
                    progress >= 300 -> {
                        bubbleContent?.background = createBubbleDrawable(BubbleDrawable.TriangleLocation.locLeft, (progress - 300f) / 100f)
                    }
                    progress >= 200 -> {
                        bubbleContent?.background = createBubbleDrawable(BubbleDrawable.TriangleLocation.locBottom, (progress - 200f) / 100f)
                    }
                    progress >= 100 -> {
                        bubbleContent?.background = createBubbleDrawable(BubbleDrawable.TriangleLocation.locRight, (progress - 100f) / 100f)
                    }
                    else -> {
                        bubbleContent?.background = createBubbleDrawable(BubbleDrawable.TriangleLocation.locTop, progress / 100f)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        bubbleContent?.background = createBubbleDrawable(BubbleDrawable.TriangleLocation.locBottom, 0.5f)
    }

    private fun createBubbleDrawable(triLoc: Int, bias: Float): BubbleDrawable {
        return BubbleDrawable().also {
            it.setTriangleWidth(resources.getDimension(R.dimen.bbd_triangle_width))
            it.setTriangleHeight(resources.getDimension(R.dimen.bbd_triangle_height))
            it.setCorners(floatArrayOf(resources.getDimension(R.dimen.bbd_dp_10), resources.getDimension(R.dimen.bbd_dp_10),
                resources.getDimension(R.dimen.bbd_dp_10), resources.getDimension(R.dimen.bbd_dp_10)))
            it.setStrokeColor(Color.BLUE)
            it.setStrokeWidth(resources.getDimension(R.dimen.bbd_dp_4))
            it.setSolidColor(Color.GREEN)
            it.setTriangleBias(bias)
            it.setTriangleLocation(triLoc)
        }
    }
}
