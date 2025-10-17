package ang.mois.pc.service;

import ang.mois.pc.entity.Faculty;
import ang.mois.pc.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty with id " + id + " does not exist"));
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty save(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId())) {
            throw new IllegalArgumentException("Faculty with id " + faculty.getId() + " already exists");
        }
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }
}