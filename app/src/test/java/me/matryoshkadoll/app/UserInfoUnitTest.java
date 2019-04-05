package me.matryoshkadoll.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import me.matryoshkadoll.app.api.model.UserInfo;

public class UserInfoUnitTest {


    @Test
    public void testseters() {
        UserInfo uif = new UserInfo();
        uif.setemail("abc");
        uif.setpassword("abc");

        assertEquals("get email", "abc", uif.getemail());
        assertEquals("get email", "abc", uif.getpassword());

    }
}