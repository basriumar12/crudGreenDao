package id.co.blogbasbas.tryormsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import id.co.blogbasbas.tryormsqlite.db.DaoSession;
import id.co.blogbasbas.tryormsqlite.db.User;
import id.co.blogbasbas.tryormsqlite.db.UserDao;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List <User> user = new ArrayList<>();
    DaoSession daoSession;
    private UserDao userDao;
    ArrayAdapter <User> userArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        //daoSession = ((App)getApplication()).getDaoSession();
         daoSession = App.getDaoSession();
        List<User> users  = daoSession.getUserDao().queryBuilder().list();

        Log.e("MainActivity","OnCreate");
        for (User user : users){
            Log.e("MainActivity.java",user.getEmail()+" | "+user.getUsername());
        }
        setupList();

    }


    @Override
    protected void onResume() {
        super.onResume();
      fetchUserList();
    }

    private void setupList() {
        userArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user);
        listView.setAdapter(userArrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showOptions(i);
                return false;
            }
        });
    }

    private void showOptions(int i) {
        final User selectedItem = user.get(i);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String[] options = new String[2];
        options[0] = "Edit " + selectedItem.getUsername();
        options[1] = "Hapus " + selectedItem.getUsername();

        alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which == 0){
                    protectUpdateitem(selectedItem);
                } else if (which == 1){
                    protectDeleteitem(selectedItem.getId());
                }
            }
        });
        alertDialogBuilder.create().show();

    }

    private void protectUpdateitem(User selectedItem) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("create", false);
        intent.putExtra("user", (Serializable)user);
        startActivity(intent);

    }

    private void protectDeleteitem(Long id) {
        UserDao userDao = daoSession.getUserDao();
        userDao.deleteByKey(id);
        fetchUserList();
    }

    private void fetchUserList() {
       // user.clear();
        UserDao userDao = daoSession.getUserDao();
        user.addAll(userDao.loadAll());
        userArrayAdapter.notifyDataSetChanged();
    }

    public void addNewItem(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("create", true);
        startActivity(intent);
    }
}
