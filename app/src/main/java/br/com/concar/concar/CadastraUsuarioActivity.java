package br.com.concar.concar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.com.concar.concar.dao.UsuarioDAO;
import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Usuario;


public class CadastraUsuarioActivity extends ActionBarActivity {

    private DatabaseHelper helper;
    private EditText edtNome, edtEmail, edtSenha, edtSenha2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_usuario);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        helper = DatabaseHelper.getInstance(this);
    }

    public void cadastrarOnClick(View view) {
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtSenha2 = (EditText) findViewById(R.id.edtSenha2);

        if (edtNome.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtSenha.getText().toString().equals("")) {
            Toast toast = Toast.makeText(this, "Todos os campos precisam ser preenchidos.", Toast.LENGTH_LONG);
            toast.show();

        } else if (edtSenha2.getText().toString().equals(edtSenha.getText().toString())) {

            SQLiteDatabase db = helper.getWritableDatabase();

            UsuarioDAO dao = new UsuarioDAO(this);

            Usuario u = new Usuario();
            u.setNome(edtNome.getText().toString());
            u.setEmail(edtEmail.getText().toString());
            u.setSenha(edtSenha.getText().toString());

            boolean resultado = dao.create(u);

            if (resultado != false) {
                SQLiteDatabase dbh = helper.getReadableDatabase();
                Cursor c = dbh.rawQuery("select * from usuarios", null);
                c.moveToFirst();

                while (c.moveToNext()) {
                    Log.d("Nome: ", c.getString(1));
                }

                c.close();
                Toast toast = Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG);
                toast.show();

                // Limpando o formulário
                edtNome.setText("");
                edtSenha.setText("");
                edtSenha2.setText("");
                edtEmail.setText("");

            } else {
                Toast toast = Toast.makeText(this, "Não foi possível cadastrar o usuário.", Toast.LENGTH_LONG);
                toast.show();
            }

        } else {
            Toast toast = Toast.makeText(this, "Senhas não conferem!", Toast.LENGTH_LONG);
            toast.show();

            // Limpando campo de senha
            edtSenha.setText("");
            edtSenha2.setText("");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastra_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
