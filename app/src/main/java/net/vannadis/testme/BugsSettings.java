package net.vannadis.testme;

import net.vannadis.testme.model.Bug;

import java.util.HashMap;
//http://findicons.com/icon/203378/bug_buddy?id=204798
//http://findicons.com/icon/567904/bug2?id=569148
//http://findicons.com/icon/559050/bug
/**
 * Created by viktoriala on 9/23/2014.
 */
public class BugsSettings {

    public static int PRODUCTS_COUNT = 60;
    public static HashMap<Integer, Bug> productsBugs = new HashMap<Integer, Bug>();

    static {
        productsBugs.put(30, new Bug(Bug.TYPE_NOT_DELETABLE)); // BUG-testme: id 14
        productsBugs.put(23, new Bug(Bug.TYPE_NOT_DELETABLE_CLEAR_ALL)); // BUG-testme: id 16
        productsBugs.put(17, new Bug(Bug.TYPE_NOT_ADDABLE)); // BUG-testme: id 5
        productsBugs.put(28, new Bug(Bug.TYPE_WRONG_DESCRIPTION, "Product %1$d description")); // BUG-testme: id 1
        productsBugs.put(44, new Bug(Bug.TYPE_WRONG_PRICE, 1124));  // BUG-testme: id 2
        productsBugs.put(54, new Bug(Bug.TYPE_CRASH)); // BUG-testme: id 6
    }




}
