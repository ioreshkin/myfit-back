package center.myfit.service;

import center.myfit.dto.ExerciseDto;
import center.myfit.entity.Exercise;
import center.myfit.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final UserAware userAware;

    public ExerciseDto createExercise(ExerciseDto dto) {
        Exercise exercise = map(dto);
        exercise.setOwner(userAware.getUser());
        Exercise saved = exerciseRepository.save(exercise);
        return map(saved);
    }

    private Exercise map(ExerciseDto dto) {
        return new Exercise() {{
            setTitle(dto.title());
            setDescription(dto.description());
            setPictureUrl(dto.pictureUrl());
            setVideoUrl(dto.videoUrl());
        }};
    }

    private ExerciseDto map(Exercise exercise) {
        return new ExerciseDto(exercise.getId(), exercise.getTitle(), exercise.getDescription(),
                exercise.getPictureUrl(), exercise.getVideoUrl());
    }
}
