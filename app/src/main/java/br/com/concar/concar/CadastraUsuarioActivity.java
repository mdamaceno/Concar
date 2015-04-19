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

import br.com.concar.concar.database.DatabaseHelper;


public class CadastraUsuarioActivity extends ActionBarActivity {

    private DatabaseHelper helper;
    private EditText edtNome, edtEmail, edtSenha, edtSenha2;
    RadioGroup rg;
    int pos, pos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_usuario);

        helper = DatabaseHelper.getInstance(this);

        rg = (RadioGroup) findViewById(R.id.rdoTipo);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pos = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));

                Toast.makeText(getBaseContext(), "ID = " + String.valueOf(pos), Toast.LENGTH_SHORT).show();

                switch (pos){
                    case 0 :
                        pos1 = 0;
                        break;
                    case 1 :
                        pos1 = 1;
                        break;
                }
            }
        });
    }

    public void cadastrarOnClick(View view) {
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtSenha2 = (EditText) findViewById(R.id.edtSenha2);

        if (edtSenha2.getText().toString().equals(edtSenha.getText().toString())) {
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nome", edtNome.getText().toString());
            values.put("email", edtEmail.getText().toString());
            values.put("senha", edtSenha.getText().toString());
            values.put("tipo", pos1);

            long resultado = db.insert("usuarios", null, values);

            if(resultado != -1) {
                SQLiteDatabase dbh = helper.getReadableDatabase();
                Cursor c = dbh.rawQuery("select * from usuarios",null);
                c.moveToFirst();

                while (c.moveToNext()){
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
