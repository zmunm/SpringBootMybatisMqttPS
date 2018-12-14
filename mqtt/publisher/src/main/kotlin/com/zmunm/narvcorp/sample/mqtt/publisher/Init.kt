package com.zmunm.narvcorp.sample.mqtt.publisher

import com.zmunm.narvcorp.sample.mqtt.model.MqttModel
import com.zmunm.narvcorp.sample.core.option.BaseOption
import com.zmunm.narvcorp.sample.core.option.BaseOption.MQTT
import com.zmunm.narvcorp.sample.core.utility.CommonUtil.hexToByteArray
import com.zmunm.narvcorp.sample.mqtt.publisher.controller.MqttPublisher
import org.apache.commons.daemon.Daemon
import org.apache.commons.daemon.DaemonContext
import org.yaml.snakeyaml.Yaml
import java.io.File

val baseOption = BaseOption()
fun main(args: Array<String>) {
	if(baseOption.init(args)) Init.start()
}

fun stop(args: Array<String>) {
    Init.stop()
}

object Init:Daemon{
	private var serviceThread:Thread? = null
	private val controller : MqttModel.Publish by lazy{
		MqttPublisher(MqttModel(baseOption.mqttMap[MQTT.Domain.name]?:MQTT.Domain.value).apply {
			quietMode = baseOption.mqttMap[BaseOption.MQTT.LogLevel.name]?:MQTT.LogLevel.value.toInt()==2
			ssl = (baseOption.mqttMap[MQTT.SSL.name]?:MQTT.SSL.value).toBoolean()
			port = (baseOption.mqttMap[MQTT.Port.name]?:MQTT.Port.value).toInt()
			userName = baseOption.mqttMap[MQTT.UserName.name]?:MQTT.UserName.value
			password = baseOption.mqttMap[MQTT.Password.name]?:MQTT.Password.value
		}).mqttAdapter {
			//DataController.division(it.first,it.second)
		}}
	private val service = Runnable{
		println("service init")
		try{
			while(!Thread.currentThread().isInterrupted){
				(1..(baseOption.mqttMap[MQTT.LoopCount.name]?:MQTT.LoopCount.value).toInt()).forEach {
					try {
						Yaml().loadAll(File("""${System.getProperty("user.dir")}\data.yml""")
								.readLines()
								.joinToString("\n")
						)
								.first()
								.let {
									it as? LinkedHashMap<*, *>?:throw TypeCastException("YAML 파싱 오류")
								}
								.map {it.key to (it.value as? ArrayList<*>?:throw TypeCastException("$it\n토픽오류"))}
								.forEach {
									it.second.forEach{msg->
										controller.publish(
												it.first.toString() to msg.toString().hexToByteArray(",")
										)
									}
								}
					}catch (e:TypeCastException){
						println(e)
						e.printStackTrace()
					}
					println(it)
					if(it == (baseOption.mqttMap[MQTT.LoopCount.name]?:MQTT.LoopCount.value).toInt()) stop()
					Thread.sleep((baseOption.mqttMap[MQTT.LoopInterval.name]?:MQTT.LoopInterval.value).toLong())
				}
			}
		}catch(e:InterruptedException){
			destroy()
			println("interrupt")
		}
	}

	override fun init(arg0:DaemonContext){
		println("linux")
	}

	override fun start(){
		controller.connect()
		//controller.subscribe(BASE_TOPIC)
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
