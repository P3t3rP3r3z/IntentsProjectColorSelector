package com.example.peterperez.color_selector_intents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import kotlin.math.roundToInt
data class ColorCustom(var redval: Int, var grnval: Int, var blueval: Int):Serializable

class MainActivity : AppCompatActivity() {
    //data class ColorCustom(var redval: Int, var grnval: Int, var blueval: Int):Serializable
    var newColor :ColorCustom= ColorCustom(0,0,0)
    var aColor:ColorCustom= ColorCustom(0,0,0)
    var bColor:ColorCustom=ColorCustom(0,0,0)
    val visibility = View.VISIBLE
    val invisibility = View.INVISIBLE
    val ACTION_COLOR="com.example.peterperez.ACTION_COLOR"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar.max=100

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var newAB=ColorCustom(0,0,0)
                if (seekBar != null) {
                    var sbp:Float = (seekBar.getProgress().toFloat()/100)
                    newAB.redval=((sbp*bColor.redval)+((1-sbp)*aColor.redval)).roundToInt()
                    newAB.grnval=((sbp*bColor.grnval)+((1-sbp)*aColor.grnval)).roundToInt()
                    newAB.blueval=((sbp*bColor.blueval)+((1-sbp)*aColor.blueval)).roundToInt()

                    Log.i("EA","$sbp")
                }
                updateAB(newAB)
                textView3.visibility=visibility
                textView3.text="MERGED COLOR VALUE IS ${newAB.redval},${newAB.grnval},${newAB.blueval}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


        set_colorA.setOnClickListener{
          //  val intent = Intent(this,ColorPicker::class.java)
            val PICK_COLOR_REQUEST =1
            val intent = Intent()
            intent.action=Intent.ACTION_VIEW
            intent.putExtra("Color a",PICK_COLOR_REQUEST)
            if (intent.extras!=null)
            {print(intent.extras)}

            startActivityForResult(intent,PICK_COLOR_REQUEST)
        }


        set_colorB.setOnClickListener {
            //val intent = Intent(this,ColorPicker::class.java)
            val PICK_COLOR_REQUEST =2
            val intent = Intent()
            intent.action=Intent.ACTION_VIEW
            intent.putExtra("Color b",PICK_COLOR_REQUEST)
            if (intent.extras!=null)
            {print(intent.extras)}

            startActivityForResult(intent,PICK_COLOR_REQUEST)
        }

    }

    fun updateAB (newAB :ColorCustom){
        var colorString= 0xff000000 + newAB.redval * 0x10000 + newAB.grnval * 0x100 + newAB.blueval
        surfaceViewAB.setBackgroundColor(colorString.toInt())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK&&requestCode==1) {
           var string = data!!.getStringExtra("Color A")!!.split(" ")
            if(string !=null) {
                val red = string[0].toInt()
                val green = string[1].toInt()
                val blue = string[2].toInt()
                aColor.redval = red
                aColor.grnval = green
                aColor.blueval = blue
            }

            var colorString = 0xff000000 + aColor.redval * 0x10000 + aColor.grnval * 0x100 + aColor.blueval
       // aColor = newColor
        surfaceViewA.setBackgroundColor(colorString.toInt())
    }
        else if(resultCode == Activity.RESULT_OK&&requestCode==2) {
          var string = data?.getStringExtra("Color B")?.split(" ")
            if(string !=null) {
                val red = string[0].toInt()
                val green = string[1].toInt()
                val blue = string[2].toInt()
                bColor.redval = red
                bColor.grnval = green
                bColor.blueval = blue
            }

            var colorString = 0xff000000 + bColor.redval * 0x10000 + bColor.grnval * 0x100 + bColor.blueval

        surfaceViewB.setBackgroundColor(colorString.toInt())
    }


    if(aColor!=bColor) {
        surfaceViewAB.visibility = visibility
        updateAB(aColor)
        textView.visibility = visibility
        textView2.visibility = visibility
        seekBar.visibility = visibility
    }

    else if(aColor==bColor)   {
        surfaceViewAB.visibility = invisibility
        updateAB(aColor)
        textView.visibility = invisibility
        textView2.visibility = invisibility
        seekBar.visibility = invisibility

    }
}


}
