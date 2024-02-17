from flask import Flask, render_template, request, redirect
import sys
import csv
import time
import datetime
import fasteners

#排他制御用ファイルを格納
LOCKFILE = './lock.txt'

#ファイル名指定
DATA_DIR = 'data'
CSV_FILENAME1 = 'DHT11_' + str(datetime.date.today()) + '.csv'
CSV_FILENAME2 = 'BME280_' + str(datetime.date.today()) + '.csv'
filename1 = DATA_DIR + '/' + CSV_FILENAME1
filename2 = DATA_DIR + '/' + CSV_FILENAME2

app = Flask(__name__)

#DHT11用のCSVに書き込み
def csv_write_for_DHT11(data_str):
    
    lock = fasteners.InterProcessLock(LOCKFILE)
    
    with open(filename1, mode='a') as f:
        f.write(data_str)
        f.write('\n')

#BME280用のCSVに書き込み
def csv_write_for_BME280(data_str):
    
    lock = fasteners.InterProcessLock(LOCKFILE)
    
    with open(filename2, mode='a') as f:
        f.write(data_str)
        f.write('\n')

@app.route("/", methods=["GET"])
def csv_read():
    print("Hello! (to Terminal)")
    data_list1 = []
    data_list2 = []
    
    with open(filename1, mode='r') as f:
        all_data1 = csv.reader(f)
        #csvに登録されているデータの数，繰り返す
        for line in all_data1:
            data_list1.append(line)
    
    with open(filename2, mode='r') as f:
        all_data2 = csv.reader(f)
        #csvに登録されているデータの数，繰り返す
        for line in all_data2:
            data_list2.append(line)

    return render_template("show_htm_01.html",  dht11_data = data_list1, bme280_data = data_list2) 

@app.route("/add_data_for_dht11", methods=["POST"])
def csv_read_add_data_dht11():
    data_list = []
    
    # dht11用のhtmlのフォーム入力値（温度・湿度）を格納
    dht11_tempe_field = request.form["dht11_tempefield"]
    dht11_humid_field = request.form["dht11_humidfield"]
    
    # Debug
    # print(field)
    # print(type(field))
    
    # webブラウザによるdht11用のフォーム入力値（温湿度データ）を文字列に変換
    dht11_data_str = dht11_tempe_field + ',' + dht11_humid_field
    # CSVに出力
    csv_write_for_DHT11(dht11_data_str)
    
    #CSVの読み込み
    with open(filename1, mode='r') as f:
        all_data = csv.reader(f)
        #csvに登録されているデータの数，繰り返す
        for line in all_data:
            data_list.append(line)
    
    # '/'にリダイレクト
    return redirect('/')

@app.route("/add_data_for_bme280", methods=["POST"])
def csv_read_add_data_bme280():
    data_list = []
        
    # bme280用のhtmlのフォーム入力値（温度・湿度）を格納
    bme280_tempe_field = request.form["bme280_tempefield"]
    bme280_humid_field = request.form["bme280_humidfield"]
    
    # Debug
    # print(field)
    # print(type(field))
    
    # webブラウザによるbme280用のフォーム入力値（温湿度データ）を文字列に変換
    bme280_data_str = bme280_tempe_field + ',' + bme280_humid_field
    
    # CSVに出力
    csv_write_for_BME280(bme280_data_str)
    
    with open(filename2, mode='r') as f:
        all_data = csv.reader(f)
        #csvに登録されているデータの数，繰り返す
        for line in all_data:
            data_list.append(line)
    
    # '/'にリダイレクト
    return redirect('/')

if __name__ == "__main__":
    app.run(host = '0.0.0.0', port = 5001, debug=True)