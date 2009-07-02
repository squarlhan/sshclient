/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.jlu.ccst.sshclient.util.test;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Woden
 */
public class Test5m {

    public static void main(String[] args){
        final int aa = 11;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            int a = 0;
            int b = 0;
            public void run(){
                System.out.println("a = "+(++a)+"; "+(b+=5));
                if(a>aa)timer.cancel();   
            }
        }, 0, 1000*5);
    }

}
