package center.myfit.service;

import center.myfit.dto.ExerciseDto;
import center.myfit.dto.UserWorkoutExerciseDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.entity.UserWorkoutExercise;
import center.myfit.mapper.ExerciseMapper;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.UserWorkoutExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final UserWorkoutExerciseRepository userWorkoutExerciseRepository;
    private final UserAware userAware;
    private final ExerciseMapper exerciseMapper;

    public ExerciseDto create(ExerciseDto dto) {
        Exercise exercise = exerciseMapper.map(dto);
        exercise.setOwner(userAware.getUser());
        Exercise saved = exerciseRepository.save(exercise);
        return exerciseMapper.map(saved);
    }

    public List<ExerciseDto> getAll() {
        User user = userAware.getUser();
        return exerciseRepository.findAllByOwner(user).stream().map(exerciseMapper::map).toList();
    }

    public void saveApproach(UserWorkoutExerciseDto dto) {
        UserWorkoutExercise uwe = new UserWorkoutExercise();
        uwe.setRepeats(dto.repeats());
        uwe.setUser(dto.user());
        uwe.setWorkoutExercise(dto.workoutExercise());
        userWorkoutExerciseRepository.save(uwe);
    }

//    private Exercise map(ExerciseDto dto) {
//        Exercise exercise = new Exercise();
//        exercise.setTitle(dto.title());
//        exercise.setDescription(dto.description());
//        exercise.setPictureUrl(dto.pictureUrl());
//        exercise.setVideoUrl(dto.videoUrl());
//        return exercise;
//    }

//    private ExerciseDto map(Exercise exercise) {
//        return new ExerciseDto(exercise.getId(), exercise.getTitle(), exercise.getDescription(),
//                exercise.getPictureUrl(), exercise.getVideoUrl());
//    }
}
