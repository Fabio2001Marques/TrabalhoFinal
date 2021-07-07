package pt.ipg.trabalho_final

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import pt.ipg.trabalho_final.databinding.ActivityMainBinding
import pt.ipg.trabalho_final.ui.enfermeiros.EditaEnfermeiroFragment
import pt.ipg.trabalho_final.ui.enfermeiros.EliminaEnfermeiroFragment
import pt.ipg.trabalho_final.ui.enfermeiros.ListaEnfermeirosFragment
import pt.ipg.trabalho_final.ui.enfermeiros.NovoEnfermeiroFragment
import pt.ipg.trabalho_final.ui.pessoas.EditaPessoaFragment
import pt.ipg.trabalho_final.ui.pessoas.EliminaPessoaFragment
import pt.ipg.trabalho_final.ui.pessoas.ListaPessoasFragment
import pt.ipg.trabalho_final.ui.pessoas.NovaPessoaFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = R.menu.activity_main_drawer
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.ListaEnfermeirosFragment, R.id.ListaPessoasFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu
        if (menuAtual == R.menu.menu_enfermeiros) {
            atualizaMenuListaEnfermeiros(false)
        }else if (menuAtual == R.menu.menu_pessoas) {
            atualizaMenuListaPessoas(false)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


           val opcaoProcessada = when (item.itemId) {
                R.id.action_settings -> {
                    Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                    true
                }

                else -> when (menuAtual) {
                    R.menu.menu_enfermeiros -> (DadosApp.fragment as ListaEnfermeirosFragment).processaOpcaoMenu(item)
                    R.menu.menu_novo_enfermeiro -> (DadosApp.fragment as NovoEnfermeiroFragment).processaOpcaoMenu(item)
                    R.menu.menu_edita_enfermeiro -> (DadosApp.fragment as EditaEnfermeiroFragment).processaOpcaoMenu(item)
                    R.menu.menu_elimina_enfermeiro -> (DadosApp.fragment as EliminaEnfermeiroFragment).processaOpcaoMenu(item)
                    R.menu.menu_pessoas -> (DadosApp.fragment as ListaPessoasFragment).processaOpcaoMenu(item)
                    R.menu.menu_nova_pessoa -> (DadosApp.fragment as NovaPessoaFragment).processaOpcaoMenu(item)
                    R.menu.menu_edita_pessoa -> (DadosApp.fragment as EditaPessoaFragment).processaOpcaoMenu(item)
                    R.menu.menu_elimina_pessoa -> (DadosApp.fragment as EliminaPessoaFragment).processaOpcaoMenu(item)
                    else -> false
                }
            }
            return if (opcaoProcessada) true else super.onOptionsItemSelected(item)

        }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaEnfermeiros(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_enfermeiros).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_enfermeiro).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaPessoas(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_pessoas).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_pessoas).setVisible(mostraBotoesAlterarEliminar)
    }

}