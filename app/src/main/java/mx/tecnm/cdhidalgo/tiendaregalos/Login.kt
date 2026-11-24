package mx.tecnm.cdhidalgo.tiendaregalos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import mx.tecnm.cdhidalgo.tiendaregalos.dataclass.Usuario

class Login : AppCompatActivity() {

    private lateinit var correo: TextInputLayout
    private lateinit var pass: TextInputLayout
    private lateinit var btn: MaterialButton
    private lateinit var btn2: MaterialButton
    private lateinit var btn3: MaterialButton

    private lateinit var auth: FirebaseAuth
    private lateinit var usuario: Usuario


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inicializar la autenticacion
        auth = FirebaseAuth.getInstance()

        //acceso ala base de datos de firestore
        val db = Firebase.firestore

        //acceso a la coleccion de usuarios
        val usuarios = db.collection("usuarios")



        correo = findViewById(R.id.correo_login)
        pass = findViewById(R.id.pass_login)
        btn = findViewById(R.id.btn_ingresar)
        btn2 = findViewById(R.id.btn_registrar)
        btn3 = findViewById(R.id.btn_olvidar)


        btn.setOnClickListener {

            val email = correo.editText?.text.toString()
            val password = pass.editText?.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty())
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        db.collection("usuarios").whereEqualTo("correo", email).get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    usuario = Usuario(
                                        document.getString("correo") ?: "",
                                        document.getString("nombre") ?: "",
                                        document.getString("apaterno") ?: "",
                                        document.getString("amaterno") ?: ""
                                    )
                                }
                                val intent = Intent(this, Tienda::class.java)
                                intent.putExtra("usuario", usuario)
                                startActivity(intent)
                            }
                    } else {
                        showAlert()

                    }

                }


        }

        btn2.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = Intent(this, Recupera_paswword::class.java)
                startActivity(intent)

            }


    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }
}
