package br.com.concar.concar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mdamaceno on 18/04/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;
    private Context ctx;
    private static final String BANCO_DADOS = "Concar";
    private static int VERSAO = 1;

    /* Tabela usuarios e suas colunas */
    public static final String TABLE_U = "usuarios";
    public static final String COLUMN_U_ID = "_id";
    public static final String COLUMN_U_NOME = "nome";
    public static final String COLUMN_U_EMAIL = "email";
    public static final String COLUMN_U_SENHA = "senha";
    public static final String COLUMN_U_TIPO = "tipo";

    /* Tabela carros e suas colunas */
    public static final String TABLE_C = "carros";
    public static final String COLUMN_C_ID = "_id";
    public static final String COLUMN_C_MARCA = "marca";
    public static final String COLUMN_C_MODELO = "modelo";
    public static final String COLUMN_C_ANO = "ano";
    public static final String COLUMN_C_QUILOMETRAGEM = "quilometragem";
    public static final String COLUMN_C_AIRBAG = "airbag";
    public static final String COLUMN_C_ARCONDICIONADO = "ar_condicionado";
    public static final String COLUMN_C_COR = "cor";
    public static final String COLUMN_C_PRECO = "preco";

    /* Tabela propostas e suas colunas */
    public static final String TABLE_P = "propostas";
    public static final String COLUMN_P_ID = "_id";
    public static final String COLUMN_P_TIPOPAGAMENTO = "tipo_pagamento";
    public static final String COLUMN_P_NUMPARCELAS = "num_parcelas";
    public static final String COLUMN_P_VALORENTRADA = "valor_entrada";
    public static final String COLUMN_P_VALORCARRO = "valor_carro";
    public static final String COLUMN_P_VALORFINAL = "valor_final";
    public static final String COLUMN_P_CONFIRMACAO = "confirmacao";
    public static final String COLUMN_P_IDCARRO = "idCarro";
    public static final String COLUMN_P_IDCLIENTE = "idCliente";
    public static final String COLUMN_P_IDVENDEDOR = "idVendedor";

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
        this.ctx = context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_U + " (" + COLUMN_U_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_U_NOME + " TEXT, " + COLUMN_U_EMAIL + " TEXT, " + COLUMN_U_SENHA + " TEXT, " + COLUMN_U_TIPO + " INTEGER);");

        db.execSQL("CREATE TABLE " + TABLE_C + " (" + COLUMN_C_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_C_MARCA + " TEXT, " + COLUMN_C_MODELO + " TEXT, " + COLUMN_C_ANO + " INTEGER, " + COLUMN_C_QUILOMETRAGEM + " REAL, " +
                COLUMN_C_AIRBAG + " INTEGER, " + COLUMN_C_ARCONDICIONADO + " INTEGER, " + COLUMN_C_COR + " TEXT, " + COLUMN_C_PRECO + " REAL);");

        db.execSQL("CREATE TABLE " + TABLE_P + " (" + COLUMN_P_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_P_IDCARRO + " INTEGER, " + COLUMN_P_IDCLIENTE + " INTEGER, " + COLUMN_P_IDVENDEDOR + " INTEGER " +
                COLUMN_P_TIPOPAGAMENTO + " INTEGER, " + COLUMN_P_NUMPARCELAS + " INTEGER, " + COLUMN_P_VALORENTRADA + " REAL " +
                COLUMN_P_VALORCARRO + " REAL, " + COLUMN_P_VALORFINAL + " REAL, " + COLUMN_P_CONFIRMACAO + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.ctx.deleteDatabase(BANCO_DADOS);
        onCreate(db);
    }
}
