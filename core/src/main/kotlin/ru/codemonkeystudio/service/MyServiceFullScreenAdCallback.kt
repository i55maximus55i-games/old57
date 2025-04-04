package ru.codemonkeystudio.service

interface MyServiceFullScreenAdCallback {

    fun onClose(wasShown: Boolean)
    fun onOpen()
    fun onError(error: String)
    fun onOffline()

}
