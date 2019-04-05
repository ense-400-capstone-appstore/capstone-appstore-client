package me.matryoshkadoll.app;

import org.junit.Test;

import java.util.List;

import me.matryoshkadoll.app.api.model.AndroidappInfo;
import me.matryoshkadoll.app.api.model.Categories;

import static org.junit.Assert.assertEquals;

public class CategoriesUnitTest {


    @Test
    public void testseters() {
        Categories cts = new Categories();
        List<Categories.Datum> data = new Categories().getData();
        data.get(1).setId(1);

        assertEquals("get price", 1, data.get(1).getId(), 0.001);
    }
}
