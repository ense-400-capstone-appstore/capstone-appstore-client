package me.matryoshkadoll.app;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import me.matryoshkadoll.app.api.model.User;
import me.matryoshkadoll.app.api.model.UserInfo;

public class UserUnitTest {


    @Test
    public void testseters() {
        User uif = new User();
        User.Data data = uif.getData();
        data.setUserId(1);
        data.setAccessToken("haha");
        uif.setData(data);

        assertEquals("get email", "haha", uif.getData().getAccessToken());
        assertEquals("get email", 1, uif.getData().getUserId(),0.001);

    }
}

