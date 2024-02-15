#!/usr/bin/env /usr/bin/python3
# -*- coding: utf-8 -*-
# You have to install AdaFruit CircuitPython DHT Libraries by
# $ pip3 install adafruit-circuitpython-dht
# .

import adafruit_dht
from board import * 

import time
import datetime

instance_dht = adafruit_dht.DHT11(pin=D26, use_pulseio=False)
#instance_dht = adafruit_dht.DHT22(pin=D4, use_pulseio=False)
WAIT_INTERVAL = 10
WAIT_INTERVAL_RETRY = 5

def get_dht_data():
    temp_dht = 200.0 # unnecessary value-setting
    humid_dht = 100.0 # unnecessary value-setting
    try:
        instance_dht.measure()
        temp_dht = instance_dht.temperature
        humid_dht = instance_dht.humidity

    except RuntimeError:
        print("RuntimeError: DHT11/22 returns wrong values, maybe.: " + str(datetime.datetime.now()))
        time.sleep(WAIT_INTERVAL_RETRY)
        raise(RuntimeError)
        
    except OSError:
        print("OSError: DHT11/22: OS Error, but we ignore it.: "+ str(datetime.datetime.now()))
        time.sleep(WAIT_INTERVAL_RETRY)
        raise(OSError)

    return float(temp_dht), float(humid_dht)


def test_get_dht_data():
    count = 0 
    tempe = 40.0
    humid = 85.0
    while True:

        try:
            tempe, humid = get_dht_data()
            now_str = str(datetime.datetime.now())
            print("Temperature: %f  Humidity: %f" % (tempe, humid), now_str)
        except RuntimeError:
            print("RuntimeError in get_dht_data(). Let us ignore it!")
            time.sleep(WAIT_INTERVAL_RETRY)
        except OSError:
            print("OSError in get_dht_data(). Let us ignore it!")
            time.sleep(WAIT_INTERVAL_RETRY)

        time.sleep(WAIT_INTERVAL)
        count = count + 1
        if (count > 5):
            break

if __name__ == '__main__':
    print("Start if __name__ == '__main__'")
    test_get_dht_data()
