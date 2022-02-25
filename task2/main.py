import RPi.GPIO as GPIO
import time

red_pin = 11
green_pin = 13
blue_pin = 15
button_pin = 12

position = 0
STATES = [
    (0,0,0),
    (1,0,0),
    (1,1,0),
    (0,1,1),
    (0,0,1),
          ]


def set_state(state: tuple):
    GPIO.output(red_pin, GPIO.LOW if state[0] == 0 else GPIO.HIGH)
    GPIO.output(green_pin, GPIO.LOW if state[1] == 0 else GPIO.HIGH)
    GPIO.output(blue_pin, GPIO.LOW if state[2] == 0 else GPIO.HIGH)

if __name__ == '__main__':
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(red_pin, GPIO.OUT)
    GPIO.setup(green_pin, GPIO.OUT)
    GPIO.setup(blue_pin, GPIO.OUT)
    GPIO.setup(button_pin, GPIO.IN)
    set_state(STATES[position])


    run = True
    try:
        while run:
            print(time.time(),GPIO.input(button_pin))
            if GPIO.input(button_pin) == GPIO.HIGH:
                position += 1
                position %= len(STATES)
                set_state(STATES[position])
                time.sleep(0.4)
            time.sleep(0.05)

    # set_state((0,0,0))
    #
    # GPIO.output(red_pin, GPIO.HIGH)
    #
    # freq = 100
    # RED = GPIO.PWM(red_pin, freq)
    # GREEN = GPIO.PWM(green_pin, freq)
    # BLUE = GPIO.PWM(blue_pin, freq)
    #
    # run = True



    except KeyboardInterrupt:
        run = False
        GPIO.cleanup()

