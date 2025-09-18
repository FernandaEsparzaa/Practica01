package com.example.practica01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica01.databinding.ListItemBinding
import com.example.practica01.models.Student

class StudentAdapter(private val students: List<Student>) :
        RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

        class StudentViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
                val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return StudentViewHolder(binding)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
                val student = students[position]
                holder.binding.tvStudentName.text = student.name
                holder.binding.tvStudentId.text = student.studentId

                // Use Glide to load the image from the URL
                Glide.with(holder.binding.ivStudentImage)
                        .load(student.imageURL)
                        .into(holder.binding.ivStudentImage)
        }

        override fun getItemCount() = students.size
}