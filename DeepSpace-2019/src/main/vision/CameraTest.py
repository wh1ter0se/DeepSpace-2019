# !/usr/bin/python


import cv2
import numpy

Stream = cv2.VideoCapture(0)
Stream.set(3, 640)
Stream.set(4, 252)


def Loop():
    while True:
        ret, img = Stream.read()
        cv2.imshow("image", img)
        cv2.waitKey(5)


if __name__ == '__main__':
    try:
        Loop()
    except KeyboardInterrupt:
        Stream.release()
        cv2.destroyAllWindows()
        quit()
