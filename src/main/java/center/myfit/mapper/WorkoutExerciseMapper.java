package center.myfit.mapper;

import center.myfit.dto.ExerciseForWorkoutDto;
import center.myfit.entity.Exercise;
import center.myfit.entity.Workout;
import center.myfit.entity.WorkoutExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WorkoutExerciseMapper {
    @Mapping(target = "repeats", source = "dto.repeats")
    @Mapping(target = "sets", source = "dto.sets")
    @Mapping(target = "orderNumber", source = "dto.orderNumber")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorkoutExercise map(ExerciseForWorkoutDto dto, Workout workout, Exercise exercise);

}

//Exercise exercise = exerciseRepository.findById(dto.id())
//        .orElseThrow(() -> new RuntimeException("Exercise with id = " + dto.id() + " not found"));
//
//WorkoutExercise workoutExercise = new WorkoutExercise();
//        workoutExercise.setWorkout(workout);
//        workoutExercise.setExercise(exercise);
//        workoutExercise.setRepeats(dto.repeats());
//        workoutExercise.setSets(dto.sets());
//        workoutExercise.setOrderNumber(dto.orderNumber());
//        return workoutExercise;