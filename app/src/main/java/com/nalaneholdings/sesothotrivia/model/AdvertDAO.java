package com.nalaneholdings.sesothotrivia.model;

import com.nalaneholdings.sesothotrivia.model.bean.Advert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ntholi.nkhatho on 2016/08/11.
 */
public class AdvertDAO {

    public Advert getAdvert(int id){
        return null;
    }

    public List<Advert> getAdverts(){
        List<Advert> adverts = new ArrayList<>();
        String body = "Lorem ipsum donec sagittis ipsum etiam odio, vivamus sodales mattis scelerisque in netus,\n" +
                "varius cursus felis potenti class auctor, ut quisque mollis lacinia phasellus interdum\n" +
                "bibendum magna risus id adipiscing, lorem hendrerit ut nisl integer eu";
        Advert a1 = new Advert();
        a1.setTitle("Shoprite");
        a1.setHeading("Shoprite is having a special this weekend");
        a1.setBody(body);
        Advert a2 = new Advert();
        a2.setTitle("KFC");
        a2.setHeading("Stand a chance to win prices at KFC");
        a2.setBody(body);

        adverts.add(a1);
        adverts.add(a2);

        return adverts;
    }

    public Advert getRandomAdvert(){
        List<Advert> ads = getAdverts();
        Advert advert = new Advert();

        if(ads.size() > 0){
            int random = randInt(0, ads.size() -1);
            advert = ads.get(random);
        }

        return advert;
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initializeDatabase their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
