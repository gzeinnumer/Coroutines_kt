package com.gzeinnumer.coroutineskt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //todo 3
//    val JOB_TIME_OUT = 1900L //total waktu adalah 2000 , jika melebihi maka batalkan
    val JOB_TIME_OUT = 2100L //total waktu adalah 2000 jika cukup, maka lanjutkan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener{
            CoroutineScope(Default).launch {
                fakeApiRequest()
            }
        }

        btn2.setOnClickListener{
            CoroutineScope(IO).launch {
                fakeApiRequest2()
            }
        }
    }


////brance 2019-09-10
    //todo 2
private suspend fun fakeApiRequest2(){
    withContext(IO){
        val job = withTimeoutOrNull(JOB_TIME_OUT){
            val result1 = getResult1FromApi()//wait
            setTextOnMainThread("Got $result1")

            val result2 = getResutl2FromApi(result1)//wait
            setTextOnMainThread("Got $result2")
        }//wait

        if(job == null){
            val cancelMessage = "Waktu load melebihi $JOB_TIME_OUT ms"
            println(cancelMessage)
            setTextOnMainThread(cancelMessage)
        }

        //ubah todo3
        //pilih salah satu 1900 atau 2000
    }

}

//////////brance master
    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)

        val result2 = getResutl2FromApi(result1)
        println("debug: $result2")
        setTextOnMainThread(result2)
    }

    private suspend fun getResult1FromApi() :String{
        logThread("getResult1FromApi")
        delay(1000)
        return "Result #1"
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private fun setNewText(input: String){
        val newText = textTv.text.toString() + "\n$input"
        textTv.text = newText
    }

    private suspend fun getResutl2FromApi(result: String):String{
        logThread("getResutl2FromApi")
        delay(1000)
        return "Resutl #2 + $result"
    }



}
