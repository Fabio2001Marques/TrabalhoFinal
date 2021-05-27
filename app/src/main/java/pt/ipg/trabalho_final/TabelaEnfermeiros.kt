package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaEnfermeiros(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_Tabela( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $MORADA TEXT NOT NULL, $CONTACTO TEXT NOT NULL)")

    }


    // CRUD

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
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(NOME_Tabela, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_Tabela = "Enfermeiros"
        const val CAMPO_NOME = "nome"
        const val CONTACTO = "contacto"
        const val MORADA = "morada"
        val TODOS_CAMPOS = arrayOf(BaseColumns._ID,CAMPO_NOME,MORADA,CONTACTO)
    }
}