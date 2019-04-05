package me.matryoshkadoll.app;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.matryoshkadoll.app.ui.AppInfoActivity;
import me.matryoshkadoll.app.ui.ProfileActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;



    @MediumTest
    @RunWith(AndroidJUnit4.class)
    public class ProfileUnitTest {

        @Rule
        public ActivityTestRule<ProfileActivity> rule  = new  ActivityTestRule<>(ProfileActivity.class);

        @Test
        public void checktextview1() throws Exception {
            ProfileActivity activity = rule.getActivity();
            TextView viewById = activity.findViewById(R.id.name);
            assertThat(viewById,notNullValue());

        }

        @Test
        public void checktextview2() throws Exception {
            ProfileActivity activity = rule.getActivity();
            TextView viewById = activity.findViewById(R.id.emailh);
            assertThat(viewById,notNullValue());

        }

    }
