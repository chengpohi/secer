package org.bugogre.crawler;

/**
 * Created by xiachen on 3/28/15.
 */
public class App {
    int i = 0;
    public static void main(String[] args) {
        App a = new App();
        a.recur();
    }

    public void recur() {
        System.out.println(i++);
        recur();
    }

}
