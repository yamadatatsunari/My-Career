#!/usr/bin/env /usr/bin/python3
# -*- coding: utf-8 -*-

import adafruit_dht
from board import *

import board
from adafruit_bme280 import basic as adafruit_bme280

import time
import datetime

import sys
import json

#数値を丸めるためのモジュール
from decimal import Decimal, ROUND_HALF_UP, ROUND_HALF_EVEN

import fasteners

#排他制御用ファイルを格納
LOCKFILE = './lock.txt'
#lock = fasteners.InterProcessLock(LOCKFILE)

#DHT11の設定（pinは各々変更して使う）
instance_dht = adafruit_dht.DHT11(pin=D26, use_pulseio=False)
WAIT_INTERVAL = 10
WAIT_INTERVAL_RETRY = 5

###############################bme280initialize###################################
i2c = board.I2C()   # uses board.SCL and board.SDA
instance_bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c, address=0x76)
instance_bme280.sea_level_pressure = 1013.25
##################################################################################

#測定回数
MESURECOUNT = 1

#####重要######
#SERVERは自分で接続しているIPアドレスに変えて使う
#自宅でやる場合は，以下でIPv4アドレスを確認し，SERVERの部分を変更する
#１．コマンドプロンプトでipconfig
#２．自宅のWi-fi（恐らくWireless LAN adapter Wi-Fi）を見つけてIPv4アドレスを確認する．
SERVER = '192.168.10.108'
WAITING_PORT = 8765
#MESSAGE_FROM_CLIENT = "Hello, I am a client."

#Decimalをjsonに変換するための関数
def decimal_default_proc(obj):
    if isinstance(obj, Decimal):
        return float(obj)
    raise TypeError

#DHT11を使う為の準備関数（インスタンス生成）
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

#BME280を使うための準備関数
def get_bme_data(instance_bme280):
    temperature_v = instance_bme280.temperature
    humidity_v = instance_bme280.relative_humidity
    pressure_v = instance_bme280.pressure
    #altitude_v = instance_bme280.altitude
    return temperature_v,humidity_v,pressure_v

#DHT11による温湿度取得関数
def DHT11_process():
    #変数の初期化
    count = 0 
    dht11tempe = 40.0
    dht11humid = 85.0
    
    lock = fasteners.InterProcessLock(LOCKFILE)
    
    with lock:
        while(count < MESURECOUNT):
            try:
                dht11tempe, dht11humid = get_dht_data()
                now_str = str(datetime.datetime.now())
                print("DHT11-Temperature: %f  DHT11-Humidity: %f" % (dht11tempe, dht11humid), now_str)
            
                #DHT11取得値を辞書型に変換
                dht11_data_tempe_humid = {"dht11tempe": dht11tempe, "dht11humid": dht11humid}
                # #温湿度データをリストに格納
                # tempe_data_list.append(tempe)
                # humid_data_list.append(humid)
                #温湿度が取得したときのみcountを増やすことでMESURECOUNT分取得できる
                count += 1
            except RuntimeError:
                print("RuntimeError in get_dht_data(). Let us ignore it!")
                time.sleep(WAIT_INTERVAL_RETRY)
            except OSError:
                print("OSError in get_dht_data(). Let us ignore it!")
                time.sleep(WAIT_INTERVAL_RETRY)
            time.sleep(WAIT_INTERVAL)
            
    return dht11_data_tempe_humid

#BME280による温湿度取得関数
def BME280_process():
    #変数の初期化
    count = 0
    bme280tempe = 40.0
    bme280humid = 85.0
    
    lock = fasteners.InterProcessLock(LOCKFILE)
    
    with lock:
        while(count < MESURECOUNT):
            try:
                #atomosphere[0]...温度
                #atomosphere[1]...湿度
                #atomosphere[2]...気圧
                atomosphere = get_bme_data(instance_bme280)
                now_str = str(datetime.datetime.now())
                
                #測定値が小数点以下14桁まであるため，
                #decimalモジュールのquantize()メソッドを使って小数点以下1桁まで丸める
                bme280tempe = Decimal(str(atomosphere[0])).quantize(Decimal('0.1'), rounding=ROUND_HALF_UP)
                bme280humid = Decimal(str(atomosphere[1])).quantize(Decimal('0.1'), rounding=ROUND_HALF_UP)
                # pre = atomosphere[2]
                # print("tem = {:.2f} ℃ | hum = {:.2f} % | pre = {:.2f} hPa\n".format(tem,hum,pre))
                print("BME280-Temperature: {:.2f} ℃  BME280-Humidity = {:.2f} %\n".format(bme280tempe,bme280humid), now_str)

                #DHT11取得値を辞書型に変換
                bme280_data_tempe_humid = {"bme280tempe": bme280tempe, "bme280humid": bme280humid}
                #温湿度が取得したときのみcountを増やすことでMESURECOUNT分取得できる
                count += 1
            except RuntimeError:
                print("RuntimeError in get_bme_data(). Let us ignore it!")
                time.sleep(WAIT_INTERVAL_RETRY)
            except OSError:
                print("OSError in get_bme_data(). Let us ignore it!")
                time.sleep(WAIT_INTERVAL_RETRY)
            time.sleep(WAIT_INTERVAL)
            
    return bme280_data_tempe_humid

#ソケット通信によるDHT11，BME280のデータ送信関数
def socket_client_senddata(hostname_v1 = SERVER, waiting_port_v1 = WAITING_PORT):
    import socket
    import time

    node_s = hostname_v1
    port_s = waiting_port_v1
    
    tempe_data_list = []
    humid_data_list = []

    # socoket for receiving and sending data
    # AF_INET     : IPv4
    # SOCK_STREAM : TCP
    socket_r_s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socket_r_s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    print("node_s:", node_s,  " port_s:", str(port_s))
    socket_r_s.connect((node_s, port_s))
    print('Connecting to the server. '
        + 'node: ' + node_s + '  '
        + 'port: ' + str(port_s))
    
    ##########DHT11で温湿度取得############
    dht11_data_tempe_humid = DHT11_process()
    #######################################
    ##########BME280で温湿度取得############
    bme280_data_tempe_humid = BME280_process()
    #######################################
    
    #DHT11の取得値をjson形式に変換
    dht11_data_tempe_humid_json = json.dumps(dht11_data_tempe_humid)
    print(dht11_data_tempe_humid_json)
    #bme280の取得値をjson形式に変換
    bme280_data_tempe_humid_json = json.dumps(bme280_data_tempe_humid, default=decimal_default_proc)
    print(bme280_data_tempe_humid_json)
    
    #DHT11のjsonデータをエンコード
    dht11_data_s = dht11_data_tempe_humid_json.encode('utf-8')
    #BME280のjsonデータをエンコード
    bme280_data_s = bme280_data_tempe_humid_json.encode('utf-8')
    
    #DHT11のデータ（tempe,humid）を辞書型文字列で送信
    socket_r_s.send(dht11_data_s)
    #BME280のデータ（tempe,humid）を辞書型文字列で送信
    socket_r_s.send(bme280_data_s)

    socket_r_s.close()

#main関数
if __name__ == '__main__':
    print("Start if __name__ == '__main__'")
    
    sys_argc = len(sys.argv)
    count = 1
    hostname_v = SERVER
    waiting_port_v = WAITING_PORT

    while True:
        #print(count, "/", sys_argc)
        if(count >= sys_argc):
            break

        option_key = sys.argv[count]
        #print(option_key)
        if ("-h" == option_key):
            count = count + 1
            hostname_v = sys.argv[count]
            #print(option_key, hostname_v)
        if ("-p" == option_key):
            count = count + 1
            waiting_port_v = int(sys.argv[count])
            #print(option_key, port_v)
        if ("-m" == option_key):
            count = count + 1
            message_v = sys.argv[count]
            #print(option_key, message_v)

        count = count + 1

    print(hostname_v)
    print(waiting_port_v)

    socket_client_senddata(hostname_v, waiting_port_v)
    