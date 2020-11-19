package hci.geeks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AvailableGroupsActivity extends AppCompatActivity {

    GroupsManager groups = new GroupsManager();
    GroupsDatabaseHelper db;
    ListView groupsList;
    GroupsListAdapter adapter;
    TextView groupName;
    int groupId;
    String name;
    //Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Geeks");
        setContentView(R.layout.activity_available_groups);
        groupName = findViewById(R.id.groupName);
        db = new GroupsDatabaseHelper(this);
        groups.setGroups(db.allGroups());
        if(groups.getGroups() == null){
            groups.newList();
        }

       // Intent intent = getIntent();

      // groupId = intent.getIntExtra("Id", 0);
       // name = intent.getStringExtra("Name");

        groupsList = findViewById(R.id.groupsListView);
        registerForContextMenu(groupsList);
        adapter = new GroupsListAdapter(this, 0, groups.getGroups());
        groupsList.setAdapter(adapter);
    }

    private static class GroupsListAdapter extends ArrayAdapter<Group> {

        private List<Group> groups;

        public GroupsListAdapter(Context ctx, int resourceId, List<Group> groups){
            super(ctx, resourceId, groups);
            this.groups = groups;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View row = convertView != null ? convertView
                    : LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_group_details, parent, false);

            Group group = groups.get(position);

            TextView view = row.findViewById(R.id.groupName);
            view.setText(group.getName());

            //view = row.findViewById(R.id.appointmentDate);
            //view.setText(String.valueOf(appointment.getDate()));

            return row;
        }
    }

    public void createGroupButtonClicked(View view){
       // Intent intent = new Intent(this, AppointmentsActivity.class); //Kevin's class
       // startActivity(intent);
    }

}