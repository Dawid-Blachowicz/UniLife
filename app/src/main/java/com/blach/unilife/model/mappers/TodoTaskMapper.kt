package com.blach.unilife.model.mappers

import com.blach.unilife.model.data.todo.TodoTask
import com.blach.unilife.model.data.todo.TodoTaskDTO
import java.time.LocalDate

object TodoTaskMapper {
    fun fromDTO(dto: TodoTaskDTO, id: String): TodoTask {
        return TodoTask(
            id = id,
            content = dto.content,
            isChecked = dto.checked,
            creationDate = LocalDate.parse(dto.creationDate)
        )
    }

    fun toDTO(model: TodoTask): TodoTaskDTO {
        return TodoTaskDTO(
            content = model.content,
            checked = model.isChecked,
            creationDate = model.creationDate.toString()
        )
    }

}