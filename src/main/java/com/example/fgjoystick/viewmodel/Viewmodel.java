package com.example.fgjoystick.viewmodel;


import com.example.fgjoystick.model.FGPlayer;

import java.io.IOException;

public class Viewmodel {
    public String _ip; // Data binded
    public int _port; // Data binded
    public double _throttle = 0, _rudder = 50, _aileron, _elevator;
    public FGPlayer _model;


    public Viewmodel() throws IOException {

    }

    public String get_ip() {
        return _ip;
    }
    public void set_ip(String ip) {
        _ip = ip;
    }
    public void set_port(int port) {
        _port = port;
    }
    public int get_port() {
        return _port;
    }

    public void set_aileron(double _aileron) {
        _model.setAileron(_aileron);
    }

    public void set_elevator(double _elevator) {
        _model.setElevator(_elevator);
    }

    public void set_rudder(double _rudder) {
        _model.setRudder(_rudder);
    }

    public void set_throttle(double _throttle) {
        _model.setThrottle(_throttle);
    }



    public void connect(String ip, int port) throws IOException {
        set_ip(ip);
        set_port(port);
        if(_ip != "Enter IP" && _port == 6400)
            _model = new FGPlayer(_ip, _port);

    }
}
