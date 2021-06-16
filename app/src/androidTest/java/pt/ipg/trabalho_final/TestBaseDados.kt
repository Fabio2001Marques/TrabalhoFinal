package pt.ipg.trabalho_final

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestBaseDados {

    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBDCovidOpenHelper() = BDCovidOpenHelper(getAppContext())


    //-----------------------------------------------------------------------------------
    // Tabela Vacinas
    //-----------------------------------------------------------------------------------
    private fun getTabelaVacinas(db: SQLiteDatabase) = TabelaVacinas(db)

    private fun insertVacina(tabelaVacinas: TabelaVacinas, vacina: Vacina): Long {
        val id = tabelaVacinas.insert(vacina.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun GetVacinaBd(tabelaVacinas: TabelaVacinas, id: Long): Vacina {
        val cursor = tabelaVacinas.query(
            TabelaVacinas.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacina.fromCursor(cursor)

    }

    private fun criarVacina(nome: String, quantidade: Int, data: Int): Vacina{
        val vacina = Vacina(nome = nome,quantidade = quantidade,data = data)

        return vacina
    }

    //-----------------------------------------------------------------------------------
    // Tabela Pessoas
    //-----------------------------------------------------------------------------------
    private fun getTabelaPessoas(db: SQLiteDatabase) = TabelaPessoas(db)

    private fun insertPessoas(tabelaPessoas: TabelaPessoas, pessoa: Pessoa): Long {
        val id = tabelaPessoas.insert(pessoa.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun GetPessoasBd(tabelaPessoas: TabelaPessoas, id: Long): Pessoa {
        val cursor = tabelaPessoas.query(
            TabelaPessoas.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Pessoa.fromCursor(cursor)

    }

    private fun criarPessoa(nome: String, data_nascimento: Int, morada: String, campo_cc: String, contacto: String) : Pessoa{
        val pessoa = Pessoa(nome ="Jose",data_nascimento = 5032021,morada = "Rua Principal nº47 Casais do Porto, Louriçal, Pombal", campo_cc = "30530747",contacto = "915710186")

        return pessoa
    }

    //-----------------------------------------------------------------------------------
    // Tabela Enfereiros
    //-----------------------------------------------------------------------------------
    private fun getTabelaEnfermeiros(db: SQLiteDatabase) = TabelaEnfermeiros(db)

    private fun insertEnfermeiros(tabelaEnfermeiros: TabelaEnfermeiros, enfermeiro: Enfermeiro): Long {
        val id = tabelaEnfermeiros.insert(enfermeiro.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun GetEnfermeirosBd(tabelaEnfermeiro: TabelaEnfermeiros, id: Long): Enfermeiro {
        val cursor = tabelaEnfermeiro.query(
            TabelaEnfermeiros.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Enfermeiro.fromCursor(cursor)

    }

    //-----------------------------------------------------------------------------------
    // Tabela Doses
    //-----------------------------------------------------------------------------------
    private fun getTabelaDose(db: SQLiteDatabase) = TabelaDose(db)

    private fun insertDoses(tabela: TabelaDose, dose: Dose): Long {
        val id = tabela.insert(dose.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun GetDoseBd(tabela: TabelaDose, id: Long): Dose {
        val cursor = tabela.query(
            TabelaDose.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Dose.fromCursor(cursor)

    }


    //-----------------------------------------------------------------------------------
    // Testes
    //-----------------------------------------------------------------------------------

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BDCovidOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {

        val db = getBDCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    //-----------------------------------------------------------------------------------
    // Tabela Vacinas
    //-----------------------------------------------------------------------------------

    @Test

    fun consegueInserirVacinas(){

        val db = getBDCovidOpenHelper().writableDatabase

        val vacina = criarVacina("Moderna",15, 5032021)
        vacina.id = insertVacina(getTabelaVacinas(db), vacina)

        assertEquals(vacina, GetVacinaBd(getTabelaVacinas(db), vacina.id))

        db.close()

    }

    @Test

    fun consegueAlterarVacinas(){

        val db = getBDCovidOpenHelper().writableDatabase

        val vacina = criarVacina("teste",15, 5032021)
        vacina.id = insertVacina(getTabelaVacinas(db), vacina)
        vacina.nome = "phizer"

        val registosAlterados = getTabelaVacinas(db).update(
            vacina.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test

    fun consegueApagarVacinas() {

        val db = getBDCovidOpenHelper().writableDatabase

        val vacina = criarVacina("Teste",15, 5032021)
        vacina.id = insertVacina(getTabelaVacinas(db), vacina)

        val registosApagados = getTabelaVacinas(db).delete("${BaseColumns._ID}=?",arrayOf(vacina.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

    @Test

    fun consegueLerVacinas() {

        val db = getBDCovidOpenHelper().writableDatabase

        val vacina = criarVacina("Astrazeneca",25, 5032021)
        vacina.id = insertVacina(getTabelaVacinas(db), vacina)

        val vacinaBd = GetVacinaBd(getTabelaVacinas(db), vacina.id)
        assertEquals(vacina, vacinaBd)

        db.close()
    }

    //-----------------------------------------------------------------------------------
    // Tabela Pessoas
    //-----------------------------------------------------------------------------------

    @Test

    fun consegueInserirPessoas(){

        val db = getBDCovidOpenHelper().writableDatabase

        val pessoa = criarPessoa("Jose",5032021,"Rua Principal nº47 Casais do Porto, Louriçal, Pombal","23696324","963532342")
        pessoa.id = insertPessoas(getTabelaPessoas(db),pessoa )

        assertEquals(pessoa, GetPessoasBd(getTabelaPessoas(db), pessoa.id))

        db.close()

    }

    @Test

    fun consegueAlterarPessoas() {

        val db = getBDCovidOpenHelper().writableDatabase

        val pessoa = criarPessoa("Jose",5032021,"Rua Principal nº47 Casais do Porto, Louriçal, Pombal","23696324","963532342")
        pessoa.id = insertPessoas(getTabelaPessoas(db), pessoa)
        pessoa.nome = "Maria"

        val registosAlterados = getTabelaPessoas(db).update(
            pessoa.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pessoa.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test

    fun consegueApagarPessoas() {

        val db = getBDCovidOpenHelper().writableDatabase
        val pessoa = criarPessoa("Jose",5032021,"Rua Principal nº47 Casais do Porto, Louriçal, Pombal","23696324","963532342")
        pessoa.id = insertPessoas(getTabelaPessoas(db), pessoa)

        val registosApagados = getTabelaPessoas(db).delete("${BaseColumns._ID}=?",arrayOf(pessoa.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

    @Test

    fun consegueLerPessoas() {

        val db = getBDCovidOpenHelper().writableDatabase

        val pessoa = criarPessoa("Jose",5032021,"Rua Principal nº47 Casais do Porto, Louriçal, Pombal","23696324","963532342")
        pessoa.id = insertPessoas(getTabelaPessoas(db), pessoa)

        val pessoaBd = GetPessoasBd(getTabelaPessoas(db), pessoa.id)
        assertEquals(pessoa, pessoaBd)

        db.close()
    }


    //-----------------------------------------------------------------------------------
    // Tabela Enfermeiros
    //-----------------------------------------------------------------------------------

    @Test

    fun consegueInserirEnfermeiros(){

        val db = getBDCovidOpenHelper().writableDatabase

        val enfermeiro = Enfermeiro(nome ="Andre",morada = "Rua Principal nº48 Casais do Porto, Louriçal, Pombal", contacto = "915710196")
        enfermeiro.id = insertEnfermeiros(getTabelaEnfermeiros(db), enfermeiro)

        assertEquals(enfermeiro, GetEnfermeirosBd(getTabelaEnfermeiros(db), enfermeiro.id))

        db.close()

    }

    @Test

    fun consegueAlterarEnfermeiros() {

        val db = getBDCovidOpenHelper().writableDatabase

        val enfermeiro = Enfermeiro(nome ="Jose",morada = "Rua Principal nº47 Casais do Porto, Louriçal, Pombal",contacto = "915711186")
        enfermeiro.id = insertEnfermeiros(getTabelaEnfermeiros(db) , enfermeiro)
        enfermeiro.nome = "Maria"

        val registosAlterados = getTabelaEnfermeiros(db).update(
            enfermeiro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(enfermeiro.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test

    fun consegueApagarEnfermeiros() {

        val db = getBDCovidOpenHelper().writableDatabase

        val enfermeiro = Enfermeiro(nome ="Jose",morada = "Rua Principal nº47 Casais do Porto, Louriçal, Pombal",contacto = "915711186")
        enfermeiro.id = insertEnfermeiros(getTabelaEnfermeiros(db), enfermeiro)

        val registosApagados = getTabelaEnfermeiros(db).delete("${BaseColumns._ID}=?",arrayOf(enfermeiro.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

    @Test

    fun consegueLerEnfermeiros() {

        val db = getBDCovidOpenHelper().writableDatabase

        val enfermeiro = Enfermeiro(nome ="Jose",morada = "Rua Principal nº47 Casais do Porto, Louriçal, Pombal",contacto = "915711186")
        enfermeiro.id = insertEnfermeiros(getTabelaEnfermeiros(db), enfermeiro)

        val enfermeiroBd = GetEnfermeirosBd(getTabelaEnfermeiros(db), enfermeiro.id)
        assertEquals(enfermeiro, enfermeiroBd)

        db.close()
    }

    //-----------------------------------------------------------------------------------
    // Tabela Doses
    //-----------------------------------------------------------------------------------

    @Test

    fun consegueInserirDose(){

        val db = getBDCovidOpenHelper().writableDatabase

        val vacina = Vacina(nome ="Moderna",quantidade = 15,data = 5032021)
        vacina.id = insertVacina(getTabelaVacinas(db), vacina)

        val pessoa = Pessoa(nome ="Jose",data_nascimento = 5032021,morada = "Rua Principal nº47 Casais do Porto, Louriçal, Pombal", campo_cc = "30530747",contacto = "915710186")
        pessoa.id = insertPessoas(getTabelaPessoas(db),pessoa )

        val enfermeiro = Enfermeiro(nome ="Andre",morada = "Rua Principal nº48 Casais do Porto, Louriçal, Pombal", contacto = "915710196")
        enfermeiro.id = insertEnfermeiros(getTabelaEnfermeiros(db), enfermeiro)

        val dose = Dose(num_dose = 1,data = 28052021, hora = 2002,id_pessoas = pessoa.id, id_enfermeiros = enfermeiro.id,id_vacinas = vacina.id)
        dose.id = insertDoses(getTabelaDose(db), dose )



        db.close()

    }

    @Test

    fun consegueAlterarDoses() {

        val db = getBDCovidOpenHelper().writableDatabase

        val dose = Dose(num_dose = 1,data = 28052021, hora = 2002,id_pessoas = 1, id_enfermeiros = 1,id_vacinas = 1)
        dose.id = insertDoses(getTabelaDose(db), dose)
        dose.num_dose = 2

        val registosAlterados = getTabelaDose(db).update(
            dose.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(dose.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test

    fun consegueApagarDose() {

        val db = getBDCovidOpenHelper().writableDatabase
        val dose = Dose(num_dose = 1,data = 28052021, hora = 2002,id_pessoas = 1, id_enfermeiros = 1,id_vacinas = 1)
        dose.id = insertDoses(getTabelaDose(db), dose)

        val registosApagados = getTabelaDose(db).delete("${BaseColumns._ID}=?",arrayOf(dose.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

    @Test

    fun consegueLerDoses() {

        val db = getBDCovidOpenHelper().writableDatabase

        val dose = Dose(num_dose = 1,data = 28052021, hora = 2002,id_pessoas = 1, id_enfermeiros = 1,id_vacinas = 1)
        dose.id = insertDoses(getTabelaDose(db), dose)

        val doseBd = GetDoseBd(getTabelaDose(db), dose.id)
        assertEquals(dose, doseBd)

        db.close()
    }
}
