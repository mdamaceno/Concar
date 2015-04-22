package br.com.concar.concar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import br.com.concar.concar.dao.CLienteDAO;
import br.com.concar.concar.model.Cliente;

/**
 * Created by mdamaceno on 21/04/15.
 */
public class ListaClientesActivity extends ActionBarActivity {
    private CLienteDAO database;
    private AlertDialog.Builder testAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        database = new CLienteDAO(this);

        try {
            database.open();
            final List<Cliente> values = database.index();

            ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, values);
            ListView allvalues = (ListView)findViewById(R.id.listagem_clientes);
            allvalues.setAdapter(adapter);

            testAlert = new AlertDialog.Builder(this);

            allvalues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String sexo;
                    if  (values.get(position).getSexo() == 0) {
                        sexo = "Masculino";
                    } else {
                        sexo = "Feminino";
                    }
                    testAlert.setTitle("Informações do cliente");
                    testAlert.setMessage(
                            "Nome: " + values.get(position).getNome() + "\n\n" +
                            "Email: " + values.get(position).getEmail() + "\n\n" +
                            "Telefone: " + values.get(position).getTelefone() + "\n\n" +
                            "Sexo: " + sexo
                    );
                    System.out.println("Sexo: " + values.get(position).getSexo());
                    AlertDialog test = testAlert.create();
                    test.show();
                    sexo = null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        getMenuInflater().inflate(R.menu.search, menu);

        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setQueryHint("busca");

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
