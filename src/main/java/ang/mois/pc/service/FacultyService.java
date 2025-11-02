package ang.mois.pc.service;

import ang.mois.pc.dto.request.FacultyRequestDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.mapper.FacultyMapper;
import ang.mois.pc.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }

    public Faculty getById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty with id " + id + " does not exist"));
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty save(FacultyRequestDto facultyRequestDto) {
        Faculty faculty = facultyMapper.toEntity(facultyRequestDto);
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        // Delete proběhne pouze pokud neexistuje ani jedna computerRoom, která by měla FK facultyId. TODO
        facultyRepository.deleteById(id);
    }

    public Faculty update(Long id, FacultyRequestDto facultyRequestDto) {
        Faculty faculty = getById(id);
        // merge entities - basically copy non-null values to existing faculty
        facultyMapper.updateEntityFromDto(facultyRequestDto, faculty);

        return facultyRepository.save(faculty);
    }
}