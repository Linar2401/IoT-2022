import time

import paho.mqtt.client as mqtt
import json

def on_message(mqttc, obj, msg):
    print(msg.topic + " " + str(msg.qos) + " " + str(msg.payload))

client_id = 'are268fud440r6k56llt'
# client_id = 'clientId-U6uAArC0yV'
password = 'qwerty007QWERTY008'
# TOPIC = 'devices/{}/events'.format(client_id)
TOPIC = 'testtopic/asdasdasd'
CONTROLLER_TOPIC = 'testtopic/asdasdasd2'

import RPi.GPIO as GPIO
import time
import DHT11lib as dht11

H_SENSOR_PIN = 8
L_SENSOR_PIN = 3
# LED_PINS =

if __name__ == '__main__':
    GPIO.setmode(GPIO.BOARD)
    h_t_sensor = dht11.DHT11(pin=H_SENSOR_PIN)
    GPIO.setup(L_SENSOR_PIN, GPIO.IN)

    sensor = dht11.DHT11(pin=H_SENSOR_PIN)

    mqttc = mqtt.Client(client_id=client_id)
    mqttc.on_message = on_message
    # mqttc.username_pw_set(client_id, password)
    # mqttc.tls_set('rootCA.crt')
    mqttc.connect("broker.mqttdashboard.com", 1883, 60)

    mqttc.subscribe(CONTROLLER_TOPIC, 0)

    # mqttc.connect("mqtt.cloud.yandex.net", 8883, 60)
    mqttc.loop_start()
    while True:
        result = sensor.read()
        light = GPIO.input(L_SENSOR_PIN)

        message = {
            "device_id": client_id,
            "timestamp": time.time(),
            "values": [
                {"Type": "Float", "Name": "Humidity", "Value": result.humidity},
                {"Type": "Float", "Name": "Temperature", "Value": result.temperature},
                {"Type": "Bool", "Name": "Light sensor", "Value": light == 0},
            ]
        }
        mqttc.publish(TOPIC, str(message), qos=1).wait_for_publish()
        time.sleep(1)
