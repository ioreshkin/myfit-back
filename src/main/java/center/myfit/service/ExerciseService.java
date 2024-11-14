package center.myfit.service;

import center.myfit.dto.ExerciseDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final UserAware userAware;

    public ExerciseDto create(ExerciseDto dto) {
        Exercise exercise = map(dto);
        exercise.setOwner(userAware.getUser());
        Exercise saved = exerciseRepository.save(exercise);
        return map(saved);
    }

    public List<ExerciseDto> getAll() {
        User user = userAware.getUser();
        return exerciseRepository.findAllByOwner(user).stream().map(this::map).toList();

    }

    private Exercise map(ExerciseDto dto) {
        Exercise exercise = new Exercise();
        exercise.setTitle(dto.title());
        exercise.setDescription(dto.description());
        exercise.setPictureUrl(dto.pictureUrl());
        exercise.setVideoUrl(dto.videoUrl());
//        {{
//            setTitle(dto.title());
//            setDescription(dto.description());
//            setPictureUrl(dto.pictureUrl());
//            setVideoUrl(dto.videoUrl());
//        }};
        return exercise;
    }

    private ExerciseDto map(Exercise exercise) {
        return new ExerciseDto(exercise.getId(), exercise.getTitle(), exercise.getDescription(),
                exercise.getPictureUrl(), exercise.getVideoUrl());
    }
}
