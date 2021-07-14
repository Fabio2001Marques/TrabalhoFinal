package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaDose(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_Tabela (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NUM_DOSE INTEGER NOT NULL, $DATA INTEGER NOT NULL, $HORA TEXT NOT NULL, $ID_PESSOAS INTEGER NOT NULL, $ID_ENFERMEIROS INTEGER NOT NULL, $ID_VACINAS INTEGER NOT NULL, Foreign KEY ($ID_PESSOAS) REFERENCES ${TabelaPessoas.NOME_Tabela}, Foreign KEY ($ID_ENFERMEIROS) REFERENCES ${TabelaEnfermeiros.NOME_Tabela}, Foreign KEY ($ID_VACINAS) REFERENCES ${TabelaVacinas.NOME_Tabela})")

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
        val ultimaColuna = columns.size

        var posColNomePessoa = -1
        var posColNomeEnfermeiro = -1
        var posColNomeVacina = -1
        for (i in 0..ultimaColuna-1) {
            if (columns[i] == CAMPO_EXTERNO_NOME_PESSOA) {
                posColNomePessoa = i
            }
            if (columns[i] == CAMPO_EXTERNO_NOME_ENFERMEIRO) {
                posColNomeEnfermeiro = i
            }
            if (columns[i] == CAMPO_EXTERNO_NOME_VACINA) {
                posColNomeVacina = i
            }
        }
        if ((posColNomePessoa == -1) && (posColNomeEnfermeiro == -1)&& (posColNomeVacina == -1)) {
            return db.query(NOME_Tabela, columns, selection, selectionArgs, groupBy, having, orderBy)
        }
        var colunas = ""
        for (i in 0..ultimaColuna-1) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomePessoa) {
                "${TabelaPessoas.NOME_Tabela}.${TabelaPessoas.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_PESSOA"
            }else if(i == posColNomeEnfermeiro) {
                "${TabelaEnfermeiros.NOME_Tabela}.${TabelaEnfermeiros.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_ENFERMEIRO"
            }else if(i == posColNomeVacina) {
                "${TabelaVacinas.NOME_Tabela}.${TabelaVacinas.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_VACINA"
            } else {
                "${NOME_Tabela}.${columns[i]}"
            }
        }
        val tabelas = "$NOME_Tabela INNER JOIN ${TabelaPessoas.NOME_Tabela} ON ${TabelaPessoas.NOME_Tabela}.${BaseColumns._ID}=$ID_PESSOAS " +
                "INNER JOIN ${TabelaEnfermeiros.NOME_Tabela} ON ${TabelaEnfermeiros.NOME_Tabela}.${BaseColumns._ID}=$ID_ENFERMEIROS " +
                "INNER JOIN ${TabelaVacinas.NOME_Tabela} ON ${TabelaVacinas.NOME_Tabela}.${BaseColumns._ID}=$ID_VACINAS"
        var sql = "SELECT $colunas FROM $tabelas"
        if (selection != null) sql += " WHERE DOSES.$selection"
        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }
        if (orderBy != null) sql += " ORDER BY $orderBy"
        return db.rawQuery(sql, selectionArgs)
    }

    companion object{
        const val NOME_Tabela = "Doses"
        const val NUM_DOSE = "num_dose"
        const val DATA = "data"
        const val HORA = "hora"
        const val ID_PESSOAS = "ID_pessoas"
        const val ID_ENFERMEIROS = "ID_enfermeiros"
        const val ID_VACINAS = "ID_vacinas"
        const val CAMPO_EXTERNO_NOME_PESSOA = "nome_pessoa"
        const val CAMPO_EXTERNO_NOME_ENFERMEIRO = "nome_enfermeiro"
        const val CAMPO_EXTERNO_NOME_VACINA = "nome_vacina"
        val TODOS_CAMPOS = arrayOf(BaseColumns._ID, NUM_DOSE,DATA,HORA, ID_PESSOAS, ID_ENFERMEIROS, ID_VACINAS, CAMPO_EXTERNO_NOME_PESSOA, CAMPO_EXTERNO_NOME_ENFERMEIRO,CAMPO_EXTERNO_NOME_VACINA )
    }
}
