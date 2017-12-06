package id.co.blogbasbas.tryormsqlite;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import id.co.blogbasbas.tryormsqlite.db.DaoMaster;
import id.co.blogbasbas.tryormsqlite.db.DaoSession;

public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"user-db"); //The users-db here is the name of our database.
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }
   /* public DaoSession getDaoSession() {
        return daoSession;
    }*/
}
