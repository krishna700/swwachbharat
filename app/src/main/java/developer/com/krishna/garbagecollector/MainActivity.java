package developer.com.krishna.garbagecollector;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import developer.com.krishna.garbagecollector.fragments.Complaints;
import developer.com.krishna.garbagecollector.fragments.Events;
import developer.com.krishna.garbagecollector.fragments.New;
import developer.com.krishna.garbagecollector.fragments.User;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.complains);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.complains:
                    fragment = new Complaints();
                    switchFragment(fragment);
                    return true;
                case R.id.event:
                    fragment = new Events();
                    switchFragment(fragment);
                    return true;
                case R.id.neww:
                    fragment = new New();
                    switchFragment(fragment);
                    return true;
                case R.id.user:
                    fragment = new User();
                    switchFragment(fragment);
                    return true;
            }
            return false;
        }
    };




    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
            {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this,"GoodBye!!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));


            }


        }
        return true;


    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Press once again to exit!",
                    Snackbar.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }

}
