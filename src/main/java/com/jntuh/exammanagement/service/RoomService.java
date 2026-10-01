package com.jntuh.exammanagement.service;

import com.jntuh.exammanagement.dto.RoomRequest;
import com.jntuh.exammanagement.exception.BadRequestException;
import com.jntuh.exammanagement.exception.ResourceNotFoundException;
import com.jntuh.exammanagement.model.Room;
import com.jntuh.exammanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
    }

    public Room createRoom(RoomRequest request) {
        if (roomRepository.existsByName(request.getName())) {
            throw new BadRequestException("Room name already exists!");
        }

        Room room = new Room();
        room.setName(request.getName());
        room.setBuilding(request.getBuilding());
        room.setFloor(request.getFloor());
        room.setCapacity(request.getCapacity());
        room.setHasProjector(request.isHasProjector());
        room.setAvailable(request.isAvailable());

        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, RoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));

        if (!room.getName().equals(request.getName()) && roomRepository.existsByName(request.getName())) {
            throw new BadRequestException("Room name already exists!");
        }

        room.setName(request.getName());
        room.setBuilding(request.getBuilding());
        room.setFloor(request.getFloor());
        room.setCapacity(request.getCapacity());
        room.setHasProjector(request.isHasProjector());
        room.setAvailable(request.isAvailable());

        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
        roomRepository.delete(room);
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }
}
