package developer.com.krishna.garbagecollector.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


import developer.com.krishna.garbagecollector.R;
import developer.com.krishna.garbagecollector.models.ComplainPojo;

public class Complaints extends Fragment {

    DatabaseReference mDatabase;
    RecyclerView mRecyclerView;
    FirebaseRecyclerOptions<ComplainPojo> options;
    private FirebaseRecyclerAdapter<ComplainPojo, ComplainHolder> fireBaseAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("complains");


        mDatabase.keepSynced(true);



    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_complaints, parent, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView=new RecyclerView(getContext());
        mRecyclerView=rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(layoutManager);

        Query query = mDatabase
                .limitToLast(50);
        options = new FirebaseRecyclerOptions.Builder<ComplainPojo>()
                .setQuery(query, ComplainPojo.class)
                .setLifecycleOwner(this)
                .build();
        fireBaseAdapter = new FirebaseRecyclerAdapter<ComplainPojo, ComplainHolder>(options) {

            @Override
            protected void onBindViewHolder
                    (@NonNull ComplainHolder viewHolder, int position,@NonNull ComplainPojo model) {

                viewHolder.message.setText(model.getMessage());
                viewHolder.name.setText(model.getName());
                viewHolder.location.setText(model.getLocation());
                Picasso.get().load(model.getImageUrl()).into(viewHolder.imageView);

            }

            @Override
            @NonNull
            public ComplainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.complain, parent, false);

                return new ComplainHolder(view);
            }




        };
        fireBaseAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(fireBaseAdapter);
        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public static class ComplainHolder extends RecyclerView.ViewHolder
    {
        public View view;
        public TextView message,name,location;
        public ImageView imageView;

        public ComplainHolder(View itemView)
        {
            super(itemView);
            view=itemView;

            this.message =  itemView.findViewById(R.id.message);
            this.name =  itemView.findViewById(R.id.name);
            this.location = itemView.findViewById(R.id.location);
            this.imageView=itemView.findViewById(R.id.post);
        }

        public void setMessage(String message) {
            this.message.setText(message);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setLocation(String location) {
            this.location.setText(location);
        }

        public void setImageView(String url) {
            Picasso.get().load(url).fit().into(this.imageView);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        fireBaseAdapter.startListening();
    }
}
