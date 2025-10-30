package ang.mois.pc.service;

import ang.mois.pc.dto.FacultyDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.repository.FacultyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final ModelMapper modelMapper;

    public FacultyService(FacultyRepository facultyRepository, ModelMapper modelMapper) {
        this.facultyRepository = facultyRepository;
        this.modelMapper = modelMapper;
    }

    public Faculty getById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty with id " + id + " does not exist"));
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty save(FacultyDto facultyDto) {
        Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        // Delete proběhne pouze pokud neexistuje ani jedna computerRoom, která by měla FK facultyId. TODO
        facultyRepository.deleteById(id);
    }

    public Faculty update(Long id, FacultyDto facultyDto) {
        Faculty faculty = getById(id);
        // merge entities - basically copy non-null values to existing faculty
        modelMapper.map(facultyDto, faculty);

        return facultyRepository.save(faculty);
    }
}