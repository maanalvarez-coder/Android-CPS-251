package com.example.Assignment7

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainViewModel : ViewModel() {

    var students = mutableStateListOf<Student>()
        private set
    var isLoading by mutableStateOf(false)
        private set

    var newStudentName by mutableStateOf("")
        private set

    var newStudentGrade by mutableStateOf("")
        private set

    fun addStudent(name: String, grade: String) {
        //Implemented the addStudent function
         val student = Student(name, grade.toFloat())
        students.add(student)
        newStudentName = ""
        newStudentGrade = ""
        // Convert grade to Float, create a Student object, add it to the students list,
        // and clear the input fields (newStudentName and newStudentGrade).
    }

    fun removeStudent(student: Student) {
        //  Implemented the removeStudent function
        students.remove(student)
        // It should take a 'Student' object as a parameter and remove it from the students list.
    }

    fun calculateGPA(): Float {
        //  Implemented the calculateGPA function
        var gpa = 0.00f
        var netGpa = 0.0f
        var count = 0
        for(student in students){
            netGpa =netGpa + student.grade
            ++count
        }
        gpa = netGpa/count
        return gpa

    }

    fun loadSampleData() {
        // TODO: Implemented the loadSampleData function using coroutines (viewModelScope.launch)
        // Set isLoading to true, simulate a delay (e.g., 1500ms), populate students with sample data,
        //The sample data will be students =
        viewModelScope.launch {
            try {
                isLoading = true
                // Simulate background work
                delay(2000)
                addStudent("Alice Johnson", 95f.toString())
                addStudent("Bob Smith", 87f.toString())
                addStudent("Carol Davis", 92f.toString())
            } catch (e: Exception) {

            } finally {
                isLoading = false
            }}

        // and set isLoading back to false.
    }

    fun updateNewStudentName(name: String) {
        //  Implemented the updateNewStudentName function
        newStudentName = name
        // It should take a 'name' (String) as a parameter and update newStudentName.
    }

    fun updateNewStudentGrade(grade: String) {
        //  Implemented the updateNewStudentGrade function
        newStudentGrade = grade
        // It should take a 'grade' (String) as a parameter and update newStudentGrade.
    }
}

data class Student(
    val name: String,
    val grade: Float
)
