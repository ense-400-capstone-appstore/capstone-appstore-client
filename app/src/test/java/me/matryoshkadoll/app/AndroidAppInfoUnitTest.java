package me.matryoshkadoll.app;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;

import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.ui.DrawerActivity;


import static org.junit.Assert.*;
public class AndroidAppInfoUnitTest {



    @Test
    public void testseters() {
        AndroidappInfo apinfo = new AndroidappInfo();
        AndroidappInfo.Data data= new AndroidappInfo().getData();
        data.setDescription("asdas");
        data.setId(1);
        data.setName("das");
        data.setPrice(3);
        data.setVersion("333");
        apinfo.setData(data);

        assertEquals("get price", 3, data.getPrice(), 0.001);
    }
}
