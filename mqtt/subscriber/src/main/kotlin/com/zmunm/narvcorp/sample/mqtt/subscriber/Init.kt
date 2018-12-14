package com.zmunm.narvcorp.sample.mqtt.subscriber

import com.zmunm.narvcorp.sample.mqtt.model.MqttModel
import com.zmunm.narvcorp.sample.core.option.BaseOption
import com.zmunm.narvcorp.sample.core.option.BaseOption.MySQL
import com.zmunm.narvcorp.sample.core.option.BaseOption.MQTT
import com.zmunm.narvcorp.sample.mqtt.subscriber.config.DatabaseConfig
import com.zmunm.narvcorp.sample.mqtt.subscriber.controller.MqttSubscriber
import org.apache.commons.daemon.Daemon
import org.apache.commons.daemon.DaemonContext
import org.eclipse.paho.client.mqttv3.MqttException
import java.util.*

val baseOption = BaseOption()
fun main(args: Array<String>) {
	if(baseOption.init(args)) {
		DatabaseConfig.option.run {
			url = "jdbc:mysql://${baseOption.mysqlMap[MySQL.Domain.name]?:MQTT.Domain.value}:" +
					"${baseOption.mysqlMap[MySQL.Port.name]?:MQTT.Port.value}/sample?allowMultiQueries=true"
			username = baseOption.mysqlMap[MySQL.UserName.name]?:MQTT.UserName.value
			password = baseOption.mysqlMap[MySQL.Password.name]?:MQTT.Password.value
		}
		Init.start()
	}
}

fun stop(args: Array<String>) {
	Init.stop()
}

object Init:Daemon{
    private var serviceThread:Thread? = null
	private val controller : MqttModel.Subscribe by lazy{
		MqttSubscriber(MqttModel(baseOption.mqttMap[MQTT.Domain.name]?:MQTT.Domain.value).apply {
			quietMode = baseOption.mqttMap[MQTT.LogLevel.name]?:MQTT.LogLevel.value.toInt()==2
			ssl = (baseOption.mqttMap[MQTT.SSL.name]?:MQTT.SSL.value).toBoolean()
			port = (baseOption.mqttMap[MQTT.Port.name]?:MQTT.Port.value).toInt()
			userName = baseOption.mqttMap[MQTT.UserName.name]?:MQTT.UserName.value
			password = baseOption.mqttMap[MQTT.Password.name]?:MQTT.Password.value
		}).mqttAdapter {
			println(it)
		}}
	private val service = Runnable{
		println("service init")
		try{
			controller.connect()
			controller.subscribe("/test")
		}catch(e:InterruptedException){
			println("interrupt")
			//destroy()
		}catch (e: MqttException){
			println(Date())
			println("reconnecting....")
			println(e)
			Thread.sleep(3 *1000L)
			start()
		}
	}

	override fun init(arg0:DaemonContext){
		println("linux")
	}

	override fun start(){
		serviceThread?.interrupt()
		serviceThread = null
		serviceThread = Thread(service).apply{start()}
	}

	override fun stop(){
        serviceThread?.interrupt()
	}

	override fun destroy(){
		serviceThread = null
		controller.disconnect()
	}
}
