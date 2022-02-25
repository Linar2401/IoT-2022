from gpiozero import CPUTemperature
import time

cpu = CPUTemperature()

while True:
    print(cpu.temperature)
    time.sleep(1)

