package com.gzeinnumer.coroutineskt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo 3
        //buat aksi untuk memanggil corou
        btn.setOnClickListener{
            //todo 7
            //IO, Main, Default
            //IO = recuest to database/ input output request
            //Main = foru UI
            //1 > untuk menggunakan funsi suspend kamu harus memakai cara seperti dibawah
            CoroutineScope(Default).launch {
                //2 > memanggil  fungtion suspend pada todo6
                fakeApiRequest()
            }
        }
    }

    //todo 4
    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

    //todo 6 // you have to delacre suspend ini fun to use suspend fun
    private suspend fun fakeApiRequest(){
        //3 > memaggil fun suspend getResult1FromApi
        val result1 = getResult1FromApi()
        //6 > delay 1 detik log akan ditampilkan
        println("debug: $result1")
        //todo 10
        //7 > lalu di set di textview
        setTextOnMainThread(result1)

        //todo 12
        //12 > bagian ini akan menunggu antrian
        val result2 = getResutl2FromApi(result1)
        println("debug: $result2")
        //15 > dikirin untuk setText
        //proses sama dengan 8-11
        setTextOnMainThread(result2)
    }

    //todo 3
    private suspend fun getResult1FromApi() :String{
        //todo 5
        logThread("getResult1FromApi")

        //4 > delay 1 detik
        delay(1000)
        //same
        //Thread.sleep(1000) = delay(1000)

        //5 > akan di kirim sebagai hasil
        return "Result #1"
    }


    //todo 9
    //8 > tidak bisa settext UI disuspend jadi harus memanggil dengan function
    private suspend fun setTextOnMainThread(input: String){
        //CoroutineScope(Main).launch {
        //9 > set value ke UI dengan fun
        withContext(Main){
            setNewText(input)
        }
    }

    //todo 8
    //10 > disini bisa lakukan proses setText
    private fun setNewText(input: String){
        val newText = textTv.text.toString() + "\n$input"
        //11 > value akan sampai ke textview
        textTv.text = newText
    }

    //todo 11
    //13 > data akan direquest
    private suspend fun getResutl2FromApi(result: String):String{
        logThread("getResutl2FromApi")
        delay(1000)
        //14 > data dikirim sebagai return
        return "Resutl #2 + $result"
    }


}
