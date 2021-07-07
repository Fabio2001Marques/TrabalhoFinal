package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


data class Pessoa (var id: Long = -1, var nome: String, var data_nascimento: Int, var morada: String, var campo_cc: String, var contacto: String){

    fun toContentValues() : ContentValues {
        val valores = ContentValues().apply {
            put(TabelaPessoas.CAMPO_NOME, nome)
            put(TabelaPessoas.Data_Nascimento, data_nascimento)
            put(TabelaPessoas.MORADA, morada)
            put(TabelaPessoas.CAMPO_CC, campo_cc)
            put(TabelaPessoas.CONTACTO, contacto)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) : Pessoa{

            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNome = cursor.getColumnIndex(TabelaPessoas.CAMPO_NOME)
            val posCampoDataNascimento = cursor.getColumnIndex(TabelaPessoas.Data_Nascimento)
            val posCampoMorada = cursor.getColumnIndex(TabelaPessoas.MORADA)
            val posCampoCC = cursor.getColumnIndex(TabelaPessoas.CAMPO_CC)
            val posCampoContacto = cursor.getColumnIndex(TabelaPessoas.CONTACTO)

            val id =cursor.getLong(posCampoId)
            val nome = cursor.getString(posCampoNome)
            val data_nascimento = cursor.getInt(posCampoDataNascimento)
            val morada = cursor.getString(posCampoMorada)
            val campo_cc = cursor.getString(posCampoCC)
            val contacto = cursor.getString(posCampoContacto)

            return Pessoa(id,nome,data_nascimento, morada, campo_cc, contacto)
        }
    }
}