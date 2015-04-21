package br.com.concar.concar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by mdamaceno on 18/04/15.
 */
public class ListaMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_menu);

        ListView listView = (ListView) findViewById(R.id.lista_menu);

        final String[] items = new String[] {
                new String("Carros cadastrados"),
                new String("Usuário cadastrados"),
                new String("Clientes cadastrados"),
                new String("Propostas"),
                new String("Cadastrar novo usuário"),
                new String("Cadastrar novo cliente")
        };

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), ListaCarrosActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ListaUsuariosActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), ListaClientesActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), ListaPropostasActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(), CadastraUsuarioActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getApplicationContext(), CadastraClienteActivity.class));
                        break;
                }
            }
        });

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
