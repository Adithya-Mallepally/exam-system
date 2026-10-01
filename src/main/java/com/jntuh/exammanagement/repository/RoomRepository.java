package com.jntuh.exammanagement.repository;

import com.jntuh.exammanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByAvailableTrue();
    List<Room> findByBuilding(String building);
    List<Room> findByCapacityGreaterThanEqual(int capacity);
    boolean existsByName(String name);
}
