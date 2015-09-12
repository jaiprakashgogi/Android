# Plotting reference: http://www.instructables.com/id/Plotting-real-time-data-from-Arduino-using-Python-/
#socket Reference:

from collections import deque
from threading import Thread
import socket

import matplotlib.pyplot as plt
import matplotlib.animation as animation

class AnalogPlot:
    # constr
    def __init__(self, maxLen):
        self.ax = deque([0.0]*maxLen)
        self.ay = deque([0.0]*maxLen)
        self.az = deque([0.0]*maxLen)
        self.maxLen = maxLen
        self.randVal = []
        self.ClientSocket = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        self.ClientSocket.connect(('localhost', 1234))
        thread = Thread(target = self.threaded_function)
        thread.start()

    def filter_string(self, s, first, last):
        try:
            start = s.index(first) + len(first)
            end = s.index(last, start)
            return s[start:end]
        except ValueError:
            return ''

    def threaded_function(self):
        while True:
            buf = self.ClientSocket.recv(30)
            if len(buf) > 0:
                y = self.filter_string(buf, "*#", '#*').split('#')
                if not y[0] == '':
                    self.randVal = [float(x) for x in y]

    def addToBuf(self, buf, val):
        if len(buf) < self.maxLen:
            buf.append(val)
        else:
            buf.pop()
            buf.appendleft(val)

# add data
    def add(self, data):
        assert(len(data) == 4)
        self.addToBuf(self.ax, data[0])
        self.addToBuf(self.ay, data[1])
        self.addToBuf(self.az, data[2])

    def update(self, frameNum, a0, a1,a2):
        try:
            data = self.randVal
            #print self.randVal
            if(len(data) == 4):
                self.add(data)
                a0.set_data(range(self.maxLen), self.ax)
                a1.set_data(range(self.maxLen), self.ay)
                a2.set_data(range(self.maxLen), self.az)
        except KeyboardInterrupt:
                print('exiting')
        return a0,


    def close(self):
        self.ClientSocket.close()

def main():
    analogPlot = AnalogPlot(100)

    print('plotting data...')
    fig = plt.figure()
    ax = plt.axes(xlim=(0, 100), ylim=(-10, 10))
    a0, = ax.plot([], [])
    a1, = ax.plot([], [])
    a2, = ax.plot([], [])
    anim = animation.FuncAnimation(fig, analogPlot.update,
                            fargs=(a0, a1,a2),
                            interval=10)
    plt.show()
    analogPlot.close()
    print('exiting.')

if __name__ == '__main__':
    main()
