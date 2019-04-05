package me.matryoshkadoll.app;

import android.support.design.widget.NavigationView;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.matryoshkadoll.app.ui.DrawerActivity;
import me.matryoshkadoll.app.ui.ProfileActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;





    @MediumTest
    @RunWith(AndroidJUnit4.class)
    public class DrawerUnitTest {

        @Rule
        public ActivityTestRule<DrawerActivity> rule  = new  ActivityTestRule<>(DrawerActivity.class);

        @Test
        public void checktextview1() throws Exception {
            DrawerActivity activity = rule.getActivity();
            TextView viewById = activity.findViewById(R.id.textView);
            assertThat(viewById,notNullValue());

        }

        @Test
        public void checktextview2() throws Exception {
            DrawerActivity activity = rule.getActivity();
            NavigationView viewById = activity.findViewById(R.id.navigation);
            assertThat(viewById,notNullValue());

        }

    }

