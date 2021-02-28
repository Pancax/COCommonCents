package com.pancaxrzcosola.cocommoncents;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pancaxrzcosola.cocommoncents.sqlitestuff.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    private AccountDao accDao;
    private TransferDao transferDao;
    private AppDatabase db;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.pancaxrzcosola.cocommoncents", appContext.getPackageName());
    }

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db= Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        accDao= db.accountDao();
        transferDao = db.transferDao();
    }
    @After
    public void closeDB(){
        db.close();
    }

    @Test
    public void writeAndReadDB()throws Exception{
        AccountDB acc  = new AccountDB("123","Checking","0");
        db.accountDao().insertAll(acc);
        TransferDB transfer = new TransferDB("90","80","123");
        db.transferDao().insertAll(transfer);

        List<TransferDB> list = db.transferDao().getAll();
        Log.d("DID IT WORK", "YES"+list.size());

        for(TransferDB x :list){
            Log.d("TEST CLASS", x.accountID);
            Log.d("TEST CLASS", x.purchaseID);
            Log.d("TEST CLASS", x.transferID);
            Log.d("TEST CLASS", String.valueOf(x.uid));
        }
    }

    @Test
    public void readDB()throws Exception{

    }

    @Test
    public void serverCommunicatorTest()throws Exception{
        ServerCommunicator bob = new ServerCommunicator(ApplicationProvider.getApplicationContext());
        bob.getPurchasesForAccount("603b01854a4a8605712848d4", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("TEST CLASS", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TEST CLASS", error.toString());
            }
        });
    }
}