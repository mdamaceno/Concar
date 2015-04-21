package br.com.concar.concar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Carro;

/**
 * Created by mdamaceno on 21/04/15.
 */
public class CarroDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] colunas = {
            DatabaseHelper.COLUMN_C_MARCA, DatabaseHelper.COLUMN_C_MODELO, DatabaseHelper.COLUMN_C_ANO,
            DatabaseHelper.COLUMN_C_QUILOMETRAGEM, DatabaseHelper.COLUMN_C_QUILOMETRAGEM,
            DatabaseHelper.COLUMN_C_AIRBAG, DatabaseHelper.COLUMN_C_ARCONDICIONADO,
            DatabaseHelper.COLUMN_C_COR, DatabaseHelper.COLUMN_C_PRECO
    };

    public CarroDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        dbHelper.close();
    }

    public boolean create(Carro carro) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_C_MARCA, carro.getMarca());
        values.put(DatabaseHelper.COLUMN_C_MODELO, carro.getModelo());
        values.put(DatabaseHelper.COLUMN_C_ANO, carro.getAno());
        values.put(DatabaseHelper.COLUMN_C_QUILOMETRAGEM, carro.getQuilometragem());
        values.put(DatabaseHelper.COLUMN_C_AIRBAG, carro.getAirbag());
        values.put(DatabaseHelper.COLUMN_C_ARCONDICIONADO, carro.getAr_condicionado());
        values.put(DatabaseHelper.COLUMN_C_COR, carro.getCor());
        values.put(DatabaseHelper.COLUMN_C_PRECO, carro.getPreco());

        return (db.insert(DatabaseHelper.TABLE_C, null, values) > 0);
    }

    public void destroy(Carro carro) {
        long id = carro.getId();

        System.out.println("Carro com id " + id + " foi excluído.");
        database.delete(DatabaseHelper.TABLE_C, DatabaseHelper.COLUMN_C_ID + " = " + id, null);
    }
}
