package pt.ipg.trabalho_final

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderCovid : ContentProvider() {

    private var bdCovidOpenHelper : BDCovidOpenHelper? = null

    override fun onCreate(): Boolean {
        bdCovidOpenHelper = BDCovidOpenHelper(context)
        return true
    }


    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdCovidOpenHelper!!.readableDatabase

        return when (getUriMatcher().match(uri)){

            URI_ENFERMEIROS -> TabelaEnfermeiros(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_ENFERMEIROS_ESPECIFICO -> TabelaEnfermeiros(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_PESSOAS -> TabelaPessoas(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_PESSOAS_ESPECIFICA -> TabelaPessoas(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_VACINAS -> TabelaVacinas(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_VACINAS_ESPECIFICO -> TabelaVacinas(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_DOSES -> TabelaDose(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_DOSES_ESPECIFICA -> TabelaDose(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (getUriMatcher().match(uri)){
            URI_ENFERMEIROS -> "$MULTIPLOS_ITEMS/$ENFERMEIROS"
            URI_ENFERMEIROS_ESPECIFICO -> "$UNICO_ITEM/$ENFERMEIROS"
            URI_PESSOAS -> "$MULTIPLOS_ITEMS/$PESSOAS"
            URI_PESSOAS_ESPECIFICA -> "$UNICO_ITEM/$PESSOAS"
            URI_VACINAS -> "$MULTIPLOS_ITEMS/$VACINAS"
            URI_VACINAS_ESPECIFICO -> "$UNICO_ITEM/$VACINAS"
            URI_DOSES -> "$MULTIPLOS_ITEMS/$DOSES"
            URI_DOSES_ESPECIFICA -> "$UNICO_ITEM/$DOSES"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdCovidOpenHelper!!.writableDatabase

        val id =  when (getUriMatcher().match(uri)){

            URI_ENFERMEIROS -> TabelaEnfermeiros(bd).insert(values!!)

            URI_PESSOAS -> TabelaPessoas(bd).insert(values!!)

            URI_VACINAS -> TabelaVacinas(bd).insert(values!!)

            URI_DOSES -> TabelaDose(bd).insert(values!!)

            else -> -1L
        }

        if(id == -1L) return null

        return Uri.withAppendedPath(uri, id.toString())
    }

    /**
     * Implement this to handle requests to delete one or more rows. The
     * implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after deleting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * The implementation is responsible for parsing out a row ID at the end of
     * the URI, if a specific row is being deleted. That is, the client would
     * pass in `content://contacts/people/22` and the implementation
     * is responsible for parsing the record number (22) when creating a SQL
     * statement.
     *
     * @param uri The full URI to query, including a row ID (if a specific
     * record is requested).
     * @param selection An optional restriction to apply to rows when deleting.
     * @return The number of rows affected.
     * @throws SQLException
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdCovidOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)){

            URI_ENFERMEIROS_ESPECIFICO -> TabelaEnfermeiros(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_PESSOAS_ESPECIFICA -> TabelaPessoas(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_VACINAS_ESPECIFICO -> TabelaVacinas(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_DOSES_ESPECIFICA -> TabelaDose(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            else -> 0
        }
    }

    /**
     * Implement this to handle requests to update one or more rows. The
     * implementation should update all rows matching the selection to set the
     * columns according to the provided values map. As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after updating. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The URI to query. This can potentially have a record ID if
     * this is an update request for a specific record.
     * @param values A set of column_name/value pairs to update in the database.
     * @param selection An optional filter to match rows to update.
     * @return the number of rows affected.
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdCovidOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)){

            URI_ENFERMEIROS_ESPECIFICO -> TabelaEnfermeiros(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_PESSOAS_ESPECIFICA -> TabelaPessoas(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_VACINAS_ESPECIFICO -> TabelaVacinas(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_DOSES_ESPECIFICA -> TabelaDose(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            else -> 0
        }
    }

    companion object{

        const val AUTHORITY = "pt.ipg.trabalho_final"
        const val ENFERMEIROS = "enfermeiros"
        const val PESSOAS = "pessoas"
        const val VACINAS = "vacinas"
        const val DOSES = "doses"

        private const val URI_ENFERMEIROS = 100
        private const val URI_ENFERMEIROS_ESPECIFICO = 101
        private const val URI_PESSOAS = 200
        private const val URI_PESSOAS_ESPECIFICA = 201
        private const val URI_VACINAS = 300
        private const val URI_VACINAS_ESPECIFICO = 301
        private const val URI_DOSES = 400
        private const val URI_DOSES_ESPECIFICA = 401

        private const val MULTIPLOS_ITEMS = "vnd.adroid.cursor.dir"
        private const val UNICO_ITEM = "vnd.android.cursor.item"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        public val ENDERECO_ENFERMEIROS = Uri.withAppendedPath(ENDERECO_BASE, ENFERMEIROS)
        public val ENDERECO_PESSOAS = Uri.withAppendedPath(ENDERECO_BASE, PESSOAS)
        public val ENDERECO_VACINAS = Uri.withAppendedPath(ENDERECO_BASE, VACINAS)
        public val ENDERECO_DOSES = Uri.withAppendedPath(ENDERECO_BASE, DOSES)

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)


            // content://pt.ipg.trabalho_final/enfermeiros
            //content://pt.ipg.trabalho_final/enfermeiros/1


            uriMatcher.addURI(AUTHORITY, ENFERMEIROS, URI_ENFERMEIROS)
            uriMatcher.addURI(AUTHORITY,"$ENFERMEIROS/#", URI_ENFERMEIROS_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, PESSOAS, URI_PESSOAS)
            uriMatcher.addURI(AUTHORITY, "$PESSOAS/#", URI_PESSOAS_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, VACINAS, URI_VACINAS)
            uriMatcher.addURI(AUTHORITY,"$VACINAS/#", URI_VACINAS_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, DOSES, URI_DOSES)
            uriMatcher.addURI(AUTHORITY, "$DOSES/#", URI_DOSES_ESPECIFICA)

            return uriMatcher
        }
    }
}