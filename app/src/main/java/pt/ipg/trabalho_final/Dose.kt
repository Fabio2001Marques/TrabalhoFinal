package pt.ipg.trabalho_final

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


data class Dose (var id: Long = -1, var num_dose: Int, var data: Int, var hora: Int, var id_pessoas: Long, var id_enfermeiros: Long, var id_vacinas: Long, var nomePessoa: String? = null, var nomeEnfermeiro: String? = null, var nomeVacina: String? = null) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaDose.NUM_DOSE, num_dose)
            put(TabelaDose.DATA, data)
            put(TabelaDose.HORA, hora)
            put(TabelaDose.ID_PESSOAS, id_pessoas)
            put(TabelaDose.ID_ENFERMEIROS, id_enfermeiros)
            put(TabelaDose.ID_VACINAS, id_vacinas)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Dose {

            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNumDose = cursor.getColumnIndex(TabelaDose.NUM_DOSE)
            val posCampoData = cursor.getColumnIndex(TabelaDose.DATA)
            val posCampoHora = cursor.getColumnIndex(TabelaDose.HORA)
            val posCampoIdPessoas = cursor.getColumnIndex(TabelaDose.ID_PESSOAS)
            val posCampoIdEnfermeiros = cursor.getColumnIndex(TabelaDose.ID_ENFERMEIROS)
            val posCampoIdVacinas = cursor.getColumnIndex(TabelaDose.ID_VACINAS)
            val colNomePessoa = cursor.getColumnIndex(TabelaDose.CAMPO_EXTERNO_NOME_PESSOA)
            val colNomeEnfermeiro = cursor.getColumnIndex(TabelaDose.CAMPO_EXTERNO_NOME_ENFERMEIRO)
            val colNomeVacina = cursor.getColumnIndex(TabelaDose.CAMPO_EXTERNO_NOME_VACINA)

            val id = cursor.getLong(posCampoId)
            val num_dose = cursor.getInt(posCampoNumDose)
            val data = cursor.getInt(posCampoData)
            val hora = cursor.getInt(posCampoHora)
            val id_pessoas = cursor.getLong(posCampoIdPessoas)
            val id_enfermeiros = cursor.getLong(posCampoIdEnfermeiros)
            val id_vacinas = cursor.getLong(posCampoIdVacinas)
            val nomePessoa = if (colNomePessoa != -1) cursor.getString(colNomePessoa) else null
            val nomeEnfermeiro = if (colNomeEnfermeiro != -1) cursor.getString(colNomeEnfermeiro) else null
            val nomeVacina = if (colNomeVacina != -1) cursor.getString(colNomeVacina) else null


            return Dose(id, num_dose, data, hora, id_pessoas, id_enfermeiros, id_vacinas, nomePessoa, nomeEnfermeiro, nomeVacina)
        }
    }
}