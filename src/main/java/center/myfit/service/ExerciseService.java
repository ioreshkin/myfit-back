package center.myfit.service;

import center.myfit.dto.CreateExerciseDto;
import center.myfit.dto.UserWorkoutExerciseDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.User;
import center.myfit.entity.UserWorkoutExercise;
import center.myfit.entity.WorkoutExercise;
import center.myfit.mapper.ExerciseMapper;
import center.myfit.repository.ExerciseRepository;
import center.myfit.repository.UserRepository;
import center.myfit.repository.UserWorkoutExerciseRepository;
import center.myfit.repository.WorkoutExerciseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {
  private final ExerciseRepository exerciseRepository;
  private final UserRepository userRepository;
  private final WorkoutExerciseRepository workoutExerciseRepository;
  private final UserWorkoutExerciseRepository userWorkoutExerciseRepository;
  private final UserAware userAware;
  private final ExerciseMapper exerciseMapper;

  public CreateExerciseDto create(CreateExerciseDto dto) {
    Exercise exercise = exerciseMapper.map(dto);
    exercise.setOwner(userAware.getUser());
    Exercise saved = exerciseRepository.save(exercise);
    return exerciseMapper.map(saved);
  }

  public List<CreateExerciseDto> getAll() {
    User user = userAware.getUser();
    return exerciseRepository.findAllByOwner(user).stream().map(exerciseMapper::map).toList();
  }

  public void saveApproach(UserWorkoutExerciseDto dto) {
    UserWorkoutExercise uwe = new UserWorkoutExercise();
    User user =
        userRepository
            .findById(dto.userId())
            .orElseThrow(
                () -> new RuntimeException("User with id = " + dto.userId() + "not found"));
    WorkoutExercise workoutExercise =
        workoutExerciseRepository
            .findById(dto.workoutExerciseId())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "WorkoutExercise with id = " + dto.workoutExerciseId() + "not found"));
    uwe.setRepeats(dto.repeats());
    uwe.setUser(user);
    uwe.setWorkoutExercise(workoutExercise);
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
