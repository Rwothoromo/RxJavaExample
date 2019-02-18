package com.example.rxjavaexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initLayout()
    }

    private fun initLayout() {
        sugar()
    }

    private fun sugar() {
        // Use RxView on the button to set up a stream.
        // Every click the user performs, will put an event to the stream
        RxView.clicks(button)
            .map { // for this map operator, for every click, the counter is incremented by 1
                incrementCounter1()
                // Could easily have been:
                // someVar -> thenActionOnVar // for example, someVar * 4, usually, we go with the default variable 'it'
            }
            .throttleFirst(1000, TimeUnit.MILLISECONDS) // this causes events to continue downstream every second and ignore any excess events
            .subscribe { // subscribe causes the stream to begin
                incrementCounter2() // this will increment less frequently because it will see fewer click events
                // Always subscribe to an observable in order to get elements from it
            }
    }

    private fun incrementCounter1() {
        var newVal = counter1.text.toString().toInt()
        newVal++
        counter1.text = newVal.toString()
    }

    private fun incrementCounter2() {
        var newVal = counter2.text.toString().toInt()
        newVal++
        counter2.text = newVal.toString()
    }
}
