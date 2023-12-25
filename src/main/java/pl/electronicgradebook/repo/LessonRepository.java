package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}