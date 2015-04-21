package br.com.concar.concar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Cliente;

/**
 * Created by mdamaceno on 20/04/15.
 */
public class CLienteDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] colunas = {
            DatabaseHelper.COLUMN_K_ID, DatabaseHelper.COLUMN_K_NOME, DatabaseHelper.COLUMN_K_EMAIL,
            DatabaseHelper.COLUMN_K_TELEFONE, DatabaseHelper.COLUMN_K_SEXO, DatabaseHelper.COLUMN_K_SENHA
    };

    public CLienteDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        dbHelper.close();
    }

    public boolean create(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_K_NOME, cliente.getNome());
        values.put(DatabaseHelper.COLUMN_K_EMAIL, cliente.getEmail());
        values.put(DatabaseHelper.COLUMN_K_TELEFONE, cliente.getTelefone());
        values.put(DatabaseHelper.COLUMN_K_SEXO, cliente.getSexo());
        values.put(DatabaseHelper.COLUMN_K_SENHA, cliente.getSenha());

        return (db.insert(DatabaseHelper.TABLE_K, null, values) > 0);
    }

    public void destroy(Cliente cliente) {
        long id = cliente.getId();

        System.out.println("Cliente com id " + id + " foi excluído.");
        database.delete(DatabaseHelper.TABLE_K, DatabaseHelper.COLUMN_K_ID + " = " + id, null);
    }

    public List<Cliente> index() {
        List<Cliente> clientes = new ArrayList<Cliente>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_K, colunas, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Cliente cliente = cursorToCliente(cursor);
            clientes.add(cliente);
            cursor.moveToNext();
        }

        cursor.close();

        return clientes;
    }

    private Cliente cursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();

        cliente.setId(cursor.getInt(0));
        cliente.setNome(cursor.getString(1));
        cliente.setEmail(cursor.getString(2));
        cliente.setTelefone(cursor.getString(3));
        cliente.setSenha(cursor.getString(4));

        return cliente;
    }
}
