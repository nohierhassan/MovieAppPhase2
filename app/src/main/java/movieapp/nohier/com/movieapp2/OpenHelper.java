package movieapp.nohier.com.movieapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by mohamed k on 02/09/2016.
 */
public class OpenHelper extends SQLiteOpenHelper {
  private Context context;
    public static final String DATABASENAME = "MovieApp";
    public static final String TABLENAME = "Movie";
    public static final String COL_1 = "id";
    public static final String COL_2 = "title";
    public static final String COL_3 = "poster_path";
    public static final String COL_4 = "overview";
    public static final String COL_5= "vote";
    public static final String COL_6 = "year";


    public OpenHelper(Context context) {
        // here the database is created
        super(context, DATABASENAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLENAME+" (id INTEGER PRIMARY KEY ,title TEXT,poster_path TEXT,overview TEXT,vote TEXT,year TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);

    }
    public void insertData(String id,String title,String poster_path,String overview,String vote,String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // check if the movie exixts in the favoutit or not

        if(!checkIfExists(id)){
            ContentValues contentValues  =new ContentValues();
            contentValues.put(COL_1,id);
            contentValues.put(COL_2,title);
            contentValues.put(COL_3,poster_path);
            contentValues.put(COL_4,overview);
            contentValues.put(COL_5,vote);
            contentValues.put(COL_6,year);
            Long result =  db.insert(TABLENAME,null,contentValues);
            if(result==-1)

                Toast.makeText(context,"Error Occured While Inserting",Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context,"Movie Added to Favourit",Toast.LENGTH_LONG).show();

            }

        }
        else
            Toast.makeText(context,"Movie Already Added",Toast.LENGTH_LONG).show();

    }
    public boolean checkIfExists(String id){
        String Query = "select * from "+TABLENAME+" where id = "+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(Query,null);
        if(res.getCount()<=0){
            // the record not exists
            return false;
        }
        else
            return true;



    }
    public Cursor returnData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLENAME,null);
        return res;


    }
}
