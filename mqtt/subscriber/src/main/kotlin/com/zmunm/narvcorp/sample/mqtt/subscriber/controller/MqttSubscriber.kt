package com.zmunm.narvcorp.sample.mqtt.subscriber.controller

import com.zmunm.narvcorp.sample.mqtt.model.MqttModel
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken

class MqttSubscriber @Throws(MqttException::class) constructor(val option: MqttModel){
	private var client : MqttClient? = null
	private var conOpt:MqttConnectOptions = MqttConnectOptions().apply {
		isCleanSession = false
		option.password?.run{ password = toCharArray() }
		option.userName?.run{ userName = this }
	}

	fun  mqttAdapter(onMessageArrived:(Pair<String,ByteArray>)->Unit) : MqttModel.Subscribe {
		client?.disconnect()
		client = MqttClient(
				option.url,
				"MQTT_SUB",
				MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir"))
		).apply{setCallback(object : MqttCallback{
			override fun deliveryComplete(token: IMqttDeliveryToken?) {}

			@Throws(MqttException::class)
			override fun messageArrived(topic: String, message: MqttMessage) {
				onMessageArrived(topic to message.payload)
			}

			override fun connectionLost(cause: Throwable?) {
				cause?.printStackTrace()
				System.exit(1)
			}
		})}
		return object : MqttModel.Subscribe() {
			override fun connect() {
				println("Connecting to ${option.url} with client ID ${client?.clientId}")
				client?.connect(conOpt) ?: throw MqttModel.ClientNullException
				println("Connected")
			}

			/*
            @Throws(MqttException::class)
            override fun publish(topic: String, qos: Int, payload: ByteArray) {
                println("Publishing at: ${Timestamp(System.currentTimeMillis())} to topic \"$topic\" qos $qos")
                client?.publish(topic, MqttMessage(payload).also{it.qos = qos})
            }
            */
			@Throws(MqttException::class)
			override fun subscribe(topic: String, qos: Int) {
				println("""Subscribing to topic "$topic" qos $qos""")
				client?.subscribe(topic, qos)
			}

			override fun disconnect() = client?.disconnect()
					.also { println("Disconnected") } ?: throw MqttModel.ClientNullException
		}
	}

		/**
     * Utility method to handle logging. If 'quietMode' is set, this method does nothing
     * @param message the message to log
     */
	private fun println(message:String){
		if(!option.quietMode) System.out.println(message)
	}
}
