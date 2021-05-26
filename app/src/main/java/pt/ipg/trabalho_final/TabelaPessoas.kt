package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_Tabela ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $Data_Nascimento TEXT NOT NULL, $CAMPO_CC TEXT NOT NULL, $MORADA TEXT NOT NULL, $CONTACTO TEXT NOT NULL)")

    }


    fun insert(values: ContentValues): Long {
        return db.insert(NOME_Tabela, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_Tabela, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_Tabela, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(NOME_Tabela, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_Tabela = "Pessoas"
        const val CAMPO_NOME = "nome"
        const val Data_Nascimento = "data_nascimento"
        const val MORADA = "morada"
        const val CAMPO_CC = "num_cartao_cidadao"
        const val CONTACTO = "contacto"
    }
}