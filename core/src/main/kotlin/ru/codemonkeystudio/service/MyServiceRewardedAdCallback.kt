package ru.codemonkeystudio.service

interface MyServiceRewardedAdCallback {

    fun onClose()
    fun onOpen()
    fun onError(error: String)
    fun onRewarded()

}
