package com.example.practica01

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica01.databinding.ActivityMainBinding
import com.example.practica01.models.Student
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), OnStudentMenuItemClickListener {

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
        studentAdapter = StudentAdapter(studentList, this)
        recyclerView.adapter = studentAdapter
    }

    private fun setupAddButton() {
        binding.addStudentButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)

            val etName = dialogView.findViewById<TextInputEditText>(R.id.et_student_name)
            val etId = dialogView.findViewById<TextInputEditText>(R.id.et_student_id)
            val etEmail = dialogView.findViewById<TextInputEditText>(R.id.et_student_email)

            AlertDialog.Builder(this)
                .setTitle("Agregar Nuevo Alumno")
                .setView(dialogView)
                .setPositiveButton("Agregar") { dialog, _ ->
                    val name = etName.text.toString()
                    val id = etId.text.toString()
                    val email = etEmail.text.toString()

                    if (name.isNotEmpty() && id.isNotEmpty() && email.isNotEmpty()) {
                        val newStudent = Student(
                            "https://cdn-icons-png.flaticon.com/512/6245/6245780.png",
                            name,
                            id,
                            email
                        )
                        studentList.add(newStudent)
                        studentAdapter.notifyItemInserted(studentList.size - 1)
                        binding.recyclerView.scrollToPosition(studentList.size - 1)
                    } else {
                        Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    // --- Implementación de los métodos de la interfaz OnStudentMenuItemClickListener ---

    override fun onEditClick(student: Student, position: Int) {
        // Infla el diseño del diálogo de edición
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)

        // Obtiene las referencias a los campos de texto
        val etName = dialogView.findViewById<TextInputEditText>(R.id.et_student_name)
        val etId = dialogView.findViewById<TextInputEditText>(R.id.et_student_id)
        val etEmail = dialogView.findViewById<TextInputEditText>(R.id.et_student_email)

        // Pre-llena los campos con los datos del estudiante
        etName.setText(student.name)
        etId.setText(student.studentId)
        etEmail.setText(student.email)

        // Crea y muestra el cuadro de diálogo
        AlertDialog.Builder(this)
            .setTitle("Editar Alumno")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                val updatedName = etName.text.toString()
                val updatedId = etId.text.toString()
                val updatedEmail = etEmail.text.toString()

                if (updatedName.isNotEmpty() && updatedId.isNotEmpty() && updatedEmail.isNotEmpty()) {
                    // Actualiza el objeto Student en la lista
                    val updatedStudent = Student(student.imageURL, updatedName, updatedId, updatedEmail)
                    studentList[position] = updatedStudent

                    // Notifica al adaptador que el elemento ha cambiado
                    studentAdapter.notifyItemChanged(position)

                    Toast.makeText(this, "Alumno actualizado: $updatedName", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDeleteClick(student: Student, position: Int) {
        studentList.removeAt(position)
        studentAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Eliminado: ${student.name}", Toast.LENGTH_SHORT).show()
    }
}