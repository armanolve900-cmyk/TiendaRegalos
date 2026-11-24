package mx.tecnm.cdhidalgo.tiendaregalos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import mx.tecnm.cdhidalgo.tiendaregalos.adaptadores.AdaptadorArtesania
import mx.tecnm.cdhidalgo.tiendaregalos.dataclass.Producto
import mx.tecnm.cdhidalgo.tiendaregalos.dataclass.Usuario

class Tienda : AppCompatActivity() {
    private lateinit var btn_cerrar : ImageButton
    private lateinit var texto : TextView
    private lateinit var usuario: Usuario

    private lateinit var rvArtesanias : RecyclerView

    private lateinit var listaArtesanias : ArrayList<Producto>

    private lateinit var adaptadorArtesania : AdaptadorArtesania

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tienda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inicializar la autenticacion
        auth = FirebaseAuth.getInstance()

        usuario = intent.getParcelableExtra("usuario")!!
        texto = findViewById(R.id.usuario_tienda)
        btn_cerrar = findViewById(R.id.btn_cerrar_sesion_tienda)
        rvArtesanias = findViewById(R.id.artesanias_tienda)
        rvArtesanias.setHasFixedSize(true)


        val nombreCompleto = "${usuario.nombre} ${usuario.apaterno} ${usuario.amaterno ?: ""}"

        texto.text = nombreCompleto.toString()

        listaArtesanias = ArrayList()


        listaArtesanias.add(Producto(
            R.drawable.ar01,
            "Xico Hoja",
            "Xico-Talavera Hoja",
            3309.00,
            "XICO es un personaje que busca generar un cambio positivo " +
                    "a través del arte y la cultura. " + "Respalda el talento emergente y provee una plataforma comercial " +
                    "de impulso creativo.",
            "artesania"



        ))
        listaArtesanias.add(Producto(
            R.drawable.ar02,
            "Xico Rojo",
            "Xico-Piel de Alebrije Rojo",
            2959.00,
            "XICO es un personaje que busca generar un cambio positivo a través del arte y la cultura." +
                    " Respalda el talento emergente y provee una plataforma comercial de impulso creativo.",
            "artesania"



        ))
        listaArtesanias.add(Producto(
            R.drawable.ar03,
            "Alebrije Liebre",
            "Alebrije Liebre Chico Azul/Rojo",
            1180.00,
            "Los alebrijes surgen de un sueño del artesano don Pedro Linares: " +
                    "\"Me morí y anduve por montañas, barrancas y en un lugar de ésos, donde había un bosque, de ahí salían los alebrijes\"... " +
                    "Forma y color se integran armoniosa e intensamente. Bello dentro de su fealdad, diabólico, horroroso, simpático e incluso tierno, " +
                    "el alebrije es símbolo del monstruo mexicano. Revoloteo incesante, remolino de garras, crestas y cuernos de colores vibrantes. " +
                    "Cuanto más extraña sea la figura, más alebrije será.",
            "artesania"



        ))
        listaArtesanias.add(Producto(
            R.drawable.ar04,
            "Alebrije Camaleón",
            "Alebrije Camaleón Mediano",
            5189.00,
            "Los alebrijes surgen de un sueño del artesano don Pedro Linares: \"Me morí y anduve por montañas, barrancas y en un lugar de ésos, donde había un bosque, de ahí salían los alebrijes\"... " +
                    "Forma y color se integran armoniosa e intensamente. Bello dentro de su fealdad, diabólico, horroroso, simpático e incluso tierno, el alebrije es símbolo del monstruo mexicano. " +
                    "Revoloteo incesante, remolino de garras, crestas y cuernos de colores vibrantes. Cuanto más extraña sea la figura, más alebrije será.",
            "artesania"



        ))

        listaArtesanias.add(Producto(
            R.drawable.ar05,
            "Catrina Rosa",
            "Catrina Arrecifes Rosa",
            4540.00,
            "Pieza artesanal con la figura de una catrina cuyo elegante vestir plasma las tradiciones de la cultura mexicana.\n" +
                    "Diseño Inspirado en la belleza y riqueza de los arrecifes mexicanos, los cuales florecen en aguas tropicales siendo el hogar y refugio de diversas especies. " +
                    "Por su gran biodiversidad, se han ganado el título de \"Selvas del mar\". Los arrecifes, fortaleza multicolor que resguarda el equilibrio de la naturaleza, " +
                    "forman diseños orgánicos y exóticos que nos invitan a preservarlos y sumergirnos en la profundidad del mar.\n",
            "artesania"



        ))


        //artesanias recyclerview

        rvArtesanias.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        adaptadorArtesania= AdaptadorArtesania(listaArtesanias)
        rvArtesanias.adapter = adaptadorArtesania

        adaptadorArtesania.onProductoClick = { producto ->
            val intent = Intent(this, DetalleProducto::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("producto", producto)
            startActivity(intent)
        }







        btn_cerrar.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            auth.signOut()
            finish()



        }


    }
}