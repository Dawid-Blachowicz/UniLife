package com.blach.unilife.model.mappers

import com.blach.unilife.model.data.todo.TodoTask
import com.blach.unilife.model.data.todo.TodoTaskDTO

object TodoTaskMapper {
    fun fromDTO(dto: TodoTaskDTO, id: String): TodoTask {
        return TodoTask(
            id = id,
            content = dto.content
        )
    }

    fun toDTO(model: TodoTask): TodoTaskDTO {
        return TodoTaskDTO(
            content = model.content
        )
    }
}