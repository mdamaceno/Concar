package br.com.concar.concar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Logger;

import br.com.concar.concar.dao.PropostaDAO;
import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Proposta;


public class CadastraPropostaActivity extends ActionBarActivity {

    private TextView txtCampo1, txtCampo2, txtCampo3, txtCampo4, txtCampo5, txtCampo6, txtCampo7;
    private EditText edtValorEntrada, edtParcela;
    private Bundle bundle;
    private AlertDialog.Builder alerta;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_proposta);

        helper = DatabaseHelper.getInstance(this);

        bundle = getIntent().getExtras();
        String marca = bundle.getString("MARCA");
        String modelo = bundle.getString("MODELO");
        String preco = bundle.getString("PRECO");
        String ano = bundle.getString("ANO");
        String cor = bundle.getString("COR");
        String idCarro = bundle.getString("ID");

        txtCampo1 = (TextView)findViewById(R.id.txtCampo1);
        txtCampo2 = (TextView)findViewById(R.id.txtCampo2);
        txtCampo3 = (TextView)findViewById(R.id.txtCampo3);
        txtCampo4 = (TextView)findViewById(R.id.txtCampo4);
        txtCampo5 = (TextView)findViewById(R.id.txtCampo5);
        txtCampo6 = (TextView)findViewById(R.id.txtCampo6);
        txtCampo7 = (TextView)findViewById(R.id.txtCampo7);

        txtCampo1.setText(marca + " " + modelo + " - " + ano);
        txtCampo2.setText("Cor: " + cor);
        txtCampo3.setText("Valor do veículo: R$" + preco);
    }

    public void calcularOnClick(View view) {
        bundle = getIntent().getExtras();
        String preco1 = bundle.getString("PRECO");
        preco1 = preco1.replaceAll(",", ".");

        edtValorEntrada = (EditText)findViewById(R.id.edtValorEntrada);
        edtParcela = (EditText)findViewById(R.id.edtParcela);

        if (edtValorEntrada.getText().toString().equals("")) {
            Toast.makeText(this, "Digite um valor para o cálculo.", Toast.LENGTH_SHORT).show();

        } else if (Double.parseDouble(preco1) < Double.parseDouble(edtValorEntrada.getText().toString())) {
            Toast.makeText(this, "Valor de entrada deve ser menor que o valor do veículo.", Toast.LENGTH_SHORT).show();

        } else {
            double totalValor =  Double.parseDouble(preco1) - Double.parseDouble(edtValorEntrada.getText().toString());
            double valorParcelas = 0;

            if (edtParcela.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Digite o número de parcelas", Toast.LENGTH_LONG).show();

            } else if (Integer.parseInt(edtParcela.getText().toString()) <= 0 || Integer.parseInt(edtParcela.getText().toString()) > 96){
                Toast.makeText(getApplicationContext(), "Digite um número entre 1 e 96", Toast.LENGTH_LONG).show();

            } else {
                valorParcelas = totalValor / Integer.parseInt(edtParcela.getText().toString());
                valorParcelas = valorParcelas + (valorParcelas * 1.52 / 100);

                txtCampo4.setText("Entrada: R$" + String.format("%10.2f", Double.parseDouble(edtValorEntrada.getText().toString())));
                txtCampo5.setText("Diferença: R$" + String.format("%10.2f", totalValor));
                txtCampo6.setText("Nº de parcelas: " + edtParcela.getText().toString());
                txtCampo7.setText("Valor das parcelas: R$" + String.format("%10.2f", valorParcelas) + " (com juros de 1,52% a.m)");
            }

        }
    }

    public void confirmarOnClick(View view) {
        bundle = getIntent().getExtras();
        String idCarro = bundle.getString("ID");
        PropostaDAO dao = new PropostaDAO(this);
        Proposta p = new Proposta();

        p.setTipo_pagamento(0);
        p.setConfirmacao(false);
        p.setNum_parcelas(15);
        p.setValor_entrada(5000);
        p.setValor_carro(8000);
        p.setValor_parcela(126);
        p.setIdCarro(1);
        p.setIdCliente(1);

        boolean resultado = dao.create(p);

        if (resultado != false) {
            SQLiteDatabase dbh = helper.getReadableDatabase();
            Cursor cur = dbh.rawQuery("select * from propostas", null);
            cur.moveToFirst();

            while (cur.moveToNext()) {
                System.out.println("PPPPPPPPPP: " + cur.getString(1) + " - AAAAAAAAAAA: " + cur.getString(2));
            }

            cur.close();
            Toast toast = Toast.makeText(this, "Proposta registrada com sucesso.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Não foi possível registrar a proposta.", Toast.LENGTH_LONG);
            toast.show();
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
