package br.com.concar.concar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class CadastraPropostaActivity extends ActionBarActivity {

    private TextView txtMarca, txtModelo, txtPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_proposta);

        Bundle bundle = getIntent().getExtras();
        String marca = bundle.getString("MARCA");
        String modelo = bundle.getString("MODELO");
        String preco = bundle.getString("PRECO");

        txtMarca = (TextView)findViewById(R.id.txtMarca);
        txtModelo = (TextView)findViewById(R.id.txtModelo);
        txtPreco = (TextView)findViewById(R.id.txtPreco);

        txtMarca.setText(marca);
        txtModelo.setText(modelo);
        txtPreco.setText("R$"+preco);
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
