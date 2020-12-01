package hci.geeks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AvailableGroupsActivity extends AppCompatActivity {

    GroupsManager groups = new GroupsManager();
    GroupsDatabaseHelper db;
    ListView groupsList;
    GroupsListAdapter adapter;
    TextView createGroup;
    Button createGroupButton;
    Group groupdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Geeks");
        setContentView(R.layout.activity_available_groups);
        createGroup = findViewById(R.id.creategroupTextview);
        createGroupButton = findViewById(R.id.createGroupButton);

        db = new GroupsDatabaseHelper(this);
        groups.setGroups(db.allGroups());

        if(groups.getGroups() == null){
            groups.newList();
        }

        groupsList = findViewById(R.id.groupsListView);
        registerForContextMenu(groupsList);
        adapter = new GroupsListAdapter(this, 0, groups.getGroups());
        groupsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settingsMenu){
            Toast.makeText(this, "Settings Cliked!", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.logOutMenu) {
            new AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                            finish();
                        }})
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Group Options");
        getMenuInflater().inflate(R.menu.group_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        groupdelete = groups.getGroup(info.position);
        switch (item.getItemId()) {
            case R.id.joinMenu:
                if(groupdelete.getPrivacy().equals("Private")){
                    new AlertDialog.Builder(this)
                            .setTitle("Join Group")
                            .setMessage("This group is private. A request will be sent to the administrator.")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    confirm();
                                }})
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    cancel();
                                }
                            }).show();
                }
                else{
                    int members = groupdelete.getMembers();
                    groupdelete.setMembers(members + 1);
                    db.updateGroup(groupdelete);
                    groups.setGroups(db.allGroups());
                    adapter = new GroupsListAdapter(this, 0, groups.getGroups());
                    groupsList.setAdapter(adapter);
                    Toast.makeText(this, "You Joined the group!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.deleteMenu:
                new AlertDialog.Builder(this)
                        .setTitle("Delete Group")
                        .setMessage("Are you sure you want to delete this group?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                confirmdelete(info.position);
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                canceldelete();
                            }
                        }).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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

            view = row.findViewById(R.id.groupSubject);
            view.setText(String.valueOf(group.getSubject()));

            view = row.findViewById(R.id.groupPrivacy);
            view.setText(String.valueOf(group.getPrivacy()));
            if(view.getText().toString().equals("Private")){
                view.setTextColor(Color.RED);
            }
            else{
                view.setTextColor(Color.GREEN);
            }

            view = row.findViewById(R.id.groupMembers);
            view.setText(String.valueOf(group.getMembers()));

            return row;
        }
    }

    public void createGroupButtonClicked(View view){
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivityForResult(intent, 2);
    }

    public void confirm(){
        Toast.makeText(this, "Join Request Sent!", Toast.LENGTH_SHORT).show();
    }

    public void cancel(){
        Toast.makeText(this, "Join Request Cancelled!", Toast.LENGTH_SHORT).show();
    }

    public void confirmdelete(int position){
        db.deleteGroup(groupdelete.getId());
        groups.removeGroup(position);
        adapter = new GroupsListAdapter(this, 0, groups.getGroups());
        groupsList.setAdapter(adapter);
        Toast.makeText(this, "Group Deleted!", Toast.LENGTH_SHORT).show();
    }

    public void canceldelete(){
        Toast.makeText(this, "Deletion Cancelled!", Toast.LENGTH_SHORT).show();
    }

    public void logout(){
        Toast.makeText(this, "Log Out!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2) {
            String name = data.getStringExtra("Name");
            String subject = data.getStringExtra("Subject");
            String privacy = data.getStringExtra("Privacy");
            int members = data.getIntExtra("Members", 0);
            if(name.equals("")){
                Toast.makeText(this, "Operation Canceled!", Toast.LENGTH_SHORT).show();
            }
            else{
                Group group = new Group(name, subject, privacy, members);
                group.setId(groups.getGroups().size());
                boolean status = db.addGroup(group);
                if(status){
                    Toast.makeText(this, "Group Created Successfully!", Toast.LENGTH_SHORT).show();
                    groups.addGroup(group);
                    adapter = new GroupsListAdapter(this, 0, groups.getGroups());
                    groupsList.setAdapter(adapter);
                }
                else{
                    Toast.makeText(this, "Group Creation Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}