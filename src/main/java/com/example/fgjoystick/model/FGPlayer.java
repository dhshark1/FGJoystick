package com.example.fgjoystick.model;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FGPlayer {

    private Socket socket;
    private PrintWriter out;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public FGPlayer (String host, int port) throws IOException {
        System.out.println("Connecting :" + host + " ON PORT: " + port);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(host, port);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.print("data\r");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void close() throws IOException {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    out.println("quit\r");
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setThrottle(double v) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                out.print("set /controls/engines/current-engine/throttle "+v+"\r\n");
                out.flush();
            }
        });
    }

    public void setRudder(double v) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                out.print("set /controls/flight/rudder "+v+"\r\n");
                out.flush();
            }
        });
    }

    public void setAileron(double v) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                out.print("set /controls/flight/aileron "+v+"\r\n");
                out.flush();
            }
        });

    }

    public void setElevator(double v) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                out.print("set /controls/flight/elevator "+v+"\r\n");
                out.flush();
            }
        });

    }
};

