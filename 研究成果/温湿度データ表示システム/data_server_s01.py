#!/usr/bin/env /usr/bin/python3
# -*- coding: utf-8 -*-

# You have to install AdaFruit CircuitPython DHT Libraries by
# $ pip3 install adafruit-circuitpython-dht
# .

import sys
import csv
import json

import time
import datetime

import fasteners

#排他制御用ファイルを格納
LOCKFILE = './lock.txt'

#####重要######
#SERVERは自分で接続しているIPアドレスに変えて使う
#自宅でやる場合は，以下でIPv4アドレスを確認し，SERVERの部分を変更する
#１．コマンドプロンプトでipconfig
#２．自宅のWi-fi（恐らくWireless LAN adapter Wi-Fi）を見つけてIPv4アドレスを確認する．
SERVER = '192.168.10.108'
#SERVER = '10.192.138.176'
WAITING_PORT = 8765

#ファイル名指定
DATA_DIR = 'data'
CSV_FILENAME1 = 'DHT11_' + str(datetime.date.today()) + '.csv'
CSV_FILENAME2 = 'BME280_' + str(datetime.date.today()) + '.csv'
filename1 = DATA_DIR + '/' + CSV_FILENAME1
filename2 = DATA_DIR + '/' + CSV_FILENAME2

#CSVに書き込み
def csv_write_for_DHT11(data_str):
    
    lock = fasteners.InterProcessLock(LOCKFILE)
    
    with lock:
        with open(filename1, mode='a') as f:
            f.write(data_str)
            f.write('\n')

#CSVに書き込み
def csv_write_for_BME280(data_str):
    
    lock = fasteners.InterProcessLock(LOCKFILE)
    
    with lock:
        with open(filename2, mode='a') as f:
            f.write(data_str)
            f.write('\n')

#サーバからデータ（DHT11の測定値）受信
def server_data_recv(server_v1=SERVER, waiting_port_v1=WAITING_PORT):
    import socket
    import time
    WAIT_TIME = 5
    
    # socoket for waiting of the requests.
    # AF_INET     : IPv4
    # SOCK_STREAM : TCP
    socket_w = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    socket_w.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    node_s = server_v1
    port_s = waiting_port_v1
    socket_w.bind((node_s, port_s))

    BACKLOG = 5
    socket_w.listen(BACKLOG)

    print('Waiting for the connection from the client(s). '
        + 'node: ' + node_s + '  '
        + 'port: ' + str(port_s))

    socket_s_r, client_address = socket_w.accept()
    print('Connection from ' 
        + str(client_address) 
        + " has been established.")
    
    #DHT11から受け取るデータを1024ビットに設定
    dht11_data_tempe_humid = socket_s_r.recv(1024)
    #bme280から受け取るデータを1024ビットに設定
    bme280_data_tempe_humid = socket_s_r.recv(1024)
    
    #sensor_node_s01.pyから送られてくるjson形式のDHT11の温湿度データを各々格納
    dht11_data_str = json.loads(dht11_data_tempe_humid)
    #sensor_node_s01.pyから送られてくるjson形式のBME280の温湿度データを各々格納
    bme280_data_str = json.loads(bme280_data_tempe_humid)
    
    # print(data_str)
    
    #data_r_tempestr = data_r.decode('utf-8')
    
    print('--- I (the server) have just received the DHT11 data ---\n'
        + 'TEMPE ' + str(dht11_data_str["dht11tempe"]) + ' ℃' + '\n' 
        + 'HUMID ' + str(dht11_data_str["dht11humid"]) + ' %' + '\n' 
        +'--- from the client. '+ str(client_address) + ' ---')
    
    print('--- I (the server) have just received the BME280 data ---\n'
        + 'TEMPE ' + str(bme280_data_str["bme280tempe"]) + ' ℃' + '\n' 
        + 'HUMID ' + str(bme280_data_str["bme280humid"]) + ' %' + '\n' 
        +'--- from the client. '+ str(client_address) + ' ---')
    
    #CSVにDHT11の温湿度データを出力
    data_str_v1 = str(dht11_data_str["dht11tempe"]) + ',' + str(dht11_data_str["dht11humid"])
    csv_write_for_DHT11(data_str_v1)
    #CSVにBME280の温湿度データを出力
    data_str_v2 = str(bme280_data_str["bme280tempe"]) + ',' + str(bme280_data_str["bme280humid"])
    csv_write_for_BME280(data_str_v2)
    
    print("Now, closing the data socket.")
    socket_s_r.close()

    print("Let us wait %d seconds (for students understanding)." %WAIT_TIME)
    time.sleep(WAIT_TIME)

    print("Caution!!!: Now, the server is closing the waiting socket.")
    socket_w.close()

#main関数
if __name__ == '__main__':
    print("Start if __name__ == '__main__'")
    
    sys_argc = len(sys.argv)
    count = 1
    hostname_v = SERVER
    waiting_port_v = WAITING_PORT

    while True:
            print(count, "/", sys_argc)
            if(count >= sys_argc):
                break

            option_key = sys.argv[count]
#            print(option_key)
            if ("-h" == option_key):
                count = count + 1
                hostname_v = sys.argv[count]
#                print(option_key, hostname_v)

            if ("-p" == option_key):
                count = count + 1
                waiting_port_v = int(sys.argv[count])
#               print(option_key, waiting_port_v)

            count = count + 1
    
    print(hostname_v)
    print(waiting_port_v)
    
    server_data_recv(hostname_v, waiting_port_v)