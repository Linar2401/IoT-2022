import paho.mqtt.client as mqtt
import json

password = 'qwerty007'
HOST = '51.250.77.250'
USERNAME = 'm5sp9oaPp02jNCoQcMrl'
TOPIC = 'v1/devices/me/telemetry'
CONTROLLER_TOPIC = 'v1/devices/me/rpc/request/+'

import RPi.GPIO as GPIO
import time
import DHT11lib as dht11

H_SENSOR_PIN = 8
L_SENSOR_PIN = 5
LED_PIN = 11

def on_message(mqttc, obj, msg):
    GPIO.output(LED_PIN, GPIO.HIGH if json.loads(msg.payload)['params'] else GPIO.LOW)
    print(msg.topic + " " + str(msg.qos) + " " + str(msg.payload))  


if __name__ == '__main__':
    GPIO.setmode(GPIO.BOARD)
    h_t_sensor = dht11.DHT11(pin=H_SENSOR_PIN)
    GPIO.setup(L_SENSOR_PIN, GPIO.IN)
    GPIO.setup(LED_PIN, GPIO.OUT)
    GPIO.output(LED_PIN, GPIO.HIGH)
    sensor = dht11.DHT11(pin=H_SENSOR_PIN)

    mqttc = mqtt.Client(client_id=USERNAME)
    mqttc.username_pw_set(username='admin', password='qwerty007')
    mqttc.on_message = on_message
    mqttc.connect(HOST, 1883, 60)
    mqttc.subscribe(CONTROLLER_TOPIC, 0)
    mqttc.loop_start()
    while True:
        result = sensor.read()
        if result.is_valid():
            light = GPIO.input(L_SENSOR_PIN)
            message = {'Temperature': result.temperature - 20, 'Humidity': result.humidity, 'Illumination': (100 - light * 100)}
            mqttc.publish(TOPIC, json.dumps(message), qos=1)
            time.sleep(2)

