package com.example.practica01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica01.StudentAdapter
import com.example.practica01.databinding.ActivityMainBinding
import com.example.practica01.models.Student

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupStudentList()
        setupRecyclerView()
        setupAddButton()
    }

    private fun setupStudentList() {
        studentList.add(Student("https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png", "José Nabor", "20161545", "jose@example.com"))
        studentList.add(Student("https://assets.pokemon.com/assets/cms2/img/pokedex/full/006.png", "Luis Pedro", "20162030", "luis@example.com"))
        studentList.add(Student("https://assets.pokemon.com/assets/cms2/img/pokedex/full/006.png", "Carlos Enrique", "20111646", "carlos@example.com"))
        studentList.add(Student("https://assets.pokemon.com/assets/cms2/img/pokedex/full/006.png", "Cesar Josue", "20124020", "cesar@example.com"))
        studentList.add(Student("https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png", "Antonio de Jesús", "20112356", "antonio@example.com"))
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(studentList)
        recyclerView.adapter = studentAdapter
    }

    private fun setupAddButton() {
        binding.addStudentButton.setOnClickListener {
            // Crea un nuevo estudiante
            val newStudent = Student(
                "https://cdn-icons-png.flaticon.com/512/6245/6245780.png", // Icono por defecto para un nuevo estudiante
                "Fernanda",
                "202027505",
                "fernanda|.com"
            )

            // Agrega el nuevo estudiante a la lista
            studentList.add(newStudent)

            // Notifica al adaptador que se ha insertado un nuevo elemento en la última posición
            studentAdapter.notifyItemInserted(studentList.size - 1)

            // Desplázate al nuevo elemento
            binding.recyclerView.scrollToPosition(studentList.size - 1)
        }
    }
}