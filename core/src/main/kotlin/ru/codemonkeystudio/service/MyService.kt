package ru.codemonkeystudio.service

import com.badlogic.gdx.Gdx

open class MyService {

    open fun isInitFinished(): Boolean {
        return true
    }

    open fun gameReady() {
        Gdx.app.log("GameService", "Game Ready")
    }

    open fun showFullScreenAd(fullscreenAdCallback: MyServiceFullScreenAdCallback) {
        Gdx.app.log("GameService AD", "Show fullscreen ad")
    }

    open fun showRewardedAd(rewardedAdCallback: MyServiceRewardedAdCallback) {
        Gdx.app.log("GameService AD", "Show rewarded ad")

        rewardedAdCallback.apply {
            onOpen()
            onRewarded()
            onClose()
        }
    }

}

