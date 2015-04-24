package br.com.concar.concar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import br.com.concar.concar.dao.PropostaDAO;
import br.com.concar.concar.model.Proposta;

/**
 * Created by mdamaceno on 18/04/15.
 */
public class ListaPropostasActivity extends ActionBarActivity {
    private PropostaDAO database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_propostas);

        database = new PropostaDAO(this);

        try {
            database.open();
            final List<Proposta> values = database.index();

            ArrayAdapter<Proposta> adapter = new ArrayAdapter<Proposta>(this, android.R.layout.simple_list_item_1, values);
            ListView allvalues = (ListView)findViewById(R.id.listagem_propostas);
            allvalues.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
