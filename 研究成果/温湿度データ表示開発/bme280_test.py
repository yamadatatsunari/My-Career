import board
import time
from adafruit_bme280 import basic as adafruit_bme280

def bme280_value(instance_bme280):
    temperature_v = instance_bme280.temperature
    humidity_v = instance_bme280.relative_humidity
    pressure_v = instance_bme280.pressure
    #altitude_v = instance_bme280.altitude
    return temperature_v,humidity_v,pressure_v

#単体テスト用
if __name__ == '__main__':
    i2c = board.I2C()   # uses board.SCL and board.SDA
    instance_bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c, address=0x76)
    instance_bme280.sea_level_pressure = 1013.25
    atomosphere = bme280_value(instance_bme280)
    print(atomosphere[0])
    print(atomosphere[1])
    print(atomosphere[2])
