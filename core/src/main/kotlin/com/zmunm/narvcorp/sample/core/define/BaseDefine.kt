package com.zmunm.narvcorp.sample.core.define

import java.text.SimpleDateFormat

object BaseDefine{
    const val LOCAL_URL = "192.168.0.37"
    const val LOCAL_MQTT_URL = "192.168.0.127"
    enum class LogMode {Debug,Default,Mute}

    val BaseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
}
