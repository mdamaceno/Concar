package br.com.concar.concar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Proposta;

/**
 * Created by mdamaceno on 22/04/15.
 */
public class PropostaDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] colunas = {
            DatabaseHelper.COLUMN_P_ID, DatabaseHelper.COLUMN_P_TIPOPAGAMENTO, DatabaseHelper.COLUMN_P_NUMPARCELAS,
            DatabaseHelper.COLUMN_P_VALORENTRADA, DatabaseHelper.COLUMN_P_VALORPARCELA,
            DatabaseHelper.COLUMN_P_IDCARRO, DatabaseHelper.COLUMN_P_IDCLIENTE
    };

    public PropostaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        dbHelper.close();
    }

    public boolean create(Proposta proposta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_P_TIPOPAGAMENTO, proposta.getTipo_pagamento());
        values.put(DatabaseHelper.COLUMN_P_NUMPARCELAS, proposta.getNum_parcelas());
        values.put(DatabaseHelper.COLUMN_P_VALORENTRADA, proposta.getValor_entrada());
//        values.put(DatabaseHelper.COLUMN_P_VALORCARRO, proposta.getValor_carro());
        values.put(DatabaseHelper.COLUMN_P_VALORPARCELA, proposta.getValor_parcela());
        values.put(DatabaseHelper.COLUMN_P_IDCARRO, proposta.getIdCarro());
        values.put(DatabaseHelper.COLUMN_P_IDCLIENTE, proposta.getIdCliente());

        return (db.insert(DatabaseHelper.TABLE_P, null, values) > 0);
    }

    public void destroy(Proposta proposta) {
        long id = proposta.getId();

        System.out.println("Proposta com id " + id + " foi excluído.");
        database.delete(DatabaseHelper.TABLE_P, DatabaseHelper.COLUMN_U_ID + " = " + id, null);
    }

    public List<Proposta> index() {
        List<Proposta> propostas = new ArrayList<Proposta>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_P, colunas, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Proposta proposta = cursorToProposta(cursor);
            propostas.add(proposta);
            cursor.moveToNext();
        }

        cursor.close();

        return propostas;
    }

    private Proposta cursorToProposta(Cursor cursor) {
        Proposta proposta = new Proposta();

        proposta.setId(cursor.getInt(0));
        proposta.setTipo_pagamento(cursor.getInt(1));
        proposta.setNum_parcelas(cursor.getInt(2));
        proposta.setValor_entrada(cursor.getDouble(3));
        proposta.setValor_carro(cursor.getDouble(4));
        proposta.setValor_parcela(cursor.getDouble(4));
        proposta.setIdCarro(cursor.getInt(5));
        proposta.setIdCliente(cursor.getInt(6));

        return proposta;
    }
}
