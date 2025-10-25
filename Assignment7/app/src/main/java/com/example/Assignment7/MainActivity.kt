package com.example.Assignment7
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Compose UI imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ViewModel imports
import androidx.lifecycle.viewmodel.compose.viewModel

// TODO: Import MainViewModel from the same package once created
// import com.example.assignment10.MainViewModel


/**
 * MainActivity is the entry point of the application.
 * It sets up the basic Compose UI.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the Compose UI
        setContent {
            MaterialTheme {
                Surface {
                    // Called the StudentGradeManager composable here
                    StudentGradeManager()
                }
            }
        }
    }
}

// Defined the Student data class
// It should have 'name' (String) and 'grade' (Int) properties.


@Composable
fun StudentGradeManager(
    // Instantiated MainViewModel using viewModel()

) {
    val viewModel: MainViewModel = viewModel()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
    ) {
        // Header
        // Added a Text composable for the header "Student Grade Manager"
        item {
            Text(
                "Student Grade Manager",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        // Used style MaterialTheme.typography.headlineMedium and Modifier.padding(bottom = 16.dp)


        // Called GPADisplay composable, passing the GPA from the ViewModel
        item {
            GPADisplay(viewModel.calculateGPA())
        }


        // Called AddStudentForm composable, passing state and event handlers from the ViewModel
        item {
            AddStudentForm(
                name = viewModel.newStudentName,
                grade = viewModel.newStudentGrade,
                onNameChange = {newName -> viewModel.updateNewStudentName(newName)},
                onGradeChange = {newGrade -> viewModel.updateNewStudentGrade(newGrade)},
                onAddStudent = {viewModel.addStudent(name = viewModel.newStudentName,viewModel.newStudentGrade )}
            )

        }



        // Created a Button to "Load Sample Data" that calls viewModel.loadSampleData()
        item {
            Button(onClick = { viewModel.loadSampleData() },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
             Text( "Load Sample Data")
            }
        }



        // Called StudentsList composable, passing the list of students and the remove student handler from the ViewModel
        item {
            StudentsList(viewModel.students, onRemoveStudent =  {student -> viewModel.removeStudent(student)})
        }


        // Conditionally shows a CircularProgressIndicator if viewModel.isLoading is true
        item {
            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }

    }
}

@Composable
fun GPADisplay(gpa: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Added a Text composable for "Class GPA"
            Text(text = "Class GPA", style = MaterialTheme.typography.titleMedium)



            //Added a Text composable to display the formatted GPA (e.g., "%.2f".format(gpa))
            Text(text = "%.2f".format(gpa), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary )

        }
    }
}

@Composable
fun AddStudentForm(
    name: String,
    grade: String,
    onNameChange: (String) -> Unit,
    onGradeChange: (String) -> Unit,
    onAddStudent: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //  Added a Text composable for "Add New Student"
            Text(text = "Add New Student", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp) )



            // Created an OutlinedTextField for student name input
            OutlinedTextField(value = name, onValueChange = {onNameChange(it)}, label = {Text("Student Name" )}, modifier = Modifier.fillMaxWidth())



            Spacer(modifier = Modifier.height(8.dp))

            //  Created an OutlinedTextField for student grade input
            OutlinedTextField(value =grade,
                onValueChange = {onGradeChange(it)},
                label = {Text("Grade (0-100)")},
                keyboardOptions= KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth())
            // Bind value to 'grade', onValueChange to 'onGradeChange', and set label to "Grade (0-100)"
            // Set keyboardOptions to KeyboardOptions(keyboardType = KeyboardType.Number)
            // Use Modifier.fillMaxWidth()


            Spacer(modifier = Modifier.height(8.dp))

            // Created a Button to "Add Student"
            Button(onAddStudent, enabled = name.isNotBlank() && grade.isNotBlank(), modifier = Modifier.fillMaxWidth(),  ) {Text("Add Student")}
            // Set onClick to 'onAddStudent' and enabled state based on 'name.isNotBlank() && grade.isNotBlank()'
            // Use Modifier.fillMaxWidth()
        }
    }
}

@Composable
fun StudentsList(
    students: List<Student>, //  Changed Any to Student after defining Student data class
    onRemoveStudent: (Student) -> Unit // Changed Any to Student after defining Student data class
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .heightIn(max = 300.dp) // Limit height to prevent overflow
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Added a Text composable for "Students (${students.size})"
            Text(text = "\"Students (${students.size})\"", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp) )
            // Use style MaterialTheme.typography.titleMedium and Modifier.padding(bottom = 8.dp)


            if (students.isEmpty()) {
                // Added a Text composable for "No students added yet" if the list is empty
                Text(text = "No students added yet", style =MaterialTheme.typography.bodyMedium, color =  MaterialTheme.colorScheme.onSurfaceVariant )
                // Use style MaterialTheme.typography.bodyMedium and color MaterialTheme.colorScheme.onSurfaceVariant

            } else {
                // TODO: Create a LazyColumn to display the list of students
                LazyColumn(modifier = Modifier.heightIn(max = 200.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(students){ student ->
                        StudentRow(student = student, onRemove = { onRemoveStudent(student) })
                    }
                }
                // Set modifier to Modifier.heightIn(max = 200.dp) and verticalArrangement to Arrangement.spacedBy(4.dp)
                // Inside items, call StudentRow for each student and a Divider if it's not the last student

            }
        }
    }
}

@Composable
fun StudentRow(
    student: Student, // TODO: Change Any to Student after defining Student data class
    onRemove: (Student) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            // TODO: Add a Text composable to display student.name
            Text(text = student.name)
            // Use style MaterialTheme.typography.bodyLarge


            // TODO: Add a Text composable to display "Grade: ${student.grade}"
            Text(text = "Grade: ${student.grade}" )
            // Use style MaterialTheme.typography.bodyMedium and color MaterialTheme.colorScheme.onSurfaceVariant

        }

        // TODO: Create an IconButton with Icons.Default.Delete for removing a student
        IconButton(onClick = {onRemove(student)}) { Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Student") }
        // Set onClick to 'onRemove'

    }
}

/**
 * Preview function for the StudentGradeManager screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentGradeManagerPreview() {
    MaterialTheme {
        StudentGradeManager()
    }
}