package developer.com.krishna.garbagecollector;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class GarbageCollector extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseApp.initializeApp(this);

    }
}
