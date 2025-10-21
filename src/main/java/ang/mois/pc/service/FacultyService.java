package ang.mois.pc.service;

import ang.mois.pc.dto.FacultyDto;
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

    public Faculty save(FacultyDto facultyDto) {
        if (facultyDto.name() == null || facultyDto.name().isBlank()){
            throw new IllegalArgumentException("Faculty name cannot be empty");
        }
        if (facultyDto.shortcut() == null || facultyDto.shortcut().isBlank()) {
            throw new IllegalArgumentException("Faculty shortcut cannot be empty");
        }
        Faculty faculty = new Faculty(facultyDto.name(), facultyDto.shortcut());
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }

    public Faculty update(Long id, FacultyDto facultyDto) {
        Faculty faculty = getById(id);
        if (facultyDto.name() != null) faculty.setName(facultyDto.name());
        if (facultyDto.shortcut() != null) faculty.setShortcut(facultyDto.shortcut());
        return facultyRepository.save(faculty);
    }
}