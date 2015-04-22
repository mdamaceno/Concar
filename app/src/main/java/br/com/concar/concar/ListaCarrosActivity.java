package br.com.concar.concar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import br.com.concar.concar.dao.CarroDAO;
import br.com.concar.concar.model.Carro;

/**
 * Created by mdamaceno on 18/04/15.
 */
public class ListaCarrosActivity extends ActionBarActivity {
    private CarroDAO database;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carros);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        database = new CarroDAO(this);

        try {
            database.open();
            final List<Carro> values = database.index();

            ArrayAdapter<Carro> adapter = new ArrayAdapter<Carro>(this, android.R.layout.simple_list_item_1, values);
            ListView allvallues = (ListView)findViewById(R.id.listagem_carros);
            allvallues.setAdapter(adapter);

            alerta = new AlertDialog.Builder(this);

            database.close();

            allvallues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String SimNao1, SimNao2;

                    if (values.get(position).getAirbag() == 0) { SimNao1 = "Não"; }
                    else { SimNao1 = "Sim"; }

                    if (values.get(position).getAr_condicionado() == 0) { SimNao2 = "Não"; }
                    else { SimNao2 = "Sim"; }

                    alerta.setTitle("Informações do carro");
                    alerta.setMessage(
                            "Marca: " + values.get(position).getMarca() + "\n\n" +
                                    "Modelo: " + values.get(position).getModelo() + "\n\n" +
                                    "Ano: " + values.get(position).getAno() + "\n\n" +
                                    "Airbag: " + SimNao1 + "\n\n" +
                                    "Ar-condicionado: " + SimNao2 + "\n\n" +
                                    "Cor: " + values.get(position).getCor() + "\n\n" +
                                    "Preço: R$" + String.format("%10.2f", values.get(position).getPreco())
                    );
                    alerta.setPositiveButton("Fazer proposta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Intent pass = new Intent(getApplicationContext(), CadastraPropostaActivity.class);
                            pass.putExtra("MARCA", values.get(position).getMarca());
                            pass.putExtra("MODELO", values.get(position).getModelo());
                            pass.putExtra("PRECO", String.format("%10.2f", values.get(position).getPreco()));
                            pass.putExtra("ANO", String.valueOf(values.get(position).getAno()));
                            startActivity(pass);
                        }
                    });
                    AlertDialog alert = alerta.create();
                    alert.show();
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
