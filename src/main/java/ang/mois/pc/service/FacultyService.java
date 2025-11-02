package ang.mois.pc.service;

import ang.mois.pc.dto.request.FacultyRequestDto;
import ang.mois.pc.dto.response.FacultyResponseDto;
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

    public FacultyResponseDto getById(Long id) {
       Faculty faculty = getFaculty(id);
       return facultyMapper.toResponseDto(faculty);
    }

    public List<FacultyResponseDto> getAll() {
        return facultyMapper.toResponseDtoList(facultyRepository.findAll());
    }

    public FacultyResponseDto save(FacultyRequestDto facultyRequestDto) {
        Faculty faculty = facultyMapper.toEntity(facultyRequestDto);
        return facultyMapper.toResponseDto(facultyRepository.save(faculty));
    }

    public void delete(Long id) {
        // Delete proběhne pouze pokud neexistuje ani jedna computerRoom, která by měla FK facultyId. TODO
        facultyRepository.deleteById(id);
    }

    public FacultyResponseDto update(Long id, FacultyRequestDto facultyRequestDto) {
        Faculty faculty = getFaculty(id);

        // merge entities - basically copy non-null values to existing faculty
        facultyMapper.updateEntityFromDto(facultyRequestDto, faculty);

        return facultyMapper.toResponseDto(facultyRepository.save(faculty));
    }

    private Faculty getFaculty(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty with id " + id + " does not exist"));
    }
}