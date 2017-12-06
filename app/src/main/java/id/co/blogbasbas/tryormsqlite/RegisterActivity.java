package id.co.blogbasbas.tryormsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.blogbasbas.tryormsqlite.db.DaoSession;
import id.co.blogbasbas.tryormsqlite.db.User;
import id.co.blogbasbas.tryormsqlite.db.UserDao;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.name_label)
    TextView nameLabel;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.btn_save)
    Button btnSave;

    User user;
    DaoSession daoSession;
    boolean createNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        daoSession = ((App) getApplication()).getDaoSession();

        handleIntent(getIntent());
        setClickEventListener();
    }

    private void setClickEventListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (createNew){
                    insertItem();
                } else {
                    updateItem (user.getId());

                }
            }
        });
    }

    private void updateItem(Long id) {
        UserDao userDao = daoSession.getUserDao();
        User  user = new User();
        user.setId(id);
    }

    private void insertItem() {
        UserDao userDao = daoSession.getUserDao();
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        userDao.insert(user);
        Toast.makeText(this,"berhasil input",Toast.LENGTH_LONG).show();
        finish();

    }

    private void handleIntent(Intent intent) {
        createNew = intent.getBooleanExtra("create", false);
        if (!createNew){
            user = (User)intent.getSerializableExtra("user");
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            email.setText(user.getEmail());

        }

    }


}
