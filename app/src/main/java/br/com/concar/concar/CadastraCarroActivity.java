package br.com.concar.concar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.com.concar.concar.dao.CarroDAO;
import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Carro;


public class CadastraCarroActivity extends ActionBarActivity {

    private DatabaseHelper helper;
    private EditText edtMarca, edtModelo, edtAno, edtQuilometragem, edtCor, edtPreco;
    private RadioGroup rgAirbag, rgArcondicionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_carro);

        helper = DatabaseHelper.getInstance(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        edtMarca = (EditText) findViewById(R.id.edtMarca);
        edtModelo = (EditText) findViewById(R.id.edtModelo);
        edtAno = (EditText) findViewById(R.id.edtAno);
        edtQuilometragem = (EditText) findViewById(R.id.edtQuilometragem);
        edtCor = (EditText) findViewById(R.id.edtCor);
        edtPreco = (EditText) findViewById(R.id.edtPreco);
        rgAirbag = (RadioGroup) findViewById(R.id.rdoAirbag);
        rgArcondicionado = (RadioGroup) findViewById(R.id.rdoArcondicionado);

        if (
            edtMarca.getText().toString().equals("") ||
            edtModelo.getText().toString().equals("") ||
            edtAno.getText().toString().equals("") ||
            edtQuilometragem.getText().toString().equals("") ||
            edtCor.getText().toString().equals("") ||
            edtPreco.getText().toString().equals("")
        ) {
            Toast toast = Toast.makeText(this, "Todos os campos precisam ser preenchidos.", Toast.LENGTH_LONG);
            toast.show();

        } else {
            CarroDAO dao = new CarroDAO(this);

            Carro c = new Carro();
            c.setMarca(edtMarca.getText().toString());
            c.setModelo(edtModelo.getText().toString());
            c.setAno(Integer.parseInt(edtAno.getText().toString()));
            c.setQuilometragem(Double.parseDouble(edtQuilometragem.getText().toString()));
            int checkedRadioButtonAirbag = rgAirbag.getCheckedRadioButtonId();
            int checkedRadioButtonAr = rgArcondicionado.getCheckedRadioButtonId();

            if (checkedRadioButtonAirbag == R.id.rdoAirbagS) {
                c.setAirbag(1);
            } else {
                c.setAirbag(0);
            }

            if (checkedRadioButtonAr == R.id.rdoArcondicionadoS) {
                c.setAr_condicionado(1);
            } else {
                c.setAr_condicionado(0);
            }

            c.setCor(edtCor.getText().toString());
            c.setPreco(Double.parseDouble(edtPreco.getText().toString()));

            boolean resultado = dao.create(c);

            if (resultado != false) {
                SQLiteDatabase dbh = helper.getReadableDatabase();
                Cursor cur = dbh.rawQuery("select * from carros", null);
                cur.moveToFirst();

                while (cur.moveToNext()) {
                    System.out.println("Marca: " + cur.getString(1) + " - Modelo: " + cur.getString(2));
                }

                cur.close();
                Toast toast = Toast.makeText(this, "Carro cadastrado com sucesso.", Toast.LENGTH_LONG);
                toast.show();

                // Limpando o formulário
                edtMarca.setText("");
                edtModelo.setText("");
                edtAno.setText("");
                edtCor.setText("");
                edtPreco.setText("");

            } else {
                Toast toast = Toast.makeText(this, "Não foi possível cadastrar o carro.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
