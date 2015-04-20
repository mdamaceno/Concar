package br.com.concar.concar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.concar.concar.database.DatabaseHelper;
import br.com.concar.concar.model.Usuario;

/**
 * Created by mdamaceno on 18/04/15.
 */
public class UsuarioDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] colunas = {
        DatabaseHelper.COLUMN_U_ID, DatabaseHelper.COLUMN_U_NOME, DatabaseHelper.COLUMN_U_EMAIL,
        DatabaseHelper.COLUMN_U_SENHA, DatabaseHelper.COLUMN_U_TIPO
    };

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        dbHelper.close();
    }

    public boolean create(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_U_NOME, usuario.getNome());
        values.put(DatabaseHelper.COLUMN_U_EMAIL, usuario.getEmail());
        values.put(DatabaseHelper.COLUMN_U_SENHA, usuario.getSenha());
        values.put(DatabaseHelper.COLUMN_U_TIPO, usuario.getTipo());

        return (db.insert(DatabaseHelper.TABLE_U, null, values) > 0);
    }

    public void destroy(Usuario usuario) {
        long id = usuario.getId();

        System.out.println("Usuário com id " + id + " foi excluído.");
        database.delete(DatabaseHelper.TABLE_U, DatabaseHelper.COLUMN_U_ID + " = " + id, null);
    }

    public List<Usuario> index() {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_U, colunas, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Usuario usuario = cursorToUsuario(cursor);
            usuarios.add(usuario);
            cursor.moveToNext();
        }

        cursor.close();

        return usuarios;
    }

    private Usuario cursorToUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();

        usuario.setId(cursor.getInt(0));
        usuario.setNome(cursor.getString(1));
        usuario.setEmail(cursor.getString(2));
        usuario.setSenha(cursor.getString(3));
        usuario.setTipo(cursor.getInt(4));

        return usuario;
    }
}
