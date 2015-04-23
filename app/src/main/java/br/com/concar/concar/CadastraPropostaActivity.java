package br.com.concar.concar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CadastraPropostaActivity extends ActionBarActivity {

    private TextView txtCampo1, txtCampo2, txtCampo3, txtValorTotal;
    private EditText edtValorEntrada;
    private Bundle bundle;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_proposta);

        bundle = getIntent().getExtras();
        String marca = bundle.getString("MARCA");
        String modelo = bundle.getString("MODELO");
        String preco = bundle.getString("PRECO");
        String ano = bundle.getString("ANO");
        String cor = bundle.getString("COR");

        txtCampo1 = (TextView)findViewById(R.id.txtCampo1);
        txtCampo2 = (TextView)findViewById(R.id.txtCampo2);
        txtCampo3 = (TextView)findViewById(R.id.txtCampo3);

        txtCampo1.setText(marca + " " + modelo + " - " + ano);
        txtCampo2.setText(cor);
        txtCampo3.setText("R$" + preco);
    }

    public void calcularOnClick(View view) {
        bundle = getIntent().getExtras();
        String preco1 = bundle.getString("PRECO");
        preco1 = preco1.replaceAll(",",".");

        edtValorEntrada = (EditText)findViewById(R.id.edtValorEntrada);

        if (edtValorEntrada.getText().toString().equals("")) {
            Toast.makeText(this, "Digite um valor para o cálculo.", Toast.LENGTH_SHORT).show();

        } else if (Double.parseDouble(preco1) < Double.parseDouble(edtValorEntrada.getText().toString())) {
            Toast.makeText(this, "Valor de entrada deve ser menor que o valor do veículo.", Toast.LENGTH_SHORT).show();

        } else {
            LayoutInflater inflater = getLayoutInflater();
            LinearLayout prop = (LinearLayout)getLayoutInflater().inflate(R.layout.proposta, null);
            double totalValor = Double.parseDouble(preco1) - Double.parseDouble(edtValorEntrada.getText().toString());

            alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Proposta");
            alerta.setMessage("Diferença a ser paga: R$" + String.format("%10.2f", totalValor));

            alerta.setView(inflater.inflate(R.layout.proposta, null));

            AlertDialog alert = alerta.create();

            alert.show();

            Button btn = (Button)prop.findViewById(R.id.btnCalcular);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "Ola mundo", Toast.LENGTH_SHORT).show();
                }
            });

            txtValorTotal = (TextView)prop.findViewById(R.id.txtValorTotal);
            txtValorTotal.setText("Teste");
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
