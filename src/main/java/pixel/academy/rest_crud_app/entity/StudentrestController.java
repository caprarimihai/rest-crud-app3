package pixel.academy.rest_crud_app.entity;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pixel.academy.rest_crud_app.rest.StudentErrorResponse;
import pixel.academy.rest_crud_app.rest.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class StudentrestController {

    // Lista în care păstrăm studenții
    private List<Student> theStudents;

    // Se execută automat la pornirea aplicației
    @PostConstruct
    public void loadData() {

        // Creăm lista
        theStudents = new ArrayList<>();

        // Adăugăm studenții
        theStudents.add(new Student("Munteanu", "Eugen"));
        theStudents.add(new Student("Ojog", "Maria"));
        theStudents.add(new Student("Gonzalez", "Pedro"));
    }

    // Returnează toți studenții
    @GetMapping("/students")
    public List<Student> getStudents() {

        // Returnăm lista
        return theStudents;
    }

    // Returnează un singur student după ID
    @GetMapping("/studentsapi/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        // Verificăm dacă ID-ul este valid
        if (studentId >= theStudents.size() || studentId < 0) {

            // Aruncăm excepția dacă studentul nu există
            throw new StudentNotFoundException(
                    "Student id not found - " + studentId
            );
        }

        // Returnăm studentul
        return theStudents.get(studentId);
    }

    // Gestionează excepția StudentNotFoundException
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(
            StudentNotFoundException ex) {

        // Creăm obiectul pentru răspunsul de eroare
        StudentErrorResponse error = new StudentErrorResponse();

        // Setăm statusul HTTP 404
        error.setStatus(HttpStatus.NOT_FOUND.value());

        // Setăm mesajul
        error.setMessage(ex.getMessage());

        // Setăm timpul
        error.setTimeStamp(System.currentTimeMillis());

        // Returnăm răspunsul
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Gestionează celelalte excepții
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception ex) {

        // Creăm obiectul pentru eroare
        StudentErrorResponse error = new StudentErrorResponse();

        // Setăm statusul HTTP 400
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        // Setăm mesajul excepției
        error.setMessage(ex.getMessage());

        // Setăm timpul
        error.setTimeStamp(System.currentTimeMillis());

        // Returnăm răspunsul
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
