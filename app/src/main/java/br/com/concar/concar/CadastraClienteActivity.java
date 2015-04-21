package br.com.concar.concar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.com.concar.concar.dao.CLienteDAO;
import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Cliente;

/**
 * Created by mdamaceno on 20/04/15.
 */
public class CadastraClienteActivity extends ActionBarActivity {

    private DatabaseHelper helper;
    private EditText edtNome, edtEmail, edtTelefone, edtSenha, edtSenha2;
    RadioGroup rg;
    int pos, pos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_cliente);

        helper = DatabaseHelper.getInstance(this);

        rg = (RadioGroup) findViewById(R.id.rdoSexo);

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

    public void cadastrarOnClick(View view) {
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtSenha2 = (EditText) findViewById(R.id.edtSenha2);
        rg = (RadioGroup) findViewById(R.id.rdoSexo);

        if (edtNome.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtTelefone.getText().toString().equals("") || edtSenha.getText().toString().equals("")) {
            Toast toast = Toast.makeText(this, "Todos os campos precisam ser preenchidos.", Toast.LENGTH_LONG);
            toast.show();

        } else if (edtSenha2.getText().toString().equals(edtSenha.getText().toString())) {

            SQLiteDatabase db = helper.getWritableDatabase();

            CLienteDAO dao = new CLienteDAO(this);

            Cliente k = new Cliente();
            k.setNome(edtNome.getText().toString());
            k.setEmail(edtEmail.getText().toString());
            k.setTelefone(edtTelefone.getText().toString());
            k.setSenha(edtSenha.getText().toString());
            int checkedRadioButton = rg.getCheckedRadioButtonId();

            if (checkedRadioButton == R.id.rdoSexoF) {
                k.setSexo(1);
            } else {
                k.setSexo(0);
            }

            boolean resultado = dao.create(k);

            if (resultado != false) {
                SQLiteDatabase dbh = helper.getReadableDatabase();
                Cursor c = dbh.rawQuery("select * from clientes", null);
                c.moveToFirst();

                while (c.moveToNext()) {
                    System.out.println("Nome: " + c.getString(1) + " - Sexo: " + c.getString(5));
                }

                c.close();
                Toast toast = Toast.makeText(this, "Cliente cadastrado com sucesso.", Toast.LENGTH_LONG);
                toast.show();

                // Limpando o formulário
                edtNome.setText("");
                edtSenha.setText("");
                edtSenha2.setText("");
                edtEmail.setText("");
                edtTelefone.setText("");

            } else {
                Toast toast = Toast.makeText(this, "Não foi possível cadastrar o cliente.", Toast.LENGTH_LONG);
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
}
