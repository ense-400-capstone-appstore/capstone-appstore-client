package me.matryoshkadoll.app;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.matryoshkadoll.app.ui.AppInfoActivity;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class AppInfoUnitTest {

    @Rule
    public ActivityTestRule<AppInfoActivity> rule  = new  ActivityTestRule<>(AppInfoActivity.class);

    @Test
    public void checktextview1() throws Exception {
        AppInfoActivity activity = rule.getActivity();
        TextView viewById = activity.findViewById(R.id.textView2);
        assertThat(viewById,notNullValue());

    }

    @Test
    public void checktextview2() throws Exception {
        AppInfoActivity activity = rule.getActivity();
        TextView viewById = activity.findViewById(R.id.textView3);
        assertThat(viewById,notNullValue());

    }

    @Test
    public void checktextview3() throws Exception {
        AppInfoActivity activity = rule.getActivity();
        TextView viewById = activity.findViewById(R.id.textView5);
        assertThat(viewById,notNullValue());

    }


    @Test
    public void checkbutton1() throws Exception {
        AppInfoActivity activity = rule.getActivity();
        Button viewById = activity.findViewById(R.id.button);
        assertThat(viewById,notNullValue());

    }
}