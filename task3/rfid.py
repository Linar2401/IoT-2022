from pirc522 import RFID
rdr = RFID()
util = rdr.util()
import time

while True:
    rdr.wait_for_tag()

    # Request tag
    (error, data) = rdr.request()
    if not error:
        print("\nDetected")

        (error, uid) = rdr.anticoll()
        if not error:
            # Print UID
            print("Card read UID: "+str(uid[0])+","+str(uid[1])+","+str(uid[2])+","+str(uid[3]))
            print("{}: Tag detected".format(time.time()))

            time.sleep(0.5)

# Calls GPIO cleanup
rdr.cleanup()