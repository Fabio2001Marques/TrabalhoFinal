package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


data class Vacina (var id: Long = -1, var nome: String, var quantidade: Int, var data: Int){

    fun toContentValues() : ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinas.CAMPO_NOME, nome)
            put(TabelaVacinas.QUANTIDADE, quantidade)
            put(TabelaVacinas.DATA_CHEGADA, data)
        }
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) : Vacina{

            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNome = cursor.getColumnIndex(TabelaVacinas.CAMPO_NOME)
            val posCampoQuantidade = cursor.getColumnIndex(TabelaVacinas.QUANTIDADE)
            val posCampoData = cursor.getColumnIndex(TabelaVacinas.DATA_CHEGADA)

            val id =cursor.getLong(posCampoId)
            val nome = cursor.getString(posCampoNome)
            val quantidade = cursor.getInt(posCampoQuantidade)
            val data = cursor.getInt(posCampoData)

            return Vacina(id,nome,quantidade, data)
        }
    }
}