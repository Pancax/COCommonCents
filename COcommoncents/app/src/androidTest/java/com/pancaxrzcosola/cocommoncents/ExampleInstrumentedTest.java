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

    }

    @Test
    public void readDB()throws Exception{

    }

    @Test
    public void serverCommunicatorTest()throws Exception{

    }
}