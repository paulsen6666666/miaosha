package com.example.fast.easy.watch;

import java.util.Observable;
import java.util.Observer;

/**
 * @author paul
 * @Date 2022/7/28 22:16
 */
public class Hire {
    public static void main(String[] args) {
        FangDong fangDong = new FangDong();
        C c = new C();
        D d = new D();
        fangDong.addObserver(c);
        fangDong.addObserver(d);
        fangDong.setMessage("交房租");

    }





}

class FangDong extends Observable{


    public void setMessage(String message) {
        setChanged();
        notifyObservers(message);
    }
}


class C implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("收到交租信息");

    }
}

class D implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("收到交租信息");


    }
}