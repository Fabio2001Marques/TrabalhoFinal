package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


data class Enfermeiro (var id: Long = -1, var nome: String,var morada: String, var contacto: String){

    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaEnfermeiros.CAMPO_NOME, nome)
        valores.put(TabelaEnfermeiros.MORADA, morada)
        valores.put(TabelaEnfermeiros.CONTACTO, contacto)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) : Enfermeiro{

            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNome = cursor.getColumnIndex(TabelaEnfermeiros.CAMPO_NOME)
            val posCampoMorada = cursor.getColumnIndex(TabelaEnfermeiros.MORADA)
            val posCampoContacto = cursor.getColumnIndex(TabelaEnfermeiros.CONTACTO)

            val id =cursor.getLong(posCampoId)
            val nome = cursor.getString(posCampoNome)
            val morada = cursor.getString(posCampoMorada)
            val contacto = cursor.getString(posCampoContacto)

            return Enfermeiro(id,nome,morada,contacto)
        }
    }
}