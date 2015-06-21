package in.lastlocal.mumbaitraffic;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.database.SQLException;

import in.lastlocal.constant.AppConstant;

/**
 * Created by USER on 21-Jun-15.
 */
public class DBHelper extends SQLiteOpenHelper {

    /** */
    String TAG = "DatabaseHelper";

    /** */
    SQLiteDatabase db;

    /**
     * If you change the database schema, you must increment the database version.
     */
    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "police.sqlite";

    public static final String DB_SUFFIX = "/databases/";
    public Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;

        // DB_PATH =
        // Environment.getExternalStorageDirectory()+"/Test/AndroidBabyName/";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
           // db.execSQL(TableContract.TimeStamp.SQL_CREATE);
           // db.execSQL(TableContract.Name.SQL_CREATE);
           // db.execSQL(TableContract.SavedStatus.SQL_CREATE);
           // Log.i(TAG, "Tatabase created Successfully");
        } catch (Exception e) {
            if (AppConstant.DEBUG)
                Log.e(TAG, e.toString());
            //AppLogger.writeLog("Class Name --> DBHelper -- " + e.toString());
        }
    }

    /** Identify Database is available or Not */
    boolean isDataBaseAvailable() {
        try {
            String myPath = getDatabasePath();
            File f = new File(myPath);
            if (f.exists()) {
                return true;
            }

        } catch (SQLiteException e) {
            Log.v(TAG, e.toString() + "   database doesn't exists yet..");
        }
        return false;
    }

    /** Give the Path of database */
    public String getDatabasePath() {
        return myContext.getApplicationInfo().dataDir + DB_SUFFIX + DB_NAME;
    }

    public void copyDataBaseFromAsset() throws Exception {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        OutputStream myOutput = null;
        try {

            String outFileName = getDatabasePath();

			/* if the path doesn't exist first, create it */
            File f = new File(myContext.getApplicationInfo().dataDir + DB_SUFFIX);
            if (!f.exists())
                f.mkdir();

			/* Open the empty db as the output stream */
            myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();

        } catch (Exception e) {
            throw e;
        } finally {
            myOutput.close();
            myInput.close();
        }

    }

    public boolean openDataBase() throws SQLException {
        String myPath = getDatabasePath();
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return db != null;
    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * @param TableName
     * @param columns
     * @param where
     * @return Cursor
     */
    public Cursor getTableValue(String TableName, String[] columns, String where) {
        Cursor c = null;
        try {
            db = this.getReadableDatabase();
            c = db.query(TableName, columns, where, null, null, null, null, null);
        } catch (Exception e) {
            //AppLogger.writeLog("Class Name --> DBHelper -- " + e.toString());
            if (AppConstant.DEBUG)
                Log.e(TAG, e.toString() + "--> getTableValue()");
        } finally {
            /** If database does not contain anything immediately close database */
            if (c == null)
                db.close();
        }
        return c;
    }

    /**
     * Execute Row query
     *
     * @param sqlQuery
     */
    public Cursor executeStatement(String sqlQuery) {
        try {
            db = this.getWritableDatabase();
           Cursor c = db.rawQuery(sqlQuery, null);
            return c;
        } catch (Exception e) {
            //AppLogger.writeLog("Class Name --> " + TAG + " " + e.toString());
            if (AppConstant.DEBUG)
                Log.e(getClass().getName(), e.toString() + "-->");
        }
        return null;
    }

}
