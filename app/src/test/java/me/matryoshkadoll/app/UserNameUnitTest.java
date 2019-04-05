package me.matryoshkadoll.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import me.matryoshkadoll.app.api.model.UserInfo;
import me.matryoshkadoll.app.api.model.UserName;

public class UserNameUnitTest {


    @Test
    public void testseters() {
        UserName unm = new UserName();
        UserName.Data data = unm.getData();
data.setId(1);
data.setName("abc");
data.setEmail("abc");
unm.setData(data);
        assertEquals("get email", "abc", unm.getData().getEmail());
        assertEquals("get email", "abc", unm.getData().getName());

    }
}

