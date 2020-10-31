package com.example.reviewproject

import androidx.lifecycle.ViewModel

class StudentListViewModel : ViewModel() {
    val students = mutableListOf<Students>()

    init {
        for (i in 0 until 5) {
            val student = Students()
            student.Num = i
            student.Stu_Name = " wafaa Al_selwi $i"
            student.pass = if(i % 2 == 0)true else false

            students.add(student)
        }

    }
    fun addStudent(student: Students){
        students.add(student)
    }
    fun deleteStudent(item: Int){
        students.removeAt(item)
    }
}