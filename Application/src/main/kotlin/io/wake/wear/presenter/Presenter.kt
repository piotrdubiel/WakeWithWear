package io.wake.wear.presenter

trait Presenter<T> {

    val view: T
//    val bus: Bus

    fun onResume(){
//        bus.register(this)
    }

    fun onPause(){
//        bus.unregister(this)
    }
}