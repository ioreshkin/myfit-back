package center.myfit.repository;

import center.myfit.entity.User;
import center.myfit.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByIdAndOwner(Long id, User owner);
    List<Workout> findAllByOwner(User owner);
}
