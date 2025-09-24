package com.example.practica01

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica01.databinding.ListItemBinding
import com.example.practica01.models.Student


/**
 * Interfaz para manejar los eventos del menú en cada elemento del RecyclerView.
 * Permite que la Activity (MainActivity en este caso) reaccione a los clics del menú.
 */
interface OnStudentMenuItemClickListener {
        fun onEditClick(student: Student, position: Int)
        fun onDeleteClick(student: Student, position: Int)
}

/**
 * Adaptador para el RecyclerView que muestra la lista de estudiantes.
 * @param students La lista de objetos Student a mostrar.
 * @param onMenuItemClickListener El listener que maneja los clics del menú.
 */
class StudentAdapter(
        private val students: List<Student>,
        private val onMenuItemClickListener: OnStudentMenuItemClickListener
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

        class StudentViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
                val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return StudentViewHolder(binding)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
                val student = students[position]
                holder.binding.tvStudentName.text = student.name
                holder.binding.tvStudentId.text = student.studentId

                // Usa la biblioteca Glide para cargar la imagen desde la URL en el ImageView
                Glide.with(holder.binding.ivStudentImage)
                        .load(student.imageURL)
                        .into(holder.binding.ivStudentImage)

                // Configura el OnClickListener para el icono del menú
                holder.binding.ivMenu.setOnClickListener {
                        // Crea un PopupMenu anclado al icono del menú
                        val popupMenu = PopupMenu(holder.binding.root.context, it)
                        // Infla el menú desde el archivo de recursos student_menu.xml
                        popupMenu.menuInflater.inflate(R.menu.student_menu, popupMenu.menu)

                        // Configura un listener para manejar los clics en las opciones del menú
                        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                                when (menuItem.itemId) {
                                        R.id.action_edit -> {
                                                // Llama al método onEditClick del listener
                                                onMenuItemClickListener.onEditClick(student, position)
                                                true
                                        }
                                        R.id.action_delete -> {
                                                // Llama al método onDeleteClick del listener
                                                onMenuItemClickListener.onDeleteClick(student, position)
                                                true
                                        }
                                        else -> false
                                }
                        }
                        // Muestra el menú
                        popupMenu.show()
                }
        }

        override fun getItemCount() = students.size
}