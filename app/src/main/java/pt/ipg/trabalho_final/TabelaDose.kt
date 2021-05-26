package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaDose(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_Tabela (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NUM_DOSE INTEGER NOT NULL, $DATA TEXT NOT NULL, $HORA TEXT NOT NULL, $ID_PESSOAS )") //todo fazer FK

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
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(NOME_Tabela, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_Tabela = "Doses"
        const val NUM_DOSE = "num_dose"
        const val DATA = "data"
        const val HORA = "hora"
        const val ID_PESSOAS = "ID_pessoas"
        const val ID_ENFERMEIROS = "ID_enfermeirps"
        const val ID_VACINAS = "ID_vacinas"
    }
}
