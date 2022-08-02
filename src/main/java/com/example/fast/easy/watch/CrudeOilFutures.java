package com.example.fast.easy.watch;

import java.util.Observable;
import java.util.Observer;

/**
 * @author paul
 * @Date 2022/7/28 22:09
 */
public class CrudeOilFutures {

    public static void main(String[] args) {
        OilFutures oil = new OilFutures();
        Observer bull = new A(); //多方
        Observer bear = new B(); //空方
        oil.addObserver(bull);
        oil.addObserver(bear);
        oil.setPrice(10);
        oil.setPrice(-8);

    }
}


class OilFutures extends Observable {
    private float price;

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        super.setChanged();  //设置内部标志位，注明数据发生变化
        super.notifyObservers(price);    //通知观察者价格改变了
        this.price = price;
    }
}

class A implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Float price = ((Float) arg).floatValue();
        if (price > 0) {
            System.out.println("油价上涨" + price + "元，多方高兴了！");
        } else {
            System.out.println("油价下跌" + (-price) + "元，多方伤心了！");
        }

    }
}


class B implements  Observer{

    @Override
    public void update(Observable o, Object arg) {
        Float price = ((Float) arg).floatValue();
        if (price > 0) {
            System.out.println("油价上涨" + price + "元，空方高兴了！");
        } else {
            System.out.println("油价下跌" + (-price) + "元，空方伤心了！");
        }
    }
}



